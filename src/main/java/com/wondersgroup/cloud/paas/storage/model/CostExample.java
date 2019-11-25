package com.wondersgroup.cloud.paas.storage.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CostExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCostIdIsNull() {
            addCriterion("cost_id is null");
            return (Criteria) this;
        }

        public Criteria andCostIdIsNotNull() {
            addCriterion("cost_id is not null");
            return (Criteria) this;
        }

        public Criteria andCostIdEqualTo(String value) {
            addCriterion("cost_id =", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdNotEqualTo(String value) {
            addCriterion("cost_id <>", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdGreaterThan(String value) {
            addCriterion("cost_id >", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdGreaterThanOrEqualTo(String value) {
            addCriterion("cost_id >=", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdLessThan(String value) {
            addCriterion("cost_id <", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdLessThanOrEqualTo(String value) {
            addCriterion("cost_id <=", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdLike(String value) {
            addCriterion("cost_id like", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdNotLike(String value) {
            addCriterion("cost_id not like", value, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdIn(List<String> values) {
            addCriterion("cost_id in", values, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdNotIn(List<String> values) {
            addCriterion("cost_id not in", values, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdBetween(String value1, String value2) {
            addCriterion("cost_id between", value1, value2, "costId");
            return (Criteria) this;
        }

        public Criteria andCostIdNotBetween(String value1, String value2) {
            addCriterion("cost_id not between", value1, value2, "costId");
            return (Criteria) this;
        }

        public Criteria andBucketIdIsNull() {
            addCriterion("bucket_id is null");
            return (Criteria) this;
        }

        public Criteria andBucketIdIsNotNull() {
            addCriterion("bucket_id is not null");
            return (Criteria) this;
        }

        public Criteria andBucketIdEqualTo(String value) {
            addCriterion("bucket_id =", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdNotEqualTo(String value) {
            addCriterion("bucket_id <>", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdGreaterThan(String value) {
            addCriterion("bucket_id >", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdGreaterThanOrEqualTo(String value) {
            addCriterion("bucket_id >=", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdLessThan(String value) {
            addCriterion("bucket_id <", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdLessThanOrEqualTo(String value) {
            addCriterion("bucket_id <=", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdLike(String value) {
            addCriterion("bucket_id like", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdNotLike(String value) {
            addCriterion("bucket_id not like", value, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdIn(List<String> values) {
            addCriterion("bucket_id in", values, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdNotIn(List<String> values) {
            addCriterion("bucket_id not in", values, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdBetween(String value1, String value2) {
            addCriterion("bucket_id between", value1, value2, "bucketId");
            return (Criteria) this;
        }

        public Criteria andBucketIdNotBetween(String value1, String value2) {
            addCriterion("bucket_id not between", value1, value2, "bucketId");
            return (Criteria) this;
        }

        public Criteria andStorageIsNull() {
            addCriterion("`storage` is null");
            return (Criteria) this;
        }

        public Criteria andStorageIsNotNull() {
            addCriterion("`storage` is not null");
            return (Criteria) this;
        }

        public Criteria andStorageEqualTo(Float value) {
            addCriterion("`storage` =", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageNotEqualTo(Float value) {
            addCriterion("`storage` <>", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageGreaterThan(Float value) {
            addCriterion("`storage` >", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageGreaterThanOrEqualTo(Float value) {
            addCriterion("`storage` >=", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageLessThan(Float value) {
            addCriterion("`storage` <", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageLessThanOrEqualTo(Float value) {
            addCriterion("`storage` <=", value, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageIn(List<Float> values) {
            addCriterion("`storage` in", values, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageNotIn(List<Float> values) {
            addCriterion("`storage` not in", values, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageBetween(Float value1, Float value2) {
            addCriterion("`storage` between", value1, value2, "storage");
            return (Criteria) this;
        }

        public Criteria andStorageNotBetween(Float value1, Float value2) {
            addCriterion("`storage` not between", value1, value2, "storage");
            return (Criteria) this;
        }

        public Criteria andStoragePriceIsNull() {
            addCriterion("storage_price is null");
            return (Criteria) this;
        }

        public Criteria andStoragePriceIsNotNull() {
            addCriterion("storage_price is not null");
            return (Criteria) this;
        }

        public Criteria andStoragePriceEqualTo(BigDecimal value) {
            addCriterion("storage_price =", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceNotEqualTo(BigDecimal value) {
            addCriterion("storage_price <>", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceGreaterThan(BigDecimal value) {
            addCriterion("storage_price >", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("storage_price >=", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceLessThan(BigDecimal value) {
            addCriterion("storage_price <", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("storage_price <=", value, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceIn(List<BigDecimal> values) {
            addCriterion("storage_price in", values, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceNotIn(List<BigDecimal> values) {
            addCriterion("storage_price not in", values, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("storage_price between", value1, value2, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andStoragePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("storage_price not between", value1, value2, "storagePrice");
            return (Criteria) this;
        }

        public Criteria andApiIsNull() {
            addCriterion("api is null");
            return (Criteria) this;
        }

        public Criteria andApiIsNotNull() {
            addCriterion("api is not null");
            return (Criteria) this;
        }

        public Criteria andApiEqualTo(Float value) {
            addCriterion("api =", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiNotEqualTo(Float value) {
            addCriterion("api <>", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiGreaterThan(Float value) {
            addCriterion("api >", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiGreaterThanOrEqualTo(Float value) {
            addCriterion("api >=", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiLessThan(Float value) {
            addCriterion("api <", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiLessThanOrEqualTo(Float value) {
            addCriterion("api <=", value, "api");
            return (Criteria) this;
        }

        public Criteria andApiIn(List<Float> values) {
            addCriterion("api in", values, "api");
            return (Criteria) this;
        }

        public Criteria andApiNotIn(List<Float> values) {
            addCriterion("api not in", values, "api");
            return (Criteria) this;
        }

        public Criteria andApiBetween(Float value1, Float value2) {
            addCriterion("api between", value1, value2, "api");
            return (Criteria) this;
        }

        public Criteria andApiNotBetween(Float value1, Float value2) {
            addCriterion("api not between", value1, value2, "api");
            return (Criteria) this;
        }

        public Criteria andApiPriceIsNull() {
            addCriterion("api_price is null");
            return (Criteria) this;
        }

        public Criteria andApiPriceIsNotNull() {
            addCriterion("api_price is not null");
            return (Criteria) this;
        }

        public Criteria andApiPriceEqualTo(BigDecimal value) {
            addCriterion("api_price =", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceNotEqualTo(BigDecimal value) {
            addCriterion("api_price <>", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceGreaterThan(BigDecimal value) {
            addCriterion("api_price >", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("api_price >=", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceLessThan(BigDecimal value) {
            addCriterion("api_price <", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("api_price <=", value, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceIn(List<BigDecimal> values) {
            addCriterion("api_price in", values, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceNotIn(List<BigDecimal> values) {
            addCriterion("api_price not in", values, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("api_price between", value1, value2, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andApiPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("api_price not between", value1, value2, "apiPrice");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNull() {
            addCriterion("exchange is null");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNotNull() {
            addCriterion("exchange is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeEqualTo(Float value) {
            addCriterion("exchange =", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotEqualTo(Float value) {
            addCriterion("exchange <>", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThan(Float value) {
            addCriterion("exchange >", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThanOrEqualTo(Float value) {
            addCriterion("exchange >=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThan(Float value) {
            addCriterion("exchange <", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThanOrEqualTo(Float value) {
            addCriterion("exchange <=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeIn(List<Float> values) {
            addCriterion("exchange in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotIn(List<Float> values) {
            addCriterion("exchange not in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeBetween(Float value1, Float value2) {
            addCriterion("exchange between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotBetween(Float value1, Float value2) {
            addCriterion("exchange not between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangePriceIsNull() {
            addCriterion("exchange_price is null");
            return (Criteria) this;
        }

        public Criteria andExchangePriceIsNotNull() {
            addCriterion("exchange_price is not null");
            return (Criteria) this;
        }

        public Criteria andExchangePriceEqualTo(BigDecimal value) {
            addCriterion("exchange_price =", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceNotEqualTo(BigDecimal value) {
            addCriterion("exchange_price <>", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceGreaterThan(BigDecimal value) {
            addCriterion("exchange_price >", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_price >=", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceLessThan(BigDecimal value) {
            addCriterion("exchange_price <", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_price <=", value, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceIn(List<BigDecimal> values) {
            addCriterion("exchange_price in", values, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceNotIn(List<BigDecimal> values) {
            addCriterion("exchange_price not in", values, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_price between", value1, value2, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andExchangePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_price not between", value1, value2, "exchangePrice");
            return (Criteria) this;
        }

        public Criteria andDataIsNull() {
            addCriterion("`data` is null");
            return (Criteria) this;
        }

        public Criteria andDataIsNotNull() {
            addCriterion("`data` is not null");
            return (Criteria) this;
        }

        public Criteria andDataEqualTo(Float value) {
            addCriterion("`data` =", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotEqualTo(Float value) {
            addCriterion("`data` <>", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThan(Float value) {
            addCriterion("`data` >", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanOrEqualTo(Float value) {
            addCriterion("`data` >=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThan(Float value) {
            addCriterion("`data` <", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThanOrEqualTo(Float value) {
            addCriterion("`data` <=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataIn(List<Float> values) {
            addCriterion("`data` in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotIn(List<Float> values) {
            addCriterion("`data` not in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataBetween(Float value1, Float value2) {
            addCriterion("`data` between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotBetween(Float value1, Float value2) {
            addCriterion("`data` not between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andDataPriceIsNull() {
            addCriterion("data_price is null");
            return (Criteria) this;
        }

        public Criteria andDataPriceIsNotNull() {
            addCriterion("data_price is not null");
            return (Criteria) this;
        }

        public Criteria andDataPriceEqualTo(BigDecimal value) {
            addCriterion("data_price =", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceNotEqualTo(BigDecimal value) {
            addCriterion("data_price <>", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceGreaterThan(BigDecimal value) {
            addCriterion("data_price >", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("data_price >=", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceLessThan(BigDecimal value) {
            addCriterion("data_price <", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("data_price <=", value, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceIn(List<BigDecimal> values) {
            addCriterion("data_price in", values, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceNotIn(List<BigDecimal> values) {
            addCriterion("data_price not in", values, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("data_price between", value1, value2, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andDataPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("data_price not between", value1, value2, "dataPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutIsNull() {
            addCriterion("cdn_out is null");
            return (Criteria) this;
        }

        public Criteria andCdnOutIsNotNull() {
            addCriterion("cdn_out is not null");
            return (Criteria) this;
        }

        public Criteria andCdnOutEqualTo(Float value) {
            addCriterion("cdn_out =", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutNotEqualTo(Float value) {
            addCriterion("cdn_out <>", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutGreaterThan(Float value) {
            addCriterion("cdn_out >", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutGreaterThanOrEqualTo(Float value) {
            addCriterion("cdn_out >=", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutLessThan(Float value) {
            addCriterion("cdn_out <", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutLessThanOrEqualTo(Float value) {
            addCriterion("cdn_out <=", value, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutIn(List<Float> values) {
            addCriterion("cdn_out in", values, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutNotIn(List<Float> values) {
            addCriterion("cdn_out not in", values, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutBetween(Float value1, Float value2) {
            addCriterion("cdn_out between", value1, value2, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutNotBetween(Float value1, Float value2) {
            addCriterion("cdn_out not between", value1, value2, "cdnOut");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceIsNull() {
            addCriterion("cdn_out_price is null");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceIsNotNull() {
            addCriterion("cdn_out_price is not null");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceEqualTo(BigDecimal value) {
            addCriterion("cdn_out_price =", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceNotEqualTo(BigDecimal value) {
            addCriterion("cdn_out_price <>", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceGreaterThan(BigDecimal value) {
            addCriterion("cdn_out_price >", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cdn_out_price >=", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceLessThan(BigDecimal value) {
            addCriterion("cdn_out_price <", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cdn_out_price <=", value, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceIn(List<BigDecimal> values) {
            addCriterion("cdn_out_price in", values, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceNotIn(List<BigDecimal> values) {
            addCriterion("cdn_out_price not in", values, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cdn_out_price between", value1, value2, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andCdnOutPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cdn_out_price not between", value1, value2, "cdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardStorageIsNull() {
            addCriterion("standard_storage is null");
            return (Criteria) this;
        }

        public Criteria andStandardStorageIsNotNull() {
            addCriterion("standard_storage is not null");
            return (Criteria) this;
        }

        public Criteria andStandardStorageEqualTo(Float value) {
            addCriterion("standard_storage =", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageNotEqualTo(Float value) {
            addCriterion("standard_storage <>", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageGreaterThan(Float value) {
            addCriterion("standard_storage >", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageGreaterThanOrEqualTo(Float value) {
            addCriterion("standard_storage >=", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageLessThan(Float value) {
            addCriterion("standard_storage <", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageLessThanOrEqualTo(Float value) {
            addCriterion("standard_storage <=", value, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageIn(List<Float> values) {
            addCriterion("standard_storage in", values, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageNotIn(List<Float> values) {
            addCriterion("standard_storage not in", values, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageBetween(Float value1, Float value2) {
            addCriterion("standard_storage between", value1, value2, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStorageNotBetween(Float value1, Float value2) {
            addCriterion("standard_storage not between", value1, value2, "standardStorage");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceIsNull() {
            addCriterion("standard_storage_price is null");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceIsNotNull() {
            addCriterion("standard_storage_price is not null");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceEqualTo(BigDecimal value) {
            addCriterion("standard_storage_price =", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceNotEqualTo(BigDecimal value) {
            addCriterion("standard_storage_price <>", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceGreaterThan(BigDecimal value) {
            addCriterion("standard_storage_price >", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_storage_price >=", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceLessThan(BigDecimal value) {
            addCriterion("standard_storage_price <", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_storage_price <=", value, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceIn(List<BigDecimal> values) {
            addCriterion("standard_storage_price in", values, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceNotIn(List<BigDecimal> values) {
            addCriterion("standard_storage_price not in", values, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_storage_price between", value1, value2, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardStoragePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_storage_price not between", value1, value2, "standardStoragePrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiIsNull() {
            addCriterion("standard_api is null");
            return (Criteria) this;
        }

        public Criteria andStandardApiIsNotNull() {
            addCriterion("standard_api is not null");
            return (Criteria) this;
        }

        public Criteria andStandardApiEqualTo(Float value) {
            addCriterion("standard_api =", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiNotEqualTo(Float value) {
            addCriterion("standard_api <>", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiGreaterThan(Float value) {
            addCriterion("standard_api >", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiGreaterThanOrEqualTo(Float value) {
            addCriterion("standard_api >=", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiLessThan(Float value) {
            addCriterion("standard_api <", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiLessThanOrEqualTo(Float value) {
            addCriterion("standard_api <=", value, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiIn(List<Float> values) {
            addCriterion("standard_api in", values, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiNotIn(List<Float> values) {
            addCriterion("standard_api not in", values, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiBetween(Float value1, Float value2) {
            addCriterion("standard_api between", value1, value2, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiNotBetween(Float value1, Float value2) {
            addCriterion("standard_api not between", value1, value2, "standardApi");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceIsNull() {
            addCriterion("standard_api_price is null");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceIsNotNull() {
            addCriterion("standard_api_price is not null");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceEqualTo(BigDecimal value) {
            addCriterion("standard_api_price =", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceNotEqualTo(BigDecimal value) {
            addCriterion("standard_api_price <>", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceGreaterThan(BigDecimal value) {
            addCriterion("standard_api_price >", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_api_price >=", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceLessThan(BigDecimal value) {
            addCriterion("standard_api_price <", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_api_price <=", value, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceIn(List<BigDecimal> values) {
            addCriterion("standard_api_price in", values, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceNotIn(List<BigDecimal> values) {
            addCriterion("standard_api_price not in", values, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_api_price between", value1, value2, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardApiPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_api_price not between", value1, value2, "standardApiPrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeIsNull() {
            addCriterion("standard_exchange is null");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeIsNotNull() {
            addCriterion("standard_exchange is not null");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeEqualTo(Float value) {
            addCriterion("standard_exchange =", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeNotEqualTo(Float value) {
            addCriterion("standard_exchange <>", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeGreaterThan(Float value) {
            addCriterion("standard_exchange >", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeGreaterThanOrEqualTo(Float value) {
            addCriterion("standard_exchange >=", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeLessThan(Float value) {
            addCriterion("standard_exchange <", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeLessThanOrEqualTo(Float value) {
            addCriterion("standard_exchange <=", value, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeIn(List<Float> values) {
            addCriterion("standard_exchange in", values, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeNotIn(List<Float> values) {
            addCriterion("standard_exchange not in", values, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeBetween(Float value1, Float value2) {
            addCriterion("standard_exchange between", value1, value2, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangeNotBetween(Float value1, Float value2) {
            addCriterion("standard_exchange not between", value1, value2, "standardExchange");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceIsNull() {
            addCriterion("standard_exchange_price is null");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceIsNotNull() {
            addCriterion("standard_exchange_price is not null");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceEqualTo(BigDecimal value) {
            addCriterion("standard_exchange_price =", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceNotEqualTo(BigDecimal value) {
            addCriterion("standard_exchange_price <>", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceGreaterThan(BigDecimal value) {
            addCriterion("standard_exchange_price >", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_exchange_price >=", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceLessThan(BigDecimal value) {
            addCriterion("standard_exchange_price <", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_exchange_price <=", value, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceIn(List<BigDecimal> values) {
            addCriterion("standard_exchange_price in", values, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceNotIn(List<BigDecimal> values) {
            addCriterion("standard_exchange_price not in", values, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_exchange_price between", value1, value2, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardExchangePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_exchange_price not between", value1, value2, "standardExchangePrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutIsNull() {
            addCriterion("standard_cdn_out is null");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutIsNotNull() {
            addCriterion("standard_cdn_out is not null");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutEqualTo(Float value) {
            addCriterion("standard_cdn_out =", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutNotEqualTo(Float value) {
            addCriterion("standard_cdn_out <>", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutGreaterThan(Float value) {
            addCriterion("standard_cdn_out >", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutGreaterThanOrEqualTo(Float value) {
            addCriterion("standard_cdn_out >=", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutLessThan(Float value) {
            addCriterion("standard_cdn_out <", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutLessThanOrEqualTo(Float value) {
            addCriterion("standard_cdn_out <=", value, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutIn(List<Float> values) {
            addCriterion("standard_cdn_out in", values, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutNotIn(List<Float> values) {
            addCriterion("standard_cdn_out not in", values, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutBetween(Float value1, Float value2) {
            addCriterion("standard_cdn_out between", value1, value2, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutNotBetween(Float value1, Float value2) {
            addCriterion("standard_cdn_out not between", value1, value2, "standardCdnOut");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceIsNull() {
            addCriterion("standard_cdn_out_price is null");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceIsNotNull() {
            addCriterion("standard_cdn_out_price is not null");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceEqualTo(BigDecimal value) {
            addCriterion("standard_cdn_out_price =", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceNotEqualTo(BigDecimal value) {
            addCriterion("standard_cdn_out_price <>", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceGreaterThan(BigDecimal value) {
            addCriterion("standard_cdn_out_price >", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_cdn_out_price >=", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceLessThan(BigDecimal value) {
            addCriterion("standard_cdn_out_price <", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("standard_cdn_out_price <=", value, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceIn(List<BigDecimal> values) {
            addCriterion("standard_cdn_out_price in", values, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceNotIn(List<BigDecimal> values) {
            addCriterion("standard_cdn_out_price not in", values, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_cdn_out_price between", value1, value2, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andStandardCdnOutPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("standard_cdn_out_price not between", value1, value2, "standardCdnOutPrice");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeIsNull() {
            addCriterion("accounting_time is null");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeIsNotNull() {
            addCriterion("accounting_time is not null");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeEqualTo(Date value) {
            addCriterion("accounting_time =", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeNotEqualTo(Date value) {
            addCriterion("accounting_time <>", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeGreaterThan(Date value) {
            addCriterion("accounting_time >", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("accounting_time >=", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeLessThan(Date value) {
            addCriterion("accounting_time <", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeLessThanOrEqualTo(Date value) {
            addCriterion("accounting_time <=", value, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeIn(List<Date> values) {
            addCriterion("accounting_time in", values, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeNotIn(List<Date> values) {
            addCriterion("accounting_time not in", values, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeBetween(Date value1, Date value2) {
            addCriterion("accounting_time between", value1, value2, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andAccountingTimeNotBetween(Date value1, Date value2) {
            addCriterion("accounting_time not between", value1, value2, "accountingTime");
            return (Criteria) this;
        }

        public Criteria andCostIdLikeInsensitive(String value) {
            addCriterion("upper(cost_id) like", value.toUpperCase(), "costId");
            return (Criteria) this;
        }

        public Criteria andBucketIdLikeInsensitive(String value) {
            addCriterion("upper(bucket_id) like", value.toUpperCase(), "bucketId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}