package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.pojo.MessageCreatePojo;
import com.wondersgroup.cloud.paas.storage.pojo.MessageGetPojo;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;
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
@RequestMapping(value = "/message")
public class MessageController {

    @Value("sms.qiniuapi.com")
    private String host;

    @Autowired
    private AccountService accountService;

    @PostMapping("create")
    public String create() {
        Account account = accountService.getAccount();
        String path = "/v1/signature";
        String url = "https://" + host + path;
        Map<String, Object> param = new HashMap<>();
        param.put("signature", "wonders0");
        param.put("source", "enterprises_and_institutions");
        MessageCreatePojo messageCreatePojo = HttpClientUtils.doPostV2(url, param, account, "application/json", MessageCreatePojo.class);
        return messageCreatePojo.getSignature_id();
    }

    @GetMapping("get")
    public String get() {
        Account account = accountService.getAccount();
        String path = "/v1/signature";
        String url = "https://" + host + path;
        MessageGetPojo messageGetPojo = HttpClientUtils.doGetV2(url, account, MessageGetPojo.class);
        return JsonUtils.getJsonString(messageGetPojo);
    }

    @DeleteMapping("delete")
    public void delete() {
        Account account = accountService.getAccount();
        String path = "/v1/signature/1135447195621789696";
        String url = "https://" + host + path;
        HttpClientUtils.doDeleteV2(url, account);
    }

    @PutMapping("update")
    public void update() {
        Account account = accountService.getAccount();
        String path = "/v1/signature/1135447195621789696";
        String url = "https://" + host + path;
        Map<String, Object> param = new HashMap<>();
        param.put("signature", "wonders8");
        HttpClientUtils.doPutV2(url, param, account, "application/json", ResponsePojo.class);
    }

    @PostMapping("send")
    public void send() {
        Account account = accountService.getAccount();
        String path = "/v1/message";
        String url = "https://" + host + path;
        Map<String, Object> param = new HashMap<>();
        param.put("template_id", "1134298389643534336");
        String[] mobiles = new String[]{"18921843520"};
        param.put("mobiles", mobiles);
        Map<String, String> map = new HashMap<>();
        map.put("code", "300168");
        map.put("msg", "万达短信服务");
        param.put("parameters", map);
        HttpClientUtils.doPostV2(url, param, account, "application/json", MessageCreatePojo.class);
    }
}
