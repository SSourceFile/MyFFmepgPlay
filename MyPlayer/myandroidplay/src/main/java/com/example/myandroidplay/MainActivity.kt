package com.example.myandroidplay

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.example.myandroidplay.demo.MyService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    startService.setOnClickListener(this)
    stopService.setOnClickListener(this)
    bindService.setOnClickListener(this)
    stopBindService.setOnClickListener(this)
  }

  lateinit var intents: Intent
  lateinit var intentService: Intent
  internal lateinit var myBinder: MyService.IMyBinder
  private lateinit var conn:MyConn
  override fun onClick(v: View?) {
    when (v) {
      startService -> {
        intents = Intent(this@MainActivity, MyService::class.java)
        startService(intents)
      }
      stopService -> {
        stopService(intents)
      }
      bindService -> {
        intentService = Intent(this@MainActivity, MyService::class.java)
        conn = MyConn()
        bindService(intentService, conn!!, Context.BIND_AUTO_CREATE)
      }
      stopBindService -> {
        if(::conn.isInitialized){
          unbindService(conn)
        }
      }
    }
  }

  internal inner class MyConn : ServiceConnection{
    override fun onServiceDisconnected(name: ComponentName?) {

    }
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
      myBinder = (service as MyService.IMyBinder).apply {
        invokeMethodService()
      }
    }

  }


}


