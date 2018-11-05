//
// Created by 左程耀 on 2018/7/4.
//

#ifndef CHEERO_TRACE_H
#define CHEERO_TRACE_H

#if defined(_WIN32) || defined(_WIN32_WCE)
#define TRACE_FILE_NAME "d:\\CheeroTrace.txt"
#elif defined(__linux) || defined(ANDROID)
#define TRACE_FILE_NAME "/sdcard/Cheero/logs/Traces"
#elif defined(__APPLE__)
#define TRACE_FILE_NAME "/Users/Shared/CheeroTrace"
#elif defined(BREW)
#define TRACE_FILE_NAME "fs:/shared/CheeroTrace"
#endif

#include <stdio.h>

#ifdef __cplusplus
extern "C"
{
#endif

typedef enum
{
    TRACE_ON_SCREEN = 0,
    TRACE_ON_FILE,
    TRACE_OFF
} TRACE_MODE;

void set_trace_mode(int mode);

void TRACE(const char *fi, int level, const char *chfr, ...);

#ifdef __cplusplus
}
#endif

#endif //__TRACE_H__
