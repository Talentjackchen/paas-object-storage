# 资源相关API

资源相关API包含资源普通上传、断点续传、移动、复制、删除、批量移动/复制、批量删除、修改资源存储类型、修改资源类型、修改资源文件名、资源下载、资源预览、资源分页查询。



## 1.普通上传

#### 1.1 请求方式

普通上传资源，一次性把文件上传到服务器。适用于较小资源上传。

``` powershell
POST  /resource/upload
Content-Type  : multipart/form-data
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数     |     说明     | 是否必填 |     类型      | 备注                            |
| :---------: | :----------: | :------: | :-----------: | ------------------------------- |
|    file     |   上传文件   |   必填   | MultipartFile | 上传文件                        |
|     key     | 存入文件名称 |   必填   |    String     | 全路径                          |
|    force    |   是否覆盖   |   选填   |    Boolean    | true-覆盖  false-不覆盖（默认） |
| storageType |   存储类型   |   选填   |    String     | 0-标准存储（默认）  1-低频存储  |

#### 1.2 返回码

| 返回码 | 说明 |             备注              |
| :----: | :--: | :---------------------------: |
|  200   | 成功 |                               |
|  400   | 失败 | 失败原因详见返回结果中msg字段 |

#### 1.3 请求示例

http://localhost:8080/paas-object-storage/resource/upload

key:test/8.jpg

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源上传成功"
}
```





## 2.断点续传

断点续传资源，可以将同一资源分一次或多次上传到服务器。适用于各种大小资源上传，并支持集群环境。

**断点续传条件：**上传文件名称相同、存入文件名称相同、文件总大小相同、上传文件最后修改时间相同、距离上次							操作时间小于2天（支持不同客户端操作）；

**断点续传操作步骤：**1.检查文件上传进度，返回ResultMap对象（详见2.4）：                     

​								  2.将上传文件切片（切片大小固定4M，不得修改），循环调用resumeUpload方法。

**特殊情况：**当所有分片全部上传结束，文件并未上传成功，请重新上传最后一个分片，并将isMakeFile属性置为true，其余情况isMakeFile不填或者填false;

备注：文件的存储类型和文件存入空间，以最后一次上传操作为准。

#### 2.1 请求方式

```powershell
POST  /resource/resumeUpload
Content-Type  : multipart/form-data
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|       参数       |       说明       | 是否必填 |     类型      | 备注                                                         |
| :--------------: | :--------------: | :------: | :-----------: | ------------------------------------------------------------ |
|       file       |     上传分片     |   必填   | MultipartFile | 分片（除最后一个外，均为4M）                                 |
|     resumeId     |     分片序号     |   必填   |      int      | 从0开始                                                      |
|       key        |   存入文件名称   |   必填   |    String     | 全路径                                                       |
|    totalSize     |    文件总大小    |   必填   |     long      | 上传文件总大小，并非分片大小                                 |
|     fileName     |     文件名称     |   必填   |    String     | 上传文件名称                                                 |
| lastModifiedTime | 文件最后修改时间 |   必填   |    String     |                                                              |
|   storageType    |     存储类型     |   选填   |    String     | 0-标准存储（默认）  1-低频存储                               |
|    isMakeFile    |     合并文件     |   选填   |    Boolean    | 特殊情况下置true，表示不论是否最后一个分片也执行合并。默认false。 |

#### 2.2 返回码

| ResultMap.code | ResultMap.msg         | 说明                          |
| -------------- | --------------------- | ----------------------------- |
| 400            |                       | 失败原因详见返回结果中msg字段 |
| 200            | resumeUploadSuccess   | 分片上传成功                  |
| 200            | resourceUploadSuccess | 整个文件上传成功              |

#### 2.3 请求示例

http://10.1.65.100:8080/paas-object-storage/fileUpload.html

**示例返回结果**

提示文件上传进度，并最后提示成功~

#### 2.4  检查文件已上传进度

资源选择断点续传时，调用断点续传前调用该方法，判断是否继续上传并返回进度或重新上传

##### 2.4.1 请求方式

```powershell
GET  /resource/checkFile
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|       参数       |       说明       | 是否必填 |  类型  | 备注                         |
| :--------------: | :--------------: | :------: | :----: | :--------------------------- |
|     fileName     |     文件名称     |   必填   | String |                              |
|       key        |   存入文件名称   |   必填   | String | 全路径                       |
|    totalSize     |    文件总大小    |   必填   |  long  | 上传文件总大小，并非分片大小 |
| lastModifiedTime | 文件最后修改时间 |   必填   | String |                              |

