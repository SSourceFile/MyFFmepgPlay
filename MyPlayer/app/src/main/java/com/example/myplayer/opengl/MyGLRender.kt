package com.example.myplayer.opengl

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRender(nativeRender: MyNativeRender?) : GLSurfaceView.Renderer {

  private var nativeRender: MyNativeRender? = null
  init {
    this.nativeRender = nativeRender
  }

  override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    nativeRender?.native_OnSurfaceCreated()
  }

  override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    nativeRender?.native_OnSurfaceChanged(width, height)
  }

  override fun onDrawFrame(gl: GL10?) {
    nativeRender?.native_OnDrawFrame()
  }
}