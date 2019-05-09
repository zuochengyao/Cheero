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
static JavaVM *mJVM = NULL;
static const char *classPathName = JNI_PACKAGE_NAME"/CheeroNative";

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

JNIEXPORT void JNICALL native_SetTraceFilePath(JNIEnv *env, jobject obj, jstring filePath)
{
    const char *_filePath = env->GetStringUTFChars(filePath, NULL);
    service_set_trace_filepath(_filePath);
    env->ReleaseStringUTFChars(filePath, _filePath);
}

// region NDK Practice

JNIEXPORT void JNICALL native_HelloWorld(JNIEnv *, jobject)
{
    LOGI("hello world");
}

JNIEXPORT void JNICALL native_CallJavaMethod(JNIEnv *env, jobject, jobject obj)
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

JNIEXPORT void JNICALL native_CallJavaNonVirtualMethod(JNIEnv *env, jobject, jobject obj)
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

JNIEXPORT void JNICALL native_GetSystemDateTime(JNIEnv *env)
{
    jclass jDate = env->FindClass("java/util/Date");
    jmethodID jConstructor = env->GetMethodID(jDate, "<init>", "()V");
    jobject jDateObj = env->NewObject(jDate, jConstructor);
    jmethodID jGetTime = env->GetMethodID(jDate, "getTime", "()J");
    jlong time = env->CallLongMethod(jDateObj, jGetTime);
    LOGI("time now is: %ld", time);
}

JNIEXPORT void JNICALL native_CppString(JNIEnv *env, jobject, jobject obj)
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

JNIEXPORT void JNICALL native_CppArray(JNIEnv *env, jobject, jobject obj)
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

JNIEXPORT void JNICALL native_iop(JNIEnv *env, jobject)
{
    const char *APP_SIGN = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3138303630363033303733355a170d3438303532393033303733355a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100a3d20c599caf49432b6dabf291e1571ccb443394b8c65744737492a5dc5473e8d87d8b11ab4746bcdf2d535710c56814e6f81b140351b01cf98c61bcaf72946e0f1c5c4e63c443b3ad8323cd2ba622ee448d520e1f3eaeeece4051e40e5b1d13d09dabd8b3989566e13ef0dd03693342c6775e1ec84414023f2980f5f805913f0203010001300d06092a864886f70d0101050500038181001f56d2af107a246ca5992b4d7b59a2e6109fe6a1da2657b9c03a283be155273516d341bc44c359c10c3e80a6ebbf679f4c91fd60b797cd6b5f55fda15bbc8fff1abaf8b1a2b593b933f89f4654db8d7b013a5ecc982d4928b23998de094d1c0c5da811b74216ed7a40fa81a76c13d9d900bfc5b64fb157aa8ad5b8e023ed4183";
    char *className = "com/icheero/sdk/util/Common";
    jclass common = env->FindClass(className);
    int flag = 0;
    if (common == NULL)
    {
        LOGE("Class not found: %s", className);
        flag = 1;
    }
    jmethodID getSignature = env->GetStaticMethodID(common, "getSignature", "()Ljava/lang/String;");
    if (getSignature == NULL)
    {
        LOGE("Method not found");
        flag = 1;
    }
    jstring sign = (jstring) env->CallStaticObjectMethod(common, getSignature);
    if (sign == NULL)
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