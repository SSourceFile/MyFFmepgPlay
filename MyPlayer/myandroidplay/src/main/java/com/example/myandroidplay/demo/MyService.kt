package com.example.myandroidplay.demo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

  private val LogKey: String? = MyService::class.simpleName
  override fun onCreate() {
    super.onCreate()
    Log.e(LogKey, "onCreate --- Thread ID = "+Thread.currentThread().id)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.e(LogKey, "onStartCommand --- Thread ID = "+Thread.currentThread().id)
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onBind(intent: Intent?): IBinder? {
    Log.e(LogKey, "onBind --- Thread ID = "+Thread.currentThread().id)
    return MyBinder();
  }

  override fun onDestroy() {
    Log.e(LogKey, "onDestroy --- Thread ID = "+Thread.currentThread().id)
    super.onDestroy()

  }

  override fun onUnbind(intent: Intent?): Boolean {
    Log.e(LogKey, "onUnbind --- Thread ID = "+Thread.currentThread().id)
    return super.onUnbind(intent)
  }
  internal inner class MyBinder: Binder(), IMyBinder {

    fun bindMethod(name: String){
      Log.e("myBinder", "服务执行${name}")
    }

    override fun invokeMethodService() {
      Log.e("myBinder", "执行的")
    }
  }

  interface IMyBinder{
    fun invokeMethodService()
  }
}