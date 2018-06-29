//
// Created by Zuo Chengyao on 2018/2/7.
//

#include <float.h>
#include "trace.h"
#include "serviceEngine.h"

void service_trace_mode(int mode)
{
    traceMode(mode);
}
#if defined(ANDROID)
void service_trace(const char *tag,const char *log,int prio)
{
    TRACE(tag, prio, (char *)log);
}
#else
void service_trace(const char *log)
{
    if (log != NULL)
    {
        TRACE("ZCY", 0, (char*)log);
    }
}
#endif

void service_sizeof_data_type()
{
    TRACE(__FILE__, __LINE__, "int存储大小： %u \n", sizeof(int));
    TRACE(__FILE__, __LINE__, "float 存储最大字节数 : %lu \n", sizeof(float));
    TRACE(__FILE__, __LINE__, "float 最小值: %e\n", FLT_MIN);
    TRACE(__FILE__, __LINE__, "float 最小值: %e\n", FLT_MAX);
    TRACE(__FILE__, __LINE__, "float 精度值: %d\n", FLT_DIG);
}


