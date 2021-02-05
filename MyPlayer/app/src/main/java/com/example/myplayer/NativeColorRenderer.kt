package com.example.myplayer

import android.R.color
import android.graphics.Color
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class NativeColorRenderer(color: Int) : GLSurfaceView.Renderer {


  private var color: Int = 0

  init {
    System.loadLibrary("native_color")
    this.color = color
  }

  external fun surfaceCreated(color: Int)

  external fun surfaceChanged(width: Int, height: Int)

  external fun onDrawFrame()
  override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    surfaceCreated(color);
  }

  override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    //设置视图窗口
    surfaceChanged(width, height);
  }

  override fun onDrawFrame(gl: GL10?) {
    //把颜色缓冲区设置为我们预设的颜色
    onDrawFrame();
  }

}