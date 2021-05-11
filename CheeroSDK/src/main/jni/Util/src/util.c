//
// Created by Work on 5/10/21.
//

#include "util.h"
#include "trace.h"

void at_request_analysis(const char *request, int32 len, at_Request *atReq);

void test_sscanf(const char *request, int32 len) {
    TRACE("LUAT", ANDROID_LOG_INFO, "[LUAT] 1.request=%s, len=%d\n", request, len);

    struct at_Request *req = NULL;
    // req = memMalloc(sizeof(at_Request));
    at_request_analysis(request, len, req);
    memFree(req);
}

void at_request_analysis(const char *request, int32 len, struct at_Request *atReq) {
    if (atReq == NULL) {
        atReq = memMalloc(sizeof(at_Request));
        TRACE("LUAT", ANDROID_LOG_INFO, "atReq malloc success\n");
    }
    int ret;
    char buf1[255];
    char buf2[255];
    char buf3[255];
    char buf4[255];
    char buf5[255];
    uint64 uid = 0;
    char *pwd;
    // AT+POC=021234 000000025D30BDD5 613B5D2D293031622E
    // 000000025D30BDD5: 10153410005
    // 613B5D2D29303162: a;]-)01b
    ret = sscanf(request, "%7s%2s%4s%llx", buf1, buf2, buf3, &uid);
    TRACE("LUAT", ANDROID_LOG_INFO,
          "[LUAT] 2.ret=%d, buf1=%s, buf2=%s, buf3=%s, uid=%llx\n", ret,
          buf1, buf2, buf3, uid);
}