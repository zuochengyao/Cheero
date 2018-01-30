//
// Created by Zuo Chengyao on 2018/1/30.
//

#include "com_zuochengyao_sdk_engine_ZcyNative.h"

JNIEXPORT jstring JNICALL Java_com_zuochengyao_sdk_engine_ZcyNative_helloWorld(JNIEnv * env, jobject obj)
{
    return (*env) -> NewStringUTF(env, "This just a test for Android Studio NDK JNI developer!");
}