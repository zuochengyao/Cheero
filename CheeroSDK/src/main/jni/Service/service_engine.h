//
// Created by 左程耀 on 2018/7/4.
//

#ifndef CHEERO_SERVICE_ENGINE_H
#define CHEERO_SERVICE_ENGINE_H

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
#if defined(ANDROID)
CHEERO_DLL_IMPORT void service_trace(const char *tag, const char *log, int prio);
#else
CHEERO_DLL_IMPORT void service_trace(const char *log);
#endif

#ifdef __cplusplus
}
#endif

#endif //CHEERO_SERVICE_ENGINE_H
