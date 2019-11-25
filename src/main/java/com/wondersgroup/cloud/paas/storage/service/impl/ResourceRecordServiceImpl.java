package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.storage.mapper.ResourceRecordMapper;
import com.wondersgroup.cloud.paas.storage.model.ResourceRecord;
import com.wondersgroup.cloud.paas.storage.service.ResourceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 资源操作记录统计
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceRecordServiceImpl implements ResourceRecordService {

    @Autowired(required = false)
    private ResourceRecordMapper resourceRecordMapper;

    /**
     * 资源操作记录
     * @param resourceRecord 统计对象
     */
    @Override
    public void insert(ResourceRecord resourceRecord) throws Exception{
        resourceRecord.setId(CommonUtils.generateId());
        resourceRecord.setCreateTime(new Date());
        resourceRecordMapper.insert(resourceRecord);
    }
}
