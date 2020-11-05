package com.example.myandroidplay

enum class DecodeState {

  START,    //开始解码
  DECODING, //解码中
  PAUSE,    //暂停
  SEEKING,  //正在快进
  FINISH,   //完成
  STOP    //停止
}