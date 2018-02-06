# cmake最小版本
cmake_minimum_required(VERSION 3.4.1)

# 定义工程名称
project(jni)

set(_VERSION 0.1.0)

# 设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

#当前cmakelists.txt所在目录的所有.c .cpp源文件
AUX_SOURCE_DIRECTORY(. SRC_LIST)

#增加其他目录的源文件到集合变量中
list(APPEND SRC_LIST
    ZcyJNI/ZcyServiceJNI.cpp
    Util/src/Trace.c)

# 生成so动态库
add_library( # 设置so文件名称.
             zcy-${_VERSION}
             # 设置这个so文件为共享.
             SHARED
             # 设置 c文件源码位置.
             ${SRC_LIST})

message ("message!!!!!!!!!!!!!!!!!!!"${CMAKE_CURRENT_LIST_DIR})

# Sets the name of the path variable.
find_library(log-lib log)

# 制定目标库.
target_link_libraries(zcy-${_VERSION} ${log-lib})

# 添加子目录,将会调用子目录中的CMakeLists.txt
# add_subdirectory(src/main/cpp/test_module)


# 指定生成目标
# add_executable(Jni main.cc)

# 添加链接库
# target_link_libraries(Jni Services)


