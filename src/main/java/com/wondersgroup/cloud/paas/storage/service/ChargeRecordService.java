package com.wondersgroup.cloud.paas.storage.service;

import java.math.BigDecimal;
import java.util.Date;

public interface ChargeRecordService {

    /**
     * 新增扣费记录
     *
     * @param tradeNo    交易流水号
     * @param accountId  账户ID
     * @param projectId  项目ID
     * @param recordTime 记录时间
     * @param money      金额
     * @param status     状态：0-失败 1-成功
     * @param remark     备注
     * @return
     */
    int insert(String tradeNo, String accountId, String projectId, Date recordTime, BigDecimal money, String status, String remark);
}
