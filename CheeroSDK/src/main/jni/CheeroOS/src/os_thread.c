/**
 * @author  左程耀
 * @date    2018/11/12
 * @description     线程处理相关
 */

#include "os_porting.h"
#include "trace.h"

#ifndef os_malloc
    #define os_malloc(S) malloc(S)
#endif

#ifndef os_free
    #define os_free(P) { if (P!=NULL) { free(P); P=NULL;} }
#endif

#define OSIP_SUCCESS 0
#define OSIP_UNDEFINED_ERROR -1
#define OSIP_BAD_PARAMETER -2

#define EBUSY 16

#if defined(HAVE_P_THREAD)

struct os_thread *os_thread_create(void *(*func) (void *), void *arg)
{
    os_thread_t *thread = (os_thread_t *) os_malloc(sizeof(os_thread_t));
    if (thread == NULL)
    {
        TRACE(__FILE__, __LINE__, "thread == NULL\n");
        return NULL;
    }
    int i = pthread_create(thread, NULL, func, arg);
    if (i != 0)
    {
        TRACE(__FILE__, __LINE__, "Error while creating a new thread\n");
        os_free(thread);
        return NULL;
    }
    return (struct os_thread *) thread;
}

int os_thread_join(struct os_thread *_thread)
{
    os_thread_t *thread = (os_thread_t *) _thread;
    if (thread == NULL)
        return OSIP_BAD_PARAMETER;
    return pthread_join(*thread, NULL);
}

void os_thread_detach()
{
    pthread_detach(pthread_self());
}

int os_thread_set_priority(struct os_thread *thread, int priority)
{
    return OSIP_SUCCESS;
}

void os_thread_exit()
{
    pthread_exit(NULL);
}

struct os_mutex *os_mutex_init(void)
{
    os_mutex_t *mutex = (os_mutex_t *) os_malloc(sizeof(os_mutex_t));
    if (mutex == NULL)
    {
        TRACE(__FILE__, __LINE__, "mutex == NULL\n");
        return NULL;
    }
    int i = pthread_mutex_init(mutex, NULL);
    if (i != 0)
    {
        TRACE(__FILE__, __LINE__, "Error while init mutex\n");
        os_free(mutex);
        return NULL;
    }
    return (struct os_mutex *) mutex;
}

void os_mutex_destroy(struct os_mutex *mut)
{
    os_mutex_t *mutex = (os_mutex_t *) mut;
    if (mutex == NULL)
        return;
    pthread_mutex_destroy(mutex);
    os_free(mutex);
}

int os_mutex_lock(struct os_mutex *mut)
{
    os_mutex_t *mutex = (os_mutex_t *) mut;
    if (mutex == NULL)
        return OSIP_BAD_PARAMETER;
    return pthread_mutex_lock(mutex);
}

int os_mutex_unlock(struct os_mutex *mut)
{
    os_mutex_t *mutex = (os_mutex_t *) mut;
    if (mutex == NULL)
        return OSIP_BAD_PARAMETER;
    return pthread_mutex_unlock(mutex);
}

#endif // defined(HAVE_P_THREAD)

#if defined(HAVE_SEMAPHORE_H)

struct os_sem *os_sem_init(unsigned int value)
{
    os_sem_t *sem = (os_sem_t *) os_malloc(sizeof(os_sem_t));
    if (sem != NULL)
    {
        memset(sem, 0, sizeof(os_sem_t));
        if (sem_init((sem_t *) sem, 0, value) == 0)
            return (struct os_sem *) sem;
    }
    os_free(sem);
    return NULL;
}

int os_sem_destroy(struct os_sem *_sem)
{
    os_sem_t *sem = (os_sem_t *) _sem;
    if (sem == NULL)
        return OSIP_SUCCESS;
    sem_destroy(sem);
    os_free(sem);
    return OSIP_SUCCESS;
}

int os_sem_post(struct os_sem *_sem)
{
    os_sem_t *sem = (os_sem_t *) _sem;
    if (sem == NULL)
        return OSIP_BAD_PARAMETER;
    return sem_post(sem);
}
int os_sem_wait(struct os_sem *_sem)
{
    os_sem_t *sem = (os_sem_t *) _sem;
    if (sem == NULL)
        return OSIP_BAD_PARAMETER;
    return sem_wait(sem);
}
int os_sem_trywait(struct os_sem *_sem)
{
    os_sem_t *sem = (os_sem_t *) _sem;
    if (sem == NULL)
        return OSIP_BAD_PARAMETER;
    return sem_trywait(sem);
}

#endif // defined(HAVE_SEMAPHORE_H)