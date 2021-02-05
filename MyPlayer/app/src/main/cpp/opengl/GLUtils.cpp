//
// Created by hmh on 2021/1/30.
//

#include "GLUtils.h"
#include <EGL/egl.h>
#include <GLES3/gl3.h>


void GLUtils::CheckGLError(const char *pGLOperation) {
//    if(GLint error = glGetError(); error; error = glGetError()){
//
//    }

}

void GLUtils::DeleteProgram(uint &program) {
    if(program){
        glUseProgram(program);
        glDeleteProgram(program);
        program = 0;
    }
}

uint GLUtils::CreateProgram(const char *pVretexShaderSource, const char *pFragShaderSource,
                            uint &vertexShaderHandle, uint &fragShaderHandle) {

    GLuint program = 0;
    vertexShaderHandle = LoadShader(GL_VERTEX_SHADER, pVretexShaderSource);
    if(!vertexShaderHandle) return program;
    fragShaderHandle = LoadShader(GL_FRAGMENT_SHADER, pVretexShaderSource);
    if(!fragShaderHandle) return program;
    program = glCreateProgram();
    if(program){
        glAttachShader(program, vertexShaderHandle);
        CheckGLError("glAttachShader");
        glAttachShader(program, fragShaderHandle);
        CheckGLError("glAttachShders");
        glLinkProgram(program);
        GLint lintStatus = GL_FALSE;
        glGetProgramiv(program, GL_LINK_STATUS, &lintStatus);
        glDeleteShader(vertexShaderHandle);
        glDeleteShader(fragShaderHandle);
        vertexShaderHandle = 0;
        fragShaderHandle = 0;
        if(lintStatus != GL_TRUE){
            GLint bufLength = 0;
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, &bufLength);
            if(bufLength){
                char *buf = (char *)malloc((size_t)bufLength);
                if(buf){
                    glGetProgramInfoLog(program, bufLength, nullptr, buf);
                    free(buf);
                }
            }
            glDeleteProgram(program);
            program = 0;
        }
    }
    return program;
}

uint GLUtils::LoadShader(GLenum shaderType, const char *pSource) {
    uint shader = 0;
    shader = glCreateShader(shaderType);
    if(shader){
        glShaderSource(shader, 1, &pSource, nullptr);
        glCompileShader(shader);
        GLint compiled = 0;
        glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
        if(!compiled){
            GLint infoLen = 0;
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);
            if(infoLen){
                char * buf = (char *) malloc((size_t) infoLen);
                if(buf){
                    glGetShaderInfoLog(shader, infoLen, nullptr, buf);
                    free(buf);
                }
                glDeleteShader(shader);
                shader = 0;
            }
        }
    }
    return shader;
}
