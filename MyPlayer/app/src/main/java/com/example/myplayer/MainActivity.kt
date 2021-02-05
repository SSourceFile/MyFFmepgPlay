package com.example.myplayer

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
//  val path = Environment.getExternalStorageDirectory().absolutePath + "/Movies/aaa.mp4"
  val path = Environment.getExternalStorageDirectory().absolutePath + "/DingTalk/test2_1.mp4"
//  val path = Environment.getExternalStorageDirectory().absolutePath + "/netease/cloudmusic/Music/suyan.mp3"

  private var player: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Example of a call to a native method
    sample_text.text = ffmpegInfo()

    initPlayer()
    btn.setOnClickListener {
//      if(player!= null){
//        pause(player!!)
//      }
    var intent = Intent(this, OpenGLBackActivity::class.java)
      startActivity(intent)
    }
    restart.setOnClickListener {
      if(player != null){
        play(player!!)
      }
    }
    time_get.setOnClickListener {
      if(player!= null){
        time.text = curTime(player!!).toString()
      }
    }
//    total_time.setOnClickListener {
      if(player!= null){
        total_time.text = totalTime(player!!).toString()
      }




  }

  private fun initPlayer() {
    if(File(path).exists()) {
      sur_play.holder.addCallback(object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
          Log.e("++++", width.toString()+"、、");


        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {

        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
          if (player == null && holder!= null) {
            player = holder?.surface?.let { createPlayer(path, it) }
            play(player!!)

          }else{
          }
        }

      })
    }else{
      Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onPause() {
    super.onPause()
    if(player!= null){
      pause(player!!)
    }
  }

  override fun onResume() {
    super.onResume()
    if(player != null){
      play(player!!)
    }
    
  }
  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
//  external fun stringFromJNI(): String

  companion object {
    // Used to load the 'native-lib' library on application startup.
    init {
      System.loadLibrary("native-lib")
    }
  }

  //===================编写jni相关接口==========================
  external fun ffmpegInfo():String

  private external fun createPlayer(path:String, surface: Surface):Int
  private external fun play(player:Int)
  private external fun pause(player: Int)
  private external fun curTime(player: Int):Long
  private external fun totalTime(player: Int):Long
}
