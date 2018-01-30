LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := zcy
LOCAL_SRC_FILES := native_api.cpp
include $(BUILD_SHARED_LIBARY)

