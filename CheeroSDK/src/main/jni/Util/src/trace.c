//
// Created by 左程耀 on 2018/7/4.
//

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <android/log.h>

#include "trace.h"

#ifdef TRACE_ON
    int tMode = TRACE_ON_SCREEN;
#else
    int tMode = TRACE_OFF;
#endif

void set_trace_mode(int mode)
{
    tMode = mode;
    TRACE(__FILE__, __LINE__, "日志模式设置：%u \n", tMode);
}

void TRACE(const char *fi, int level, const char *chfr, ...)
{
    if (tMode == TRACE_ON_SCREEN)
    {
        int prio;
        switch (level)
        {
            default:
            case 0:
                prio = ANDROID_LOG_INFO;
                break;
            case 1:
                prio = ANDROID_LOG_INFO;
                break;
            case 2:
                prio = ANDROID_LOG_INFO;
                break;
            case 3:
                prio = ANDROID_LOG_INFO;
                break;
        }
        va_list ap;
        va_start(ap, chfr);
        __android_log_vprint(prio, fi, chfr, ap);
        va_end(ap);
    }
    else if (tMode == TRACE_ON_FILE)
    {

    }
}