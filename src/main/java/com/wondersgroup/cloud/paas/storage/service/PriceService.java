package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.Price;
import com.wondersgroup.cloud.paas.storage.pojo.PricePojo;

import java.util.List;

/**
 * @author chenlong
 */
public interface PriceService {

    /**
     * 所有价格信息
     *
     * @return
     */
    List<PricePojo> list();

    /**
     * 更新价格信息
     *
     * @param list
     */
    void update(List<PricePojo> list);
}
