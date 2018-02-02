# cmake最小版本
cmake_minimum_required(VERSION 3.4.1)

# 定义工程名称
project(jni)

# 设置生成的so动态库最后输出的路径
# set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}../../../../libs/${ANDROID_ABI})

# 生成so动态库
# add_library(Zcy SHARED ZcyJNI/ZcyServiceJNI.cpp)
add_library(Zcy SHARED com_zcy_sdk_engine_JniNative.h jni.c)

find_library( # Sets the name of the path variable.
              log-lib
              log )


target_link_libraries( # 制定目标库.
                        Zcy
                        ${log-lib} )


# 添加子目录,将会调用子目录中的CMakeLists.txt
# add_subdirectory(src/main/cpp/test_module)

# include_directories(${CMAKE_CURRENT_LIST_DIR}/include)

# 查找当前目录下的所有源文件,并将名称保存到 DIR_SRCS 变量
# aux_source_directory(. DIR_SRCS)


# 指定生成目标
# add_executable(Jni main.cc)

# 添加链接库
# target_link_libraries(Jni Services)


