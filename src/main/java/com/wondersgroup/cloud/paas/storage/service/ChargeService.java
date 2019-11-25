package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.pojo.ResourceCharge;

import java.util.Date;
import java.util.List;

/**
 * @author chenlong
 */
public interface ChargeService {
    /**
     * 计算空间资源计量
     *
     * @param beginDate 出账开始时间
     * @param endDate   出账结束时间
     * @return
     */
    List<ResourceCharge> calculateResourceCharge(Date beginDate, Date endDate);
}