##### 2.4.2 返回码

| ResultMap.code | ResultMap.msg | 说明                                                     |
| -------------- | ------------- | -------------------------------------------------------- |
| 400            | existsKey     | 失败：存在相同的存入文件名称                             |
| 400            | error         | 失败：连接缓存服务器异常                                 |
| 200            | reLoadFile    | 成功：首次上传                                           |
| 200            | errorFile     | 成功：与上次上传文件有差异，重新上传                     |
| 200            | existsFile    | 成功：断点续传，（ResultMap.result中返回已上传分片序列） |

#### 2.5 切片方式

##### 2.5.1 前端切片方式

仅供参考，具体依据自身项目框架

```powershell
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script>        
        var blockSize = 4*1024*1024;//每个文件切片大小定为4M(4*1024*1024字节)
        var uploadSize = 0;        
        var totalSlices;// 总切片数
        var endSize = 0;//末尾块大小

        // 提交方法
        function commit() {          
            var file = document.getElementById("file").files[0]; // 获取文件          
            var totalSize = file.size;// 文件的总字节数           
            var start,end;// 分片的开始、结束（不含）           
            var fileName = file.name; // 文件名            
            var lastModifiedTime = getLastModifiedTime(file);// 最后修改时间          
            totalSlices = Math.ceil(totalSize/blockSize);// 计算文件切片总数（向上取整）
            endSize = totalSize - (totalSlices-1)*blockSize;
            var key = $("#toFile").val();
            $("#progress").text("");
            uploadSize = 0;
            $.ajax({
                type:"GET",
                url:"resource/checkFile",
                headers:{"request_from":"Explore","bucketId":$("#bucketId").val()},
                data:{
                    "fileName":fileName,
                    "key":key,
                    "totalSize":totalSize,
                    "lastModifiedTime":lastModifiedTime
                },
                dataType:"json",
                success:function(data){
                    if(data.code == 200){
                        if(data.msg == "existsFile"){
                            var existsResumeArr = data.result;
                            if($.inArray((totalSlices-1),existsResumeArr) != -1){
                                uploadSize = (existsResumeArr.length - 1) * blockSize + 													endSize;
                            }else{
                                uploadSize = existsResumeArr.length * blockSize;
                            }
                            				                                                               $("#progress").text(Math.floor((uploadSize/totalSize)*10000)/100+"%");
                            if(uploadSize == totalSize){
                                $("#progress").text("正在执行上传，请稍等...");
                                var slice1 =file.slice((totalSlices-			                                                        1)*blockSize,totalSize);//切割文件
                                uploadFile(slice1, totalSlices-                                                                   1,fileName,totalSize,lastModifiedTime,key,true);
                            }else{
                                // 当前片数
                                var index = 0;
                                // 不断循环将切片上传
                                while(index < totalSlices) {
                                    start = index*blockSize;
                                    end = start + blockSize;
                                    var slice =file.slice(start,end);//切割文件
                                    if($.inArray(index,existsResumeArr) == -1){
                                        uploadFile(slice,                                                                    index,fileName,totalSize,lastModifiedTime,key,false);
                                    }
                                    index++;
                                }
                            }
                        }else{
                            // 当前片数
                            var index = 0;
                            // 不断循环将切片上传
                            while(index < totalSlices) {
                                start = index*blockSize;
                                end = start + blockSize;
                                var slice =file.slice(start,end);//切割文件
                                uploadFile(slice,                                                                           index,fileName,totalSize,lastModifiedTime,key,false);
                                index++;
                            }
                        }
                    }
                },
                error:function(jqXHR){
                    console.log("Error: "+jqXHR.status);
                }
            });


        }

        //上传文件
        function uploadFile(slice,                                                                      index,fileName,totalSize,lastModifiedTime,key,makeFile) {
            var formDate = new FormData();
            formDate.append("file", slice);
            formDate.append("key",key);
            formDate.append("resumeId",index);
            formDate.append("totalSize",totalSize);
            formDate.append("fileName",fileName);
            formDate.append("storageType",$("#storageType").val());
            formDate.append("lastModifiedTime",lastModifiedTime);
            formDate.append("isMakeFile",makeFile);
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function (){
                if(xhr.readyState==4) {
                    if(xhr.status==200) {
                        var responseText = JSON.parse(xhr.responseText);
                        if(responseText.code == 200) {
                            if(responseText.msg == "resourceUploadSuccess"){
                                $("#progress").text("资源上传成功！");
                            }else{
                                if((index+1) == totalSlices){
                                    uploadSize += endSize;
                                }else{
                                    uploadSize += blockSize;
                                }
                                var progress =                                                                               Math.floor((uploadSize/totalSize)*10000)/100;
                                if(progress > 100 || progress == 100){
                                    $("#progress").text("正在执行上传，请稍等...");
                                    uploadFile(slice,                                                                       index,fileName,totalSize,lastModifiedTime,key,true);
                                }else{
                                    $("#progress").text(progress+"%");
                                }

                            }
                        }else{

                        }
                    }
                }
            };
            xhr.open('POST', 'resource/resumeUpload', true);//false指同步上传
            xhr.setRequestHeader("bucketId",$("#bucketId").val());
            xhr.setRequestHeader("request_from","Explore");
            xhr.send(formDate);

        }

        function getLastModifiedTime(file){
            var datetime = new Date(file.lastModified);
            var year = datetime.getFullYear();
            var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
            var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
            var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
            var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
            var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
            return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
        }
    </script>
</head>
<body>
<h2>文件上传</h2>
<p>toFile: <input type="text" id="toFile" name="toFile"/></p>
<p>file: <input type="file" id="file" name="file"/></p><span id="progress"></span>
<p>bucketId: <input type="text" id="bucketId" name="bucketId"/></p>
<p>storageType: <select  id="storageType" name="storageType"/>
                    <option value="0">标准存储</option>
                    <option value="1">低频存储</option>
                </select>
</p>
<p><input type="button" value="提交" onclick="commit();"/></p>
</body>
</html>


```



