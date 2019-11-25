package com.wondersgroup.cloud.paas.storage.tools;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlong
 * 空间管理工具
 */
public class ResourceMangerTool {

    private Map<String,Zone> zoneMap = new HashMap<>();
    private static ResourceMangerTool instance;

    private ResourceMangerTool(){
        zoneMap.put("z0",Zone.huadong());
        zoneMap.put("z1",Zone.huabei());
        zoneMap.put("z2",Zone.huanan());
        zoneMap.put("na0",Zone.beimei());
        zoneMap.put("as0",Zone.xinjiapo());
    }

    public static ResourceMangerTool getInstance(){
        if(instance == null){
            synchronized (ResourceMangerTool.class) {
                if (instance == null) {
                    instance = new ResourceMangerTool();
                }
            }
        }
        return instance;
    }

    /**
     * 构建空间管理对象
     * @param accessKey 密钥对
     * @param secretKey 密钥对
     * @return
     */
    public BucketManager buildBucketManger(String accessKey,String secretKey){
        return buildBucketManger(accessKey,secretKey,"");
    }

    /**
     * 构建空间管理对象
     * @param accessKey 密钥对
     * @param secretKey 密钥对
     * @param region 区域
     * @return
     */
    public BucketManager buildBucketManger(String accessKey,String secretKey,String region){
        Auth auth = Auth.create(accessKey,secretKey);
        Configuration configuration;
        if(StringUtils.isNullOrEmpty(region)){
            configuration = new Configuration(zoneMap.get(region));
        }else{
            configuration = new Configuration();
        }
        BucketManager bucketManager = new BucketManager(auth,configuration);
        return bucketManager;
    }

    public Configuration buildConfiguration(String region){
        if(StringUtils.isNullOrEmpty(region)){
            return new Configuration(zoneMap.get(region));
        }else{
            return new Configuration();
        }
    }


    /**
     * 返回文件大小数值和单位
     * @param size 文件大小
     * @return
     */
    public  static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.2f GB", (float) size / gb);
        } else if (size >= mb) {
            return String.format("%.2f MB", (float) size / mb);
        } else if (size >= kb) {
            return String.format("%.2f KB", (float) size / kb);
        } else
            return String.format("%d B", size);
    }
}
