package com.wondersgroup.cloud.paas.storage.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Constants;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;
import okhttp3.MediaType;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 七牛Http请求
 */
public class HttpClientUtils {

    /**
     * 七牛post请求 QBox
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T  doPost(String url, Map<String, Object> params, Account account, String contentType, Class<T> clazz) {
        Response postResponse = postAction(url,params,account,contentType);
        if(postResponse == null){
            return null;
        }
        return parseJson(postResponse, clazz);
    }

    /**
     * 七牛post请求 Qiniu
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T  doPostV2(String url, Map<String, Object> params, Account account, String contentType, Class<T> clazz) {
        Response postResponse = postActionV2(url,params,account,contentType);
        if(postResponse == null){
            return null;
        }
        return parseJson(postResponse, clazz);
    }

    /**
     * 七牛post请求 QBox
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo List
     */
    public static <T extends ResponsePojo> List<T> doPostToList(String url, Map<String, Object> params, Account account, String contentType, Class<T> clazz) {
        Response postResponse = postAction(url,params,account,contentType);
        if(postResponse == null){
            return null;
        }
        return parseArray(postResponse,clazz);

    }

    /**
     * 七牛post请求 Qiniu
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo List
     */
    public static <T extends ResponsePojo> List<T> doPostV2ToList(String url, Map<String, Object> params, Account account, String contentType, Class<T> clazz) {
        Response postResponse = postActionV2(url,params,account,contentType);
        if(postResponse == null){
            return null;
        }
        return parseArray(postResponse,clazz);

    }

    /**
     * 七牛get请求 QBox
     * @param url 路径（不包含参数）
     * @param params 参数
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T doGet(String url,Map<String, Object> params,Account account, Class<T> clazz) {
        Response postResponse = getAction(connectParams(url,params),account);
        if(postResponse == null){
            return null;
        }
        return parseJson(postResponse, clazz);
    }

    /**
     * 七牛get请求 QBox
     * @param url 路径（不包含参数）
     * @param params 参数
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo List
     */
    public static <T extends ResponsePojo> List<T> doGetToList(String url,Map<String, Object> params,Account account, Class<T> clazz) {
        Response postResponse = getAction(connectParams(url,params),account);
        if(postResponse == null){
            return null;
        }
        return parseArray(postResponse,clazz);
    }


    /**
     * 七牛get请求 Qiniu
     * @param url 路径（不包含参数）
     * @param params 参数
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo List
     */
    public static <T extends ResponsePojo> List<T> doGetV2ToList(String url,Map<String, Object> params,Account account, Class<T> clazz) {
        Response postResponse = getActionV2(connectParams(url,params),account);
        if(postResponse == null){
            return null;
        }
        return parseArray(postResponse,clazz);
    }

    /**
     * 七牛get请求 QBox
     * @param url 路径（包含参数）
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T doGet(String url,Account account, Class<T> clazz) {
        Response postResponse = getAction(url,account);
        if(postResponse == null){
            return null;
        }
        return parseJson(postResponse, clazz);
    }

    /**
     * 七牛get请求 QBox
     * @param url 路径（包含参数）
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo List
     */
    public static <T extends ResponsePojo> List<T> doGetToList(String url,Account account, Class<T> clazz) {
        Response postResponse = getAction(url,account);
        if(postResponse == null){
            return null;
        }
        return parseArray(postResponse,clazz);
    }

    /**
     * 七牛get请求 Qiniu
     * @param url 路径（包含参数）
     * @param account 密钥对象
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T doGetV2(String url,Account account, Class<T> clazz) {
        Response postResponse = getActionV2(url,account);
        if(postResponse == null){
            return null;
        }
        return parseJson(postResponse, clazz);
    }

    /**
     * 参数拼接
     * @param url 请求URL
     * @param params 参数
     * @return
     */
    public static String connectParams(String url,Map<String, Object> params){
        StringBuilder urlBuilder = new StringBuilder(url);
        int i = 0;
        if(params != null && params.size() > 0){
            for(Map.Entry<String, Object> entry : params.entrySet()){
                urlBuilder.append((i == 0) ? "?" : "&");
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                i++;
            }
        }
        return urlBuilder.toString();
    }

