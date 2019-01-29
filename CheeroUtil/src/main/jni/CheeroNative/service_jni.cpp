//
// Created by 左程耀 on 2018/7/4.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <android/log.h>
#include "service_engine.h"

#define JNI_PACKAGE_NAME "com/icheero/util"
#define TAG "Cheero"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// jvm obj
static JavaVM *mJVM = NULL;
static const char *classPathName = JNI_PACKAGE_NAME"/Log";

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL native_SetTraceMode(JNIEnv *, jobject, jint traceMode)
{
    service_set_trace_mode(traceMode);
}

JNIEXPORT void JNICALL native_Trace(JNIEnv *env, jobject, jstring tag, jstring log, jint prio)
{
    const char *_tag = env->GetStringUTFChars(tag, NULL);
    const char *_log = env->GetStringUTFChars(log, NULL);
    service_trace(_tag, prio, _log);
    env->ReleaseStringUTFChars(tag, _tag);
    env->ReleaseStringUTFChars(log, _log);
}

static JNINativeMethod methods[] = {
    {"nativeSetTraceMode", "(I)V", (void *) native_SetTraceMode},
    {"nativeTrace", "(Ljava/lang/String;Ljava/lang/String;I)V", (void *) native_Trace}
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

jint JNICALL JNI_OnLoad(JavaVM *vm, void *)
{
    JNIEnv *env = NULL;
    LOGI("JNI OnLoading!");
    mJVM = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK)
    {
        return JNI_EVERSION;
    }
    registerNativeMethods(env);
    return JNI_VERSION_1_4;
}

#ifdef __cplusplus
};
#endif