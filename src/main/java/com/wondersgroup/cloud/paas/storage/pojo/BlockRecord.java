package com.wondersgroup.cloud.paas.storage.pojo;

import com.wondersgroup.cloud.paas.storage.model.Bucket;

import java.io.InputStream;
import java.io.Serializable;

public class BlockRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int resumeId;//第几个分片
    private String fileName;//文件名称
    private String key;//存储七牛文件名
    private long totalSize;//文件总大小
    private int blockSize;//当前上传块大小
    private String lastModifiedTime;//最后修改时间
    private String storageType;//存储类型

    private Bucket bucket;//空间
    private String redisHashKey;//记录文件信息
    private String redisListKey;//记录文件存储信息
    private String redisIndexKey;//记录上转文件数量
    private InputStream file;//块流
    private boolean isMakeFile;//是否执行合并

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public String getRedisHashKey() {
        return redisHashKey;
    }

    public void setRedisHashKey(String redisHashKey) {
        this.redisHashKey = redisHashKey;
    }

    public String getRedisListKey() {
        return redisListKey;
    }

    public void setRedisListKey(String redisListKey) {
        this.redisListKey = redisListKey;
    }

    public String getRedisIndexKey() {
        return redisIndexKey;
    }

    public void setRedisIndexKey(String redisIndexKey) {
        this.redisIndexKey = redisIndexKey;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public boolean isMakeFile() {
        return isMakeFile;
    }

    public void setMakeFile(boolean makeFile) {
        isMakeFile = makeFile;
    }
}
