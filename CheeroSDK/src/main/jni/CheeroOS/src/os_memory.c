/**
 * @author  左程耀
 * @date    2018/11/12
 * @description     内存处理相关
 */

#include "os_porting.h"

#define OS_MEMORY_MAP_MAX   1000
// #define OS_MEMORY_STACK_MAX 6
#define OS_MEMORY_STACK_BUF 2 * 1024
#define TRACE_MEMORY_TIME   60 * 1000

typedef struct _memory_t
{
    void *ptr;
    size_t size;
    long ts;
    char *_stack_;

    struct _memory_t *pPrev;
    struct _memory_t *pNext;
} memory_t;

typedef struct _memory_block_t
{
    struct _memory_t *pFirst;
    struct _memory_t *pLast;
    struct os_mutex *mutex;
} memory_block_t;

static memory_block_t gMemoryMap[OS_MEMORY_MAP_MAX];

static bool gMemoryDebugging = false;
static long gMemoryTimerId = 0;
static int gMemoryLeakCount = 0;

static char* os_memory_stack(char* logFile, int logLine);
static void os_memory_block_append(memory_t* memory);
static void os_memory_block_delete(memory_t* memory);
static memory_t* os_memory_block_find(void* ptr);

void memoryDebug()
{
    if (!gMemoryDebugging)
    {
        int i;
        memset(&gMemoryMap, 0, sizeof(memory_block_t) * OS_MEMORY_MAP_MAX);
        for (i = 0; i < OS_MEMORY_MAP_MAX; i++)
            gMemoryMap[i].mutex = os_mutex_init();
        // gMemoryTimerId = timer_register()
        gMemoryDebugging = true;
    }
}

void *memoryMalloc(size_t len, char *logFile, int logLine)
{
    void *p = malloc(len);
    memset(p, 0, len);
    if (gMemoryDebugging)
    {
        memory_t *memory = (memory_t *) malloc(sizeof(memory_t));
        memset(memory, 0, sizeof(memory_t));
        memory->ptr = p;
        memory->size = len;
        memory->ts = timestamp_milli_second();
        memory->_stack_ = os_memory_stack(logFile, logLine);
        os_memory_block_append(memory);
    }
    return p;
}

void memoryFree(void *p, char *logFile, int logLine)
{
    if (p)
    {
        if (gMemoryDebugging)
        {
            memory_t *m = os_memory_block_find(p);
            if (m)
                os_memory_block_delete(m);
        }
        free(p);
    }
    p = NULL;
}

static char* os_memory_stack(char* logFile, int logLine)
{
    char buf[OS_MEMORY_STACK_BUF+1] = "";
    snprintf(buf, OS_MEMORY_STACK_BUF, "%s:%d", logFile, logLine);
    return strdup(buf);
}

static memory_t* os_memory_block_find(void* ptr)
{
    memory_t* m = NULL;
// #ifndef WIN32
    if(ptr)
    {
        bool isFound = false;
        memory_block_t* block = &(gMemoryMap[(uint64)ptr % OS_MEMORY_MAP_MAX]);
        os_mutex_lock(block->mutex);
        m = block->pFirst;
        while(m)
        {
            if(m->ptr == ptr)
            {
                isFound = true;
                break;
            }
            m = m->pNext;
        }
        if(!isFound)
            m = NULL;
        os_mutex_unlock(block->mutex);
    }
// #endif
    return m;
}

static void os_memory_block_append(memory_t* memory)
{
    if (memory)
    {
        memory_block_t *block = &(gMemoryMap[(uint64) memory->ptr % OS_MEMORY_MAP_MAX]);
        os_mutex_lock(block->mutex);
    }
}

static void os_memory_block_delete(memory_t* memory)
{
#ifndef WIN32
    if(memory)
    {
        memory_block_t* block = &(gMemoryMap[(uint64)memory->ptr % OS_MEMORY_MAP_MAX]);
        os_mutex_lock(block->mutex);
        if(memory->pPrev)
            memory->pPrev->pNext = memory->pNext;
        else
            block->pFirst = memory->pNext;
        if(memory->pNext)
            memory->pNext->pPrev = memory->pPrev;
        else
            block->pLast = memory->pPrev;

        free(memory->_stack_);
        free(memory);
        os_mutex_unlock(block->mutex);
    }
#endif
}