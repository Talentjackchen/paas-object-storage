# SSL证书相关API

SSL证书API包含上传自定义证书、删除证书、获取证书详情、分页展示证书信息。



## 1.上传自定义证书

#### 1.1 请求方式

``` powershell
POST  /sslCert/addSslCert
Content-Type  : application/x-www-form-urlencoded
```

|    参数     |   说明   | 是否必填 |  类型  | 备注 |
| :---------: | :------: | :------: | :----: | ---- |
|    name     | 备注名称 |   必填   | String |      |
| commonName  | 通用名称 |   必填   | String |      |
| certificate | 证书内容 |   必填   | String |      |
| privateKey  | 证书私钥 |   必填   | String |      |

#### 1.2 返回码

| 返回码 |           说明           |             备注              |
| :----: | :----------------------: | :---------------------------: |
|  200   |           成功           |                               |
|  400   |           失败           | 失败原因详见返回结果中msg字段 |
| 400500 | 超过用户绑定证书最大额度 |                               |
| 404906 |    https证书解码失败     |                               |
| 400323 |   验证https证书链失败    |                               |
| 400322 |   https证书有效期太短    |                               |
| 400329 |      https证书过期       |                               |

#### 1.3 请求示例

http://localhost:8080/paas-object-storage/sslCert/addSslCert

