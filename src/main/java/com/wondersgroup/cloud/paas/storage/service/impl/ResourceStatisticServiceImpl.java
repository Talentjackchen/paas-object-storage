package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.storage.enums.ResourceOperationEnum;
import com.wondersgroup.cloud.paas.storage.enums.StorageTypeEnum;
import com.wondersgroup.cloud.paas.storage.mapper.ResourceStatisticMapper;
import com.wondersgroup.cloud.paas.storage.mapper.ext.ResourceStatisticExtendMapper;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.Resource;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatistic;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatisticExample;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.ResourceStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceStatisticServiceImpl implements ResourceStatisticService {

    @Autowired(required = false)
    private ResourceStatisticMapper resourceStatisticMapper;

    @Autowired(required = false)
    private ResourceStatisticExtendMapper resourceStatisticExtendMapper;

    @Autowired(required = false)
    private BucketService bucketService;
    /**
     * 资源计费项
     * @param resource 资源
     * @param ifAdd true-增加  false-删除
     * @return
     */
    @Override
    public int add(Resource resource, boolean ifAdd){

        ResourceStatistic resourceStatistic = new ResourceStatistic();

        resourceStatistic.setResourceStatisticId(CommonUtils.generateId());
        resourceStatistic.setBucketId(resource.getBucketId());
        resourceStatistic.setFileSize(resource.getFileSize());
        resourceStatistic.setRecordTime(resource.getUpdateTime());
        resourceStatistic.setStorageType(resource.getStorageType());
        resourceStatistic.setResourceId(resource.getResourceId());
        if(ifAdd){
            resourceStatistic.setOperation(ResourceOperationEnum.ADD.value());
        }else{
            resourceStatistic.setOperation(ResourceOperationEnum.DELETE.value());
        }

        if (!ifAdd && StorageTypeEnum.INFREQUENCY.value().equals(resource.getStorageType())) {
            resourceStatistic.setAddRecordTime(resource.getCreateTime());
        }
        Bucket bucket = bucketService.getById(resource.getBucketId());
        if(bucket != null){
            resourceStatistic.setProjectId(bucket.getProjectId());
        }
        return resourceStatisticMapper.insert(resourceStatistic);
    }

    @Override
    public List<ResourceStatistic> getListByDate(String projectId, Date beginDate, Date endDate, String type) {
        ResourceStatisticExample example = new ResourceStatisticExample();
        ResourceStatisticExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andStorageTypeEqualTo(type);
        criteria.andRecordTimeBetween(beginDate, endDate);
        example.setOrderByClause("record_time asc");
        List<ResourceStatistic> list = resourceStatisticMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<ResourceStatistic> getDeleteListForSixtyDate(String projectId, Date date, String type) {
        Date beginDate = DateUtils.getDateByDay(date, -30);
        Date endDate = date;
        List<ResourceStatistic> list = resourceStatisticExtendMapper.getDeleteListForSixtyDate(projectId, beginDate, endDate, StorageTypeEnum.INFREQUENCY.value());
        return list;
    }

    @Override
    public long getPreStorageByDate(String projectId, Date date, String type) {
        long storage = resourceStatisticExtendMapper.getPreStorageByDate(projectId, date, type);
        return storage;
    }
}
