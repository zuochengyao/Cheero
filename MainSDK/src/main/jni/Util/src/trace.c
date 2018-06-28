//
// Created by Zuo Chengyao on 2018/2/6.
//

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <android/log.h>

#include "trace.h"

FILE *m_trace_file = NULL;
char *m_trace_file_path = NULL;

#ifdef TRACE_ON
TRACE_MODE tMode = TRACE_ON_SCREEN;
#else
TRACE_MODE tMode = TRACE_OFF;
#endif

void traceMode(int mode)
{
    tMode = mode;
}

FILE *getTraceFile()
{
    return m_trace_file;
}

void setTraceFile(FILE *file)
{
    m_trace_file = file;
}

void setTraceFilePath(char *tracePath)
{
    if (tracePath)
    {
        // malloc分配file path空间
        m_trace_file_path = (char *) malloc(strlen(tracePath) + 1);
        // memset赋值
        memset(m_trace_file_path, 0, strlen(tracePath));
        sprintf(m_trace_file_path, "%s", tracePath);
    }
}

void TRACE(const char *fi, int level, const char *chfr, ...)
{
    if (tMode == TRACE_ON_SCREEN)
    {
#ifdef __APPLE__
        printf("%s", chfr);
#endif

#ifdef ANDROID
        int prio = 0;
        switch (level)
        {
            default:
            case 0:prio = ANDROID_LOG_INFO;
                break;
            case 1:prio = ANDROID_LOG_ERROR;
                break;
            case 2:prio = ANDROID_LOG_DEBUG;
                break;
            case 3:prio = ANDROID_LOG_WARN;
                break;
        }
        va_list ap;
        va_start(ap, chfr);
        __android_log_vprint(prio, "Cheero", chfr, ap);
        va_end(ap);
#endif
    }
    else if (tMode == TRACE_ON_FILE)
    {
        // TODO
    }
}

