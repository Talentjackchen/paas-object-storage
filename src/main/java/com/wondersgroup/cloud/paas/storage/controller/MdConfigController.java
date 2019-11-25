package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.service.MdConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author chenlong
 */
@RestController
@RequestMapping(value = "/mdConfig")
public class MdConfigController {

    @Autowired
    private MdConfigService mdConfigService;

    @GetMapping("getNames")
    public ResultMap getMapByParentId(@RequestParam String parentId) {
        Map<String, String> map = mdConfigService.getMapByParentId(parentId);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, map);
    }
}
