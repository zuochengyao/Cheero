//
// Created by Zuo Chengyao on 2018/2/7.
//

#include "../inc/trace.h"
#include "serviceEngine.h"

void service_trace_mode(int mode)
{
    traceMode(mode);
}

void service_sizeof_data_type()
{
    TRACE(__FILE__, __LINE__, "int存储大小： %lu \n", sizeof(int));
}


