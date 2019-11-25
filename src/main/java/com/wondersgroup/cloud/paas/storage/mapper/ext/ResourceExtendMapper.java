package com.wondersgroup.cloud.paas.storage.mapper.ext;

import com.wondersgroup.cloud.paas.storage.mapper.ResourceMapper;
import com.wondersgroup.cloud.paas.storage.model.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 资源拓展
 * zhangyongzhao
 */
public interface ResourceExtendMapper extends ResourceMapper {

    /**
     * 按文件类型对文件存储量进行统计
     */
    List<HashMap<String,Object>> queryCountAndSize(@Param("fileType") String fileType,
                                                   @Param("projectId") String projectId,
                                                   @Param("bucketId") String bucketId);

    /**
     * 统计某空间下标准存储文件总大小
     * @param bucketId
     * @return
     */
    Long getTotalSizeByBucketId(@Param("bucketId") String bucketId,@Param("storageType") String storageType);

    /**
     * 依据空间ID删除资源
     * @param record
     * @return
     */
    int deleteByResource(Resource record);

    /**
     * 按文件类型对文件存储量进行统计
     */
    HashMap<String, Object> getCountAndSizeByBucketId(@Param("bucketId") String bucketId);

}