`name:测试123`
`commonName:test123`
`certificate:-----BEGIN CERTIFICATE-----↵MIIGEzCCBPugAwIBAgIMTSXP3aQ8klNyCAmcMA0GCSqGSIb3DQEBCwUAMGAxCzAJ↵BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTYwNAYDVQQDEy1H↵bG9iYWxTaWduIERvbWFpbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0gRzIwHhcN↵MTgwNTA5MDIwMjA1WhcNMTkwNzIxMDA0NjMwWjBAMSEwHwYDVQQLExhEb21haW4g↵Q29udHJvbCBWYWxpZGF0ZWQxGzAZBgNVBAMMEioud29uZGVyc2Nsb3VkLmNvbTCC↵ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMlKNEjblHM3e7i4fRHYs36M↵j4duvaSloVPXBJSjX9g0F32OLQBlAKvsHZKsteYnJfMTZzYfoB8YiBHg3GvprpoN↵LUH1KLLlPDgKytnTFcO4c0ALjHim0HC3WycYDiiA4tSL3/hrySPaRDUhLY6O8Enj↵L7DgS0bF2ENBoN1Lp9wNJajQz0K281UOsvi+Aq1ZmxKk2mFhoCFP71ouLhOQf2+y↵fLXTlYERhXnbhS/UZlTHJocYgh2CZP1uI9vOykoep5p27zmBdE7VrGVLN6J+F+uN↵H9Q78ZY+/W2XHcVShMGLq2Lo/UmnizgXad608PRC/VW31opLXH5XbICwZ71mmdMC↵AwEAAaOCAuswggLnMA4GA1UdDwEB/wQEAwIFoDCBlAYIKwYBBQUHAQEEgYcwgYQw↵RwYIKwYBBQUHMAKGO2h0dHA6Ly9zZWN1cmUuZ2xvYmFsc2lnbi5jb20vY2FjZXJ0↵L2dzZG9tYWludmFsc2hhMmcycjEuY3J0MDkGCCsGAQUFBzABhi1odHRwOi8vb2Nz↵cDIuZ2xvYmFsc2lnbi5jb20vZ3Nkb21haW52YWxzaGEyZzIwVgYDVR0gBE8wTTBB↵BgkrBgEEAaAyAQowNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2ln↵bi5jb20vcmVwb3NpdG9yeS8wCAYGZ4EMAQIBMAkGA1UdEwQCMAAwQwYDVR0fBDww↵OjA4oDagNIYyaHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9ncy9nc2RvbWFpbnZh↵bHNoYTJnMi5jcmwwLwYDVR0RBCgwJoISKi53b25kZXJzY2xvdWQuY29tghB3b25k↵ZXJzY2xvdWQuY29tMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAdBgNV↵HQ4EFgQU9wQ5e/qRryQkVaSU/EybcDeztzcwHwYDVR0jBBgwFoAU6k581IAt5RWB↵hiaMgm3AmKTPlw8wggEEBgorBgEEAdZ5AgQCBIH1BIHyAPAAdwC72d+8H4pxtZOU↵I5eqkntHOFeVCqtS6BqQlmQ2jh7RhQAAAWNCol+0AAAEAwBIMEYCIQC1k4bUYczh↵sqe1fUHz1e8k+GBSIwqlqRRCfLsZa6k/GAIhAOqgXdXNb6Btp/gEIyzwE2jmAsYY↵okATN0WbBshhHiJbAHUAb1N2rDHwMRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMA↵AAFjQqJfZAAABAMARjBEAiBNFS6Z+qDOI1XH8yWIewj9oNUF8zpWLrJqrw4XBGl5↵JQIgDsWRijCb0qBh7UEotmwzsNEAh1K61wp8+9ZcnYMsI2MwDQYJKoZIhvcNAQEL↵BQADggEBAHSWn7RFne3nwnO75h1o4Il5pPOKr2Ik3B7BgHWAQAClHHXZP6wDy6Qs↵wgbvCGXIyCWg6qb+w5IZMWkKPCfX3Z/7aJWW3o2Qe/r8xiufRLvrnJ9uEZeywZUz↵VmMbVwxVEE5gf1ISI+6ZmO+yUKFHAvclT6d5tdSnwSFuqAJy9RrBLis0s/p8Zmq5↵ChYFHfQmtPPLDBjoiZeVSD0E2duCFB546CXvtzxEJ28FSspAvIQL97zE6lVQko/U↵eRov3Pf5zvBVMMLWwU2Rr4X0AQfmM525UH255sFyylElFpcccGd4Rg3hAC/3LJ05↵wDlD/PInwK2jN+jQWa8rTLYkh9BIYx8=↵-----END CERTIFICATE-----↵-----BEGIN CERTIFICATE-----↵MIIEYzCCA0ugAwIBAgILBAAAAAABRE7wPiAwDQYJKoZIhvcNAQELBQAwVzELMAkG↵A1UEBhMCQkUxGTAXBgNVBAoTEEdsb2JhbFNpZ24gbnYtc2ExEDAOBgNVBAsTB1Jv↵b3QgQ0ExGzAZBgNVBAMTEkdsb2JhbFNpZ24gUm9vdCBDQTAeFw0xNDAyMjAxMDAw↵MDBaFw0yNDAyMjAxMDAwMDBaMGAxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9i↵YWxTaWduIG52LXNhMTYwNAYDVQQDEy1HbG9iYWxTaWduIERvbWFpbiBWYWxpZGF0↵aW9uIENBIC0gU0hBMjU2IC0gRzIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK↵AoIBAQCp3cwOs+IyOd1JIqgTaZOHiOEM7nF9vZCHll1Z8syz0lhXV/lG72wm2DZC↵jn4wsy+aPlN7H262okxFHzzTFZMcie089Ffeyr3sBppqKqAZUn9R0XQ5CJ+r69eG↵ExWXrjbDVGYOWvKgc4Ux47JkFGr/paKOJLu9hVIVonnu8LXuPbj0fYC82ZA1ZbgX↵qa2zmJ+gfn1u+z+tfMIbWTaW2jcyS0tdNQJjjtunz2LuzC7Ujcm9PGqRcqIip3It↵INH6yjfaGJjmFiRxJUvE5XuJUgkC/VkrBG7KB4HUs9ra2+PMgKhWBwZ8lgg3nds4↵tmI0kWIHdAE42HIw4uuQcSZiwFfzAgMBAAGjggElMIIBITAOBgNVHQ8BAf8EBAMC↵AQYwEgYDVR0TAQH/BAgwBgEB/wIBADAdBgNVHQ4EFgQU6k581IAt5RWBhiaMgm3A↵mKTPlw8wRwYDVR0gBEAwPjA8BgRVHSAAMDQwMgYIKwYBBQUHAgEWJmh0dHBzOi8v↵d3d3Lmdsb2JhbHNpZ24uY29tL3JlcG9zaXRvcnkvMDMGA1UdHwQsMCowKKAmoCSG↵Imh0dHA6Ly9jcmwuZ2xvYmFsc2lnbi5uZXQvcm9vdC5jcmwwPQYIKwYBBQUHAQEE↵MTAvMC0GCCsGAQUFBzABhiFodHRwOi8vb2NzcC5nbG9iYWxzaWduLmNvbS9yb290↵cjEwHwYDVR0jBBgwFoAUYHtmGkUNl8qJUC99BM00qP/8/UswDQYJKoZIhvcNAQEL↵BQADggEBANdFnqDc4ONhWgt9d4QXLWVagpqNoycqhffJ7+mG/dRHzQFSlsVDvTex↵4bjyqdKKEYRxkRWJ3AKdC8tsM4U0KJ4gsrGX3G0LEME8zV/qXdeYMcU0mVwAYVXE↵GwJbxeOJyLS4bx448lYm6UHvPc2smU9ZSlctS32ux4j71pg79eXw6ImJuYsDy1oj↵H6T9uOr7Lp2uanMJvPzVoLVEgqtEkS5QLlfBQ9iRBIvpES5ftD953x77PzAAi1Pj↵tywdO02L3ORkHQRYM68bVeerDL8wBHTk8w4vMDmNSwSMHnVmZkngvkA0x1xaUZK6↵EjxS1QSCVS1npd+3lXzuP8MIugS+wEY=↵-----END CERTIFICATE-----`
`privateKey:-----BEGIN RSA PRIVATE KEY-----↵MIIEpQIBAAKCAQEAyUo0SNuUczd7uLh9EdizfoyPh269pKWhU9cElKNf2DQXfY4t↵AGUAq+wdkqy15icl8xNnNh+gHxiIEeDca+mumg0tQfUosuU8OArK2dMVw7hzQAuM↵eKbQcLdbJxgOKIDi1Ivf+GvJI9pENSEtjo7wSeMvsOBLRsXYQ0Gg3Uun3A0lqNDP↵QrbzVQ6y+L4CrVmbEqTaYWGgIU/vWi4uE5B/b7J8tdOVgRGFeduFL9RmVMcmhxiC↵HYJk/W4j287KSh6nmnbvOYF0TtWsZUs3on4X640f1Dvxlj79bZcdxVKEwYurYuj9↵SaeLOBdp3rTw9EL9VbfWiktcfldsgLBnvWaZ0wIDAQABAoIBAQCBSxiqc+C24Tyn↵OlmbzQX+k2reI1PNHlax87sNfqNhzJQKIfjl9/qmc9PLEwUEAMFvkVcKfOXtE2TT↵lwv+hOuSKESZyU+LGZPEq6oAoIWqYiAlU0W3ee8Jd70lL15oxeDthasndpSMYvbM↵6bLJjybpTvSjOG8h1pNDIDSj8KSxwtxGEXk5ztIHA2rDhzqosyYkXz50Pz+w4Y3C↵yVgPm49qrFcGnqx6wjFm2DCeHhuX/cTUP4HpCc+PxsbH3ZzGinuwXAyeJ16FrJIL↵EZF20yQaxIWQ3DSuR/sfRhSD0Nr1Wmb5jLVyzZUgfwwCiCdticKWx+eZDjemyfbE↵Em8AqIUBAoGBAOv7rS7z48AQNgeHzkrY0UQGaIsWsBgyznkt/SOdE6Vj22NxJebU↵bOhm/job2b4TVrFqqkBMJfWMJN0MzVSHMdssiEstIMm1srO1VTIG6U74VRgOhq91↵AnsSNEno8Q1zwAf/hMCwD2XZoKCNAMDqIe1b1jJo4XFpqGCGVTqHBwFrAoGBANpd↵K83Gv6sC8LE8rGWDfmwd0HuK7ScdsHdpQO6tcOO9DUkGd9sVcdh4Sp1bTw5JpykC↵U9nZEuujW6BudLvj9pzIeQWUYXcL4oLPE1SpqXGWDIjbyOEnYvu+uTVL8+l/e9+Y↵AluLd5ldLHgpAa0icoC+wXG4+VYU90JhomHuUxs5AoGBALwMvfBZmBdYk8Wiekj4↵yKI9vReBh+hwrEqy1DOKG7rDfXOcGgj9JKB6WoLx6K1oecA98Nf2FpW7DTE9325J↵ofg3X5EQsD0tVu+QRAjuqwMsL5tREBFH3Cc41YREZbtDjzaQbzih1FNkfnUSiBQz↵P8AHXASlwWmAOhZZVheivHlrAoGAQVy8POct/iFuLI9Nx0EZ+YDfI+vM2vWPGd8+↵mXL/JJw1OtOd0s1Te4leREXNnyg6yhKxSLcqa7BFP/twI8Gz0vBLEXl9xyWekqsr↵TR9P4fkZHWo0cfjZLyPCyopGo3EOgjo9Emvny9dQ9VLI9qsl/RWcT88fiz3RWWRi↵N9AfvjkCgYEAiVAAQ+qMMXzLdov6vxStMxtY5HRirpj61fS3w1KQGRvVCTZQE+ny↵7t3+y+Xvqq97QunTiQ6lOur2LSM9rGPwAix63h4DVGPzv1o+bX8fbUEVz1ASyr9R↵U8Vcsc3zHhE1IVU7W4fkyN14FnczECt69VTmeCC2ojR05IE71sNroJ4=↵-----END RSA PRIVATE KEY-----`

