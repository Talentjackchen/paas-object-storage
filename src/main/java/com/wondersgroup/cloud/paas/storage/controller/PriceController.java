package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.pojo.PricePojo;
import com.wondersgroup.cloud.paas.storage.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenlong
 */
@RestController
@RequestMapping("price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("list")
    public ResultMap list() {
        List<PricePojo> list = priceService.list();
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, list);
    }

    @PutMapping("update")
    public ResultMap update(@RequestBody List<PricePojo> list) {
        priceService.update(list);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS);
    }
}
