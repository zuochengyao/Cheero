//
// Created by 左程耀 on 2018/7/4.
//

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <iostream>
#include <algorithm>
#include "service_engine.h"
using namespace std;

#define JNI_PACKAGE_NAME "com/icheero/sdk/base"
#define TAG "CheeroNDK"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

// jvm obj
static JavaVM *mJVM = nullptr;
static const char *classPathName = JNI_PACKAGE_NAME"/CheeroNative";

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL native_SetTraceMode(JNIEnv *, jclass, jint traceMode)
{
    service_set_trace_mode(traceMode);
}

JNIEXPORT void JNICALL native_Trace(JNIEnv *env, jclass, jstring tag, jstring log, jint prio)
{
    const char *_tag = env->GetStringUTFChars(tag, NULL);
    const char *_log = env->GetStringUTFChars(log, NULL);
    service_trace(_tag, prio, _log);
    env->ReleaseStringUTFChars(tag, _tag);
    env->ReleaseStringUTFChars(log, _log);
}

JNIEXPORT void JNICALL native_SetTraceFilePath(JNIEnv *env, jclass, jstring filePath)
{
    const char *_filePath = env->GetStringUTFChars(filePath, NULL);
    service_set_trace_filepath(_filePath);
    env->ReleaseStringUTFChars(filePath, _filePath);
}

// region NDK Practice

JNIEXPORT void JNICALL native_HelloWorld(JNIEnv *, jclass)
{
    LOGI("hello world");
}

JNIEXPORT void JNICALL native_CallJavaMethod(JNIEnv *env, jclass, jobject obj)
{
    jclass jClass = env->GetObjectClass(obj);
    jfieldID jNumber = env->GetFieldID(jClass, "number", "I");
    jint number = env->GetIntField(obj, jNumber);
    LOGI("number:%d", number);
    env->SetIntField(obj, jNumber, 100L);
    jmethodID jGetNumber = env->GetMethodID(jClass, "getNumber", "()I");
    number = env->CallIntMethod(obj, jGetNumber);
    LOGI("getNumber:%d", number);
    jmethodID jSummation = env->GetMethodID(jClass, "summation", "(FF)F");
    jvalue *args = new jvalue[2];
    args[0].f = 2.1f;
    args[1].f = 3.1f;
    jfloat numberF = env->CallFloatMethodA(obj, jSummation, args);
    LOGI("summation:%f", numberF);
}

JNIEXPORT void JNICALL native_CallJavaNonVirtualMethod(JNIEnv *env, jclass, jobject obj)
{
    // 获取obj中对象的class对象
    jclass jClass = env->GetObjectClass(obj);
    // 获取java中father字段的id
    jfieldID jFatherID = env->GetFieldID(jClass, "father", "Lcom/icheero/app/activity/reverse/JniActivity$Father;");
    // 获取father字段的对象类型
    jobject jFather = env->GetObjectField(obj, jFatherID);
    // 获取father对象的class对象
    jclass jFatherClass = env->FindClass("com/icheero/app/activity/reverse/JniActivity$Father");
    // 获取father对象中的function方法id
    jmethodID jFatherFunction = env->GetMethodID(jFatherClass, "function", "()V");
    // 调用父类方法：会执行子类的方法
    env->CallVoidMethod(jFather, jFatherFunction);
    // 调用父类方法：会执行父类的方法
    env->CallNonvirtualVoidMethod(jFather, jFatherClass, jFatherFunction);
}

JNIEXPORT void JNICALL native_GetSystemDateTime(JNIEnv *env, jclass)
{
    jclass jDate = env->FindClass("java/util/Date");
    jmethodID jConstructor = env->GetMethodID(jDate, "<init>", "()V");
    jobject jDateObj = env->NewObject(jDate, jConstructor);
    jmethodID jGetTime = env->GetMethodID(jDate, "getTime", "()J");
    jlong time = env->CallLongMethod(jDateObj, jGetTime);
    LOGI("time now is: %ld", time);
}

JNIEXPORT void JNICALL native_CppString(JNIEnv *env, jclass, jobject obj)
{
    jfieldID jMsg = env->GetFieldID(env->GetObjectClass(obj), "msg", "Ljava/lang/String;");
    // 获取属性msg的对象
    jstring msg = (jstring) env->GetObjectField(obj, jMsg);

    // region 第一种方式
    // 获得字符串指针
    const jchar *jStr = env->GetStringChars(msg, NULL);
    // 转换成宽字符串
    wstring wStr((const wchar_t *) jStr);
    // 释放指针
    env->ReleaseStringChars(msg, jStr);
    // endregion

    // region 第二种方式
    const jchar *jStr2 = env->GetStringCritical(msg, NULL);
    wstring wStr2((const wchar_t *) jStr2);
    env->ReleaseStringCritical(msg, jStr2);
    // endregion

    // region 第三种方式
    jsize len = env->GetStringLength(msg);
    jchar *jStr3 = new jchar[len + 1];
    jStr3[len] = L'\0';
    env->GetStringRegion(msg, 0, len, jStr3);
    wstring wStr3((const wchar_t *) jStr3);
    delete[] jStr3;
    // endregion
    reverse(wStr.rbegin(), wStr.rend());
    jstring newStr = env->NewString((const jchar *)wStr.c_str(), (jint)wStr.size());
    env->SetObjectField(obj, jMsg, newStr);
}

