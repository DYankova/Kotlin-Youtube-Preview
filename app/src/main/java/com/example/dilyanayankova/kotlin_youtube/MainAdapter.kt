package com.example.dilyanayankova.kotlin_youtube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*
//homeFeed parameter to be able to get data
class MainAdapter(val homeFeed:HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    //num of items
    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //how do we create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
       //from CustomViewHolder
        val video = homeFeed.videos.get(position) //my model object!!!
        holder?.view?.textView_video_title?.text = video.name
        holder?.view?.textView_channel_name.text = video.channel.name

        val thumnailImage = holder?.view?.imageView_thumnail
        Picasso.with(holder?.view?.context).load(video.imageUrl).into(thumnailImage) //set image

        val channelProfileImgView = holder?.view?.channel_img
        Picasso.with(holder?.view?.context).load(video.channel.profileImageUrl).into(channelProfileImgView)//set image

        holder?.video = video //to refresh videos
    }
}
class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view){

    //some constants
    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }
    init {
        view.setOnClickListener {
       //using intent to start new activity
           val intent = Intent(view.context, CourseDetailsActivity::class.java)

            intent.putExtra( VIDEO_TITLE_KEY,video?.name)
            intent.putExtra( VIDEO_ID_KEY,video?.id)

            view.context.startActivity(intent)
        }
    }

}