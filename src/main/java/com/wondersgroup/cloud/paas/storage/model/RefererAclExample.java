package com.wondersgroup.cloud.paas.storage.model;

import java.util.ArrayList;
import java.util.List;

public class RefererAclExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RefererAclExample() {
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

        public Criteria andRefererAclIdIsNull() {
            addCriterion("referer_acl_id is null");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdIsNotNull() {
            addCriterion("referer_acl_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdEqualTo(String value) {
            addCriterion("referer_acl_id =", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdNotEqualTo(String value) {
            addCriterion("referer_acl_id <>", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdGreaterThan(String value) {
            addCriterion("referer_acl_id >", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdGreaterThanOrEqualTo(String value) {
            addCriterion("referer_acl_id >=", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdLessThan(String value) {
            addCriterion("referer_acl_id <", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdLessThanOrEqualTo(String value) {
            addCriterion("referer_acl_id <=", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdLike(String value) {
            addCriterion("referer_acl_id like", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdNotLike(String value) {
            addCriterion("referer_acl_id not like", value, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdIn(List<String> values) {
            addCriterion("referer_acl_id in", values, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdNotIn(List<String> values) {
            addCriterion("referer_acl_id not in", values, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdBetween(String value1, String value2) {
            addCriterion("referer_acl_id between", value1, value2, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andRefererAclIdNotBetween(String value1, String value2) {
            addCriterion("referer_acl_id not between", value1, value2, "refererAclId");
            return (Criteria) this;
        }

        public Criteria andDomainIsNull() {
            addCriterion("`domain` is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("`domain` is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("`domain` =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("`domain` <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("`domain` >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("`domain` >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("`domain` <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("`domain` <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("`domain` like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("`domain` not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("`domain` in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("`domain` not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("`domain` between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("`domain` not between", value1, value2, "domain");
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

        public Criteria andAllowedEmptyIsNull() {
            addCriterion("allowed_empty is null");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyIsNotNull() {
            addCriterion("allowed_empty is not null");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyEqualTo(String value) {
            addCriterion("allowed_empty =", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyNotEqualTo(String value) {
            addCriterion("allowed_empty <>", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyGreaterThan(String value) {
            addCriterion("allowed_empty >", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyGreaterThanOrEqualTo(String value) {
            addCriterion("allowed_empty >=", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyLessThan(String value) {
            addCriterion("allowed_empty <", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyLessThanOrEqualTo(String value) {
            addCriterion("allowed_empty <=", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyLike(String value) {
            addCriterion("allowed_empty like", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyNotLike(String value) {
            addCriterion("allowed_empty not like", value, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyIn(List<String> values) {
            addCriterion("allowed_empty in", values, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyNotIn(List<String> values) {
            addCriterion("allowed_empty not in", values, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyBetween(String value1, String value2) {
            addCriterion("allowed_empty between", value1, value2, "allowedEmpty");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyNotBetween(String value1, String value2) {
            addCriterion("allowed_empty not between", value1, value2, "allowedEmpty");
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

        public Criteria andRefererAclIdLikeInsensitive(String value) {
            addCriterion("upper(referer_acl_id) like", value.toUpperCase(), "refererAclId");
            return (Criteria) this;
        }

        public Criteria andDomainLikeInsensitive(String value) {
            addCriterion("upper(`domain`) like", value.toUpperCase(), "domain");
            return (Criteria) this;
        }

        public Criteria andTypeLikeInsensitive(String value) {
            addCriterion("upper(`type`) like", value.toUpperCase(), "type");
            return (Criteria) this;
        }

        public Criteria andAllowedEmptyLikeInsensitive(String value) {
            addCriterion("upper(allowed_empty) like", value.toUpperCase(), "allowedEmpty");
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