    /**
     * 七牛delete请求 QBox
     * @param url 路径
     * @param account 密钥对象
     * @return ResponsePojo
     */
    public static ResponsePojo doDelete(String url,Account account) {
        if(account == null || StringUtils.isBlank(url)){
            ResponsePojo response = new ResponsePojo();
            response.setCode(CommonConstant.ERROR);
            response.setError(CommonConstant.DATA_CHECK_FAILURE_MSG);
            return response;
        }
        ResponsePojo response = new ResponsePojo();
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            Client client = new Client();
            StringMap headers = auth.authorization(url);
            Response delResponse = client.delete(url,headers);
            response.setCode(delResponse.statusCode);
            response.setError(delResponse.error);
            return response;
        }catch(QiniuException e){
            if(e.response != null){
                response.setCode(e.response.statusCode);
                response.setError(e.response.error);
            }else{
                response.setCode(CommonConstant.ERROR);
                response.setError(CommonConstant.RESULT_ERROR);
            }
            return response;
        }
    }

    /**
     * 七牛delete请求 Qiniu
     * @param url 路径
     * @param account 密钥对象
     * @return ResponsePojo
     */
    public static ResponsePojo doDeleteV2(String url,Account account) {
        if(account == null || StringUtils.isBlank(url)){
            ResponsePojo response = new ResponsePojo();
            response.setCode(CommonConstant.ERROR);
            response.setError(CommonConstant.DATA_CHECK_FAILURE_MSG);
            return response;
        }
        ResponsePojo response = new ResponsePojo();
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            Client client = new Client();
            StringMap headers = auth.authorizationV2(url,CommonConstant.REQUEST_TYPE_DELETE,null,null);
            Response delResponse = client.delete(url,headers);
            response.setCode(delResponse.statusCode);
            response.setError(delResponse.error);
            return response;
        }catch(QiniuException e){
            if(e.response != null){
                response.setCode(e.response.statusCode);
                response.setError(e.response.error);
            }else{
                response.setCode(CommonConstant.ERROR);
                response.setError(CommonConstant.RESULT_ERROR);
            }
            return response;
        }
    }

    /**
     * 七牛put请求 QBox
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T doPut(String url,Map<String, Object> params,Account account,String contentType, Class<T> clazz) {
        Response putResponse = putAction(url,params,account,contentType);
        if(putResponse == null){
            return null;
        }
        return parseJson(putResponse, clazz);
    }

    /**
     * 七牛put请求 Qiniu
     * @param url 路径
     * @param params 可以是哈希Map
     * @param account 密钥对象
     * @param contentType application/octet-stream  application/json（默认）  application/x-www-form-urlencoded
     * @param clazz 返回的泛型类
     * @return ResponsePojo JSON
     */
    public static <T extends ResponsePojo> T doPutV2(String url,Map<String, Object> params,Account account,String contentType, Class<T> clazz) {
        Response putResponse = putActionV2(url,params,account,contentType);
        if(putResponse == null){
            return null;
        }
        return parseJson(putResponse, clazz);
    }


    private static Response putAction(String url, Map<String, Object> params, Account account, String contentType){
        if(account == null || StringUtils.isBlank(url)){
           return null;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            byte[] bodyByte =  Json.encode(params).getBytes(Constants.UTF_8);
            StringMap headers = auth.authorization(url, bodyByte,contentType);
            MediaType t = MediaType.parse(contentType);
            Builder requestBuilder = new Builder().url(url).put(RequestBody.create(t, bodyByte));
            return new Client().send(requestBuilder, headers);
        }catch(QiniuException e){
           return e.response;
        }
    }

    private static Response putActionV2(String url, Map<String, Object> params, Account account, String contentType){
        if(account == null || StringUtils.isBlank(url)){
            return null;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            byte[] bodyByte =  Json.encode(params).getBytes(Constants.UTF_8);
            StringMap headers = auth.authorizationV2(url, CommonConstant.REQUEST_TYPE_PUT, bodyByte,contentType);
            MediaType t = MediaType.parse(contentType);
            Builder requestBuilder = new Builder().url(url).put(RequestBody.create(t, bodyByte));
            return new Client().send(requestBuilder, headers);
        }catch(QiniuException e){
            return e.response;
        }
    }

    private static Response postAction(String url, Map<String, Object> params, Account account, String contentType){
        if(account == null || StringUtils.isBlank(url)){
            return null;
        }
        if(StringUtils.isBlank(contentType)){
            contentType = CommonConstant.CONTENT_TYPE_JSON;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            byte[] bodyByte =  Json.encode(params).getBytes(Constants.UTF_8);
            StringMap headers = auth.authorization(url, bodyByte,contentType);
            return new Client().post(url,bodyByte,headers,contentType);
        }catch(QiniuException e){
            return e.response;
        }
    }

    private static Response postActionV2(String url, Map<String, Object> params, Account account, String contentType){
        if(account == null || StringUtils.isBlank(url)){
            return null;
        }
        if(StringUtils.isBlank(contentType)){
            contentType = CommonConstant.CONTENT_TYPE_JSON;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            byte[] bodyByte =  Json.encode(params).getBytes(Constants.UTF_8);
            StringMap headers = auth.authorizationV2(url, CommonConstant.REQUEST_TYPE_POST, bodyByte,contentType);
            return new Client().post(url,bodyByte,headers,contentType);
        }catch(QiniuException e){
            return e.response;
        }
    }

    private static Response getAction(String url,Account account){
        if(account == null || StringUtils.isBlank(url)){
            return null;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            StringMap headers = auth.authorization(url);
            return  new Client().get(url,headers);
        }catch(QiniuException e){
            return e.response;
        }
    }

    private static Response getActionV2(String url,Account account){
        if(account == null || StringUtils.isBlank(url)){
            return null;
        }
        try{
            Auth auth = Auth.create(account.getAccessKey(),account.getSecretKey());
            StringMap headers = auth.authorizationV2(url);
            return  new Client().get(url,headers);
        }catch(QiniuException e){
            return e.response;
        }
    }

    private static <T extends ResponsePojo> T parseJson(Response postResponse,Class<T> clazz){
        ResponsePojo response = new ResponsePojo();
        try{
            String responseMsg = postResponse.bodyString();
            if(StringUtils.isNotBlank(responseMsg) && !"{}".equals(responseMsg)){
                JSONObject responseJson = JSONObject.parseObject(responseMsg);
                return JSONObject.toJavaObject(responseJson, clazz);
            }else{
                response.setCode(postResponse.statusCode);
                response.setError(postResponse.error);
            }
        }catch(Exception e){
            response.setCode(postResponse.statusCode);
            response.setError(postResponse.error);
        }
        String jsonStr = JsonUtils.getJsonString(response);
        JSONObject responseJson = JSONObject.parseObject(jsonStr);
        return JSONObject.toJavaObject(responseJson, clazz);
    }

    private static <T extends ResponsePojo> List<T>  parseArray(Response postResponse,Class<T> clazz){
        ResponsePojo response = new ResponsePojo();
        try{
            String responseMsg = postResponse.bodyString();
            if(StringUtils.isNotBlank(responseMsg) && !"{}".equals(responseMsg)){
                JSONArray responseJson = JSONArray.parseArray(responseMsg);
                return responseJson.toJavaList(clazz);
            }else{
                response.setCode(postResponse.statusCode);
                response.setError(postResponse.error);
            }
        }catch(Exception e){
            response.setCode(postResponse.statusCode);
            response.setError(postResponse.error);
        }
        List<T> list = new ArrayList<>();
        String jsonStr = JsonUtils.getJsonString(response);
        JSONObject responseJson = JSONObject.parseObject(jsonStr);
        list.add(JSONObject.toJavaObject(responseJson, clazz));
        return list;
    }
}
