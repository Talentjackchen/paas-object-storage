package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.constant.StatisticsConstant;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsRequestPojo;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsStoragePojo;
import com.wondersgroup.cloud.paas.storage.service.StatisticsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 统计controller
 * zhangyongzhao
 */
@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    private static Logger logger = Logger.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取get请求次数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param granularity 时间颗粒度
     * @param domain 空间域名
     * @param storageType 存储类型
     * @param region 存储区域
     * @return
     */
    @GetMapping("/GETRequests")
    public ResultMap getGETRequestsNumber(@RequestParam String beginDate,
                                          @RequestParam String endDate,
                                          @RequestParam String bucketId,
                                          @RequestParam (required = false)String granularity,
                                          @RequestParam(required = false) String domain,
                                          @RequestParam(required = false) String storageType,
                                          @RequestParam(required = false) String region) {
        List<StatisticsRequestPojo> statisticsPojoList = new ArrayList<>();
        try{
            statisticsPojoList = statisticsService.getGETRequestsNumberByDay(beginDate, endDate, bucketId,
                    granularity, domain, storageType, region);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            if (e instanceof BusinessException){
                return ((BusinessException)e).getResultMap();
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_QUERY_GET_SUCCESS, statisticsPojoList);
    }

    /**
     * 获取put请求次数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param bucketId 空间ID
     * @param granularity 时间颗粒度
     * @param storageType 存储类型
     * @param region 存储区域
     * @return
     */
    @GetMapping("/PUTRequests")
    public ResultMap getPUTRequestsNumber(@RequestParam String beginDate,
                                          @RequestParam String endDate,
                                          @RequestParam String bucketId,
                                          @RequestParam (required = false)String granularity,
                                          @RequestParam (required = false) String storageType,
                                          @RequestParam(required = false) String region) {
        List<StatisticsRequestPojo> statisticsPojos = new ArrayList<>();
        try{
            statisticsPojos = statisticsService.getPUTRequestsNumberByDay(beginDate, endDate, bucketId, granularity, storageType, region);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            if (e instanceof BusinessException){

                return ((BusinessException)e).getResultMap();
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_QUERY_PUT_SUCCESS, statisticsPojos);
    }

    /**
     * 根据文件类型和项目ID查询文件类型大小和数量
     * @param fileType 文件类型
     * @param projectId 项目ID
     * @return ResultMap
     */
    @GetMapping("/queryCountAndSize")
    public ResultMap queryCountAndSize(@RequestParam (required = false) String fileType,
                                       @RequestParam (required = false) String projectId,
                                       @RequestParam (required = false) String bucketId){
        List<HashMap<String, Object>> hashMaps = statisticsService.queryCountAndSize(fileType,projectId,bucketId);
        return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_QUERY_FILE_SIZE_AND_NUM_SUCCESS, hashMaps);
    }

    /**
     * 获取标准存储的存储量统计
     * @param begin
     * @param end
     * @param bucketId
     * @return
     */
    @GetMapping("/common")
    public ResultMap getCommonSpace(@RequestParam String begin,
                                    @RequestParam String end,
                                    @RequestParam String bucketId){
        StatisticsStoragePojo statisticsStoragePojo = new StatisticsStoragePojo();
        try{
            statisticsStoragePojo = statisticsService.getSpaceStorage(begin, end, bucketId);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            if (e instanceof BusinessException){

                return ((BusinessException)e).getResultMap();
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_COMMON_SUCCESS, statisticsStoragePojo);
    }

    /**
     * 获得存储量统计
     * @param beginDate
     * @param endDate
     * @param bucketId
     * @param noPreDel
     * @param onlyPreDel
     * @return
     */
    @GetMapping("/storageCapacity")
    public ResultMap getInfrequencySpace(@RequestParam String beginDate,
                                         @RequestParam String endDate,
                                         @RequestParam String bucketId,
                                         @RequestParam String storageType,
                                         @RequestParam (required = false) String noPreDel,
                                         @RequestParam (required = false) String onlyPreDel) {
        StatisticsStoragePojo statisticsStoragePojo = new StatisticsStoragePojo();
        try {
            if (storageType.equals("0")) {
                statisticsStoragePojo = statisticsService.getSpaceStorage(beginDate, endDate, bucketId);
            } else if (storageType.equals("1")) {
                statisticsStoragePojo = statisticsService.getSpaceLineStorage(beginDate, endDate, bucketId, noPreDel, onlyPreDel);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                return ((BusinessException) e).getResultMap();
            }
        }
        if (storageType.equals("0")) {
            return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_COMMON_SUCCESS, statisticsStoragePojo);
        } else {
            return new ResultMap(CommonConstant.SUCCESS, StatisticsConstant.MSG_INFREQUENCY_SUCCESS, statisticsStoragePojo);
        }
    }
}
