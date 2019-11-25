package com.wondersgroup.cloud.paas.storage.service;

import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.storage.model.Bucket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zhangyongzhao
 * bucket中心
 */
public interface BucketService  {

    /**
     *  创建bucket
     * @param bucket 对象
     */
    void create(Bucket bucket)throws Exception;

    /**
     *  删除bucket
     * @param bucketId 数据ID
     */
    void delete(String bucketId) throws Exception;

    /**
     * 查询所有有效的空间数据
     * @param projectId 项目ID
     * @return
     */
    List<Bucket> list(String projectId);

    /**
     * 根据ID查询方法
     * @param bucketId
     * @returnl
     */
    Bucket getById(String bucketId);

    /**
     * 根据bucketId并且七牛云上未删除查询数据
     *
     * @param bucketId
     * @return
     */
    Bucket getByIdAndRemoteFlag(String bucketId);

    /**
     * 分页查询
     * @param name 名称
     * @param projectId 项目ID
     * @param accountId 账号ID
     * @param type 空间类型
     * @param pageNum 分页条数
     * @param pageSize 当前页条数
     * @param orderByClause 排序字段
     * @return
     */
    PageInfo<Bucket> page(String name, String projectId, String accountId, String type,
                          Integer pageNum, Integer pageSize, String orderByClause) throws Exception;


    /**
     * 校验名称是否重复,重复返回true,不重复返回false
     * @param name
     * @return
     */
    boolean checkName(String name,String projectId);


    void editByExample(Bucket bucket);

    /**
     * 设置空间权限
     *
     * @param bucketId 空间ID
     * @param acl      权限 1 私有  0公开
     */
    void setPermission(String bucketId, int acl) throws Exception;

    /**
     * 临时转正式空间，或者修改域名
     *
     * @param bucketId 空间ID
     */
    void setFreeDomain(String bucketId) throws Exception;

    /**
     * 根据项目ID查询以存储区域分组的数据
     *
     * @param projectId 项目ID
     * @return
     */
    Map<String, List<Bucket>> getByProjectId(String projectId);

    /**
     * 获取项目空间
     *
     * @param projectId
     * @return
     */
    List<Bucket> getListByProjectId(String projectId);

    /**
     * 逻辑删除时间大于20天未绑定域名的空间
     */
    void deleteByCreateDate(Date date);

    /**
     * 据创建时间查询bucket数据(已被逻辑删除的数据)
     *
     * @return
     */
    List<Bucket> getCompareCreateTime(Date date);

    /**
     * 七牛云上删除bucket
     *
     * @param bucket
     */
    void deleteBucket(Bucket bucket) throws QiniuException, BusinessException;

    /**
     * 根据bucketId查询详细数据
     *
     * @param bucketId 空间ID
     * @return
     * @throws BusinessException
     */
    HashMap<String, Object> getBucketInfo(String bucketId) throws Exception;

    /**
     * 判断是否存在空间（存在返回true,不存在返回false）
     *
     * @param projectId
     * @return
     */
    boolean isExistBucket(String projectId) throws Exception;

    /**
     * 获取所有项目Id
     *
     * @return
     */
    List<String> getProjectIds();
}
