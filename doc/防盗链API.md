### 1. IP防盗链

#### 1.1 创建

##### 1.1.1 请求方式

```
POST /ipAcl/create
Content-Type  : application/json
bucketId      : 空间Id
```

| 参数 |  说明  | 是否必填 |     类型     |   备注   |
| :--: | :----: | :------: | :----------: | :------: |
| ips  | ip集合 |    是    | List<String> | ip白名单 |
|      |        |          |              |          |

##### 1.1.2 返回码

| 返回码 |       说明       | 备注 |
| :----: | :--------------: | :--: |
|  200   |       成功       |      |
|  400   |       失败       |      |
|  1002  | IP地址不符合规范 |      |

##### 1.1.3 请求示例

```
/paas-object-storage/ipAcl/create
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

["127.0.0.1","127.0.0.2"]
```

**示例返回结果**

```
{
    "code": 200,
    "msg" : "IP白名单防盗链配置创建成功"
}
```

#### 1.2 更新

##### 1.2.1 请求方式

```
PUT /ipAcl/update
Content-Type  : application/json
bucketId      : 空间Id
```

| 参数 |  说明  | 是否必填 |     类型     |   备注   |
| :--: | :----: | :------: | :----------: | :------: |
| ips  | ip集合 |    是    | List<String> | ip白名单 |
|      |        |          |              |          |

##### 1.2.2 返回码

| 返回码 |       说明       | 备注 |
| :----: | :--------------: | :--: |
|  200   |       成功       |      |
|  400   |       失败       |      |
|  1002  | IP地址不符合规范 |      |

##### 1.2.3 请求示例

```
/paas-object-storage/ipAcl/update
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

["127.0.0.1","127.0.0.3"]
```

**示例返回结果**

```
{
    "code": 200,
    "msg" : "IP白名单防盗链配置更新成功"
}
```

#### 1.3 查询

##### 1.3.1 请求方式

```
GET /ipAcl/queryByBucketId
Content-Type  : application/json
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 1.3.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 1.3.3 请求示例

```
/paas-object-storage/ipAcl/queryByBucketId
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

```

**示例返回结果**

```
{
    "code": 200,
    "msg": "success",
    "result": {
        "ipAclId": "410c3e905af34ccdb457ec21b65b9b3f",
        "ipAddress": "127.0.0.1,127.0.0.3",
        "type": "WHITE",
        "bucketId": "78689fbfce3847ec896d798cd2c6904b"
    }
}
```



#### 1.4 删除

##### 1.4.1 请求方式

```
DELETE /ipAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 1.4.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 1.4.3 请求示例

```
/paas-object-storage/ipAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

**示例返回结果**

```
{
    "code": 200,
    "msg" : "IP白名单防盗链配置删除成功"
}
```

### 2. Referer防盗链

#### 2.1 创建

##### 2.1.1 请求方式

```
POST /refererAcl/create
Content-Type  : application/json
bucketId      : 空间Id
```

|     参数     |       说明       | 是否必填 |  类型  |      备注       |
| :----------: | :--------------: | :------: | :----: | :-------------: |
|    domain    |      白名单      |    是    | String |  referer白名单  |
| allowedEmpty | 是否允许空Refere |    是    | String | 0:不允许 1:允许 |

##### 1.1.2 返回码

| 返回码 |      说明      | 备注 |
| :----: | :------------: | :--: |
|  200   |      成功      |      |
|  400   |      失败      |      |
|  1002  | 域名不符合规范 |      |

##### 1.1.3 请求示例

```
/paas-object-storage/refererAcl/create
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

{"domain":"wonderscloud.com,*.wonderscloud.com","allowedEmpty":"1"}
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "Referer白名单防盗链配置创建成功"
}
```

#### 2.2 更新

##### 2.2.1 请求方式

```
PUT /refererAcl/update
Content-Type  : application/json
bucketId      : 空间Id
```

|     参数     |       说明       | 是否必填 |  类型  |      备注       |
| :----------: | :--------------: | :------: | :----: | :-------------: |
|    domain    |      白名单      |    是    | String |  referer白名单  |
| allowedEmpty | 是否允许空Refere |    是    | String | 0:不允许 1:允许 |

##### 2.2.2 返回码

| 返回码 |      说明      | 备注 |
| :----: | :------------: | :--: |
|  200   |      成功      |      |
|  400   |      失败      |      |
|  1002  | 域名不符合规范 |      |

##### 2.2.3 请求示例

```
/paas-object-storage/refererAcl/update
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

