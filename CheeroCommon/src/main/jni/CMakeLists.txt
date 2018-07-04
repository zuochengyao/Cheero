# cmake最小版本
cmake_minimum_required(VERSION 3.4.1)

# 定义工程名称
project(cheero)

set(_VERSION 1.0.0)

# 设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

# 向当前目录添加头文件
include_directories(./Util/inc)
include_directories(./Service)
#当前cmakelists.txt所在目录的所有.c .cpp源文件
aux_source_directory(. ROOT_LIST)
aux_source_directory(./Service/api SERVICE_SRC_LIST)
aux_source_directory(./Util/src UTIL_SRC_LIST)

#增加其他目录的源文件到集合变量中
list(APPEND ROOT_LIST
    Native/service_jni.cpp
    ${SERVICE_SRC_LIST}
    ${UTIL_SRC_LIST})

# 生成so动态库
add_library( # 设置so文件名称.
             cheero-${_VERSION}
             # 设置这个so文件为共享.
             SHARED
             # 设置 c文件源码位置.
             ${ROOT_LIST})

message ("MSG: CMAKE_CURRENT_LIST_DIR："${CMAKE_CURRENT_LIST_DIR})

# Sets the name of the path variable.
find_library(log-lib log)

# 制定目标库.
target_link_libraries(cheero-${_VERSION} ${log-lib})

# 添加子目录,将会调用子目录中的CMakeLists.txt
# add_subdirectory(src/main/cpp/test_module)


# 指定生成目标
# add_executable(Jni main.cc)

# 添加链接库
# target_link_libraries(Jni Services)


