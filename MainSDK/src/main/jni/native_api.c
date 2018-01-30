//
// Created by Zuo Chengyao on 2018/1/30.
//
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include "com_zuochengyao_sdk_engine_ZcyNative.h"

#define TAG "zcy_jni"
#define LOG_V(...)  __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_zuochengyao_sdk_engine_ZcyNative_helloWorld(JNIEnv *env, jclass obj)
{
    return (*env)->NewStringUTF(env, "This just a test for Android Studio NDK JNI developer!");
}

JNIEXPORT void JNICALL Java_com_zuochengyao_sdk_engine_ZcyNative_updateFileContent(JNIEnv *env, jclass obj, jstring filePath)
{
    const char *file_path = (*env)->GetStringUTFChars(env, filePath, NULL);
    if (file_path != NULL) {
        FILE *file = fopen(file_path, "a+");
        if (file != NULL) {
            char data[] = "I am a boy";
            int count = fwrite(data, strlen(data), 1, file);
            if (count > 0)
            {
                LOG_V("write sucess");
            }
            fclose(file);
        }
    }

}