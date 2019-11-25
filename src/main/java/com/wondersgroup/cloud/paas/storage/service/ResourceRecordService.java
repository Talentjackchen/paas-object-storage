package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.ResourceRecord;

/**
 * 资源操作记录统计
 */
public interface ResourceRecordService {

    /**
     * 资源操作记录
     * @param resourceRecord 统计对象
     */
    void insert(ResourceRecord resourceRecord) throws Exception;
}
