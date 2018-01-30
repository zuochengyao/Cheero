//
// Created by Zuo Chengyao on 2018/1/29.
//

#include <com_zuochengyao_sdk_engine_ZcyNative.h>

JNIEXPORT jstring JNICALL Java_com_zuochengyao_sdk_engine_ZcyNative_helloWorld(JNIEnv * env, jobject obj)
{
    printf("%s", "jni, hello world");
}