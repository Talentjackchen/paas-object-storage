package com.wondersgroup.cloud.paas.storage.service.impl;

import com.qiniu.common.Zone;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.storage.mapper.RequestStatisticMapper;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.RequestStatistic;
import com.wondersgroup.cloud.paas.storage.model.RequestStatisticExample;
import com.wondersgroup.cloud.paas.storage.pojo.ext.BucketFlowPojo;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import com.wondersgroup.cloud.paas.storage.service.RequestStatisticService;
import com.wondersgroup.cloud.paas.storage.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author chenlong
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RequestStatisticServiceImpl implements RequestStatisticService {
    @Autowired(required = false)
    private RequestStatisticMapper requestStatisticMapper;

    @Autowired
    private AccountService accountService;
    /**
     * 请求类型，低频与标频转换
     */
    private String type = "3";

    @Override
    public int getRequestStatisticAmount(String bucketId, Date beginDate, Date endDate, String type, String storageType) {
        int amount = 0;
        RequestStatisticExample example = new RequestStatisticExample();
        example.createCriteria().andBucketIdEqualTo(bucketId)
                .andTypeEqualTo(type)
                .andRecordTimeBetween(beginDate, endDate)
                .andStorageTypeEqualTo(storageType);
        List<RequestStatistic> requestStatisticList = requestStatisticMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(requestStatisticList)) {
            amount = requestStatisticList.get(0).getAmount();
        }
        return amount;
    }

    /**
     * 获取删除空间的低频与标频转换次数
     *
     * @param bucketId
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public int getExchangeStatisticAmount(String bucketId, Date beginDate, Date endDate) {
        int amount = 0;
        RequestStatisticExample example = new RequestStatisticExample();
        example.createCriteria().andBucketIdEqualTo(bucketId)
                .andTypeEqualTo(type)
                .andRecordTimeBetween(beginDate, endDate);
        List<RequestStatistic> requestStatisticList = requestStatisticMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(requestStatisticList)) {
            amount = requestStatisticList.get(0).getAmount();
        }
        return amount;
    }

    /**
     * 保存删除空间的低频与标频转换的次数
     *
     * @param bucket
     */
    @Override
    public void saveExchangeAmount(Bucket bucket) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Date beginDate = DateUtils.getDateOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
        Date endDate = DateUtils.getDateOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 2, 1);
        int allHit = getRealTimeExchangeStatisticAmount(bucket.getName(), beginDate, endDate);
        RequestStatistic requestStatistic = new RequestStatistic();
        requestStatistic.setBucketId(bucket.getBucketId());
        requestStatistic.setRequestStatisticId(CommonUtils.generateId());
        requestStatistic.setRecordTime(new Date());
        requestStatistic.setAmount(allHit);
        requestStatistic.setType(type);
        requestStatisticMapper.insert(requestStatistic);
    }

    /**
     * 实时获取空间低频与标频转换次数
     *
     * @param bucketName
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public int getRealTimeExchangeStatisticAmount(String bucketName, Date beginDate, Date endDate) {
        int allHit = 0;
        Zone zone = new Zone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String begin = sdf.format(beginDate);
        String end = sdf.format(endDate);
        String url = zone.getApiHttp() + "/v6/rs_chtype?begin=" + begin + "&end=" + end + "&g=day&select=hits&$bucket=" + bucketName;
        Account account = accountService.getAccount();
        List<BucketFlowPojo> list = HttpClientUtils.doGetToList(url, account, BucketFlowPojo.class);

        for (BucketFlowPojo flowPojo : list) {
            long hit = flowPojo.getValues().get("hits");
            allHit += hit;
        }
        return allHit;
    }
}
