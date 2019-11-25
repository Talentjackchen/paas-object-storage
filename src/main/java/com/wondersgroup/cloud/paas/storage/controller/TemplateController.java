package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;
import com.wondersgroup.cloud.paas.storage.pojo.TemplateGetPojo;
import com.wondersgroup.cloud.paas.storage.pojo.TemplatePojo;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import com.wondersgroup.cloud.paas.storage.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlong
 */
@RestController
@RequestMapping(value = "/template")
public class TemplateController {
    @Value("sms.qiniuapi.com")
    private String host;

    @Autowired
    private AccountService accountService;

    @PostMapping("create")
    public String create() {
        Account account = accountService.getAccount();
        String path = "/v1/template";
        String url = "https://" + host + path;
        Map<String, Object> param = new HashMap<>();
        param.put("name", "wondersTemplate");
        param.put("template", "测试模板12");
        param.put("type", "notification");
        param.put("description", "测试模板12");
        param.put("signature_id", "1134287277560696832");
        TemplatePojo templatePojo = HttpClientUtils.doPostV2(url, param, account, "application/json", TemplatePojo.class);
        return templatePojo.getTemplate_id();
    }

    @GetMapping("get")
    public String get() {
        Account account = accountService.getAccount();
        String path = "/v1/template";
        String url = "https://" + host + path;
        TemplateGetPojo templateGetPojo = HttpClientUtils.doGetV2(url, account, TemplateGetPojo.class);
        return JsonUtils.getJsonString(templateGetPojo);
    }

    @DeleteMapping("delete")
    public void delete() {
        Account account = accountService.getAccount();
        String path = "/v1/template/1135460929287229440";
        String url = "https://" + host + path;
        HttpClientUtils.doDeleteV2(url, account);
    }

    @PutMapping("update")
    public void update() {
        Account account = accountService.getAccount();
        String path = "/v1/template/1135460929287229440";
        String url = "https://" + host + path;
        Map<String, Object> param = new HashMap<>();
        param.put("name", "wondersTemplate1");
        param.put("template", "测试模板1");
        param.put("description", "测试模板1");
        param.put("signature_id", "1134287277560696832");
        HttpClientUtils.doPutV2(url, param, account, "application/json", ResponsePojo.class);
    }
}
