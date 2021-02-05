//
// Created by hmh on 2021/1/28.
//
#include "jni.h"
#ifndef MYPLAYER_MYGLRENDERCONTEXT_H
#define MYPLAYER_MYGLRENDERCONTEXT_H
#include <GLES3/gl3.h>
#include "stdint.h"

class MyGLRenderContext {

public:
    static MyGLRenderContext * m_pContext;
    MyGLRenderContext * GetInstance();
    ~MyGLRenderContext();
    void SetImageData(int format, int width, int height, uint8_t *pData);
    void OnSurfaceCreated();
    void OnSurfaceChanged(int widht, int height);
    void OnDrawFrame();
    void DestoryInstance();
};


#endif //MYPLAYER_MYGLRENDERCONTEXT_H
