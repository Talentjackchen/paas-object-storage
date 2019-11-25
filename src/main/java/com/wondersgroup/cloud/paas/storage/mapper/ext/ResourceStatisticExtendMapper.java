package com.wondersgroup.cloud.paas.storage.mapper.ext;

import com.wondersgroup.cloud.paas.storage.mapper.ResourceStatisticMapper;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author chenlong
 */
public interface ResourceStatisticExtendMapper extends ResourceStatisticMapper {
    /**
     * 统计60天内上传并且删除的资源
     *
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param type
     * @return
     */
    List<ResourceStatistic> getDeleteListForSixtyDate(@Param("projectId") String projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("type") String type);

    /**
     * 统计截至日期项目空间总存储量
     *
     * @param projectId 项目Id
     * @param date      截至日期
     * @param type
     * @return
     */
    long getPreStorageByDate(@Param("projectId") String projectId, @Param("date") Date date, @Param("type") String type);
}
