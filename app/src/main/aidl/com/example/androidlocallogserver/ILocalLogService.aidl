package com.example.androidlocallogserver;

import com.example.androidlocallogserver.model.LocalLog;

interface ILocalLogService {

    int getLogCount(String app);

    void writeLog(String appPackage, in LocalLog log);

}