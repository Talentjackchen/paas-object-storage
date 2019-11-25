package com.wondersgroup.cloud.paas.storage.service.impl;

import com.qiniu.common.Zone;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.constant.ResourceConstant;
import com.wondersgroup.cloud.paas.storage.constant.StatisticsConstant;
import com.wondersgroup.cloud.paas.storage.mapper.ext.ResourceExtendMapper;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsRequestPojo;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsStoragePojo;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.StatisticsService;
import com.wondersgroup.cloud.paas.storage.tools.CertificateTool;
import com.wondersgroup.cloud.paas.storage.tools.ResourceMangerTool;
import com.wondersgroup.cloud.paas.storage.utils.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计服务
 * zhangyongzhao
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static Logger logger = Logger.getLogger(StatisticsServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired(required = false)
    private ResourceExtendMapper resourceExtendMapper;

    @Autowired
    private BucketService bucketService;

    /**
     * 获取get请求次数
     *
     * @param beginDate   开始时间
     * @param endDate     结束时间
     * @param bucketId    主键
     * @param granularity 时间颗粒度
     * @param domain      空间域名
     * @param storageType 存储类型
     * @param region      存储区域
     * @return result
     */
    private List<StatisticsRequestPojo> getGETRequestsNumber(String beginDate, String endDate, String bucketId, String granularity,
                                                            String domain, String storageType, String region) throws BusinessException {
        List<StatisticsRequestPojo> statisticsPojo;
        Account account = accountService.getAccount();
        try {
            String path = StatisticsConstant.API_URL + StatisticsConstant.API_GET_URL;
            LinkedHashMap<String, Object> params = buildParams(beginDate, endDate, bucketId, granularity,
                    storageType, region, StatisticsConstant.SRC_INNER+"&$src="+StatisticsConstant.SRC_ORIGIN, domain);
            String url = new Zone().getApiHttp() + StatisticsConstant.API_URL + StatisticsConstant.API_GET_URL;
            Map<String, String> headers = new HashMap<>();
            String access_token = CertificateTool.generateAuthorization(account.getAccessKey(), account.getSecretKey(), path);
            headers.put(StatisticsConstant.AUTHORIZATION, access_token);
            statisticsPojo = HttpClientUtils.doGetV2ToList(url, params, account, StatisticsRequestPojo.class);
            return statisticsPojo;
        } catch (Exception e) {
            throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR, e.getCause());
        }
    }

    @Override
    public List<StatisticsRequestPojo> getGETRequestsNumberByDay(String beginDate, String endDate, String bucketId, String granularity,
                                                                 String domain, String storageType, String region) throws BusinessException {
        try{
            List<StatisticsRequestPojo> statisticsPojo = getGETRequestsNumber(beginDate,endDate,bucketId,granularity,domain,storageType,region);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if(statisticsPojo.get(0).getCode() != 400){
                for(StatisticsRequestPojo s : statisticsPojo){
                    Long times = format.parse(s.getTime().substring(0,10)).getTime();
                    s.setTime(String.valueOf(times/1000));
                }
            }
            return statisticsPojo;
            //return increaseTime(beginDate,statisticsPojo);
        } catch (Exception e){
            throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR, e.getCause());
        }
    }

    @Override
    public int getGETRequestsNumberByMonth(String beginDate, String endDate, String bucketId, String domain, String storageType, String region) throws BusinessException {
        List<StatisticsRequestPojo> statisticsPojos = getGETRequestsNumber(beginDate,endDate,bucketId,"month",domain,storageType,region);
        int hits = 0;
        for(StatisticsRequestPojo s:statisticsPojos){
            if(s.getValues() != null){
                hits += Integer.parseInt(String.valueOf(s.getValues().get("hits")));
            }
        }
        return hits;
    }

    /**
     * 获取put请求次数
     *
     * @param beginDate   开始时间
     * @param endDate     结束时间
     * @param bucketId    主键
     * @param granularity 时间颗粒度
     * @param storageType 存储类型
     * @param region      存储区域
     * @return result
     */
    private List<StatisticsRequestPojo> getPUTRequestsNumber(String beginDate, String endDate, String bucketId, String granularity,
                                                            String storageType, String region) throws BusinessException {
        List<StatisticsRequestPojo> statisticsPojos;
        Account account = accountService.getAccount();
        try {
            LinkedHashMap<String, Object> params = buildParams(beginDate, endDate, bucketId, granularity,
                    storageType, region, "", "");
            String url = new Zone().getApiHttp() + StatisticsConstant.API_URL + StatisticsConstant.API_PUT_URL;
            statisticsPojos = HttpClientUtils.doGetV2ToList(url, params, account, StatisticsRequestPojo.class);
            return statisticsPojos;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR, e.getCause());
        }
    }

    public List<StatisticsRequestPojo> getPUTRequestsNumberByDay(String beginDate, String endDate, String bucketId, String granularity,
                                                                  String storageType, String region) throws BusinessException {
        try{
            List<StatisticsRequestPojo> statisticsPojos = getPUTRequestsNumber(beginDate, endDate, bucketId, granularity, storageType, region);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if(statisticsPojos.get(0).getCode() != 400){
                for(StatisticsRequestPojo s : statisticsPojos){
                    Long times = format.parse(s.getTime().substring(0,10)).getTime();
                    s.setTime(String.valueOf(times/1000));
                }
            }
            return statisticsPojos;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR, e.getCause());
        }
    }

    public int getPUTRequestsNumberByMonth(String beginDate, String endDate, String bucketId, String storageType, String region) throws BusinessException {
        List<StatisticsRequestPojo> statisticsPojos = getPUTRequestsNumber(beginDate, endDate, bucketId, "month", storageType, region);
        int hits = 0;
        for(StatisticsRequestPojo s:statisticsPojos){
            if(s.getValues() != null){
                hits += Integer.parseInt(String.valueOf(s.getValues().get("hits")));
            }
        }
        return hits;
    }

    /**
     * 获取文件类型大小和数量
     *
     * @return hashMapList
     */
    public List<HashMap<String, Object>> queryCountAndSize(String fileType, String projectId,String bucketId) {
        List<HashMap<String, Object>> hashMapList = resourceExtendMapper.queryCountAndSize(fileType, projectId,bucketId);
        for (HashMap<String, Object> hashMap : hashMapList) {
            for (String key : hashMap.keySet()) {
                if (ResourceConstant.FILE_TYPE_SIZE.equals(key)) {
                    BigDecimal typeSize = (BigDecimal) hashMap.get(key);
                    //自动转换文件大小单位
                    String s = ResourceMangerTool.convertFileSize(typeSize.longValue());
                    hashMap.put(key, s);
                }
            }
        }
        return hashMapList;
    }

    /**
     * 根据bucketID查询文件大小和数量
     *
     * @param bucketId 空间ID
     * @return
     */
    @Override
    public HashMap<String, Object> getCountAndSizeByBucketId(String bucketId) {
        return resourceExtendMapper.getCountAndSizeByBucketId(bucketId);
    }


    /**
     * 获取七牛云上标准存储量
     *
     * @param begin    开始时间
     * @param end      结束时间
     * @param bucketId 空间ID
     * @return 存储量
     * @throws Exception
     */
    @Override
    public StatisticsStoragePojo getSpaceStorage(String begin, String end, String bucketId) throws Exception {
        String path = StatisticsConstant.API_URL + StatisticsConstant.API_SPACE_URL + StatisticsConstant.SYMBOL_QUESTION
                + StatisticsConstant.BEGIN_DATE + StatisticsConstant.SYMBOL_EQUAL + begin
                + StatisticsConstant.SYMBOL_AND + StatisticsConstant.END_DATE + StatisticsConstant.SYMBOL_EQUAL + end
                + StatisticsConstant.SYMBOL_AND + StatisticsConstant.GRANULARITY + StatisticsConstant.SYMBOL_EQUAL + StatisticsConstant.DAY;
        Bucket bucket;
        if (StringUtils.isNotBlank(bucketId)) {
            bucket = bucketService.getByIdAndRemoteFlag(bucketId);
            path += StatisticsConstant.SYMBOL_AND + StatisticsConstant.BUCKET + StatisticsConstant.SYMBOL_EQUAL + bucket.getAliasName();
            if (bucket == null) {
                throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR);
            }
        }
        Account account = accountService.getAccount();
        String url = new Zone().getApiHttp() + path;
        StatisticsStoragePojo statisticsStoragePojo = HttpClientUtils.doGet(url, account, StatisticsStoragePojo.class);
        return statisticsStoragePojo;
    }


    /**
     * 获取七牛云上低频存储量
     *
     * @param begin    开始时间
     * @param end      结束时间
     * @param bucketId 空间ID
     * @return 存储量
     * @throws Exception
     */
    @Override
    public StatisticsStoragePojo getSpaceLineStorage(String begin, String end, String bucketId, String noPreDel, String onlyPreDel) throws Exception {
        String path = StatisticsConstant.API_URL + StatisticsConstant.API_SPACE_LINE_URL + StatisticsConstant.SYMBOL_QUESTION
                + StatisticsConstant.BEGIN_DATE + StatisticsConstant.SYMBOL_EQUAL + begin
                + StatisticsConstant.SYMBOL_AND + StatisticsConstant.END_DATE + StatisticsConstant.SYMBOL_EQUAL + end
                + StatisticsConstant.SYMBOL_AND + StatisticsConstant.GRANULARITY + StatisticsConstant.SYMBOL_EQUAL + StatisticsConstant.DAY;
        Bucket bucket;
        if (StringUtils.isNotBlank(bucketId)) {
            bucket = bucketService.getByIdAndRemoteFlag(bucketId);
            path += StatisticsConstant.SYMBOL_AND + StatisticsConstant.BUCKET + StatisticsConstant.SYMBOL_EQUAL + bucket.getAliasName();
            if (bucket == null) {
                throw new BusinessException(CommonConstant.ERROR, StatisticsConstant.MSG_ERROR);
            }
        }

        if (StringUtils.isNotBlank(noPreDel)) {
            path += StatisticsConstant.SYMBOL_AND + StatisticsConstant.NO_PREDEL + StatisticsConstant.SYMBOL_EQUAL + noPreDel;
        }
        if (StringUtils.isNotBlank(onlyPreDel)) {
            path += StatisticsConstant.SYMBOL_AND + StatisticsConstant.ONLY_PREDEL + StatisticsConstant.SYMBOL_EQUAL + onlyPreDel;
        }
        Account account = accountService.getAccount();
        String url = new Zone().getApiHttp() + path;
        StatisticsStoragePojo statisticsStoragePojo = HttpClientUtils.doGet(url, account, StatisticsStoragePojo.class);
        return statisticsStoragePojo;
    }


    /**
     * 构建请求参数
     *
     * @param beginDate   开始时间
     * @param endDate     结束时间
     * @param bucketId    主键
     * @param granularity 时间颗粒度
     * @param domain      空间域名
     * @param storageType 存储类型
     * @param region      存储区域
     */
    private LinkedHashMap<String, Object> buildParams(String beginDate, String endDate,
                                                      String bucketId, String granularity,
                                                      String storageType, String region, String src, String domain) throws BusinessException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        Bucket bucket = bucketService.getByIdAndRemoteFlag(bucketId);
        if (bucket == null) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.BUCKET_NOT_EXIST_ERROR);
        }
        params.put(StatisticsConstant.BEGIN_DATE, beginDate);
        if (endDate != null && StringUtils.isNotBlank(endDate)) {
            params.put(StatisticsConstant.END_DATE, endDate);
        }

