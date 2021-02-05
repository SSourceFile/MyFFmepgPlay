package com.example.myplayer.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class MyGLSurfaceView : GLSurfaceView {


  companion object{
    val IMAGE_FORMAT_RGBA = 0x01
    val IMAGE_FORMAT_NV21 = 0x02
    val IMAGE_FORMAT_NV12 = 0x03
    val IMAGE_FORMAT_I420 = 0x04
  }

  private var mGLRender: MyGLRender? = null
  private var mNativeRender: MyNativeRender? = null

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  init{
    mNativeRender = MyNativeRender()
    mGLRender = MyGLRender(mNativeRender)

    setRenderer(mGLRender)
    renderMode = RENDERMODE_CONTINUOUSLY
  }

}