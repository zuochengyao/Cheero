//
// Created by Zuo Chengyao on 2018/2/7.
//

#ifndef __SERVICE_ENGINE_H__
#define __SERVICE_ENGINE_H__

#include "trace.h"

#ifdef __cplusplus
extern "C"
{
#endif

#ifdef ZCY_DLL_IMPORT
#else
    #if defined(WIN32) || defined(_WIN32_WCE)
        #define ZCY_DLL_IMPORT __declspec(dllimport)
    #else
        #define ZCY_DLL_IMPORT
    #endif
#endif

ZCY_DLL_IMPORT void service_sizeof_data_type();
ZCY_DLL_IMPORT void service_trace_mode(int mode);


#ifdef __cplusplus
}
#endif // __cplusplus

#endif //__SERVICE_ENGINE_H__