{"domain":"wonderscloud.com,*.wonderscloud.com","allowedEmpty":"1"}
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "Referer白名单防盗链配置更新成功"
}
```

#### 2.3 查询

##### 2.3.1 请求方式

```
GET /refererAcl/queryByBucketId
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 2.3.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 2.3.3 请求示例

```
/paas-object-storage/refererAcl/queryByBucketId
bucketId      : d69e88b5242345df82673a798b01ca25
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "success",
    "result": {
        "refererAclId": "cc903f382b8a4ee7b9322f012fbc36d3",
        "domain": "wonderscloud.com,*.wonderscloud.com",
        "type": "WHITE",
        "allowedEmpty": "1",
        "bucketId": "d69e88b5242345df82673a798b01ca25"
    }
}
```



#### 2.4 删除

##### 2.4.1 请求方式

```
DELETE /refererAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 2.4.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 2.4.3 请求示例

```
/paas-object-storage/refererAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "Referer白名单防盗链配置删除成功"
}
```

### 3. 时间戳防盗链

#### 3.1 创建/更新

##### 3.1.1 请求方式

```
POST /timestampAcl/create
Content-Type  : application/json
bucketId      : 空间Id
```

|   参数   |     说明     | 是否必填 |  类型  | 备注 |
| :------: | :----------: | :------: | :----: | :--: |
| mainKey  |   主要key    |    是    | String |      |
| spareKey |   备用key    |    是    | String |      |
|   url    | 带时间戳链接 |    是    | String |      |

##### 3.1.2 返回码

| 返回码 |          说明          | 备注 |
| :----: | :--------------------: | :--: |
|  200   |          成功          |      |
|  400   |          失败          |      |
|  1002  | 时间戳防盗链检查不可用 |      |

##### 3.1.3 请求示例

```
/paas-object-storage/timestampAcl/create
Content-Type  : application/json
bucketId      : 78689fbfce3847ec896d798cd2c6904b

{
	"mainKey": "14db3287eced45eea6f6539abe120e6f",
	"spareKey": "ce89e59ea7f846d4b7b0f2c08a2ea1431"
	"url":"http://127.0.0.1:8080/paas-object-storage/resource/preview?key=1548035079.png&&bucketId=78689fbfce3847ec896d798cd2c6904b1&&expire=1557734107079&&token=2963c228c9251d9ff80441e7410a236f"
}
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "时间戳防盗链配置创建成功"
}
```



#### 3.2 查询

##### 3.2.1 请求方式

```
GET /timestampAcl/queryByBucketId
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 3.2.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 3.2.3 请求示例

```
/paas-object-storage/timestampAcl/queryByBucketId
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "success",
    "result": {
        "timestampAclId": "5190038d68b949f7a8ad3b754099e749",
        "mainKey": "14db3287eced45eea6f6539abe120e62",
        "spareKey": "ce89e59ea7f846d4b7b0f2c08a2ea1432",
        "bucketId": "78689fbfce3847ec896d798cd2c6904b"
    }
}
```



#### 3.3 删除

##### 3.3.1 请求方式

```
DELETE /timestampAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 空间Id
```

| 参数 | 说明 | 是否必填 | 类型 | 备注 |
| :--: | :--: | :------: | :--: | :--: |
|      |      |          |      |      |

##### 3.3.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |
|        |      |      |

##### 3.3.3 请求示例

```
/paas-object-storage/timestampAcl/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

**示例返回结果**

```
{
    "code": 200,
    "msg": "时间戳防盗链配置删除成功"
}
```



#### 3.4 时间戳资源外连接生成规则

```
时间戳防盗链URL需要对资源访问路径进行md5加密，加密的规则是除却scheme和域名信息加密生成token值。
token = md5(mainKey + path + "?" + queryPath);
queryPath参数顺序依次为expire（时间戳）、bucketId（空间Id）、key（资源路径）

例子：
path = "/link/resource/preview"；
queryPath = "expire=1557213932137&bucketId=78689fbfce3847ec896d798cd2c6904b&key=1548035079.png";
url  = URLEncoder.encode("/resource/preview?expire=1557213932137&bucketId=78689fbfce3847ec896d798cd2c6904b&key=1548035079.png")；
token = md5(mainKey + url);

url = "http://10.2.102.124:8080/paas-object-storage/resource/preview?key=1548035079.png&bucketId=78689fbfce3847ec896d798cd2c6904b&expire=1557213932137&token=8ab0206d7a786643443c6bb5c75000c4"
```
