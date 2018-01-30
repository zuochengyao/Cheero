# cmake最小版本
cmake_minimum_required(VERSION 3.4.1)

# 定义工程名称
project(jni)

# 将一组源文件 source 生成一个可执行文件
# add_executable(api native_api.c)

# 将一组源文件 source 编译出一个库文件，并保存为so
# add_library(设置so文件名称 设置这个so文件为共享 设置c文件源码位置)
add_library(zcy SHARED src/main/jni/native_api.c)


find_library( # Sets the name of the path variable.
              log-lib
              log )


target_link_libraries( # 制定目标库.
                       zcy
                       ${log-lib} )