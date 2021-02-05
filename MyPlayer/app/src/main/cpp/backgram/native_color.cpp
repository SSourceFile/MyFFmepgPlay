//
// Created by hmh on 2021/2/5.
//https://github.com/byhook/opengles4android/blob/master/readme/android%E5%B9%B3%E5%8F%B0%E4%B8%8BOpenGLES3.0%E7%BB%98%E5%88%B6%E7%BA%AF%E8%89%B2%E8%83%8C%E6%99%AF.md
//
#include <jni.h>
#include "native_color.h"

#include <EGL/egl.h>
#include <GLES3/gl3.h>

//动态注册
JNINativeMethod methods[] = {
        {"surfaceCreated", "(I)V",  (void *)surfaceCreated},
        {"surfaceChanged", "(II)V", (void *)surfaceChanged},
        {"onDrawFrame",    "()V",   (void *)onDrawFrame}
};

jint registerNativeMethod(JNIEnv *env){
    jclass  cl = env->FindClass("com/example/myplayer/NativeColorRenderer");
    if(env->RegisterNatives(cl, methods, sizeof(methods[3]) / sizeof(methods[0])) < 0){
        return -1;
    }
    return 0;
}
/**
 * 加载默认回调
 * @param vm
 * @param reserved
 * @return
 */
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    //注册方法
    if (registerNativeMethod(env) != JNI_OK) {
        return -1;
    }
    return JNI_VERSION_1_6;
}
JNIEXPORT void JNICALL surfaceCreated(JNIEnv *env, jobject obj, jint color) {
    //分离RGBA的百分比
    GLfloat redF = ((color >> 16) & 0xFF) * 1.0f / 255;
    GLfloat greenF = ((color >> 8) & 0xFF) * 1.0f / 255;
    GLfloat blueF = (color & 0xFF) * 1.0f / 255;
    GLfloat alphaF = ((color >> 24) & 0xFF) * 1.0f / 255;
    glClearColor(redF, greenF, blueF, alphaF);
}

JNIEXPORT void JNICALL surfaceChanged(JNIEnv *env, jobject obj, jint width, jint height) {
    glViewport(0, 0, width, height);
}

JNIEXPORT void JNICALL onDrawFrame(JNIEnv *env, jobject obj) {
    //把颜色缓冲区设置为我们预设的颜色
    glClear(GL_COLOR_BUFFER_BIT);
}

