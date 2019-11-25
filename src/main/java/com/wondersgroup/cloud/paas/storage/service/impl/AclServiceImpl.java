package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.utils.IpUtils;
import com.wondersgroup.cloud.paas.common.utils.MD5Utils;
import com.wondersgroup.cloud.paas.storage.constant.TimestampAclConstant;
import com.wondersgroup.cloud.paas.storage.model.IpAcl;
import com.wondersgroup.cloud.paas.storage.model.RefererAcl;
import com.wondersgroup.cloud.paas.storage.model.TimestampAcl;
import com.wondersgroup.cloud.paas.storage.service.AclService;
import com.wondersgroup.cloud.paas.storage.service.IpAclService;
import com.wondersgroup.cloud.paas.storage.service.RefererAclService;
import com.wondersgroup.cloud.paas.storage.service.TimestampAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenlong
 */
@Service
public class AclServiceImpl implements AclService {
    @Autowired
    private RefererAclService refererAclService;

    @Autowired
    private IpAclService ipAclService;

    @Autowired
    private TimestampAclService timestampAclService;

    /**
     * 允许空referer
     */
    private static final String allowEmpty = "1";
    @Override
    public boolean validTimestamp(String bucketId, String path, String expire, String sign) throws Exception {
        TimestampAcl timestampAcl = timestampAclService.queryByBucketId(bucketId);
        if (timestampAcl != null) {
            path = URLEncoder.encode(path,"utf-8");
            String s = String.format("%s%s", timestampAcl.getMainKey(), path);
            String mainToken = MD5Utils.encode(s);
            if (!mainToken.equals(sign)) {
                s = String.format("%s%s", timestampAcl.getSpareKey(), path);
                String spareToken = MD5Utils.encode(s);
                if (!spareToken.equals(sign)) {
                    throw new BusinessException(TimestampAclConstant.URL_MODIFIED_CODE, TimestampAclConstant.URL_MODIFIED_MSG);
                }
            }

            long currentTime = System.currentTimeMillis();
            if (currentTime < Long.valueOf(expire)) {
                return true;
            } else {
                throw new BusinessException(TimestampAclConstant.URL_TIME_EXPIRE_CODE, TimestampAclConstant.URL_TIME_EXPIRE_MSG);
            }
        }else{
            return true;
        }
    }

    @Override
    public boolean validIp(String bucketId, String clientIp) {
        IpAcl ipAcl = ipAclService.queryByBucketId(bucketId);
        if(ipAcl != null){
            String addressStr = ipAcl.getIpAddress();
            String[] addressList = addressStr.split(",");
            boolean passFlag = false;
            for(String address : addressList){
                if (address.contains(CommonConstant.BACKSLASH)) {
                    if(IpUtils.isInRange(clientIp,address)){
                        passFlag = true;
                        break;
                    }
                }else{
                    if(address.equals(clientIp)){
                        passFlag = true;
                        break;
                    }
                }
            }
            return passFlag;
        }else{
            return true;
        }
    }

    @Override
    public boolean validReferer(String bucketId, String referer) {
        RefererAcl refererAcl = refererAclService.queryByBucketId(bucketId);
        if(refererAcl != null){
            if (referer == null) {
                return allowEmpty.equals(refererAcl.getAllowedEmpty());
            }

            boolean passFlag = false;
            String pattern = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(referer);

            if(m.find()){
                referer = m.group();
                if(referer.contains("https")){
                    referer = referer.substring(8);
                }else{
                    referer = referer.substring(7);
                }
            }else{
                /* referer信息不是域名格式的直接不通过 */
                return false;
            }

            String domainStr = refererAcl.getDomain();
            String[] domains = domainStr.split(",");
            for (String domain : domains) {
                /* 验证子域名 */
                if(domain.contains("*")){
                    pattern = domain.replace("*","") + "$";

                }else{
                    pattern = "^" + domain + "$";
                }

                p = Pattern.compile(pattern);
                m = p.matcher(referer);
                if(m.find()){
                    passFlag = true;
                    break;
                }
            }

            return passFlag;
        }

        return true;
    }
}
