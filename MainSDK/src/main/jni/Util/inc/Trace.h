//
// Created:
// by Zuo Chengyao
// on 2018/2/6.
//

#ifndef TRACE_H
#define TRACE_H

#if defined(_WIN32) || defined(_WIN32_WCE)
#define TRACE_FILE_NAME "d:\\ZcyTrace.txt"
#elif defined(__linux) || defined(ANDROID)
#define TRACE_FILE_NAME "/sdcard/AirTalkee/logs/PocTrace"
#elif defined(__APPLE__)
#define TRACE_FILE_NAME "/Users/Shared/PocTrace"
#elif defined(BREW)
#define TRACE_FILE_NAME "fs:/shared/PocTrace"
#endif

#include "stdio.h"

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

#endif //TRACE_H
