package com.example.myandroidplay

import android.media.MediaFormat
import android.util.Log
import java.nio.ByteBuffer

interface IExtractor {

  //获取音视频格式参数
  fun getFormat(): MediaFormat?

  //读取音视频数据
  fun readBuffer(byteBuffer: ByteBuffer): Int

  //获取当前帧时间
  fun getCurrentTimestamp(): Long

  //Seek指定位置，并返回实际帧的时间错
  fun seek(pos: Long): Long

  fun setStartPos(pos: Long)

  //停止读写
  fun stop()
}