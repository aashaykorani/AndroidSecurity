package com.example.giftcardsite.api.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.location.Location
import android.location.LocationListener
import android.util.Log
import com.example.giftcardsite.api.service.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Reporter(private val token: String) : LocationListener{
    override fun onLocationChanged(location: Location) {
        var userInfoContainer = UserInfoContainer(token)
        var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://nyuappsec.com").addConverterFactory(
            GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        var client: UserInfo = retrofit.create(UserInfo::class.java)
        client.postInfo(userInfoContainer, token)?.enqueue(object: Callback<User?> {
            override fun onFailure(call: Call<User?>, t: Throwable) {
                Log.d("Metric Failure", "Metric Failure in onFailure")
                Log.d("Metric Failure", t.message.toString())

            }

            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (!response.isSuccessful) {
                    Log.d("Metric Failure", "Metric failure. Yay.")
                } else {
                    Log.d("Metric Success", "Metric success. Boo.")
                    Log.d("Metric Success", "Token:${userInfoContainer.token}")
                }
            }
        })
    }

//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event != null) {
//            var userInfoContainer = UserInfoContainer(token)
//            var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://nyuappsec.com").addConverterFactory(
//                GsonConverterFactory.create())
//            var retrofit: Retrofit = builder.build()
//            var client: UserInfo = retrofit.create(UserInfo::class.java)
//            client.postInfo(userInfoContainer, token)?.enqueue(object: Callback<User?> {
//                override fun onFailure(call: Call<User?>, t: Throwable) {
//                    Log.d("Metric Failure", "Metric Failure in onFailure")
//                    Log.d("Metric Failure", t.message.toString())
//
//                }
//
//                override fun onResponse(call: Call<User?>, response: Response<User?>) {
//                    if (!response.isSuccessful) {
//                        Log.d("Metric Failure", "Metric failure. Yay.")
//                    } else {
//                        Log.d("Metric Success", "Metric success. Boo.")
//                        Log.d("Metric Success", "Token:${userInfoContainer.token}")
//                    }
//                }
//            })
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        return
//    }
}