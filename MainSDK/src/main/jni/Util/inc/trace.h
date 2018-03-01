//
// Created:
// by Zuo Chengyao
// on 2018/2/6.
//

#ifndef __TRACE_H__
#define __TRACE_H__

#if defined(_WIN32) || defined(_WIN32_WCE)
#define TRACE_FILE_NAME "d:\\ZcyTrace.txt"
#elif defined(__linux) || defined(ANDROID)
#define TRACE_FILE_NAME "/sdcard/ZuoChengyao/logs/Traces"
#elif defined(__APPLE__)
#define TRACE_FILE_NAME "/Users/Shared/PocTrace"
#elif defined(BREW)
#define TRACE_FILE_NAME "fs:/shared/PocTrace"
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

void traceMode(int mode);

void TRACE(const char *fi, int level, const char *chfr, ...);

#ifdef __cplusplus
}
#endif

#endif //__TRACE_H__
