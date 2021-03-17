package com.example.weather_assignment_01.ui.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.weather_assignment_01.api.ApiTask
import com.example.weather_assignment_01.service.WeatherService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import retrofit2.HttpException

abstract class BaseActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    var mContext = this

    private var isSetBackButtonValid = false

    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    fun getRepository(): WeatherService = ApiTask.getInstance(mContext)

    fun handleError(e: Throwable) {
//        HttpErrorHandler.handle(e)

        val msg = (e as? HttpException)?.response()?.errorBody()?.string()
        Log.e("error in BaseActivity", "msg: ${msg}")

        if (msg != null) {
            val json = JSONObject(msg!!)
            val messageStr = json.getString("message")

//        val msg = (e as? Exception)?.message
            Log.e("error in BaseActivity", "messageStr: ${messageStr}")
            if (msg.isNullOrEmpty())
                return

            Toast.makeText(mContext, messageStr, Toast.LENGTH_SHORT).show()
        }
    }
}