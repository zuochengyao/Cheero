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

#define at_pocReqHeader "AT+POC="
#define at_pocRespHeader "+POC:"

#define at_pocReqHeaderLen 7
#define at_pocReqIdLen 2
#define at_pocRespHeaderLen 4

typedef struct at_Request
{
    char header[at_pocReqHeaderLen]; // 指令 header
    uint8 cmd;    // 指令 1 字节
    uint16 id;    // 操作 id 2 字节
    uint64 extra; // 扩展数据
} at_Request;

typedef struct at_Response
{
    char header[at_pocRespHeaderLen]; // 指令 header
    uint8 cmd;    // 指令 1 字节
    uint16 id;    // 操作 id 2 字节
    uint8 result; // 结果 1 字节
    uint64 extra; // 扩展数据
} at_Response;

typedef struct at_Notify
{
    char header[at_pocRespHeaderLen]; // 指令 header
    uint8 cmd;    // 指令 1 字节
    uint8 code; // code 1 字节
    char *extra; // 扩展数据
} at_Notify;

void test_sscanf(const char *request, int32 len);

#ifdef __cplusplus
}
#endif

#endif //CHEERO_UTIL_H
