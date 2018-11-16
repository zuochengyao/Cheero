//
// Created by 左程耀 on 2018/7/4.
//

#ifndef CHEERO_SERVICE_ENGINE_H
#define CHEERO_SERVICE_ENGINE_H

#include "os_porting.h"
#include "trace.h"

#ifdef __cplusplus
extern "C" {
#endif

#ifdef CHEERO_DLL_IMPORT
#elif defined(WIN32) || defined(_WIN32_WCE)
    #define CHEERO_DLL_IMPORT __declspec(dllimport)
#else
    #define CHEERO_DLL_IMPORT
#endif

CHEERO_DLL_IMPORT void service_set_trace_mode(int mode);
CHEERO_DLL_IMPORT void service_set_trace_filepath(char *path);
CHEERO_DLL_IMPORT void service_trace(const char *tag, int prio, const char *log);

#ifdef __cplusplus
}
#endif

#endif //CHEERO_SERVICE_ENGINE_H