## 3.移动

移动一份资源到指定空间中，操作者必须具有2个空间权限。

#### 3.1 请求方式

```powershell
PUT  /resource/move
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数    |     说明     | 是否必填 |  类型   | 备注                            |
| :--------: | :----------: | :------: | :-----: | ------------------------------- |
|    key     | 存入文件名称 |   必填   | String  | 全路径                          |
| toBucketId |   目标空间   |   必填   | String  |                                 |
|   toKey    |  目标文件名  |   必填   | String  |                                 |
|   force    |   是否覆盖   |   选填   | Boolean | true-覆盖  false-不覆盖（默认） |

#### 3.2 返回码

| 返回码 | 说明 |             备注              |
| :----: | :--: | :---------------------------: |
|  200   | 成功 |                               |
|  400   | 失败 | 失败原因详见返回结果中msg字段 |

#### 3.3 请求示例

http://localhost:8080/paas-object-storage/resource/move

resourceId:9b7b5f9337324e58bc2b2eb187bff59f
toBucketId:5a6df55a16014a6ab42f12638373cfca
toKey:test/move2.jpg
force:false

**示例返回结果**

```powershell
{
    "code": 200,
    "msg": "资源移动成功"
}
```





## 4.复制

复制一份资源到指定空间中，操作者必须具有2个空间权限。

#### 4.1 请求方式

```powershell
PUT  /resource/copy
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数    |     说明     | 是否必填 |  类型   | 备注                            |
| :--------: | :----------: | :------: | :-----: | ------------------------------- |
|    key     | 存入文件名称 |   必填   | String  | 全路径                          |
| toBucketId |   目标空间   |   必填   | String  |                                 |
|   toKey    |  目标文件名  |   必填   | String  |                                 |
|   force    |   是否覆盖   |   选填   | Boolean | true-覆盖  false-不覆盖（默认） |

#### 4.2 返回码