**示例返回结果**

```powershell
{
    "code": 200,
    "msg": "添加证书成功"
}
```





## 2.删除证书

#### 2.1 请求方式

```powershell
Delete  /sslCert/delete
Content-Type  : application/x-www-form-urlencoded
```

|       参数       |   说明   | 是否必填 |  类型  | 备注 |
| :--------------: | :------: | :------: | :----: | ---- |
| sslCertificateId | 证书编号 |   必填   | String |      |

#### 2.2 返回码

| 返回码 |      说明      |             备注              |
| :----: | :------------: | :---------------------------: |
|  200   |      成功      |                               |
|  400   |      失败      | 失败原因详见返回结果中msg字段 |
| 400401 |    无此证书    |                               |
| 404908 | 无权操作该证书 |                               |
| 400611 | 证书已绑定域名 |                               |

#### 2.3 请求示例

http://localhost:8080/paas-object-storage/sslCert/delete

`sslCertificateId:ae299f9b883c4c96bc9b541cfb89dbf5`

**示例返回结果**

```powershell
{
    "code": 200,
    "msg": "删除证书成功"
}
```







## 3.获取证书详情

#### 3.1 请求方式

```powershell
GET  /sslCert/getQNSslCert
Content-Type  : application/x-www-form-urlencoded
```

