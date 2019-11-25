package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.Zone;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.common.utils.QueryUtils;
import com.wondersgroup.cloud.paas.storage.constant.SslCertConstant;
import com.wondersgroup.cloud.paas.storage.mapper.SslCertificateMapper;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.model.SslCertificate;
import com.wondersgroup.cloud.paas.storage.model.SslCertificateExample;
import com.wondersgroup.cloud.paas.storage.pojo.CertPojo;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;
import com.wondersgroup.cloud.paas.storage.pojo.ext.CertResponse;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import com.wondersgroup.cloud.paas.storage.service.SslCertService;
import com.wondersgroup.cloud.paas.storage.utils.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class SslCertServiceImpl implements SslCertService {

    private Logger logger = Logger.getLogger(SslCertServiceImpl.class);

    @Autowired(required = false)
    private AccountService accountService;

    @Autowired(required = false)
    private SslCertificateMapper sslCertificateMapper;

    /**
     * SSL证书分页查询
     * @param remarkName 备注名
     * @param genericName 通用名称
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页
     * @param pageSize 页大小
     * @param orderByClause 排序
     * @return
     */
    @Override
    public ResultMap page(String remarkName, String genericName, String beginTime, String endTime, int pageNum, int pageSize, String orderByClause){
        PageHelper.startPage(pageNum, pageSize);
        SslCertificateExample example = new SslCertificateExample();
        SslCertificateExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(remarkName)){
            criteria.andRemarksNameLike(QueryUtils.generateLikeString(remarkName));
        }

        if (StringUtils.isNotBlank(genericName)){
            criteria.andGenericNameLike(QueryUtils.generateLikeString(genericName));
        }

        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isNotBlank(beginTime)){
            try{
                criteria.andCreateTimeGreaterThan(sf.parse(beginTime));
            }catch(ParseException e){
                new ResultMap(CommonConstant.ERROR, CommonConstant.BEGIN_DATE_FORMAT_ERROR);
            }
        }

        if(StringUtils.isNotBlank(endTime)){
            try{
                criteria.andCreateTimeLessThan(sf.parse(endTime));
            }catch(ParseException e){
                new ResultMap(CommonConstant.ERROR, CommonConstant.END_DATE_FORMAT_ERROR);
            }
        }

        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());

        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        List<SslCertificate> sslCertificateList = sslCertificateMapper.selectByExample(example);
        return  new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS,new PageInfo<>(sslCertificateList));
    }

    /**
     * 上传证书
     * @param name 备注名称
     * @param ca 证书内容
     * @param pri 证书私钥
     * @return
     * @throws Exception
     */
    @Override
    public ResultMap add(String name, String ca, String pri) throws Exception {
        if(StringUtils.isBlank(name) || StringUtils.isBlank(ca) || StringUtils.isBlank(pri)){
            return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE,CommonConstant.DATA_CHECK_FAILURE_MSG);
        }
        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE,CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("name",name);
        params.put("ca",ca);
        params.put("pri",pri);

        //执行7牛新增证书操作
        CertResponse addResponsePojo = HttpClientUtils.doPost(new Zone().getApiHttp() + SslCertConstant.API_SSLCERT_URL, params, account, CommonConstant.CONTENT_TYPE_JSON, CertResponse.class);
        String certId = addResponsePojo.getCertID();
        if(CommonConstant.SUCCESS == addResponsePojo.getCode() && StringUtils.isNotBlank(certId)){
            String getUrl = new Zone().getApiHttp() + SslCertConstant.API_SSLCERT_URL + "/" + certId;

            //获取7牛证书详情
            CertResponse getResponsePojo = HttpClientUtils.doGet(getUrl, account, CertResponse.class);
            if(getResponsePojo.getCode() == CommonConstant.SUCCESS && getResponsePojo.getCert() != null){
                CertPojo cert = getResponsePojo.getCert();
                SslCertificate sslCertificate = new SslCertificate();
                sslCertificate.setSslCertificateId(CommonUtils.generateId());
                sslCertificate.setCertificateId(cert.getCertId());

                sslCertificate.setValidFlag(ValidStatusEnum.VALID.value());
                Date createTime = DateUtils.longToDate(cert.getCreate_time()*1000L,"yyyy-MM-dd HH:mm:ss");
                try{
                    sslCertificate.setUpdateTime(createTime);
                    sslCertificate.setCreateTime(createTime);
                    sslCertificate.setAwardTime(DateUtils.longToDate(cert.getNot_before()*1000L,"yyyy-MM-dd"));
                    sslCertificate.setExpireTime(DateUtils.longToDate(cert.getNot_after()*1000L,"yyyy-MM-dd"));
                }catch(ParseException e){
                    return new ResultMap(CommonConstant.ERROR,SslCertConstant.INSERT_ERROR_MSG);
                }

                sslCertificate.setRemarksName(cert.getName());
                sslCertificate.setGenericName(cert.getCommon_name());
                //新增到库
                int returnCode = sslCertificateMapper.insert(sslCertificate);
                if(returnCode < 1){
                    return new ResultMap(CommonConstant.ERROR,SslCertConstant.INSERT_ERROR_MSG);
                }else{
                    return new ResultMap(CommonConstant.SUCCESS,SslCertConstant.INSERT_SUCCESS_MSG);
                }
            }else{
                return new ResultMap(CommonConstant.ERROR,SslCertConstant.GET_ERROR_MSG);
            }
        }else{
            logger.error(addResponsePojo.getError());
            return new ResultMap(CommonConstant.ERROR, SslCertConstant.INSERT_ERROR_MSG);
        }
    }

    /**
     * 删除证书
     * @param sslCertificateId 业务主键
     * @return
     */
    @Override
    public ResultMap delete(String sslCertificateId) {
        if(StringUtils.isBlank(sslCertificateId)) {
            return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE,CommonConstant.DATA_CHECK_FAILURE_MSG);
        }

        Account account = accountService.getAccount();
        if(account == null || StringUtils.isBlank(account.getAccessKey()) || StringUtils.isBlank(account.getSecretKey())){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE,CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        String certId;
        SslCertificate sslCertificate = getById(sslCertificateId);
        if(sslCertificate != null){
            certId = sslCertificate.getCertificateId();
        }else{
            return new ResultMap(CommonConstant.ERROR,SslCertConstant.GET_DEL_DATA_MSG);
        }

        String url = new Zone().getApiHttp() + SslCertConstant.API_SSLCERT_URL + "/" + certId;

        //执行删除七牛
        ResponsePojo getResponsePojo= HttpClientUtils.doDelete(url,account);
        if(getResponsePojo.getCode() == CommonConstant.SUCCESS){
            sslCertificate.setValidFlag(ValidStatusEnum.INVALID.value());
            sslCertificate.setUpdateTime(new Date());
            //删除数据库
            int returnCode = sslCertificateMapper.updateByPrimaryKey(sslCertificate);
            if(returnCode < 1){
                return new ResultMap(CommonConstant.ERROR,SslCertConstant.DELETE_ERROR_MSG);
            }else{
                return new ResultMap(CommonConstant.SUCCESS,SslCertConstant.DELETE_SUCCESS_MSG);
            }
        }else{
            logger.error(getResponsePojo.getError());
            return new ResultMap(CommonConstant.ERROR, SslCertConstant.DELETE_ERROR_MSG);
        }

    }

    /**
     * 获取证书记录信息
     * @param sslCertificateId
     * @return
     */
    @Override
    public SslCertificate getById(String sslCertificateId) {
        if(StringUtils.isBlank(sslCertificateId)){
            return null;
        }
        SslCertificate sslCertificate = sslCertificateMapper.selectByPrimaryKey(sslCertificateId);
        if(sslCertificate != null && ValidStatusEnum.VALID.value().equals(sslCertificate.getValidFlag())){
            return sslCertificate;
        }else{
            return null;
        }
    }

    /**
     * 依据通用名称加载7天后不过期的证书
     * @param genericName 通用名称
     * @return
     */
    @Override
    public SslCertificate getByGenericName(String genericName) {

        SslCertificateExample example = new SslCertificateExample();
        SslCertificateExample.Criteria criteria = example.createCriteria();

        if(genericName.indexOf(".") > -1){
            genericName = genericName.substring(genericName.indexOf("."));
        }

        if (StringUtils.isNotBlank(genericName)){
            criteria.andGenericNameLike(QueryUtils.generateLikeString(genericName));
        }else{
            return null;
        }
        LocalDate  currentDate = LocalDate.now();
        LocalDate  lastDate = currentDate.plusDays(SslCertConstant.EXPIRE_DAY);
        criteria.andExpireTimeGreaterThan(Date.from(lastDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<SslCertificate> sslCertificateList = sslCertificateMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(sslCertificateList)){
            return sslCertificateList.get(0);
        }
        return null;
    }


}