| 返回码 | 说明 |             备注              |
| :----: | :--: | :---------------------------: |
|  200   | 成功 |                               |
|  400   | 失败 | 失败原因详见返回结果中msg字段 |

#### 4.3 请求示例

http://localhost:8080/paas-object-storage/resource/copy

resourceId:5b03fec927554d61ad3c32387c7bf126
toBucketId:5a6df55a16014a6ab42f12638373cfca
toKey:test/copy8.jpg
force:false

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源复制成功"
}
```



## 5.删除

单个资源删除，资源必须存在于头部信息中传入空间中。

#### 5.1 请求方式

```powershell
Delete  /resource/delete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

| 参数 |     说明     | 是否必填 |  类型  | 备注   |
| :--: | :----------: | :------: | :----: | ------ |
| key  | 存入文件名称 |   必填   | String | 全路径 |

#### 5.2 返回码

| 返回码 | 说明 |             备注              |
| :----: | :--: | :---------------------------: |
|  200   | 成功 |                               |
|  400   | 失败 | 失败原因详见返回结果中msg字段 |

#### 5.3 请求示例

http://localhost:8080/paas-object-storage/resource/delete

resourceId:5b03fec927554d61ad3c32387c7bf126

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源删除成功"
}
```



## 6.批量移动

资源可跨空间移动，但是操作者必须具有所有空间权限。所有被移动资源必须存在与同一空间下（以请求头部bucketId为准），目标空间编号数组可以不填，如果填写，那么必须与目标文件数组一一对应。如果目标空间已存在相同命名的资源，则单条移动失败。

#### 6.1 请求方式

```powershell
PUT  /resource/batchMove
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数     |       说明       | 是否必填 |   类型   | 备注                         |
| :---------: | :--------------: | :------: | :------: | ---------------------------- |
|    keys     | 存入文件名称数组 |   必填   | String[] |                              |
| toBucketIds | 目标空间编号数组 |   选填   | String[] | 不填则为资源在同空间内重命名 |
|   toKeys    |   目标文件数组   |   必填   | String[] | 资源不实现覆盖               |

#### 6.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 6.3 请求示例

http://localhost:8080/paas-object-storage/resource/batchMove

resourceIds:4318acbd8e0c44adb852c11a166ad99a,8ffbc44e67704213b5bc1cc590a0ebe6
toBucketIds:5a6df55a16014a6ab42f12638373cfca,5a6df55a16014a6ab42f12638373cfca
toKeys:batchMove/sky.jpg,batchMove/flower.jpg

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源批量移动成功"
}
```



## 7.批量复制

与批量移动类似，批量复制在不删除源资源的情况下，复制一份到相应的空间下。

#### 7.1 请求方式

```powershell
PUT  /resource/batchCopy
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数     |       说明       | 是否必填 |   类型   | 备注                         |
| :---------: | :--------------: | :------: | :------: | ---------------------------- |
|    keys     | 存入文件名称数组 |   必填   | String[] |                              |
| toBucketIds | 目标空间编号数组 |   选填   | String[] | 不填则为资源在同空间内重命名 |
|   toKeys    |   目标文件数组   |   必填   | String[] | 资源不实现覆盖               |

#### 7.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 7.3 请求示例

http://localhost:8080/paas-object-storage/resource/batchCopy

resourceIds:b1500668b07d4e169cf00aca144d3e1b,cee079017de84d37bbc527dc93c1a02c
toBucketIds:5a6df55a16014a6ab42f12638373cfca,5a6df55a16014a6ab42f12638373cfca
toKeys:batchCopy/car.jpg,batchCopy/dog.jpg

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源批量复制成功"
}
```



## 8.批量删除

同时删除一个或多个资源，所有资源必须处于同一空间下（以请求头部bucketId为准）

#### 8.1 请求方式

```powershell
Delete  /resource/batchDelete
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

| 参数 |       说明       | 是否必填 |   类型   | 备注 |
| :--: | :--------------: | :------: | :------: | ---- |
| keys | 存入文件名称数组 |   必填   | String[] |      |

#### 8.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 8.3 请求示例

http://localhost:8080/paas-object-storage/resource/batchDelete

