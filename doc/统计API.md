# 统计API

## 获取PUT请求次数

### 描述

该接⼝可以获取 PUT 请求次数。监控统计延迟⼤概 5 分钟。

### 语法

```
GET /statistics/PUTRequests
```

### 参数

| 参数名称    | 必填 | 说明                                                         |
| ----------- | ---- | ------------------------------------------------------------ |
| beginDate   | 是   | 起始时间字符串，格式为20060102150405，闭区间                 |
| endDate     | 是   | 结束时间字符串，格式为20060102150405，开区间                 |
| bucketId    | 是   | 空间名称ID                                                   |
| granularity | 否   | 时间聚合粒度( `5min` `hour` `day`  `month` )                 |
| storageType | 否   | 存储类型 （`0` 标准存储）（`1` 低频存储）                    |
| region      | 否   | 存储区域（`z0` 华东）（`z1` 华北）（`z2` 华南）（`na0` 北美） |

### 示例

请求示例

```
GET /statistics/PUTRequests？beginDate=20190417000000&endDate=20190419000000&bucketId=78689fbfce3847ec896d798cd2c6904b
```

响应

```
{
    "code": 200,
    "msg": "获取put请求次数成功",
    "result": "[{\"time\":\"2019-04-17T00:00:00+08:00\",\"values\":{\"hits\":0}},{\"time\":\"2019-04-18T00:00:00+08:00\",\"values\":{\"hits\":14}}]"
}
```



## 获取文件类型存储量和数量

### 描述

该接口获取文件类型的存储量和数量

### 语法

```
GET /statistics/queryCountAndSize
```

### 参数

| 参数名称  | 必填 | 说明     |
| --------- | ---- | -------- |
| fileType  | 否   | 文件类型 |
| projectId | 否   | 项目ID   |

### 示例

请求示例

```
GET /statistics/queryCountAndSize？fileType=image/jpeg
```

响应

```
{
    "code": 200,
    "msg": "获取文件类型存储量成功",
    "result": [
        {
            "size": "645.37 KB",
            "count": 14,
            "type": "image/jpeg"
        }
    ]
}
```