//        时间颗粒度,默认为day
        if (StringUtils.isBlank(granularity)) {
            params.put(StatisticsConstant.GRANULARITY, StatisticsConstant.DAY);
        } else {
            params.put(StatisticsConstant.GRANULARITY, granularity);
        }
        params.put(StatisticsConstant.SELECT, StatisticsConstant.SELECT_HITS);
        if (StringUtils.isNotBlank(src)) {
            params.put(StatisticsConstant.SRC, src);
        }
        params.put(StatisticsConstant.BUCKET_NAME, bucket.getAliasName());
        if (StringUtils.isNotBlank(domain)) {
            params.put(StatisticsConstant.DOMAIN, domain);
        }
        if (StringUtils.isNotBlank(storageType)) {
            params.put(StatisticsConstant.STORAGE_TYPE, storageType);
        }
        if (StringUtils.isNotBlank(region)) {
            params.put(StatisticsConstant.REGION, region);
        }
        return params;
    }

    private List<StatisticsRequestPojo> increaseTime(String beginDate,List<StatisticsRequestPojo> statisticsPojos) throws ParseException {
        String dateString = beginDate.substring(0, 8);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);
        Date startDate = new Date();
        startDate.setTime(date.getTime());
        Long times = startDate.getTime();
        String createdStr = statisticsPojos.get(0).getTime();
        Date createdDate = formatStr.parse(createdStr);
        long days = (createdDate.getTime()-date.getTime())/(24*60*60*1000L);
        Map<String,Object> map = new HashMap<>();
        map.put("hits",0);
        for(int i=0;i<days;i++){
            StatisticsRequestPojo statisticsRequestPojo = new StatisticsRequestPojo();
            startDate.setTime(times+(24*60*60*1000L*i));
            statisticsRequestPojo.setTime(formatStr.format(startDate));
            statisticsRequestPojo.setValues(map);
            statisticsPojos.add(i,statisticsRequestPojo);
        }

        if (statisticsPojos.size() >= 31) {
            return getMonth(statisticsPojos);
        } else {
            return changeTimeType(statisticsPojos);
        }
    }

    /**
     * 修改集合中的时间格式为MM月dd日
     * @param list
     * @return 修改时间格式后的集合
     */
    public List<StatisticsRequestPojo> changeTimeType(List<StatisticsRequestPojo> list) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        for (StatisticsRequestPojo statisticsRequestPojo : list) {
            String time = statisticsRequestPojo.getTime();
            Date date = simpleDateFormat.parse(time);
            String dataString = format.format(date);
            statisticsRequestPojo.setTime(dataString);
        }
        return list;
    }

    /**
     * 当查询的时间大于31天时,数据按照月份显示
     * @param list 按天显示数据
     * @return 按月显示的数据
     */
    public List<StatisticsRequestPojo> getMonth(List<StatisticsRequestPojo> list) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");

        // 月份集合
        List<String> result = new ArrayList<>();

        // 获取数据中的第一天和最后一天的日期,用来判断数据跨了哪几个月
        StatisticsRequestPojo statisticsRequestPojoFirst = list.get(0);
        StatisticsRequestPojo statisticsRequestPojoLast = list.get(list.size() - 1);
        String firstTime = statisticsRequestPojoFirst.getTime();
        String lastTime = statisticsRequestPojoLast.getTime();
        Date firstDate = simpleDateFormat.parse(firstTime);
        Date lastDate = simpleDateFormat.parse(lastTime);

        // 构建最大月份和最小月份
        Calendar max = Calendar.getInstance();
        Calendar min = Calendar.getInstance();

        // 获取最大月份
        max.setTime(lastDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

        // 获取最小月份
        min.setTime(firstDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        // 获取最大月份和最小月份之间的所有月份
        Calendar current = min;
        while (!current.after(max)) {
            result.add(format.format(current.getTime()));
            current.add(Calendar.MONTH, 1);
        }

        // 构建新集合
        List<StatisticsRequestPojo> newList = new ArrayList<>();

        // 以月份为单位重新构建集合
        for (String month : result) {
            StatisticsRequestPojo sr = new StatisticsRequestPojo();
            int hits = 0;
            for (StatisticsRequestPojo statisticsRequestPojo : list) {
                String time = statisticsRequestPojo.getTime();
                String subTime = time.substring(0, 4) + "年" + time.substring(5, 7) + "月";
                if (month.equals(subTime)) {
                    hits += (int) statisticsRequestPojo.getValues().get("hits");
                }
            }
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("hits", hits);
            sr.setValues(map);
            sr.setTime(month);
            newList.add(sr);
        }
        return newList;
    }

    /**
     * 修改实体类中的时间格式为MM月dd日
     * @param statisticsStoragePojo 待修改的实体类
     * @return 修改时间格式后的实体类
     */
    public StatisticsStoragePojo changeDateType(StatisticsStoragePojo statisticsStoragePojo) {
        List<String> times = statisticsStoragePojo.getTimes();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date date = new Date();

        List<String> newTimes = new ArrayList<>();
        for (String time : times) {
            date.setTime(Long.parseLong(time + "000"));
            String dateFormat = format.format(date);
            newTimes.add(dateFormat);
        }

        statisticsStoragePojo.setTimes(newTimes);
        return statisticsStoragePojo;
    }
}
