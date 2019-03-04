//
// Created by 左程耀 on 2018/7/4.
//

#ifndef CHEERO_TRACE_H
#define CHEERO_TRACE_H

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <android/log.h>
#include "os_porting.h"

#ifdef __cplusplus
extern "C"
{
#endif


#if defined(__linux) || defined(ANDROID)
    #define TRACE_FILE_NAME "/sdcard/Cheero/logs/"
#endif

typedef enum
{
    TRACE_ON_SCREEN = 0,
    TRACE_ON_FILE,
    TRACE_OFF
} TRACE_MODE;

void set_trace_mode(int mode);
void set_trace_filepath(const char *tracePath);
void TRACE(const char *tag, int level, const char *log, ...);

#ifdef __cplusplus
}
#endif

#endif //__TRACE_H__