|       参数       |   说明   | 是否必填 |  类型  | 备注 |
| :--------------: | :------: | :------: | :----: | ---- |
| sslCertificateId | 证书编号 |   必填   | String |      |

#### 3.2 返回码

| 返回码 |      说明      |             备注              |
| :----: | :------------: | :---------------------------: |
|  200   |      成功      |                               |
|  400   |      失败      | 失败原因详见返回结果中msg字段 |
| 400401 |    无此证书    |                               |
| 404908 | 无权操作该证书 |                               |

#### 3.3 请求示例

http://localhost:8080/paas-object-storage/sslCert/getQNSslCert

`sslCertificateId:ae299f9b883c4c96bc9b541cfb89dbf5`

**示例返回结果**

```powershell
{
    "code": 200,
    "msg": "获取证书成功",
    "result": {
        "certId": "5cd118063405971836000359",
        "name": "测试哈哈",
        "uid": "1381797854",
        "common_name": "*.wonderscloud.com",
        "dnsnames": [
            "*.wonderscloud.com",
            "wonderscloud.com"
        ],
        "create_time": 1557207046,
        "not_before": 1525831325,
        "not_after": 1563669990,
        "orderid": "",
        "product_short_name": "",
        "product_type": "",
        "encrypt": "RSA",
        "encryptParameter": "",
        "enable": true,
        "ca": "-----BEGIN CERTIFICATE-----\nMIIGEzCCBPugAwIBAgIMTSXP3aQ8klNyCAmcMA0GCSqGSIb3DQEBCwUAMGAxCzAJ\nBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTYwNAYDVQQDEy1H\nbG9iYWxTaWduIERvbWFpbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0gRzIwHhcN\nMTgwNTA5MDIwMjA1WhcNMTkwNzIxMDA0NjMwWjBAMSEwHwYDVQQLExhEb21haW4g\nQ29udHJvbCBWYWxpZGF0ZWQxGzAZBgNVBAMMEioud29uZGVyc2Nsb3VkLmNvbTCC\nASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMlKNEjblHM3e7i4fRHYs36M\nj4duvaSloVPXBJSjX9g0F32OLQBlAKvsHZKsteYnJfMTZzYfoB8YiBHg3GvprpoN\nLUH1KLLlPDgKytnTFcO4c0ALjHim0HC3WycYDiiA4tSL3/hrySPaRDUhLY6O8Enj\nL7DgS0bF2ENBoN1Lp9wNJajQz0K281UOsvi+Aq1ZmxKk2mFhoCFP71ouLhOQf2+y\nfLXTlYERhXnbhS/UZlTHJocYgh2CZP1uI9vOykoep5p27zmBdE7VrGVLN6J+F+uN\nH9Q78ZY+/W2XHcVShMGLq2Lo/UmnizgXad608PRC/VW31opLXH5XbICwZ71mmdMC\nAwEAAaOCAuswggLnMA4GA1UdDwEB/wQEAwIFoDCBlAYIKwYBBQUHAQEEgYcwgYQw\nRwYIKwYBBQUHMAKGO2h0dHA6Ly9zZWN1cmUuZ2xvYmFsc2lnbi5jb20vY2FjZXJ0\nL2dzZG9tYWludmFsc2hhMmcycjEuY3J0MDkGCCsGAQUFBzABhi1odHRwOi8vb2Nz\ncDIuZ2xvYmFsc2lnbi5jb20vZ3Nkb21haW52YWxzaGEyZzIwVgYDVR0gBE8wTTBB\nBgkrBgEEAaAyAQowNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2ln\nbi5jb20vcmVwb3NpdG9yeS8wCAYGZ4EMAQIBMAkGA1UdEwQCMAAwQwYDVR0fBDww\nOjA4oDagNIYyaHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9ncy9nc2RvbWFpbnZh\nbHNoYTJnMi5jcmwwLwYDVR0RBCgwJoISKi53b25kZXJzY2xvdWQuY29tghB3b25k\nZXJzY2xvdWQuY29tMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAdBgNV\nHQ4EFgQU9wQ5e/qRryQkVaSU/EybcDeztzcwHwYDVR0jBBgwFoAU6k581IAt5RWB\nhiaMgm3AmKTPlw8wggEEBgorBgEEAdZ5AgQCBIH1BIHyAPAAdwC72d+8H4pxtZOU\nI5eqkntHOFeVCqtS6BqQlmQ2jh7RhQAAAWNCol+0AAAEAwBIMEYCIQC1k4bUYczh\nsqe1fUHz1e8k+GBSIwqlqRRCfLsZa6k/GAIhAOqgXdXNb6Btp/gEIyzwE2jmAsYY\nokATN0WbBshhHiJbAHUAb1N2rDHwMRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMA\nAAFjQqJfZAAABAMARjBEAiBNFS6Z+qDOI1XH8yWIewj9oNUF8zpWLrJqrw4XBGl5\nJQIgDsWRijCb0qBh7UEotmwzsNEAh1K61wp8+9ZcnYMsI2MwDQYJKoZIhvcNAQEL\nBQADggEBAHSWn7RFne3nwnO75h1o4Il5pPOKr2Ik3B7BgHWAQAClHHXZP6wDy6Qs\nwgbvCGXIyCWg6qb+w5IZMWkKPCfX3Z/7aJWW3o2Qe/r8xiufRLvrnJ9uEZeywZUz\nVmMbVwxVEE5gf1ISI+6ZmO+yUKFHAvclT6d5tdSnwSFuqAJy9RrBLis0s/p8Zmq5\nChYFHfQmtPPLDBjoiZeVSD0E2duCFB546CXvtzxEJ28FSspAvIQL97zE6lVQko/U\neRov3Pf5zvBVMMLWwU2Rr4X0AQfmM525UH255sFyylElFpcccGd4Rg3hAC/3LJ05\nwDlD/PInwK2jN+jQWa8rTLYkh9BIYx8=\n-----END CERTIFICATE-----\n-----BEGIN CERTIFICATE-----\nMIIEYzCCA0ugAwIBAgILBAAAAAABRE7wPiAwDQYJKoZIhvcNAQELBQAwVzELMAkG\nA1UEBhMCQkUxGTAXBgNVBAoTEEdsb2JhbFNpZ24gbnYtc2ExEDAOBgNVBAsTB1Jv\nb3QgQ0ExGzAZBgNVBAMTEkdsb2JhbFNpZ24gUm9vdCBDQTAeFw0xNDAyMjAxMDAw\nMDBaFw0yNDAyMjAxMDAwMDBaMGAxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9i\nYWxTaWduIG52LXNhMTYwNAYDVQQDEy1HbG9iYWxTaWduIERvbWFpbiBWYWxpZGF0\naW9uIENBIC0gU0hBMjU2IC0gRzIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\nAoIBAQCp3cwOs+IyOd1JIqgTaZOHiOEM7nF9vZCHll1Z8syz0lhXV/lG72wm2DZC\njn4wsy+aPlN7H262okxFHzzTFZMcie089Ffeyr3sBppqKqAZUn9R0XQ5CJ+r69eG\nExWXrjbDVGYOWvKgc4Ux47JkFGr/paKOJLu9hVIVonnu8LXuPbj0fYC82ZA1ZbgX\nqa2zmJ+gfn1u+z+tfMIbWTaW2jcyS0tdNQJjjtunz2LuzC7Ujcm9PGqRcqIip3It\nINH6yjfaGJjmFiRxJUvE5XuJUgkC/VkrBG7KB4HUs9ra2+PMgKhWBwZ8lgg3nds4\ntmI0kWIHdAE42HIw4uuQcSZiwFfzAgMBAAGjggElMIIBITAOBgNVHQ8BAf8EBAMC\nAQYwEgYDVR0TAQH/BAgwBgEB/wIBADAdBgNVHQ4EFgQU6k581IAt5RWBhiaMgm3A\nmKTPlw8wRwYDVR0gBEAwPjA8BgRVHSAAMDQwMgYIKwYBBQUHAgEWJmh0dHBzOi8v\nd3d3Lmdsb2JhbHNpZ24uY29tL3JlcG9zaXRvcnkvMDMGA1UdHwQsMCowKKAmoCSG\nImh0dHA6Ly9jcmwuZ2xvYmFsc2lnbi5uZXQvcm9vdC5jcmwwPQYIKwYBBQUHAQEE\nMTAvMC0GCCsGAQUFBzABhiFodHRwOi8vb2NzcC5nbG9iYWxzaWduLmNvbS9yb290\ncjEwHwYDVR0jBBgwFoAUYHtmGkUNl8qJUC99BM00qP/8/UswDQYJKoZIhvcNAQEL\nBQADggEBANdFnqDc4ONhWgt9d4QXLWVagpqNoycqhffJ7+mG/dRHzQFSlsVDvTex\n4bjyqdKKEYRxkRWJ3AKdC8tsM4U0KJ4gsrGX3G0LEME8zV/qXdeYMcU0mVwAYVXE\nGwJbxeOJyLS4bx448lYm6UHvPc2smU9ZSlctS32ux4j71pg79eXw6ImJuYsDy1oj\nH6T9uOr7Lp2uanMJvPzVoLVEgqtEkS5QLlfBQ9iRBIvpES5ftD953x77PzAAi1Pj\ntywdO02L3ORkHQRYM68bVeerDL8wBHTk8w4vMDmNSwSMHnVmZkngvkA0x1xaUZK6\nEjxS1QSCVS1npd+3lXzuP8MIugS+wEY=\n-----END CERTIFICATE-----",
        "pri": "-----BEGIN RSA PRIVATE KEY-----\nMIIEpQIBAAKCAQEAyUo0SNuUczd7uLh9EdizfoyPh269pKWhU9cElKNf2DQXfY4t\nAGUAq+wdkqy15icl8xNnNh+gHxiIEeDca+mumg0tQfUosuU8OArK2dMVw7hzQAuM\neKbQcLdbJxgOKIDi1Ivf+GvJI9pENSEtjo7wSeMvsOBLRsXYQ0Gg3Uun3A0lqNDP\nQrbzVQ6y+L4CrVmbEqTaYWGgIU/vWi4uE5B/b7J8tdOVgRGFeduFL9RmVMcmhxiC\nHYJk/W4j287KSh6nmnbvOYF0TtWsZUs3on4X640f1Dvxlj79bZcdxVKEwYurYuj9\nSaeLOBdp3rTw9EL9VbfWiktcfldsgLBnvWaZ0wIDAQABAoIBAQCBSxiqc+C24Tyn\nOlmbzQX+k2reI1PNHlax87sNfqNhzJQKIfjl9/qmc9PLEwUEAMFvkVcKfOXtE2TT\nlwv+hOuSKESZyU+LGZPEq6oAoIWqYiAlU0W3ee8Jd70lL15oxeDthasndpSMYvbM\n6bLJjybpTvSjOG8h1pNDIDSj8KSxwtxGEXk5ztIHA2rDhzqosyYkXz50Pz+w4Y3C\nyVgPm49qrFcGnqx6wjFm2DCeHhuX/cTUP4HpCc+PxsbH3ZzGinuwXAyeJ16FrJIL\nEZF20yQaxIWQ3DSuR/sfRhSD0Nr1Wmb5jLVyzZUgfwwCiCdticKWx+eZDjemyfbE\nEm8AqIUBAoGBAOv7rS7z48AQNgeHzkrY0UQGaIsWsBgyznkt/SOdE6Vj22NxJebU\nbOhm/job2b4TVrFqqkBMJfWMJN0MzVSHMdssiEstIMm1srO1VTIG6U74VRgOhq91\nAnsSNEno8Q1zwAf/hMCwD2XZoKCNAMDqIe1b1jJo4XFpqGCGVTqHBwFrAoGBANpd\nK83Gv6sC8LE8rGWDfmwd0HuK7ScdsHdpQO6tcOO9DUkGd9sVcdh4Sp1bTw5JpykC\nU9nZEuujW6BudLvj9pzIeQWUYXcL4oLPE1SpqXGWDIjbyOEnYvu+uTVL8+l/e9+Y\nAluLd5ldLHgpAa0icoC+wXG4+VYU90JhomHuUxs5AoGBALwMvfBZmBdYk8Wiekj4\nyKI9vReBh+hwrEqy1DOKG7rDfXOcGgj9JKB6WoLx6K1oecA98Nf2FpW7DTE9325J\nofg3X5EQsD0tVu+QRAjuqwMsL5tREBFH3Cc41YREZbtDjzaQbzih1FNkfnUSiBQz\nP8AHXASlwWmAOhZZVheivHlrAoGAQVy8POct/iFuLI9Nx0EZ+YDfI+vM2vWPGd8+\nmXL/JJw1OtOd0s1Te4leREXNnyg6yhKxSLcqa7BFP/twI8Gz0vBLEXl9xyWekqsr\nTR9P4fkZHWo0cfjZLyPCyopGo3EOgjo9Emvny9dQ9VLI9qsl/RWcT88fiz3RWWRi\nN9AfvjkCgYEAiVAAQ+qMMXzLdov6vxStMxtY5HRirpj61fS3w1KQGRvVCTZQE+ny\n7t3+y+Xvqq97QunTiQ6lOur2LSM9rGPwAix63h4DVGPzv1o+bX8fbUEVz1ASyr9R\nU8Vcsc3zHhE1IVU7W4fkyN14FnczECt69VTmeCC2ojR05IE71sNroJ4=\n-----END RSA PRIVATE KEY-----"
    }
}
```







