//
// Created by 左程耀 on 2018/7/4.
//

#include "service_engine.h"

void service_set_trace_mode(int mode)
{
    set_trace_mode(mode);
}

void service_set_trace_filepath(const char *path)
{
    set_trace_filepath(path);
}

void service_trace(const char *tag, int prio, const char *log)
{
    TRACE(tag, prio, log);
}