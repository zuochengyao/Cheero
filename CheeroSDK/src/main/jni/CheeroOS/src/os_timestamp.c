/**
 * @author  左程耀
 * @date    2018/11/12
 * @description     时间戳处理相关
 */

#include "os_porting.h"

#define STD_TIMESTAMP 2147483647

int timestamp_second()
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (int) tv.tv_sec;
}

long timestamp_milli_second()
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    long ts = tv.tv_sec * 1000 + tv.tv_usec / 1000;
    return ts < 0 ? (ts += STD_TIMESTAMP) : ts;
}

long timestamp_micro_second()
{
    long ts = get_system_time();
    return ts < 0 ? (ts += STD_TIMESTAMP) : ts;
}

long get_system_time()
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec * 1000 * 1000 + tv.tv_usec;
}

char* time_string(int timeGap)
{
    struct timeval tv;
    char sTime[64 + 1] = {0};
    gettimeofday(&tv, NULL);
    {
        time_t now = time(NULL);
        tm_t *timeNow;
        if (now > 0)
        {
            timeNow = (tm_t *)  localtime((const time_t *) &now);
            if (timeNow)
                snprintf(sTime, 64, "%04d-%02d-%02d %02d:%02d:%02d", 1900 + timeNow->tm_year, timeNow->tm_mon + 1, timeNow->tm_mday, timeNow->tm_hour, timeNow->tm_min, timeNow->tm_sec);
        }
    }
    return strdup(sTime);
}