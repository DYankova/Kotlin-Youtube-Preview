package com.example.dilyanayankova.kotlin_youtube

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson()
    }

    fun fetchJson(){
    //val = constant
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val client = OkHttpClient()

        val request = Request.Builder().url(url).build()
        //okhttp library for fetching json
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call?, response: Response?) {
                //when we get the response

                val body = response?.body()?.string()
                //gson turns json into object
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                //set to the view the adapter with parameter the object homefeed

                runOnUiThread { // not to be on the background thread
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }
}
