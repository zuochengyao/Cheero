//
// Created by 左程耀 on 2018/7/4.
//

#include "trace.h"

FILE *g_trace_file = NULL;
char *g_trace_file_path = NULL;
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

void set_trace_filepath(char *tracePath)
{
    char *path = tracePath ? tracePath : TRACE_FILE_NAME;
    g_trace_file_path = (char *) memMalloc(strlen(path) + 1);
    memset(g_trace_file_path, 0, strlen(path));
    sprintf(g_trace_file_path, "%s", path);
}

void TRACE(const char *tag, int level, const char *log, ...)
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
                prio = ANDROID_LOG_ERROR;
                break;
            case 2:
                prio = ANDROID_LOG_DEBUG;
                break;
            case 3:
                prio = ANDROID_LOG_WARN;
                break;
        }
        va_list ap;
        va_start(ap, log);
        __android_log_vprint(prio, "Cheero", log, ap);
        va_end(ap);
    }
    else if (tMode == TRACE_ON_FILE)
    {
        if (g_trace_file == NULL)
        {
            char filename[128];
            char* time = time_string(0);
            sprintf(filename, "%s%s.%s", TRACE_FILE_NAME, time, "txt");
            g_trace_file = fopen(filename, "a+");
        }
        {
            va_list ap;
            struct timeval tv;
            va_start(ap, log);
            gettimeofday(&tv, NULL);
            if (g_trace_file)
            {
                if (tv.tv_usec < 100000)
                {
                    char uSecond[7] = {0};
                    sprintf(uSecond, "%6d", (int) tv.tv_usec);
                    for (int i = 0; i < 6; ++i)
                    {
                        if (uSecond[i] == ' ')
                            uSecond[i] = '0';
                    }
                    fprintf(g_trace_file, "[%d.%s]<%s: %i> ", (int)tv.tv_sec, uSecond, tag, level);
                }
                else
                    fprintf(g_trace_file, "[%d.%d]<%s: %i> ", (int)tv.tv_sec, (int) tv.tv_usec, tag, level);
                vfprintf(g_trace_file, log, ap);
                fflush(g_trace_file);
            }
            va_end(ap);
        }
    }
}