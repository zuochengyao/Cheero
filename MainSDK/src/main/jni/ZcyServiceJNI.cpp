//
// Created by Zuo Chengyao on 2018/1/31.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <android/log.h>

#define JNI_PACKAGE_NAME "com/zcy/sdk/"
#define TAG "ZCY_JNI"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// jvm obj
static JavaVM *mJVM = NULL;
static const char *classPathName = JNI_PACKAGE_NAME"engine/JniNative";

#ifdef __cplusplus
extern "C"
{
#endif

JNIEXPORT void JNICALL zcy_native_helloWorld(JNIEnv, jclass) {
    LOGI("This just a test for Android Studio NDK JNI developer!");
}

JNIEXPORT jint JNICALL zcy_native_add(JNIEnv, jclass, jint a, jint b) {
    return a + b;
}

static JNINativeMethod methods[] = {
        {"helloWorld", "()V", (void *) zcy_native_helloWorld},
        {"add",        "(II)I",                (void *) zcy_native_add}
};

static int registNativeMethods(JNIEnv *env) {
    jclass clazz;
    LOGI("Registering %s native\n", classPathName);
    clazz = env->FindClass(classPathName);
    if (clazz == NULL) {
        LOGE("ERROR: Class not found");
        return JNI_FALSE;
    }
    if ((env -> RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0]))) < 0)
    {
        LOGE("Register natives failed for '%s'\n", classPathName);
        return JNI_ERR;
    }
    env -> DeleteLocalRef(clazz);
    return JNI_OK;
}

jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    LOGI("OnLoad");
    mJVM = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return JNI_EVERSION;
    }
    registNativeMethods(env);
    return JNI_VERSION_1_4;
}

#ifdef __cplusplus
}
#endif
