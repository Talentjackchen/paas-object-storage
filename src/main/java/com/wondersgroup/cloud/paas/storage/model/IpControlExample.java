package com.wondersgroup.cloud.paas.storage.model;

import java.util.ArrayList;
import java.util.List;

public class IpControlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IpControlExample() {
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

        public Criteria andIpControlIdIsNull() {
            addCriterion("ip_control_id is null");
            return (Criteria) this;
        }

        public Criteria andIpControlIdIsNotNull() {
            addCriterion("ip_control_id is not null");
            return (Criteria) this;
        }

        public Criteria andIpControlIdEqualTo(String value) {
            addCriterion("ip_control_id =", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdNotEqualTo(String value) {
            addCriterion("ip_control_id <>", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdGreaterThan(String value) {
            addCriterion("ip_control_id >", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdGreaterThanOrEqualTo(String value) {
            addCriterion("ip_control_id >=", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdLessThan(String value) {
            addCriterion("ip_control_id <", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdLessThanOrEqualTo(String value) {
            addCriterion("ip_control_id <=", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdLike(String value) {
            addCriterion("ip_control_id like", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdNotLike(String value) {
            addCriterion("ip_control_id not like", value, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdIn(List<String> values) {
            addCriterion("ip_control_id in", values, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdNotIn(List<String> values) {
            addCriterion("ip_control_id not in", values, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdBetween(String value1, String value2) {
            addCriterion("ip_control_id between", value1, value2, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdNotBetween(String value1, String value2) {
            addCriterion("ip_control_id not between", value1, value2, "ipControlId");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCdnIdIsNull() {
            addCriterion("cdn_id is null");
            return (Criteria) this;
        }

        public Criteria andCdnIdIsNotNull() {
            addCriterion("cdn_id is not null");
            return (Criteria) this;
        }

        public Criteria andCdnIdEqualTo(String value) {
            addCriterion("cdn_id =", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdNotEqualTo(String value) {
            addCriterion("cdn_id <>", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdGreaterThan(String value) {
            addCriterion("cdn_id >", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdGreaterThanOrEqualTo(String value) {
            addCriterion("cdn_id >=", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdLessThan(String value) {
            addCriterion("cdn_id <", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdLessThanOrEqualTo(String value) {
            addCriterion("cdn_id <=", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdLike(String value) {
            addCriterion("cdn_id like", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdNotLike(String value) {
            addCriterion("cdn_id not like", value, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdIn(List<String> values) {
            addCriterion("cdn_id in", values, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdNotIn(List<String> values) {
            addCriterion("cdn_id not in", values, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdBetween(String value1, String value2) {
            addCriterion("cdn_id between", value1, value2, "cdnId");
            return (Criteria) this;
        }

        public Criteria andCdnIdNotBetween(String value1, String value2) {
            addCriterion("cdn_id not between", value1, value2, "cdnId");
            return (Criteria) this;
        }

        public Criteria andIpControlIdLikeInsensitive(String value) {
            addCriterion("upper(ip_control_id) like", value.toUpperCase(), "ipControlId");
            return (Criteria) this;
        }

        public Criteria andAddressLikeInsensitive(String value) {
            addCriterion("upper(address) like", value.toUpperCase(), "address");
            return (Criteria) this;
        }

        public Criteria andTypeLikeInsensitive(String value) {
            addCriterion("upper(`type`) like", value.toUpperCase(), "type");
            return (Criteria) this;
        }

        public Criteria andCdnIdLikeInsensitive(String value) {
            addCriterion("upper(cdn_id) like", value.toUpperCase(), "cdnId");
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