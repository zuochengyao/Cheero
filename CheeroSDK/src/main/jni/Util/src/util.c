//
// Created by Work on 5/10/21.
//

#include "util.h"
#include "trace.h"

void at_request_analysis(const char *request, int32 len, at_Request *atReq);

void test_sscanf(const char *request, int32 len)
{
    TRACE("LUAT", ANDROID_LOG_INFO, "1.request=%s, len=%d\n", request, len);
    struct at_Request *req = memMalloc(sizeof(at_Request));
    at_request_analysis(request, len, req);
    memFree(req);
}

void at_request_analysis(const char *request, int32 len, struct at_Request *atReq)
{
    // AT+POC=021234 000000025D30BDD5 613B5D2D293031622E
    // 000000025D30BDD5: 10153410005
    // 613B5D2D29303162: a;]-)01b

    char *buf1 = memMalloc(7);
    char *buf2 = memMalloc(2);
    char *buf3 = memMalloc(5);
    char *buf4 = memMalloc(len - 7 - 2 - 5);
    sscanf(request, "%7s%2s%4s%s", buf1, buf2, buf3, buf4);
    TRACE("LUAT", ANDROID_LOG_INFO,"2.buf1=%s, buf2=%s, buf3=%s, buf4=%s\n", buf1, buf2, buf3, buf4);
    memcpy(atReq->header, buf1, sizeof(buf1));
    TRACE("LUAT", ANDROID_LOG_INFO,"3.atReq->header=%s\n", atReq->header);
    atReq->cmd = strtoul(buf2, NULL, 16);
    TRACE("LUAT", ANDROID_LOG_INFO,"4.atReq->cmd=%x\n", atReq->cmd);
    atReq->id = strtoul(buf3, NULL, 16);
    TRACE("LUAT", ANDROID_LOG_INFO,"5.atReq->cmd=%x\n", atReq->id);
    atReq->extra = strtoul(buf4, NULL, 16);
    TRACE("LUAT", ANDROID_LOG_INFO,"6.atReq->extra=%llx\n", atReq->extra);
    memFree(buf1);
    memFree(buf2);
    memFree(buf3);
    memFree(buf4);
//    memcpy(atReq->header, request, at_pocReqHeaderLen);
//    atReq->cmd = request[at_pocReqHeaderLen];
}