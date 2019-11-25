package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.SslCertificate;
import com.wondersgroup.cloud.paas.storage.model.SslCertificateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SslCertificateMapper {
    long countByExample(SslCertificateExample example);

    int deleteByExample(SslCertificateExample example);

    int deleteByPrimaryKey(String sslCertificateId);

    int insert(SslCertificate record);

    int insertSelective(SslCertificate record);

    List<SslCertificate> selectByExampleWithRowbounds(SslCertificateExample example, RowBounds rowBounds);

    List<SslCertificate> selectByExample(SslCertificateExample example);

    SslCertificate selectByPrimaryKey(String sslCertificateId);

    int updateByExampleSelective(@Param("record") SslCertificate record, @Param("example") SslCertificateExample example);

    int updateByExample(@Param("record") SslCertificate record, @Param("example") SslCertificateExample example);

    int updateByPrimaryKeySelective(SslCertificate record);

    int updateByPrimaryKey(SslCertificate record);
}