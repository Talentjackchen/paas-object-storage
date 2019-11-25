package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.MD5Utils;
import com.wondersgroup.cloud.paas.storage.constant.TimestampAclConstant;
import com.wondersgroup.cloud.paas.storage.model.TimestampAcl;
import com.wondersgroup.cloud.paas.storage.pojo.TimestampAclPojo;
import com.wondersgroup.cloud.paas.storage.service.TimestampAclService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenlong
 */
@RestController
@RequestMapping(value = "/timestampAcl")
public class TimestampAclController {

    private static Logger logger = Logger.getLogger(TimestampAclController.class);

    @Autowired
    private TimestampAclService timeAclService;

    @PostMapping("create")
    public ResultMap create(@RequestBody TimestampAclPojo timestampAclPojo, HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        try {
            if (check(timestampAclPojo)) {
                timeAclService.update(bucketId, timestampAclPojo.getMainKey(), timestampAclPojo.getSpareKey());
            } else {
                return new ResultMap(TimestampAclConstant.CHECK_FAIL_CODE, TimestampAclConstant.CHECK_FAIL_MSG);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
        }

        return new ResultMap(CommonConstant.SUCCESS, TimestampAclConstant.UPDATE_SUCCESS_MSG);
    }

    @PutMapping("update")
    private ResultMap update(@RequestBody TimestampAclPojo timestampAclPojo, HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        if (check(timestampAclPojo)) {
            timeAclService.update(bucketId, timestampAclPojo.getMainKey(), timestampAclPojo.getSpareKey());
        } else {
            return new ResultMap(TimestampAclConstant.CHECK_FAIL_CODE, TimestampAclConstant.CHECK_FAIL_MSG);
        }

        return new ResultMap(CommonConstant.SUCCESS, TimestampAclConstant.UPDATE_SUCCESS_MSG);
    }

    @DeleteMapping("delete")
    public ResultMap delete(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        timeAclService.delete(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, TimestampAclConstant.DELETE_SUCCESS_MSG);
    }

    @GetMapping("queryByBucketId")
    public ResultMap queryByBucketId(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        TimestampAcl timestampAcl = timeAclService.queryByBucketId(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, timestampAcl);
    }

    /**
     * 生成时间戳密钥
     * @return
     */
    @GetMapping("generateKey")
    public ResultMap generateKey(){
        TimestampAcl timeAcl = new TimestampAcl();
        timeAcl.setMainKey(CommonUtils.generateId());
        timeAcl.setSpareKey(CommonUtils.generateId());
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS,timeAcl);
    }

    /**
     * 时间戳URL可用性检查
     * @return
     */
    private boolean check(TimestampAclPojo timestampAclPojo) {
        boolean flag = true;
        try{
            String token = "";
            String path = "";
            String url = timestampAclPojo.getUrl();
            if(url.contains(CommonConstant.HTTP_PROTOCOL)){
                url = url.substring(7);
            }else if(path.contains(CommonConstant.HTTPS_PROTOCOL)){
                url = url.substring(8);
            }

            /* 取域名后面的path */
            String pattern = "\\/(.*)$";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(url);
            if(m.find()){
                url = m.group();
            }

            /* Url?前面的path */
            pattern = "([^\\?]*)";
            p = Pattern.compile(pattern);
            m = p.matcher(url);
            if(m.find()){
                path = m.group();
            }

            /* 取Url?后面的path*/
            pattern = "(\\?(.*)$)";
            p = Pattern.compile(pattern);
            m = p.matcher(url);
            if(m.find()){
                Map<String,String> paramMap = new HashMap<String,String>();
                String paramsStr = m.group();
                paramsStr = paramsStr.substring(1);
                String[] params = paramsStr.split("&&");
                for (String param:params) {
                    String[] temp = param.split("=");
                    paramMap.put(temp[0],temp[1]);
                }
                token = paramMap.get("token");
                path = path + "?expire=" + paramMap.get("expire") + "&bucketId=" + paramMap.get("bucketId") + "&resourceName=" +  paramMap.get("resourceName");
            }

            path = URLEncoder.encode(path,"utf-8");

            /* 用主要key验证 */
            String s = String.format("%s%s", timestampAclPojo.getMainKey(), path);
            String sign = MD5Utils.encode(s);
            if(!token.equals(sign)){
                /* 用备用key验证*/
                s = String.format("%s%s", timestampAclPojo.getSpareKey(), path);
                sign = MD5Utils.encode(s);
                if (!token.equals(sign)) {
                    flag = false;
                }
            }
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args){
        String path = "http://10.2.102.124:8080/pass-object-storage/resource/preview?resourceName=1548035079.png&bucketId=78689fbfce3847ec896d798cd2c6904b&expire=1222";
        path = path.substring(7);
        String pattern = "\\/(.*)$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        if(m.find()){
            path = m.group();
            System.out.println(m.group());
        }

        pattern = "(\\?(.*)$)";
        p = Pattern.compile(pattern);
        m = p.matcher(path);
        if(m.find()){
            System.out.println(m.group());
        }

        pattern = "([^\\?]*)";
        p = Pattern.compile(pattern);
        m = p.matcher(path);
        if(m.find()){
            System.out.println(m.group());
        }

        System.out.println(System.currentTimeMillis() + 120000);
    }
}
