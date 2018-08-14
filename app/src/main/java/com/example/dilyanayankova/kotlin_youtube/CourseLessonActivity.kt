package com.example.dilyanayankova.kotlin_youtube

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)

        val courseLessonLink = intent.getStringExtra(CourseDetailsActivity.CourseDetailsLessonViewHolder.COURSE_LESSON_LINK_KEY)
        webView_course_lesson.loadUrl(courseLessonLink)

        //to be able to open
        // the page
        webView_course_lesson.settings.javaScriptEnabled = true
        webView_course_lesson.settings.loadWithOverviewMode = true
        webView_course_lesson.settings.useWideViewPort  = true
    }

}