## 4.分页展示证书信息

#### 4.1 请求方式

```powershell
GET  /sslCert/page
Content-Type  : application/x-www-form-urlencoded
```

|    参数     |     说明     | 是否必填 |  类型   | 备注                |
| :---------: | :----------: | :------: | :-----: | ------------------- |
| remarkName  |   备注名称   |   选填   | String  |                     |
| genericName |   通用名称   |   选填   | String  |                     |
|  beginTime  | 创建开始时间 |   选填   | String  | yyyy-MM-dd HH:mm:ss |
|   endTime   | 创建结束时间 |   选填   | String  | yyyy-MM-dd HH:mm:ss |
|    page     |      页      |   选填   | Integer |                     |
|    rows     |      码      |   选填   | Integer |                     |
|    sidx     |   排序字段   |   选填   | String  |                     |
|    sord     |   排序方式   |   选填   | String  |                     |

#### 4.2 返回码

| 返回码 | 说明 |             备注              |
| :----: | :--: | :---------------------------: |
|  200   | 成功 |                               |
|  400   | 失败 | 失败原因详见返回结果中msg字段 |

#### 4.3 请求示例

http://localhost:8080/paas-object-storage/sslCert/page

**示例返回结果**

```powershell
{
    "code": 200,
    "msg": "success",
    "result": {
        "total": 2,
        "list": [
            {
                "sslCertificateId": "e9bcca48f729482a8f8dc4971f3e379d",
                "certificateId": "5cd2640234059718360003c7",
                "remarksName": "测试123",
                "genericName": "*.wonderscloud.com",
                "brand": "0",
                "awardTime": "2018-05-08T16:00:00.000+0000",
                "expireTime": "2019-07-20T16:00:00.000+0000",
                "createTime": "2019-05-08T05:07:14.000+0000",
                "updateTime": "2019-05-08T05:07:14.000+0000",
                "validFlag": "1"
            },
            {
                "sslCertificateId": "c7db7db486f04d06be750981963e1c49",
                "certificateId": "5cd118063405971836000359",
                "remarksName": "测试哈哈",
                "genericName": "*.wonderscloud.com",
                "brand": "0",
                "awardTime": "2018-05-08T16:00:00.000+0000",
                "expireTime": "2019-07-20T16:00:00.000+0000",
                "createTime": "2019-05-07T05:30:46.000+0000",
                "updateTime": "2019-05-07T05:30:46.000+0000",
                "validFlag": "1"
            }
        ],
        "pageNum": 1,
        "pageSize": 10,
        "size": 2,
        "startRow": 1,
        "endRow": 2,
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



