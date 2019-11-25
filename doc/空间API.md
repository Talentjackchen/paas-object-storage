# 空间API

## 创建空间

### **描述** 

本接⼝⽤于创建⼀个新的空间。此接⼝不⽀持匿名请求。您可以在请求参数中指定存储区域，例如，您在华东，选择华东存储区域可以减少延迟、降低成本。

### 语法 

```
POST /bucket/create

Content-Type  : application/json
```

### 参数

| 参数名称       | 必 填 | 说明                                                         |
| -------------- | ----- | ------------------------------------------------------------ |
| name           | 是    | 空间名称的URL安全的Base64编码，空间名称仅⽀持字⺟、短划线 - 、下划 线 _ 、数字的组合。 |
| region         | 是    | 存储区域（`z0` 华东）（`z1` 华北）（`z2` 华南）（`na0` 北美）（`as0` 东南亚） |
| type           | 是    | 存储类型 （`0` 公有）（`1` 私有）                            |
| accountId | 是    | 机构ID                                                       |
| projectId      | 是    | 项目ID                                                       |

### 示例

请求示例

```
POST /bucket/create

Content-Type  : application/json

{
	
	"name":"testbucket",
	"projectId":"121",
	"region":"z1",
	"type":"1",
	"accountId":"22"

}
```

响应

```
{
    "code": 200,
    "msg": "bucket创建成功"
}
```



## 删除空间

### 描述 

本接⼝⽤于删除指定的空间。 

### 语法

```
DELETE /bucket/delete

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| bucketId | 是   | 空间ID |

请求示例

```
DELETE /bucket/delete?bucketId=ea068fa27abc48ba9a0fe67b5321006c

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "bucket删除成功"
}
```



## 设置空间权限

### 描述

设置空间公开和私有

### 语法

```
POST /bucket/setPermission
```

### 参数

| 参数名称 | 必填 | 说明                       |
| -------- | ---- | -------------------------- |
| bucketId | 是   | 空间ID                     |
| acl      | 是   | 权限代码 `1` 私有 `0` 公有 |

### 请求示例

```
POST /bucket/setPermission?bucketId=c44288b119894fa78438ce71723e8ebc&acl=1

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "设置权限成功"
}
```

## 临时转转正式

### 描述

临时空间转正式空间，绑定一个空闲的域名

### 语法

```
PUT /bucket/setFreeDomain
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| bucketId | 是   | 空间ID |

### 请求示例

```
PUT /bucket/setFreeDomain?bucketId=aa18a67d15344e8f9062370d36b00e01

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "转换成功"
}
```



## 分页查询

### 描述

根据条件筛选出结果

### 语法

```
GET /bucket/page
```

### 参数

| 参数名称       | 必填 | 说明                       |      |
| -------------- | ---- | -------------------------- | ---- |
| name           | 否   | 空间名称（支持模糊匹配）   |      |
| projectId      | 否   | 项目ID                     |      |
| accountId | 否   | 机构ID                     |      |
| page           | 否   | 当前页（默认第一页）       |      |
| rows           | 否   | 显示的条数（默认10条）     |      |
| sidx           | 否   | 排序字段（默认为创建时间） |      |
| sord           | 否   | 默认为倒序                 |      |

### 请求示例

```
GET /bucket/page?name=testbucket&projectId=121&accountId=22
```

响应

```
{
    "code": 200,
    "msg": "获取bucket数据成功",
    "result": {
        "total": 1,
        "list": [
            {
                "bucketId": "ea068fa27abc48ba9a0fe67b5321006c",
                "name": "testbucket",
                "aliasName": "testbucket_121",
                "region": "z1",
                "type": "0",
                "domain": "pr6ae1i7o.bkt.clouddn.com",
                "projectId": "121",
                "accountId": "22",
                "domainId": null,
                "status": null,
                "createTime": "2019-05-08T06:36:27.000+0000",
                "updateTime": "2019-05-08T06:45:57.000+0000",
                "validFlag": "1"
            }
        ],
        "pageNum": 1,
        "pageSize": 10,
        "size": 1,
        "startRow": 1,
        "endRow": 1,
        "pages": 1,
        "prePage": 0,
        "nextPage": 0,
        "isFirstPage": true,
        "isLastPage": true,
        "hasPreviousPage": false,
        "hasNextPage": false,
        "navigatePages": 8,
        "navigatepageNums": [
            1
        ],
        "navigateFirstPage": 1,
        "navigateLastPage": 1,
        "lastPage": 1,
        "firstPage": 1
    }
}
```

