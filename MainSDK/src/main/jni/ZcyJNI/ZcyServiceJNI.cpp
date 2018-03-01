//
// Created by Zuo Chengyao on 2018/1/31.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <android/log.h>

#include "trace.h"
#include "serviceEngine.h"

#define JNI_PACKAGE_NAME "com/zcy/sdk/"
#define TAG "ZCY_JNI"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// jvm obj
static JavaVM *mJVM = NULL;
static const char *classPathName = JNI_PACKAGE_NAME"ndk/JniNative";

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL zcy_native_serviceSetTraceMode(JNIEnv *, jobject, jint jTraceMode)
{
    TRACE(__FILE__, __LINE__, "zcy_native_serviceSetTraceMode IN\n");
    service_trace_mode(jTraceMode);
}

JNIEXPORT void JNICALL zcy_native_serviceSizeOfDataType(JNIEnv *, jobject)
{
    TRACE(__FILE__, __LINE__, "zcy_native_serviceSizeOfDataType IN\n");
    service_sizeof_data_type();
}

JNIEXPORT void JNICALL zcy_native_serviceTrace(JNIEnv * env, jobject, jstring tag, jstring log, jint prio)
{
    TRACE(__FILE__, __LINE__, "zcy_native_serviceTrace IN\n");
    const char *id = env->GetStringUTFChars(log, NULL);
    const char *_tag = env->GetStringUTFChars(tag, NULL);
    service_trace(_tag, id, prio);
    env->ReleaseStringUTFChars(log, id);
    env->ReleaseStringUTFChars(tag, _tag);
}

static JNINativeMethod methods[] = {
    {"serviceSizeOfDataType", "()V", (void *) zcy_native_serviceSizeOfDataType},
    {"serviceSetTraceMode","(I)V", (void*) zcy_native_serviceSetTraceMode},
    {"serviceTrace","(Ljava/lang/String;Ljava/lang/String;I)V", (void*) zcy_native_serviceTrace}
};

static int registerNativeMethods(JNIEnv *env)
{
    jclass clazz;
    LOGI("Registering %s native\n", classPathName);
    clazz = env->FindClass(classPathName);
    if (clazz == NULL)
    {
        LOGE("ERROR: Class not found");
        return JNI_FALSE;
    }
    if ((env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0]))) < 0)
    {
        LOGE("Register natives failed for '%s'\n", classPathName);
        return JNI_ERR;
    }
    env->DeleteLocalRef(clazz);
    return JNI_OK;
}

jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JNIEnv *env = NULL;
    LOGI("OnLoad");
    mJVM = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK)
    {
        return JNI_EVERSION;
    }
    registerNativeMethods(env);
    return JNI_VERSION_1_4;
}

#ifdef __cplusplus
}
#endif
