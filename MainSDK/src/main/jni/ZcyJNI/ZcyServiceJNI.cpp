//
// Created by Zuo Chengyao on 2018/1/31.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <android/log.h>
#include <assert.h>

#define JNI_PACKAGE_NAME "com/zuochengyao/sdk/"
#define TAG "ZCY_JNI"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// jvm obj
static JavaVM *mJVM = NULL;

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL zcy_native_helloWorld(JNIEnv *env, jclass obj) {
    return env->NewStringUTF((char *) "This just a test for Android Studio NDK JNI developer!");
}

JNIEXPORT void JNICALL zcy_native_updateFileContent(JNIEnv *env, jclass obj, jstring filePath) {
    // const char *file_path = (*env)->GetStringUTFChars(env, filePath, NULL);
    const char *file_path = env->GetStringUTFChars(filePath, NULL);
    if (file_path != NULL) {
        FILE *file = fopen(file_path, "a+");
        if (file != NULL) {
            char data[] = "I am a boy";
            int count = fwrite(data, strlen(data), 1, file);
            if (count > 0)
                LOGI("write sucess");
            fclose(file);
        }
    }
}

static const char *classPathName = JNI_PACKAGE_NAME"engine/ZcyNative";

static JNINativeMethod methods[] = {
        {"helloWorld",        "()Ljava/lang/String",   (void *) zcy_native_helloWorld},
        {"updateFileContent", "(Ljava/lang/String;)V", (void *) zcy_native_updateFileContent}
};

jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    LOGI("OnLoad");
    mJVM = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: GetEnv failed");
        return JNI_ERR;
    }
    assert(env != NULL);

    jclass clazz = env->FindClass(classPathName);
    if (clazz == NULL) {
        LOGE("ERROR: Class not found");
        return JNI_FALSE;
    }

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) < 0)
    {
        LOGE("ERROR: RegisterNatives failed");
        return JNI_FALSE;
    }

    return JNI_OK;
}

#ifdef __cplusplus
}
#endif
