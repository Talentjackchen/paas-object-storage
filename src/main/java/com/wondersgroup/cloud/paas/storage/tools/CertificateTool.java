package com.wondersgroup.cloud.paas.storage.tools;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author chenlong
 * 上传下载凭证工具
 */
public class CertificateTool {
    private static Logger logger = Logger.getLogger(CertificateTool.class);


    /**
     * 生成资源下载Url
     * @param accessKey
     * @param secretKey
     * @param publicUrl
     * @return
     */
    public static String generatePrivateDownloadUrl(String accessKey, String secretKey, String publicUrl){
        Auth auth = Auth.create(accessKey, secretKey);
        String url = auth.privateDownloadUrl(publicUrl, CommonConstant.EXPIRE_TIME_DEFAULT);
        return url;
    }

    /**
     * 生成资源下载Url
     * @param accessKey
     * @param secretKey
     * @param publicUrl
     * @param expireInSeconds 失效时间
     * @return
     */
    public static String generatePrivateDownloadUrl(String accessKey, String secretKey, String publicUrl, long expireInSeconds){
        Auth auth = Auth.create(accessKey, secretKey);
        String url = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return url;
    }

    /**
     * 生成公开访问链接
     * @param fileName
     * @param domainOfBucket
     * @return
     */
    public static String generatePublicDownloadUrl(String fileName,String domainOfBucket){
        try{
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            return publicUrl;
        }catch (UnsupportedEncodingException ex){
            logger.error("URL编码utf-8化失败.");
            return null;
        }
    }

    /**
     * 生成凭证
     * @param accessKey
     * @param secretKey
     * @param signingUrl
     * @return
     */
    public static String generateAuthorization(String accessKey, String secretKey,String signingUrl){
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap stringMap = auth.authorization(signingUrl);
        return String.valueOf(stringMap.get("Authorization"));
    }

    /**
     * 生成空间域
     * @param accessKey
     * @param secretKey
     * @param bucket
     * @return
     */
    public static String getDomainOfBucket(String accessKey, String secretKey, String bucket)throws QiniuException{
        Auth auth = Auth.create(accessKey,secretKey);
        Configuration configuration = new Configuration();
        BucketManager bucketManager = new BucketManager(auth,configuration);
        try{
            String[] domains = bucketManager.domainList(bucket);
            return domains[0];
        }catch (QiniuException ex){
            logger.error("获取空间域名失败!",ex);
            throw ex;
        }
    }
}
