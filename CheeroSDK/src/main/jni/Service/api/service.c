//
// Created by 左程耀 on 2018/7/4.
//

#include "trace.h"
#include "service_engine.h"

void service_set_trace_mode(int mode)
{
    set_trace_mode(mode);
}

void service_trace(const char *tag, const char *log, int prio)
{
    TRACE(tag, prio, log);
}