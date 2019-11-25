package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.storage.constant.ResourceStatisticConstant;
import com.wondersgroup.cloud.paas.storage.enums.PriceItemEnum;
import com.wondersgroup.cloud.paas.storage.enums.RequestEnum;
import com.wondersgroup.cloud.paas.storage.enums.StorageTypeEnum;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatistic;
import com.wondersgroup.cloud.paas.storage.pojo.ResourceCharge;
import com.wondersgroup.cloud.paas.storage.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenlong
 */
@Service
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private ResourceStatisticService resourceStatisticService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private RequestStatisticService requestStatisticService;

    @Value("${service.application.id}")
    private String serviceApplicationId;

    @Autowired
    private static Logger logger = Logger.getLogger(ChargeService.class);

    /**
     * 价格以GB计算
     */
    private int gb = 1024 * 1024 * 1024;

    /**
     * 次数以万计算
     */
    private int tenThousand = 10000;

    /**
     * 计算空间存储量费用
     * @param projectId
     * @param beginDate 出账开始时间
     * @param endDate   出账结束时间
     * @return
     */
    private Map<String, Double> calculateStorageCost(String projectId, Date beginDate, Date endDate) {
        Map<String, Double> map = new HashMap<>();
        Map<String, Double> standardCostMap = standardStorageCost(projectId, beginDate, endDate);
        Map<String, Double> lowCostMap = lowStorageCost(projectId, beginDate, endDate);
        map.putAll(standardCostMap);
        map.putAll(lowCostMap);
        return map;
    }

    /**
     * 计算标频存储量计费
     *
     * @param projectId
     * @return
     */
    private Map<String, Double> standardStorageCost(String projectId, Date beginDate, Date endDate) {
        Map<String, Double> storageCostMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        /* 统计截至日期项目空间总存储量*/
        long lastMonthStorage = resourceStatisticService.getPreStorageByDate(projectId, beginDate, StorageTypeEnum.COMMON.value());

        /* 每月平均存储量 */
        long averageStorage = 0;
        /* 存储空间费用 */
        double cost = 0;
        List<ResourceStatistic> list = resourceStatisticService.getListByDate(projectId, beginDate, endDate, StorageTypeEnum.COMMON.value());
        if (list != null && list.size() > 0) {
            /* 构建资源统计集合 */
            Map<Integer, List<ResourceStatistic>> resourceStatisticMap = buildResourceStatistic(list);
            /* 当月每天存储量集合 */
            Map<Integer, Long> map = new HashMap<Integer, Long>();
            /* 当月每天存储量峰值集合 */
            Map<Integer, Long> peakValueMap = new HashMap<Integer, Long>();

            /* 截至时间当月的天数 原则最大天数即为月份天数，但是当传入的时间为当天删除空间的时，最大天数即为当日在月份中的天数*/
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            int maxDay = calendar.getMaximum(Calendar.DATE);

            for (int day = 1; day <= maxDay; day++) {
                if (day == 1) {
                    /* 每月1号当天空间资源存储量为上一个月空间存储量 */
                    map.put(day, lastMonthStorage);
                } else {
                    /* 每月其他当天空间资源存储量初始值为前一天资源存储量 */
                    map.put(day, map.get(day - 1));
                }

                /* 每天存储峰值初始值为当天空间存储量初始值 */
                peakValueMap.put(day, map.get(day));

                List<ResourceStatistic> resourceStatisticList = resourceStatisticMap.get(day);

                /* 统计每天存储量峰值*/
                statisticsPeakValue(peakValueMap, map, resourceStatisticList, day, StorageTypeEnum.COMMON.value());
            }

            /* 当月每天存储量峰值之和 */
            long allPeakValue = 0;
            for (long value : peakValueMap.values()) {
                allPeakValue += value;
            }

            /* 每月平均存储量 ＝ 当月每天存储量峰值之和 ÷ 当月天数 */
            averageStorage = allPeakValue / maxDay;
        } else {
            averageStorage = lastMonthStorage;
        }

        storageCostMap.put(ResourceStatisticConstant.STAND_NUMBER_KEY, new BigDecimal(averageStorage).divide(BigDecimal.valueOf(gb), 4, RoundingMode.HALF_UP).doubleValue());
        return storageCostMap;
    }

    /**
     * 计算低频存储量费用
     *
     * @param projectId
     * @return
     */
    private Map<String, Double> lowStorageCost(String projectId, Date beginDate, Date endDate) {
        Map<String, Double> storageCostMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        /* 每月平均存储量 */
        long averageStorage = 0;
        /* 存储空间费用 */
        double cost = 0;
        /* 统计截至日期项目空间总存储量*/
        long lastMonthStorage = resourceStatisticService.getPreStorageByDate(projectId, beginDate, StorageTypeEnum.INFREQUENCY.value());

        /* 截至时间当月的天数 原则最大天数即为月份天数，但是当传入的时间为当天删除空间的时，最大天数即为当日在月份中的天数*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        int maxDay = calendar.getMaximum(Calendar.DATE);

        /* 初始化起始时间 */
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);

        /* 当月每天存储量峰值集合 */
        Map<Integer, Long> peakValueMap = new HashMap<Integer, Long>();
        /* 当月每天存储量集合 */
        Map<Integer, Long> map = new HashMap<Integer, Long>();

        List<ResourceStatistic> list = resourceStatisticService.getListByDate(projectId, beginDate, endDate, StorageTypeEnum.INFREQUENCY.value());
        if (list != null && list.size() > 0) {
            /* 构建资源统计集合 */
            Map<Integer, List<ResourceStatistic>> resourceStatisticMap = buildResourceStatistic(list);
            for (int day = 1; day <= maxDay; day++) {
                beginCalendar.set(Calendar.DATE, day);
                Date date = beginCalendar.getTime();
                /* 当天之前的资源量*/
                long lastDayStorage = resourceStatisticService.getPreStorageByDate(projectId, DateUtils.getDateByDay(date, -1), StorageTypeEnum.INFREQUENCY.value());
                /* 计算60天内删除的低频资源存储量 */
                long deleteStorage = calculateDeleteLowStorage(projectId, date);
                lastDayStorage += deleteStorage;

                map.put(day, lastDayStorage);
                /* 每天存储峰值初始值为当天空间存储量初始值 */
                peakValueMap.put(day, lastDayStorage);

                List<ResourceStatistic> resourceStatisticList = resourceStatisticMap.get(day);
                /* 统计每天存储量峰值*/
                statisticsPeakValue(peakValueMap, map, resourceStatisticList, day, StorageTypeEnum.INFREQUENCY.value());
            }
            /* 当月每天存储量峰值之和 */
            long allPeakValue = 0;
            for (long value : peakValueMap.values()) {
                allPeakValue += value;
            }

            /* 每月平均存储量 ＝ 当月每天存储量峰值之和 ÷ 当月天数 */
            averageStorage = allPeakValue / maxDay;
        } else {
            averageStorage = lastMonthStorage;
        }

        storageCostMap.put(ResourceStatisticConstant.LOW_NUMBER_KEY, new BigDecimal(averageStorage).divide(BigDecimal.valueOf(gb), 4, RoundingMode.HALF_UP).doubleValue());
        return storageCostMap;
    }


    /**
     * 统计峰值
     *
     * @param peakValueMap
     * @param map
     * @param resourceStatisticList
     * @param day
     * @param type
     */
    private void statisticsPeakValue(Map<Integer, Long> peakValueMap, Map<Integer, Long> map, List<ResourceStatistic> resourceStatisticList, int day, String type) {
        if (resourceStatisticList != null) {
            Date date = resourceStatisticList.get(0).getRecordTime();
            Date sixtyDate = DateUtils.getDateByDay(date, -60);
            long storage = map.get(day);
            for (ResourceStatistic resourceStatistic : resourceStatisticList) {
                if ("0".equals(resourceStatistic.getOperation())) {
                    /* 低频当天删除且为60天前上传的资源参与容量统计 */
                    if (type.equals(StorageTypeEnum.INFREQUENCY.value()) && resourceStatistic.getAddRecordTime().getTime() > sixtyDate.getTime()) {
                        continue;
                    }
                    storage = storage - resourceStatistic.getFileSize();
                    map.put(day, storage);
                } else {
                    storage = storage + resourceStatistic.getFileSize();
                    map.put(day, storage);
                    long peakValue = peakValueMap.get(day);
                    if (storage > peakValue) {
                        peakValueMap.put(day, storage);
                    }
                }
            }
        }
    }

    /**
     * 计算put/delete请求费用
     *
     * @param buckets
     * @param beginDate
     * @param endDate
     * @return
     */
    private Map<String, Double> calculateApiRequestCost(List<Bucket> buckets, Date beginDate, Date endDate) throws Exception {
        Map<String, Double> priceMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String begin = sdf.format(beginDate);
        String end = sdf.format(endDate);
        try {
            BigDecimal charging = new BigDecimal(tenThousand);
            int lowHits = 0;
            int standardHits = 0;
            for (Bucket bucket : buckets) {
                if (ValidStatusEnum.VALID.value().equals(bucket.getRemoteFlag())) {
                    //低频
                    lowHits += statisticsService.getPUTRequestsNumberByMonth(begin, end, bucket.getBucketId(), StorageTypeEnum.INFREQUENCY.value(), "");

                    //标准
                    standardHits += statisticsService.getPUTRequestsNumberByMonth(begin, end, bucket.getBucketId(), StorageTypeEnum.COMMON.value(), "");

                } else {
                    /* 从库中获取删除的空间Put/Delete请求次数 */
                    lowHits += requestStatisticService.getRequestStatisticAmount(bucket.getBucketId(), beginDate, endDate, RequestEnum.PUT_DELETE.value(), StorageTypeEnum.INFREQUENCY.value());
                    standardHits += requestStatisticService.getRequestStatisticAmount(bucket.getBucketId(), beginDate, endDate, RequestEnum.PUT_DELETE.value(), StorageTypeEnum.COMMON.value());
                }
            }


            BigDecimal lowNumber = BigDecimal.valueOf(lowHits);
            BigDecimal standardNumber = BigDecimal.valueOf(standardHits);
            priceMap.put(ResourceStatisticConstant.LOW_NUMBER_KEY, lowNumber.divide(charging, 4, RoundingMode.HALF_UP).doubleValue());
            priceMap.put(ResourceStatisticConstant.STAND_NUMBER_KEY, standardNumber.divide(charging, 4, RoundingMode.HALF_UP).doubleValue());
        } catch (Exception ex) {
            logger.error(ex);
            throw new BusinessException(CommonConstant.ERROR, ex.getMessage(), ex.getCause());
        }
        return priceMap;
    }

    /**
     * 计算get请求费用
     *
     * @param buckets
     * @param beginDate
     * @param endDate
     * @return
     */
    private Map<String, Double> calculateGetRequestCost(List<Bucket> buckets, Date beginDate, Date endDate) throws Exception {
        Map<String, Double> priceMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String begin = sdf.format(beginDate);
        String end = sdf.format(endDate);
        try {
            BigDecimal charging = new BigDecimal(tenThousand);
            int lowHits = 0;
            int standardHits = 0;
            for (Bucket bucket : buckets) {
                if (ValidStatusEnum.VALID.value().equals(bucket.getRemoteFlag())) {
                    //低频
                    lowHits += statisticsService.getGETRequestsNumberByMonth(begin, end, bucket.getBucketId(), "", StorageTypeEnum.INFREQUENCY.value(), "");

                    //标准
                    standardHits += statisticsService.getGETRequestsNumberByMonth(begin, end, bucket.getBucketId(), "", StorageTypeEnum.COMMON.value(), "");

                } else {
                    /* 从库中获取删除的空间get请求 */
                    lowHits += requestStatisticService.getRequestStatisticAmount(bucket.getBucketId(), beginDate, endDate, RequestEnum.GET.value(), StorageTypeEnum.INFREQUENCY.value());
                    standardHits += requestStatisticService.getRequestStatisticAmount(bucket.getBucketId(), beginDate, endDate, RequestEnum.GET.value(), StorageTypeEnum.COMMON.value());
                }
            }

            BigDecimal lowNumber = BigDecimal.valueOf(lowHits);
            BigDecimal standardNumber = BigDecimal.valueOf(standardHits);
            priceMap.put(ResourceStatisticConstant.LOW_NUMBER_KEY, lowNumber.divide(charging, 4, RoundingMode.HALF_UP).doubleValue());
            priceMap.put(ResourceStatisticConstant.STAND_NUMBER_KEY, standardNumber.divide(charging, 4, RoundingMode.HALF_UP).doubleValue());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new BusinessException(CommonConstant.ERROR, ex.getMessage(), ex);
        }
        return priceMap;
    }

    /**
     * 计算低频/标频转换费用
     * @param buckets
     * @param beginDate
     * @param endDate
     * @return
     */
    private Map<String, Double> calculateExchangeCost(List<Bucket> buckets, Date beginDate, Date endDate) {
        double cost = 0;
        Map<String, Double> map = new HashMap<>();
        long allHit = 0;
        for (Bucket bucket : buckets) {
            if (ValidStatusEnum.VALID.value().equals(bucket.getRemoteFlag())) {
                allHit += requestStatisticService.getRealTimeExchangeStatisticAmount(bucket.getName(), beginDate, endDate);
            } else {
                allHit += requestStatisticService.getExchangeStatisticAmount(bucket.getBucketId(), beginDate, endDate);
            }
        }

        map.put(ResourceStatisticConstant.LOW_NUMBER_KEY, BigDecimal.valueOf(allHit).divide(BigDecimal.valueOf(tenThousand), 4, RoundingMode.HALF_UP).doubleValue());
        return map;
    }


    /**
     * 构建资源统计信息，以当天号码作为key
     *
     * @param list
     * @return
     */
    private Map<Integer, List<ResourceStatistic>> buildResourceStatistic(List<ResourceStatistic> list) {
        Map<Integer, List<ResourceStatistic>> map = new HashMap<>();
        for (ResourceStatistic resourceStatistic : list) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(resourceStatistic.getRecordTime());
            int dayKey = calendar.get(Calendar.DATE);
            List<ResourceStatistic> resourceStatisticList;
            if (!map.containsKey(dayKey)) {
                resourceStatisticList = new ArrayList<ResourceStatistic>();
                map.put(dayKey, resourceStatisticList);
            } else {
                resourceStatisticList = map.get(dayKey);
            }
            resourceStatisticList.add(resourceStatistic);
        }

        return map;
    }

    /**
     * 计算60天内删除的低频资源存储量
     * @return
     */
    private long calculateDeleteLowStorage(String projectId, Date date) {
        List<ResourceStatistic> list = resourceStatisticService.getDeleteListForSixtyDate(projectId, date, StorageTypeEnum.INFREQUENCY.value());
        long deleteStorage = 0;
        if (list != null && list.size() > 0) {
            for (ResourceStatistic resourceStatistic : list) {
                deleteStorage += resourceStatistic.getFileSize();
            }
        }

        return deleteStorage;
    }

    @Override
    public List<ResourceCharge> calculateResourceCharge(Date beginDate, Date endDate) {
        List<ResourceCharge> list = new ArrayList<>();
        List<String> projectIds = bucketService.getProjectIds();
        if (!CollectionUtils.isEmpty(projectIds)) {
            projectIds.parallelStream().forEach(projectId -> {
                ResourceCharge resourceCharge = new ResourceCharge();
                resourceCharge.setProjectId(projectId);
                Map<String, BigDecimal> fetchMeterMap = new HashMap<>();
                try {
                    List<Bucket> buckets = bucketService.getListByProjectId(projectId);
                    resourceCharge.setAccountId(buckets.get(0).getAccountId());
                    Double standardNum;
                    Double lowNum;

                    /* 空间存储量计费 */
                    Map<String, Double> storageCostMap = calculateStorageCost(projectId, beginDate, endDate);
                    standardNum = storageCostMap.get(ResourceStatisticConstant.STAND_NUMBER_KEY);
                    lowNum = storageCostMap.get(ResourceStatisticConstant.LOW_NUMBER_KEY);
                    fetchMeterMap.put(PriceItemEnum.STANDARD_STORAGE_Z0.value(), BigDecimal.valueOf(standardNum));
                    fetchMeterMap.put(PriceItemEnum.LOW_STORAGE_Z0.value(), BigDecimal.valueOf(lowNum));

                    /* get请求计费 */
                    Map<String, Double> getMap = calculateGetRequestCost(buckets, beginDate, endDate);
                    standardNum = getMap.get(ResourceStatisticConstant.STAND_NUMBER_KEY);
                    lowNum = getMap.get(ResourceStatisticConstant.LOW_NUMBER_KEY);
                    fetchMeterMap.put(PriceItemEnum.STANDARD_GET_REQUEST.value(), BigDecimal.valueOf(standardNum));
                    fetchMeterMap.put(PriceItemEnum.LOW_GET_REQUEST.value(), BigDecimal.valueOf(lowNum));

                    /* put/delete请求计费 */
                    Map<String, Double> apiMap = calculateApiRequestCost(buckets, beginDate, endDate);
                    standardNum = apiMap.get(ResourceStatisticConstant.STAND_NUMBER_KEY);
                    lowNum = apiMap.get(ResourceStatisticConstant.LOW_NUMBER_KEY);
                    fetchMeterMap.put(PriceItemEnum.STANDARD_API_REQUEST.value(), BigDecimal.valueOf(standardNum));
                    fetchMeterMap.put(PriceItemEnum.LOW_API_REQUEST.value(), BigDecimal.valueOf(lowNum));

                    /* 存储低频/标频转换计费 */
                    Map<String, Double> exchangeMap = calculateExchangeCost(buckets, beginDate, endDate);
                    lowNum = exchangeMap.get(ResourceStatisticConstant.LOW_NUMBER_KEY);
                    fetchMeterMap.put(PriceItemEnum.EXCHANGE.value(), BigDecimal.valueOf(lowNum));

                    resourceCharge.setFetchMeterMap(fetchMeterMap);
                    list.add(resourceCharge);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            });
        }

        return list;
    }
}
