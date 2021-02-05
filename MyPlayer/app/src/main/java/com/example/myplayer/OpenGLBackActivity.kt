package com.example.myplayer

import android.graphics.Color
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.annotation.Native

class OpenGLBackActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    var surface = GLSurfaceView(this)
    setContentView(surface)
    surface.setEGLContextClientVersion(3)
    var render = NativeColorRenderer(Color.CYAN)
    surface.setRenderer(render)
  }


}