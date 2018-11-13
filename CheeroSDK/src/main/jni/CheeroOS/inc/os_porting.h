//
// Created by 左程耀 on 2018/11/12.
//

#ifndef CHEERO_OS_PORTING_H
#define CHEERO_OS_PORTING_H

#ifdef __cplusplus
extern "C"
{
#endif

#include <sys/socket.h>

// region Basic type define
#ifndef uint64
typedef unsigned long long uint64;
#endif
#ifndef int64
typedef long long int64;
#endif

#ifndef uint32
typedef unsigned int uint32;
#endif
#ifndef int32
typedef int int32;
#endif

#ifndef uint16
typedef unsigned short uint16;
#endif
#ifndef int16
typedef short int16;
#endif

#ifndef byte
typedef unsigned char byte;
#endif
#ifndef uint8
typedef unsigned char uint8;
#endif
#ifndef int8
typedef char int8;
#endif

#ifndef __cplusplus
#ifndef bool
typedef int bool;
#endif
#ifndef false
    #define false        0
#endif
#ifndef true
    #define true        1
#endif
#endif

typedef struct _tm_t
{
    int tm_sec; //seconds   0-61
    int tm_min; //minutes   1-59
    int tm_hour; //hours   0-23
    int tm_mday; //day   of   the   month   1-31
    int tm_mon; //months   since   jan   0-11
    int tm_year; //years   from   1900
    int tm_wday; //days   since   Sunday,   0-6
    int tm_yday; //days   since   Jan   1,   0-365
    int tm_isdst; //Daylight   Saving   time   indicator
} tm_t;
// endregion

// region Timestamp functions

/**
 * 获取当前时间戳（秒）
 */
int timestamp_second();

/**
 * 获取当前时间戳（毫秒）
 */
long timestamp_milli_second();

/**
 * 获取当前时间戳（微秒）
 */
long timestamp_micro_second();

/**
 * 获取当前系统时间
 */
long get_system_time();

char *time_string(int timeGap);
// endregion

// region Memory functions

#define memMalloc(p) memoryMalloc(p, __FILE__, __LINE__)
#define memFree(p) memoryFree(p, __FILE__, __LINE__)

void memoryDebug();

void *memoryMalloc(size_t len, char *logFile, int logLine);

void memoryFree(void *p, char *logFile, int logLine);

// endregion

#if defined(ANDROID)

#include <pthread.h>
#ifndef HAVE_P_THREAD
#define HAVE_P_THREAD
#endif
typedef pthread_t os_thread_t;
typedef pthread_mutex_t os_mutex_t;

#include <sys/types.h>
#include <sys/ipc.h>
#include <semaphore.h>
#ifndef HAVE_SEMAPHORE_H
#define HAVE_SEMAPHORE_H
#endif
typedef struct
{
    int semid;
} os_sem_t;

#endif // defined(ANDROID)

#include <stdio.h>

// region Thread, Mutes, Sem functions

/**
 * Structure for referencing a thread
 * @struct os_thread
 */
struct os_thread;

/**
 * Allocate (or initialise if a thread address is given)
 * @param stacksize The stack size of the thread. (20000 is a good value)
 * @param func The method where the thread start.
 * @param arg A pointer on the argument given to the method 'func'.
 */
struct os_thread *os_thread_create(void *(*func)(void *), void *arg);

/**
 * Join a thread.
 * @param thread The thread to join.
 */
int os_thread_join(struct os_thread *thread);

/**
 * Detach a thread.
 * @param thread The thread to join.
 */
void os_thread_detach();

/**
 * Set the priority of a thread. (NOT IMPLEMENTED ON ALL SYSTEMS)
 * @param thread The thread to work on.
 * @param priority The priority value to set.
 */
int os_thread_set_priority(struct os_thread *thread, int priority);

/**
 * Exit from a thread.
 */
void os_thread_exit(void);

/**
 * Structure for referencing a semaphore element.
 * @struct os_sem
 */
struct os_sem;

/**
 * Allocate and Initialise a semaphore.
 * @param value The initial value for the semaphore.
 */
struct os_sem *os_sem_init(unsigned int value);

/**
 * Destroy a semaphore.
 * @param sem The semaphore to destroy.
 */
int os_sem_destroy(struct os_sem *sem);

/**
 * Post operation on a semaphore.
 * @param sem The semaphore to destroy.
 */
int os_sem_post(struct os_sem *sem);

/**
 * Wait operation on a semaphore.
 * NOTE: this call will block if the semaphore is at 0.
 * @param sem The semaphore to destroy.
 */
int os_sem_wait(struct os_sem *sem);

/**
 * Wait operation on a semaphore.
 * NOTE: if the semaphore is at 0, this call won't block.
 * @param sem The semaphore to destroy.
 */
int os_sem_trywait(struct os_sem *sem);

/**
 * Structure for referencing a mutex element.
 * @struct os_mutex
 */
struct os_mutex;

/**
 * Allocate and Initialise a mutex.
 */
struct os_mutex *os_mutex_init(void);

/**
 * Destroy the mutex.
 * @param mut The mutex to destroy.
 */
void os_mutex_destroy(struct os_mutex *mut);

/**
 * Lock the mutex.
 * @param mut The mutex to lock.
 */
int os_mutex_lock(struct os_mutex *mut);

/**
 * Unlock the mutex.
 * @param mut The mutex to unlock.
 */
int os_mutex_unlock(struct os_mutex *mut);

// endregion

#if !defined(WIN32) && !defined(_WIN32_WCE)
/********************************/
/* definitions for UNIX flavour */
/********************************/

#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>

#if defined(__linux) || defined(ANDROID)
#include <stdint.h>
#endif

#include <netinet/in.h>

#endif // !defined(WIN32) && !defined(_WIN32_WCE)

#ifdef __cplusplus
}
#endif

#endif //CHEERO_OS_PORTING_H
