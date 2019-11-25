# 域名API

## 创建域名

### 描述

该接口提供域名的创建

### 语法

```
POST /domain/create
Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明                                               |
| -------- | ---- | -------------------------------------------------- |
| name     | 是   | 域名名称                                           |
| bucketId | 是   | 空间ID                                             |
| protocol | 是   | 协议: `http`/`https`                               |
| platform | 是   | 平台类型: `web`(网页)/`download`(下载)/`vod`(点播) |

### 示例

请求示例

```
POST domain/create?name=ts2.wonderscloud.com&protocol=https&platform=web&bucketId=0aa6b5ca046843868ed10df0afbf5d7c

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "创建成功"
}
```



## 删除域名

### 描述

该接口用于删除已停用的域名

### 语法

```
DELETE /domain/delete

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
DELETE /domain/delete?domainId=74e3420d29d943c48a6bca7caeb55bcb

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "删除成功"
}
```



## 启用域名

### 描述

该接口用于启用已停用的域名

### 语法

```
POST /domain/enable

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
POST /domain/enable?domainId=74e3420d29d943c48a6bca7caeb55bcb

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "启用成功"
}
```



## 停用域名

### 描述

该接口用于停用已启用的域名

### 语法

```
POST /domain/stop

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
POST /domain/stop?domainId=74e3420d29d943c48a6bca7caeb55bcb
```

响应

```
{
    "code": 200,
    "msg": "停用成功"
}
```



## 升级域名

### 描述

该接口用于将http的域名升级为https

### 语法

```
PUT /domain/upgrade

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
PUT /domain/upgrade?domainId=74e3420d29d943c48a6bca7caeb55bcb

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "升级成功"
}
```



## 降级域名

### 描述

该接口用于将https的域名降级为http

### 语法

```
PUT /domain/downgrade

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
PUT /domain/downgrade?domainId=74e3420d29d943c48a6bca7caeb55bcb

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "降级成功"
}
```



## 详情查询

### 描述

该接口用于查询域名的详细信息

### 语法

```
GET /domain/detail
```

### 参数

| 参数名称 | 必填 | 说明   |
| -------- | ---- | ------ |
| domainId | 是   | 域名ID |

### 示例

请求示例

```
GET /domain/detail?domainId=74e3420d29d943c48a6bca7caeb55bcb
```

响应

```
{
    "code": 200,
    "msg": "数据获取成功",
    "result": {
        "domainId": "74e3420d29d943c48a6bca7caeb55bcb",
        "name": "ts4.wonderscloud.com",
        "type": "normal",
        "platform": "web",
        "geoCover": "china",
        "protocol": "https",
        "ipAclStatus": null,
        "refererAclStatus": null,
        "timeActStatus": null,
        "sslCertificateId": null,
        "createTime": "2019-05-09T02:25:44.000+0000",
        "updateTime": null,
        "validFlag": "1",
        "belongBucketId": "0aa6b5ca046843868ed10df0afbf5d7c",
        "operatingState": "success"
    }
}
```



## 修改域名信息

### 描述

该接口用于修改域名信息

### 语法

```
PUT /domain/edit

Content-Type  : application/x-www-form-urlencoded
```

### 参数

| 参数名称 | 必填 | 说明               |
| -------- | ---- | ------------------ |
| domainId | 是   | 域名ID             |
| bucketId | 是   | 需要修改成的空间ID |

### 示例

请求示例

```
PUT /domain/edit?domainId=74e3420d29d943c48a6bca7caeb55bcb&bucket=5a6df55a16014a6ab42f12638373cfca

Content-Type  : application/x-www-form-urlencoded
```

响应

```
{
    "code": 200,
    "msg": "数据修改成功"
}
```



## 分页查询

### 描述

该接口用于根据对应的条件分页查询数据

### 语法

```
GET /domain/page
```

### 参数

| 参数名称 | 必填 | 说明                                               |
| -------- | ---- | -------------------------------------------------- |
| name     | 否   | 域名名称                                           |
| protocol | 否   | 协议: `http`/`https`                               |
| platform | 否   | 平台类型: `web`(网页)/`download`(下载)/`vod`(点播) |
| bucketId | 否   | 空间ID                                             |
| page     | 否   | 当前页                                             |
| rows     | 否   | 显示的条数                                         |
| sidx     | 否   | 排序字段                                           |
| sord     | 否   | 排序规则：`ASC`/`DESC`缺省为：`DESC`               |

### 示例

请求示例

```
GET /domain/page？name=ts4.wonderscloud.com
```

响应

```
{
    "code": 200,
    "msg": "数据获取成功",
    "result": {
        "total": 1,
        "list": [
            {
                "domainId": "74e3420d29d943c48a6bca7caeb55bcb",
                "name": "ts4.wonderscloud.com",
                "type": "normal",
                "platform": "web",
                "geoCover": "china",
                "protocol": "https",
                "ipAclStatus": null,
                "refererAclStatus": null,
                "timeActStatus": null,
                "sslCertificateId": null,
                "createTime": "2019-05-09T02:25:44.000+0000",
                "updateTime": "2019-05-09T03:01:33.000+0000",
                "validFlag": "1",
                "belongBucketId": "5a6df55a16014a6ab42f12638373cfca"
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
        "firstPage": 1,
        "lastPage": 1
    }
}
```

