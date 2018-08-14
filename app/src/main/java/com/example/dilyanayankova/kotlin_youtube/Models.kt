package com.example.dilyanayankova.kotlin_youtube

//model object on which the json will be converted
class HomeFeed( val videos: List<Video>)

class Video(val id :Int, val name: String, val link: String, val imageUrl: String, val numberOfViews: Int, val channel: Channel)

class Channel(val name: String,val profileImageUrl: String)

class CourseLesson(val name: String ,val duration: String , val number: Int, val imageUrl: String, val link: String)