resourceIds:b1500668b07d4e169cf00aca144d3e1b,cee079017de84d37bbc527dc93c1a02c

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源批量删除成功"
}
```



## 9.修改存储类型

修改存储资源的存储类型，是低频存储和标准存储之间的相互切换。不填写存储类型，表示将转低频存储。

#### 9.1 请求方式

```powershell
PUT  /resource/changeStorageType
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|    参数     |     说明     | 是否必填 |  类型  | 备注                   |
| :---------: | :----------: | :------: | :----: | ---------------------- |
|     key     | 存入文件名称 |   必填   | String | 全路径                 |
| storageType |   存储类型   |   选填   | String | 0-标准；1-低频（默认） |

#### 9.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 9.3 请求示例

http://localhost:8080/paas-object-storage/resource/changeStorageType

resourceId:b1500668b07d4e169cf00aca144d3e1b
storageType:1

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "资源修改成功"
}
```



## 10.修改资源类型

修改对象存储服务器中记录的资源类型（image/jpeg、application/pdf、application/zip、video/mp4等）

#### 10.1 请求方式

```powershell
PUT  /resource/updateMimeType
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

|   参数   |     说明     | 是否必填 |  类型  | 备注 |
| :------: | :----------: | :------: | :----: | ---- |
|   key    | 存入文件名称 |   必填   | String |      |
| mimeType |   文件类型   |   必填   | String |      |

#### 10.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 10.3 请求示例

http://localhost:8080/paas-object-storage/resource/updateMimeType

resourceId:684debd5f51442b5bdce6a6a8700305a
mimeType:application/x-msdownload

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "修改资源成功"
}
```





## 11.修改资源名称

修改存入文件名称，修改后的资源名称不能与原有资源名称重复。

#### 11.1 请求方式

```powershell
PUT  /resource/updateName
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

| 参数  |     说明     | 是否必填 |  类型  | 备注   |
| :---: | :----------: | :------: | :----: | ------ |
|  key  | 存入文件名称 |   必填   | String | 全路径 |
| toKey |   文件名称   |   必填   | String |        |

#### 11.2 返回码

| 返回码 | 说明 |       备注        |
| :----: | :--: | :---------------: |
|  200   | 成功 |                   |
|  400   | 失败 | msg中记录失败记录 |

#### 11.3 请求示例

http://localhost:8080/paas-object-storage/resource/updateName

resourceId:684debd5f51442b5bdce6a6a8700305a
toKey:test/yui1234.pdf

**示例返回结果**：

```powershell
{
    "code": 200,
    "msg": "修改资源成功"
}
```



## 12.资源预览

支持格式：图片。

#### 12.1 请求方式

```
GET  /resource/preview
Content-Type  : application/x-www-form-urlencoded
bucketId      : 空间Id

```

| 参数 |     说明     | 是否必填 |  类型  |  备注  |
| :--: | :----------: | :------: | :----: | :----: |
| key  | 存入文件名称 |    是    | String | 全路径 |
| bucketId |    空间ID    |    是    | String |        |

#### 12.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |

#### 12.3 请求示例

```
http://127.0.0.1:8080/paas-object-storage/resource/preview?key=1548035079.png&bucketId=78689fbfce3847ec896d798cd2c6904b
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b
```

**示例返回结果**

```
预览资源
```



## 13. 资源下载

支持公有、私有空间。

#### 13.1请求方式

```
GET /resource/download
Content-Type  : application/x-www-form-urlencoded
bucketId      : 空间Id

```

|   参数   |     说明     | 是否必填 |  类型  |  备注  |
| :------: | :----------: | :------: | :----: | :----: |
|   key    | 存入文件名称 |    是    | String | 全路径 |
| bucketId |    空间ID    |    是    | String |        |

#### 13.2 返回码

| 返回码 | 说明 | 备注 |
| :----: | :--: | :--: |
|  200   | 成功 |      |
|  400   | 失败 |      |

#### 13.3 请求示例

```
http://127.0.0.1:8080/paas-object-storage/resource/download?key=1548035079.png&bucketId=78689fbfce3847ec896d798cd2c6904b
Content-Type  : application/x-www-form-urlencoded
bucketId      : 78689fbfce3847ec896d798cd2c6904b

```

**示例返回结果**

```
下载资源
```

