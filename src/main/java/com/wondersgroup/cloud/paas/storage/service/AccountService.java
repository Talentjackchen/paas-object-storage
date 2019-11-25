package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.Account;

/**
 * @author chenlong
 */
public interface AccountService {
    /**
     * 账号认证信息
     * @return
     */
    Account getAccount();
}
