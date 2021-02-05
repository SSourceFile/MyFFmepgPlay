//
// Created by hmh on 2021/1/28.
//

#include "MyGLRenderContext.h"
#include "TriangleSample.h"


MyGLRenderContext *MyGLRenderContext::m_pContext = nullptr;

MyGLRenderContext *MyGLRenderContext::GetInstance() {
 if(m_pContext == nullptr){
     m_pContext = new MyGLRenderContext();
 }
 return m_pContext;
}

//析构函数
MyGLRenderContext::~MyGLRenderContext() {

}

void MyGLRenderContext::SetImageData(int format, int width, int height, uint8_t *pData) {
//    NativeImage nativeImage;
//    nativeImage.format = format;


}

void MyGLRenderContext::OnSurfaceCreated() {

}

void MyGLRenderContext::OnSurfaceChanged(int widht, int height) {

}

void MyGLRenderContext::OnDrawFrame() {

}

void MyGLRenderContext::DestoryInstance() {

    if(m_pContext != nullptr){
        delete m_pContext;
        m_pContext = nullptr;
    }
}
