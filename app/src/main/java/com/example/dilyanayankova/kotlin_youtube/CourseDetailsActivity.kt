package com.example.dilyanayankova.kotlin_youtube

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this) //to render
//        recyclerView_main.adapter = CourseDetailsAdapter()
        //CHANGE THE NAV TITLE
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()
    }

    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailsURL = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailsURL).build()
        //enqueue puts it in the background thread, to ui be responsive
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java) //specify an array type
                runOnUiThread() {
                    recyclerView_main.adapter = CourseDetailsAdapter(courseLessons)

                }
            }
        })

    }

    private class CourseDetailsAdapter(val courseLessons: Array<CourseLesson>) : RecyclerView.Adapter<CourseDetailsLessonViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailsLessonViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.course_lesson_row, parent, false)
            return CourseDetailsLessonViewHolder(customView)
        }

        override fun getItemCount(): Int {
            return courseLessons.size
        }

        override fun onBindViewHolder(holder: CourseDetailsLessonViewHolder, position: Int) {
            val courseLesson = courseLessons.get(position)

            holder.customView.lesson_title.text = courseLesson.name
            holder.customView.lesson_duration.text = courseLesson.duration

            val imgView = holder.customView.lesson_thumnail
            Picasso.with(holder.customView.context).load(courseLesson.imageUrl).into(imgView)

            holder?.courseLesson = courseLesson

        }
    }

    //var = can modify it!
    class CourseDetailsLessonViewHolder(val customView: View, var courseLesson: CourseLesson? = null) : RecyclerView.ViewHolder(customView) {

        companion object {
            val COURSE_LESSON_LINK_KEY = "lesson link"
        }
        init {
            customView.setOnClickListener {
                val intent = Intent(customView.context, CourseLessonActivity::class.java)

                intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)
                customView.context.startActivity(intent)
            }
        }
    }
}