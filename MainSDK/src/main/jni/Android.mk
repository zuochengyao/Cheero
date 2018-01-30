LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# 指定了生成的动态链接库的名字
LOCAL_MODULE := zcy
#指定了C的源文件叫什么名字
LOCAL_SRC_FILES := native_api.c

# 制定要生成动态链接库
include $(BUILD_SHARED_LIBARY)

