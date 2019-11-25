package com.wondersgroup.cloud.paas.storage.mapper.ext;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleJobExtandMapper {

    int updateBatch(@Param("ids") List ids, @Param("status") String status);

    int deleteBatch(@Param("ids") List<String> ids);
}