JNIEXPORT void JNICALL native_CppArray(JNIEnv *env, jclass, jobject obj)
{
    // Java中的intArrays
    jfieldID jIntArrays = env->GetFieldID(env->GetObjectClass(obj), "intArrays", "[I");
    // Java中的intArrays的对象
    jintArray intArrays = (jintArray) env->GetObjectField(obj, jIntArrays);
    jint *intArraysPtr = env->GetIntArrayElements(intArrays, NULL);
    jsize len = env->GetArrayLength(intArrays);
    string str1 = "数组的值为：";
    for (int i = 0; i < len; ++i)
    {
        str1 += intArraysPtr[i];
        if (i != len - 1)
            str1 += ", ";
    }
    LOGI("%s", str1.c_str());

    // 新建一个 jintArray对象
    jintArray jIntArraysTmp = env->NewIntArray(len);
    jint *intArrayTmpPtr = env->GetIntArrayElements(jIntArraysTmp, NULL);
    // 计数
    jint count = 0;
    //
}

JNIEXPORT void JNICALL native_iop(JNIEnv *env, jclass)
{
    const char *APP_SIGN = "308203753082025da003020102020401c8b74b300d06092a864886f70d01010b0500306b310b300906035504061302434e3110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a13074943686565726f3110300e060355040b13074943686565726f311430120603550403130b5a756f4368656e6779616f301e170d3138313130373037323933325a170d3433313130313037323933325a306b310b300906035504061302434e3110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a13074943686565726f3110300e060355040b13074943686565726f311430120603550403130b5a756f4368656e6779616f30820122300d06092a864886f70d01010105000382010f003082010a0282010100be309d5bbceb70d9f75595026767c5550017d4313e9b91e9ffc285dd3d88371c7aa0c9dcc0f95da2e08129609aaf0413ee160e3640c48d6c36057d4b251c1087f273136c7bcfc9b6f50cbb4e7b5e8f53576eafce94143b819d377702a062dc73761759dd29ecd2c4a8fcac34a7f31cb5d3f5c22ddab158a2c134373f316c32e6192e4424344d3ee6c57969ab777557e57c595704e4e9bb0ee7eba8476279ceab7a3111c459f671798f0a74ccd60876c66d72194dee98079da4466b9f3b85f65cc4fcecefc6899f6bfff4466f7d5e9b2547b27b833bb3d41f5b24fe5777f517fc27a94333e0bc09d08e64ab3ec7b3c8d8244767ded25b010057c7d5d8a9a3fbcf0203010001a321301f301d0603551d0e041604141f1cd1a9ec5cefd54f341b246423da719ea24724300d06092a864886f70d01010b050003820101000f987893fc1c721844fdb2b6f79f480988f0575afdf0ea2b4b38c6c49a4a9db128d0834729d71848612558cfa1b22a01fac4eaec5149d2fe9fdba0c6e5a370af23e85befc6538b36040b320e3134dd385ae425e76fb9db9d5b02f0f7f78beef706aed9fa6a9d12fc257820c69c0fccc6233c8f8b83b17724751b7aa83d598d12891ff13cf8346ffe94dc81dccf0aa776c70ee811fdee3017fb27ea9bf8e30779b694ddede55e39e94123bb4ce7a9f8c88347df2ef14380f8efe21dbd2e1bc991e1dfe27b445c4d506ab9372bcb74d8f39569c833ece0fca803f47413ef9a9faa70df7790eec99460eb9fd60e8d18f7b32f1edd2688955295ad98e18bc0565ece";
    const char *className = "com/icheero/sdk/util/Common";
    jclass common = env->FindClass(className);
    int flag = 0;
    LOGE("do native_iop");
    if (common == nullptr)
    {
        LOGE("Class not found: %s", className);
        flag = 1;
    }
    jmethodID getSignature = env->GetStaticMethodID(common, "getSignature", "()Ljava/lang/String;");
    if (getSignature == nullptr)
    {
        LOGE("Method not found");
        flag = 1;
    }
    jstring sign = (jstring) env->CallStaticObjectMethod(common, getSignature);
    if (sign == nullptr)
    {
        LOGE("The sign is NULL");
        flag = 1;
    }
    const char *strAry = env->GetStringUTFChars(sign, NULL);
    int val = strcmp(strAry, APP_SIGN);
    if (val != 0)
    {
        flag = 1;
    }
    env->ReleaseStringUTFChars(sign, strAry);
    if (flag == 1)
    {
        exit(0);
    }
}

// endregion

static JNINativeMethod methods[] = {
    {"nativeHelloWorld", "()V", (void *) native_HelloWorld},
    {"nativeCallJavaMethod", "(Ljava/lang/Object;)V", (void *) native_CallJavaMethod},
    {"nativeCallJavaNonVirtualMethod", "(Ljava/lang/Object;)V", (void *) native_CallJavaNonVirtualMethod},
    {"nativeGetSystemDateTime", "()V", (void *) native_GetSystemDateTime},
    {"nativeCppString", "(Ljava/lang/Object;)V", (void *) native_CppString},
    {"nativeCppArray", "(Ljava/lang/Object;)V", (void *) native_CppArray},
    {"nativeSetTraceMode", "(I)V", (void *) native_SetTraceMode},
    {"nativeTrace", "(Ljava/lang/String;Ljava/lang/String;I)V", (void *) native_Trace},
    {"nativeSetTraceFilePath", "(Ljava/lang/String;)V", (void *) native_SetTraceFilePath},
    {"nativeIsOwnApp", "()V", (void *) native_iop}
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