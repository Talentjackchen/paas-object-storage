package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.PageUtils;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.controller.common.CommonResourceController;
import com.wondersgroup.cloud.paas.storage.model.Resource;
import com.wondersgroup.cloud.paas.storage.service.ResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 七牛资源操作
 */
@RestController
@RequestMapping(value = "/resource")
public class ResourceController extends CommonResourceController {

    private Logger logger = Logger.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    /**
     * 列表检索数据
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param fileSaveType 存储类型
     * @param page 页数
     * @param rows 条数
     * @param sidx 排序字段
     * @param sord 排序方式
     * @return 有效数据
     */
    @GetMapping("/page")
    public ResultMap page(@RequestParam(required = false) String fileName,
                          @RequestParam(required = false) String fileType,
                          @RequestParam(required = false) String fileSaveType,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer rows,
                          @RequestParam(required = false) String sidx,
                          @RequestParam(required = false) String sord,
                          HttpServletRequest request){
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);
        int pageNum = PageUtils.getParamToInt(page, CommonConstant.PAGENUM);
        int pageSize = PageUtils.getParamToInt(rows, CommonConstant.PAGESIZE);
        String orderByClause = PageUtils.getOrderByClause(sidx, sord, CommonConstant.ORDERBYCLAUSE);

        ResultMap resultMap = new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, resourceService.page(bucketId,fileName,fileType,fileSaveType,pageNum,pageSize,orderByClause));
        return resultMap;
    }

    /**
     * 获取单个资源信息
     * @param key
     * @return
     */
    @GetMapping("getById")
    public ResultMap getById(@RequestParam String key,HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        Resource resource = resourceService.getResourceByBucketIdAndName(bucketId,key);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, resource);
    }

}
