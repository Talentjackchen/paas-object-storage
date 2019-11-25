package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.BucketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BucketMapper {
    long countByExample(BucketExample example);

    int deleteByExample(BucketExample example);

    int deleteByPrimaryKey(String bucketId);

    int insert(Bucket record);

    int insertSelective(Bucket record);

    List<Bucket> selectByExampleWithRowbounds(BucketExample example, RowBounds rowBounds);

    List<Bucket> selectByExample(BucketExample example);

    Bucket selectByPrimaryKey(String bucketId);

    int updateByExampleSelective(@Param("record") Bucket record, @Param("example") BucketExample example);

    int updateByExample(@Param("record") Bucket record, @Param("example") BucketExample example);

    int updateByPrimaryKeySelective(Bucket record);

    int updateByPrimaryKey(Bucket record);
}