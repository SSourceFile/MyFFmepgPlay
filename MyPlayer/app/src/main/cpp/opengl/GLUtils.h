//
// Created by hmh on 2021/1/30.
//

#ifndef MYPLAYER_GLUTILS_H
#define MYPLAYER_GLUTILS_H
#include <stdlib.h>
#include <GLES3/gl3.h>
class GLUtils {

    enum shaderType{};
public:
    void CheckGLError(const char *pGLOperation);

    void DeleteProgram(uint &program);
    uint CreateProgram(const char *pVretexShaderSource, const char *pFragShaderSource, uint &vertexShaderHandle, uint &fragShaderHandle);
    uint LoadShader(GLenum shaderType, const char *pSource);
};




#endif //MYPLAYER_GLUTILS_H
