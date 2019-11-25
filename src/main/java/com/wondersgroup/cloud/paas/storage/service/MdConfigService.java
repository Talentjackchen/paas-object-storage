package com.wondersgroup.cloud.paas.storage.service;

import java.util.Map;

/**
 * @author chenlong
 */
public interface MdConfigService {
    /**
     * 加载数据到缓存
     */
    void load();

    /**
     * 根据分类Id获取键值对map
     *
     * @param parentId
     * @return
     */
    Map<String, String> getMapByParentId(String parentId);
}
