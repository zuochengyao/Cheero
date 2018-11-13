//
// Created by Work on 2018/11/12.
//

#ifndef CHEERO_TIMER_H
#define CHEERO_TIMER_H

#include "os_porting.h"
#include "trace.h"

#ifdef __cplusplus
extern "C" {
#endif

typedef void (*timer_param_free_cb)(void *paramData);
typedef void (*timer_cb)(const int timerId, const int timerCount, int paramInt1, int paramInt2, void *paramData, timer_param_free_cb paramDataFreeCb);

/**
 * return timerId
 */
int timer_register(const char *name, timer_cb callback, int paramInt1, int paramInt2, void *paramData, timer_param_free_cb paramDataFreeCb, int timeout, bool isLoop, bool wakeup);
void timer_unregister(const int timerId);

#ifdef __cplusplus
}
#endif

#endif //CHEERO_TIMER_H
