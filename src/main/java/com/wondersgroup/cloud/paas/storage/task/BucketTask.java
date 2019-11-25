package com.wondersgroup.cloud.paas.storage.task;

import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * describe : 空间定时任务
 * create_by : zhangyongzhao
 * create_time : 2019/6/3
 */
@Component("bucketTask")
public class BucketTask {

    private static Logger logger = Logger.getLogger(BucketTask.class);

    @Autowired
    private BucketService bucketService;

    /**
     * 定时删除未绑定正式域名且创建时间大于20天的bucket
     */
    public void delete() {
        try {
            Date date = DateUtils.getDateByDay(new Date(), -20);
            bucketService.deleteByCreateDate(date);
        } catch (Exception e) {
            logger.error("定时任务删除bucket失败", e);
        }
    }

    /**
     * 定时删除创建时间大于等于30天的七牛云bucket
     */
    public void deleteQiNiuBucket() {
        try {
            Date date = DateUtils.getDateByDay(new Date(), -30);
            List<Bucket> bucketList = bucketService.getCompareCreateTime(date);
            if (CollectionUtils.isNotEmpty(bucketList)) {
                bucketList.stream().forEach(bucket -> {
                    try {
                        bucketService.deleteBucket(bucket);
                    } catch (Exception e) {
                        logger.error("定时删除bucket失败,失败的bucketId为：" + bucket.getBucketId());
                        logger.error(e);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("定时任务删除bucket失败", e);
        }
    }

}
