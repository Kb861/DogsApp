package com.example.dogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://dog.ceo/api/"
    private var dogs = ArrayList<String>()
    private var client: OkHttpClient? = null
    private val gson = Gson()
    private var dogsListAdapter: DogsListAdapter? = null

    fun showDog() {
        try {
            getRandomDogPhoto("breeds/image/random")

        } catch (e: IOException) {
            e.printStackTrace()
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val randomDogResponse = RandomDogResponse()
        randomDogResponse.name = "testDog"
        setAdapter()
        createClient()
        button_showDog.setOnClickListener {
            showDog()

        }
        try {
            getDogsList("breeds/list")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getDogsList(url: String) {
        val request = createRequest(url)

        client!!.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e(e.localizedMessage, e.message)

            }


            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val responseBody = response.body()
                val responseListModel = gson.fromJson(responseBody!!.string(), DogListResponse::class.java)
                if (responseListModel.status == "success") {
                    dogs.addAll(responseListModel?.message!!)
                    runOnUiThread {

                        Glide.with(this@MainActivity)
                            .load(responseListModel.message)
                            .into(imageView_randomDog)

                    }

                }

            }

        })
    }


    private fun setAdapter() {

        val layoutManager = LinearLayoutManager(this)
        recyclerView_dogs.layoutManager = layoutManager
        val listener = object : RecyclerViewClickListener {

            override fun onClick(view: View, position: Int, name: String) {

                var url: String? = "breed/$name/images/random"
                getRandomDogPhoto(url!!)


            }

        }

        dogsListAdapter = DogsListAdapter(dogs, listener)
        recyclerView_dogs.adapter = dogsListAdapter

    }
    private fun createClient() {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()

            .addInterceptor(logging)
            .build()

    }



    private fun createRequest(url: String): Request {

        return Request.Builder()
            .url(BASE_URL + url)
            .build()

    }
    @Throws(IOException::class)

    private fun getRandomDogPhoto(url: String) {

        val request = createRequest(url)

        client!!.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e(e.localizedMessage, e.message)

            }



            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val responseBody = response.body()
                val responseModel = gson.fromJson(responseBody!!.string(), RandomDogResponse::class.java)
                if (responseModel.status == "success") {

                    runOnUiThread {

                        Glide.with(this@MainActivity)

                            .load(responseModel.message)

                            .into(imageView_randomDog)

                    }

                }

            }

        })

    }
}
