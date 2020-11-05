//package com.example.myandroidplay
//
//import android.drm.DrmStore.Playback.STOP
//import android.media.MediaCodec
//import android.media.MediaFormat
//import android.util.Log
//import java.io.File
//import java.lang.Exception
//import java.nio.ByteBuffer
//
//abstract class BaseDecoder : IDecoder {
//
//  private var tag: String? = "++++"
//  //解码器是否在运行
//  private var mIsRunnable = true
//
//  //线程等待锁
//  private val mLock = Object()
//
//  //是否可以进入解码
//  private var mReadyForDecode = false
//
//  //=============================解码相关========================================
//  //音视频解码
//  protected var mCodec: MediaCodec? = null
//
//  //音视频数据读取器
//  protected var mExtractor: IExtractor? = null
//
//  //解码输入缓存区
//  protected var mInputBuffers: Array<ByteBuffer>? = null
//
//  //解码输出缓存区
//  protected var mOutputBuffers: Array<ByteBuffer>? = null
//
//  //解码数据信息
//  protected val mBufferInfo = MediaCodec.BufferInfo()
//
//  protected var mState = DecodeState.STOP
//  private val mStateListener: IDecoderStateListener? = null
//  private val mFilePath: String? = ""
//
//  private var mDuration: Long = 0
//  private var mEndPos: Long = 0
//
//  /**流数据是否结束*/
//  private val mIsEOS = false
//  private val mVideoWidth = 0
//  private val mVideoHeight = 0
//  private var mStartTimeForSync: Long? = 0
////  private val mState: DecodeState? = null
//
//  //是否需要音视频同步渲染
//  private var mSyncRender = true
//  override fun run() {
//    mState = DecodeState.START
//    mStateListener?.decoderPrepare(this)
//    if (!init()) return
//
//    Log.e(tag, "开始解码")
//    try {
//
//      while (mIsRunnable) {
//        if (mState != DecodeState.START && mState != DecodeState.DECODING && mState != DecodeState.SEEKING) {
//          Log.e(tag, "等待进入解码: $mState")
//          waitDecode()
//        }
//
//        ////======音视频同步
//        mStartTimeForSync = System.currentTimeMillis() - getCurrentTimestamprTimeStamp()
//        if (!mIsRunnable || mState == DecodeState.STOP) {
//          mIsRunnable = false
//          break
//        }
//        if(mStartTimeForSync == -1L){
//          mStartTimeForSync = System.currentTimeMillis()
//        }
//        //如果数据没有解码完毕，将数据推入解码器解码
//        if (!mIsEOS) {
//          //将数据压入解码器输入缓冲
//          var mIsEOS = pushBufferToDecoder()
//        }
//
//        var index = pullBufferFromDecoder()
//        if (index!! >= 0) {
//          //======音视频同步
//          if(mSyncRender && mState == DecodeState.DECODING){
//            sleepRender()
//          }
//          //解码渲染
//          render(mOutputBuffers!![index!!], mBufferInfo)
//
//          //将解码数据传出去
//          var frame = Frame()
//          frame.buffer = mOutputBuffers!![index]
//          frame.setBufferInfo(mBufferInfo)
//          mStateListener?.decodeOneFrame(this)
//
//          //释放输出缓冲
//          mCodec!!.releaseOutputBuffer(index, true)
//          if (mState == DecodeState.START) {
//            mState = DecodeState.PAUSE
//          }
//        }
//        if (mBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
//          mState = DecodeState.FINISH
//          mStateListener?.decoderFinish(this)
//        }
//      }
//    }catch (e: Exception){
//
//    }finally {
//      doneDecode()
//
////      release()
//    }
//
//  }
//
//  private fun sleepRender() {
//    //音视频同步渲染
//
//  }
//
//  private fun pullBufferFromDecoder(): Int? {
//    //查询是否有解码完成的数据， index >= 0表示数据有效，并且index为缓冲区索引
//    var index = mCodec?.dequeueOutputBuffer(mBufferInfo, 1000)
//    when(index){
//      MediaCodec.INFO_OUTPUT_FORMAT_CHANGED->{}
//      MediaCodec.INFO_TRY_AGAIN_LATER->{}
//      MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED->{
//        mOutputBuffers = mCodec?.outputBuffers
//      }
//      else->{
//        return index
//      }
//    }
//    return -1
//  }
//
//  abstract fun doneDecode()
//
//  abstract fun render(byteBuffer: ByteBuffer, mBufferInfo: MediaCodec.BufferInfo)
//
//  private fun pushBufferToDecoder(): Boolean {
//    var inputBufferIndex = mCodec?.dequeueInputBuffer(1000)
//    var isEndOfStream = false
//    if (inputBufferIndex!! >= 0) {
//      val inputBuffer = mInputBuffers?.get(inputBufferIndex)
//      val sampleSize = inputBuffer?.let { mExtractor!!.readBuffer(it) }
//      if (sampleSize!! < 0) {
//        //如果数据已经取完，压入数据结束标志，MediaCodec.Buffer_flag_end_of_stream
//        mCodec?.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
//        isEndOfStream = true
//      }
//      else {
//        mCodec?.queueInputBuffer(inputBufferIndex, 0, sampleSize, mExtractor?.getCurrentTimestamp()
//                                                                  ?: 0, 0)
//      }
//    }
//    return isEndOfStream
//  }
//
//  private fun waitDecode() {
//    try {
//      if (mState == DecodeState.PAUSE) {
//        mStateListener?.decoderPause(this)
//      }
//      synchronized(mLock) {
//        mLock.wait()
//      }
//    }
//    catch (e: Exception) {
//
//    }
//  }
//
//  private fun init(): Boolean {
//    //检查参参数是否已完整
//    if (mFilePath.isNullOrEmpty() || File(mFilePath).exists()) {
//      mStateListener?.decoderError(this, "文件路径为空")
//    }
//
//    if (!check()) {
//      return false
//    }
//
//    //初始化数据提取器
//    mExtractor = initExtractor(mFilePath)
//    if (mExtractor == null || mExtractor!!.getFormat() == null) {
//      return false
//    }
//    //初始化参数
//    if (!initParams()) return false
//    //初始化渲染器
//    if (!initRender()) return false
//
//    //初始化解码器
//    if (!initCodec()) return false
//    return false
//  }
//
//  private fun initCodec(): Boolean {
//    try {
//      val type = mExtractor?.getFormat()?.getString(MediaFormat.KEY_MIME)
//      mCodec = MediaCodec.createDecoderByType(type ?: "")
//      if (!configCodec(mCodec, mExtractor?.getFormat())) {
//        waitDecode()
//      }
//      mCodec?.start()
//      mInputBuffers = mCodec?.inputBuffers
//      mOutputBuffers = mCodec?.outputBuffers
//    }
//    catch (e: Exception) {
//      return false
//    }
//    return true
//  }
//
//  abstract fun configCodec(mCodec: MediaCodec?, format: MediaFormat?): Boolean
//
//  abstract fun initRender(): Boolean
//
//  private fun initParams(): Boolean {
//    try {
//      val format = mExtractor?.getFormat()
//      mDuration = format?.getLong(MediaFormat.KEY_DURATION)?.div(1000) ?: 0
//      if (mEndPos == 0L) {
//        mEndPos = mDuration
//      }
//      initSpecParams(mExtractor?.getFormat())
//    }
//    catch (e: Exception) {
//      return false
//    }
//    return true
//  }
//
//  private fun initSpecParams(format: MediaFormat?) {
//
//  }
//
//  abstract fun initExtractor(mFilePath: String?): IExtractor?
//
//}