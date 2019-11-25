package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Cost implements Serializable {
    private String costId;

    private String bucketId;

    private Float storage;

    private BigDecimal storagePrice;

    private Float api;

    private BigDecimal apiPrice;

    private Float exchange;

    private BigDecimal exchangePrice;

    private Float data;

    private BigDecimal dataPrice;

    private Float cdnOut;

    private BigDecimal cdnOutPrice;

    private Float standardStorage;

    private BigDecimal standardStoragePrice;

    private Float standardApi;

    private BigDecimal standardApiPrice;

    private Float standardExchange;

    private BigDecimal standardExchangePrice;

    private Float standardCdnOut;

    private BigDecimal standardCdnOutPrice;

    private Date accountingTime;

    private static final long serialVersionUID = 1L;

    public Cost(String costId, String bucketId, Float storage, BigDecimal storagePrice, Float api, BigDecimal apiPrice, Float exchange, BigDecimal exchangePrice, Float data, BigDecimal dataPrice, Float cdnOut, BigDecimal cdnOutPrice, Float standardStorage, BigDecimal standardStoragePrice, Float standardApi, BigDecimal standardApiPrice, Float standardExchange, BigDecimal standardExchangePrice, Float standardCdnOut, BigDecimal standardCdnOutPrice, Date accountingTime) {
        this.costId = costId;
        this.bucketId = bucketId;
        this.storage = storage;
        this.storagePrice = storagePrice;
        this.api = api;
        this.apiPrice = apiPrice;
        this.exchange = exchange;
        this.exchangePrice = exchangePrice;
        this.data = data;
        this.dataPrice = dataPrice;
        this.cdnOut = cdnOut;
        this.cdnOutPrice = cdnOutPrice;
        this.standardStorage = standardStorage;
        this.standardStoragePrice = standardStoragePrice;
        this.standardApi = standardApi;
        this.standardApiPrice = standardApiPrice;
        this.standardExchange = standardExchange;
        this.standardExchangePrice = standardExchangePrice;
        this.standardCdnOut = standardCdnOut;
        this.standardCdnOutPrice = standardCdnOutPrice;
        this.accountingTime = accountingTime;
    }

    public Cost() {
        super();
    }

    public String getCostId() {
        return costId;
    }

    public Cost withCostId(String costId) {
        this.setCostId(costId);
        return this;
    }

    public void setCostId(String costId) {
        this.costId = costId == null ? null : costId.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public Cost withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public Float getStorage() {
        return storage;
    }

    public Cost withStorage(Float storage) {
        this.setStorage(storage);
        return this;
    }

    public void setStorage(Float storage) {
        this.storage = storage;
    }

    public BigDecimal getStoragePrice() {
        return storagePrice;
    }

    public Cost withStoragePrice(BigDecimal storagePrice) {
        this.setStoragePrice(storagePrice);
        return this;
    }

    public void setStoragePrice(BigDecimal storagePrice) {
        this.storagePrice = storagePrice;
    }

    public Float getApi() {
        return api;
    }

    public Cost withApi(Float api) {
        this.setApi(api);
        return this;
    }

    public void setApi(Float api) {
        this.api = api;
    }

    public BigDecimal getApiPrice() {
        return apiPrice;
    }

    public Cost withApiPrice(BigDecimal apiPrice) {
        this.setApiPrice(apiPrice);
        return this;
    }

    public void setApiPrice(BigDecimal apiPrice) {
        this.apiPrice = apiPrice;
    }

    public Float getExchange() {
        return exchange;
    }

    public Cost withExchange(Float exchange) {
        this.setExchange(exchange);
        return this;
    }

    public void setExchange(Float exchange) {
        this.exchange = exchange;
    }

    public BigDecimal getExchangePrice() {
        return exchangePrice;
    }

    public Cost withExchangePrice(BigDecimal exchangePrice) {
        this.setExchangePrice(exchangePrice);
        return this;
    }

    public void setExchangePrice(BigDecimal exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public Float getData() {
        return data;
    }

    public Cost withData(Float data) {
        this.setData(data);
        return this;
    }

    public void setData(Float data) {
        this.data = data;
    }

    public BigDecimal getDataPrice() {
        return dataPrice;
    }

    public Cost withDataPrice(BigDecimal dataPrice) {
        this.setDataPrice(dataPrice);
        return this;
    }

    public void setDataPrice(BigDecimal dataPrice) {
        this.dataPrice = dataPrice;
    }

    public Float getCdnOut() {
        return cdnOut;
    }

    public Cost withCdnOut(Float cdnOut) {
        this.setCdnOut(cdnOut);
        return this;
    }

    public void setCdnOut(Float cdnOut) {
        this.cdnOut = cdnOut;
    }

    public BigDecimal getCdnOutPrice() {
        return cdnOutPrice;
    }

    public Cost withCdnOutPrice(BigDecimal cdnOutPrice) {
        this.setCdnOutPrice(cdnOutPrice);
        return this;
    }

    public void setCdnOutPrice(BigDecimal cdnOutPrice) {
        this.cdnOutPrice = cdnOutPrice;
    }

    public Float getStandardStorage() {
        return standardStorage;
    }

    public Cost withStandardStorage(Float standardStorage) {
        this.setStandardStorage(standardStorage);
        return this;
    }

    public void setStandardStorage(Float standardStorage) {
        this.standardStorage = standardStorage;
    }

    public BigDecimal getStandardStoragePrice() {
        return standardStoragePrice;
    }

    public Cost withStandardStoragePrice(BigDecimal standardStoragePrice) {
        this.setStandardStoragePrice(standardStoragePrice);
        return this;
    }

    public void setStandardStoragePrice(BigDecimal standardStoragePrice) {
        this.standardStoragePrice = standardStoragePrice;
    }

    public Float getStandardApi() {
        return standardApi;
    }

    public Cost withStandardApi(Float standardApi) {
        this.setStandardApi(standardApi);
        return this;
    }

    public void setStandardApi(Float standardApi) {
        this.standardApi = standardApi;
    }

    public BigDecimal getStandardApiPrice() {
        return standardApiPrice;
    }

    public Cost withStandardApiPrice(BigDecimal standardApiPrice) {
        this.setStandardApiPrice(standardApiPrice);
        return this;
    }

    public void setStandardApiPrice(BigDecimal standardApiPrice) {
        this.standardApiPrice = standardApiPrice;
    }

    public Float getStandardExchange() {
        return standardExchange;
    }

    public Cost withStandardExchange(Float standardExchange) {
        this.setStandardExchange(standardExchange);
        return this;
    }

    public void setStandardExchange(Float standardExchange) {
        this.standardExchange = standardExchange;
    }

    public BigDecimal getStandardExchangePrice() {
        return standardExchangePrice;
    }

    public Cost withStandardExchangePrice(BigDecimal standardExchangePrice) {
        this.setStandardExchangePrice(standardExchangePrice);
        return this;
    }

    public void setStandardExchangePrice(BigDecimal standardExchangePrice) {
        this.standardExchangePrice = standardExchangePrice;
    }

    public Float getStandardCdnOut() {
        return standardCdnOut;
    }

    public Cost withStandardCdnOut(Float standardCdnOut) {
        this.setStandardCdnOut(standardCdnOut);
        return this;
    }

    public void setStandardCdnOut(Float standardCdnOut) {
        this.standardCdnOut = standardCdnOut;
    }

    public BigDecimal getStandardCdnOutPrice() {
        return standardCdnOutPrice;
    }

    public Cost withStandardCdnOutPrice(BigDecimal standardCdnOutPrice) {
        this.setStandardCdnOutPrice(standardCdnOutPrice);
        return this;
    }

    public void setStandardCdnOutPrice(BigDecimal standardCdnOutPrice) {
        this.standardCdnOutPrice = standardCdnOutPrice;
    }

    public Date getAccountingTime() {
        return accountingTime;
    }

    public Cost withAccountingTime(Date accountingTime) {
        this.setAccountingTime(accountingTime);
        return this;
    }

    public void setAccountingTime(Date accountingTime) {
        this.accountingTime = accountingTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Cost other = (Cost) that;
        return (this.getCostId() == null ? other.getCostId() == null : this.getCostId().equals(other.getCostId()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
                && (this.getStorage() == null ? other.getStorage() == null : this.getStorage().equals(other.getStorage()))
                && (this.getStoragePrice() == null ? other.getStoragePrice() == null : this.getStoragePrice().equals(other.getStoragePrice()))
                && (this.getApi() == null ? other.getApi() == null : this.getApi().equals(other.getApi()))
                && (this.getApiPrice() == null ? other.getApiPrice() == null : this.getApiPrice().equals(other.getApiPrice()))
                && (this.getExchange() == null ? other.getExchange() == null : this.getExchange().equals(other.getExchange()))
                && (this.getExchangePrice() == null ? other.getExchangePrice() == null : this.getExchangePrice().equals(other.getExchangePrice()))
                && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()))
                && (this.getDataPrice() == null ? other.getDataPrice() == null : this.getDataPrice().equals(other.getDataPrice()))
                && (this.getCdnOut() == null ? other.getCdnOut() == null : this.getCdnOut().equals(other.getCdnOut()))
                && (this.getCdnOutPrice() == null ? other.getCdnOutPrice() == null : this.getCdnOutPrice().equals(other.getCdnOutPrice()))
                && (this.getStandardStorage() == null ? other.getStandardStorage() == null : this.getStandardStorage().equals(other.getStandardStorage()))
                && (this.getStandardStoragePrice() == null ? other.getStandardStoragePrice() == null : this.getStandardStoragePrice().equals(other.getStandardStoragePrice()))
                && (this.getStandardApi() == null ? other.getStandardApi() == null : this.getStandardApi().equals(other.getStandardApi()))
                && (this.getStandardApiPrice() == null ? other.getStandardApiPrice() == null : this.getStandardApiPrice().equals(other.getStandardApiPrice()))
                && (this.getStandardExchange() == null ? other.getStandardExchange() == null : this.getStandardExchange().equals(other.getStandardExchange()))
                && (this.getStandardExchangePrice() == null ? other.getStandardExchangePrice() == null : this.getStandardExchangePrice().equals(other.getStandardExchangePrice()))
                && (this.getStandardCdnOut() == null ? other.getStandardCdnOut() == null : this.getStandardCdnOut().equals(other.getStandardCdnOut()))
                && (this.getStandardCdnOutPrice() == null ? other.getStandardCdnOutPrice() == null : this.getStandardCdnOutPrice().equals(other.getStandardCdnOutPrice()))
                && (this.getAccountingTime() == null ? other.getAccountingTime() == null : this.getAccountingTime().equals(other.getAccountingTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCostId() == null) ? 0 : getCostId().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getStorage() == null) ? 0 : getStorage().hashCode());
        result = prime * result + ((getStoragePrice() == null) ? 0 : getStoragePrice().hashCode());
        result = prime * result + ((getApi() == null) ? 0 : getApi().hashCode());
        result = prime * result + ((getApiPrice() == null) ? 0 : getApiPrice().hashCode());
        result = prime * result + ((getExchange() == null) ? 0 : getExchange().hashCode());
        result = prime * result + ((getExchangePrice() == null) ? 0 : getExchangePrice().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        result = prime * result + ((getDataPrice() == null) ? 0 : getDataPrice().hashCode());
        result = prime * result + ((getCdnOut() == null) ? 0 : getCdnOut().hashCode());
        result = prime * result + ((getCdnOutPrice() == null) ? 0 : getCdnOutPrice().hashCode());
        result = prime * result + ((getStandardStorage() == null) ? 0 : getStandardStorage().hashCode());
        result = prime * result + ((getStandardStoragePrice() == null) ? 0 : getStandardStoragePrice().hashCode());
        result = prime * result + ((getStandardApi() == null) ? 0 : getStandardApi().hashCode());
        result = prime * result + ((getStandardApiPrice() == null) ? 0 : getStandardApiPrice().hashCode());
        result = prime * result + ((getStandardExchange() == null) ? 0 : getStandardExchange().hashCode());
        result = prime * result + ((getStandardExchangePrice() == null) ? 0 : getStandardExchangePrice().hashCode());
        result = prime * result + ((getStandardCdnOut() == null) ? 0 : getStandardCdnOut().hashCode());
        result = prime * result + ((getStandardCdnOutPrice() == null) ? 0 : getStandardCdnOutPrice().hashCode());
        result = prime * result + ((getAccountingTime() == null) ? 0 : getAccountingTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", costId=").append(costId);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", storage=").append(storage);
        sb.append(", storagePrice=").append(storagePrice);
        sb.append(", api=").append(api);
        sb.append(", apiPrice=").append(apiPrice);
        sb.append(", exchange=").append(exchange);
        sb.append(", exchangePrice=").append(exchangePrice);
        sb.append(", data=").append(data);
        sb.append(", dataPrice=").append(dataPrice);
        sb.append(", cdnOut=").append(cdnOut);
        sb.append(", cdnOutPrice=").append(cdnOutPrice);
        sb.append(", standardStorage=").append(standardStorage);
        sb.append(", standardStoragePrice=").append(standardStoragePrice);
        sb.append(", standardApi=").append(standardApi);
        sb.append(", standardApiPrice=").append(standardApiPrice);
        sb.append(", standardExchange=").append(standardExchange);
        sb.append(", standardExchangePrice=").append(standardExchangePrice);
        sb.append(", standardCdnOut=").append(standardCdnOut);
        sb.append(", standardCdnOutPrice=").append(standardCdnOutPrice);
        sb.append(", accountingTime=").append(accountingTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}