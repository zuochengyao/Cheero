//
// Created by Work on 5/10/21.
//

#ifndef CHEERO_UTIL_H
#define CHEERO_UTIL_H

#ifdef __cplusplus
extern "C"
{
#endif

#include <stdio.h>
#include "os_porting.h"

typedef struct at_Request {
    char *header; // 指令 header
    char cmd;    // 指令 1 字节
    char *id;    // 操作 id 2 字节
    char *extra; // 扩展数据
} at_Request;

void test_sscanf(const char *request, int32 len);

#ifdef __cplusplus
}
#endif

#endif //CHEERO_UTIL_H
