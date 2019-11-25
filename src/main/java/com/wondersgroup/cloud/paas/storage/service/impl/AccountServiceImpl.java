package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.storage.mapper.AccountMapper;
import com.wondersgroup.cloud.paas.storage.model.Account;
import com.wondersgroup.cloud.paas.storage.model.AccountExample;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author chenlong
 * 账户认证服务
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired(required = false)
    private AccountMapper accountMapper;
    /**
     * 账号认证信息
     * @return
     */
    @Override
    public Account getAccount(){
        AccountExample example = new AccountExample();
        example.createCriteria().andAccessKeyIsNotNull().andSecretKeyIsNotNull().andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Account> accounts = accountMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(accounts)){
            for(int i=0;i<accounts.size();i++){
                Account account = accounts.get(i);
                if(StringUtils.isNotBlank(account.getAccessKey()) && StringUtils.isNotBlank(account.getSecretKey())){
                    return account;
                }
            }
        }else{
            return null;
        }
        return null;
    }
}
