package com.wondersgroup.cloud.paas.storage.model;

import java.util.ArrayList;
import java.util.List;

public class TimestampAclExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TimestampAclExample() {
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

        public Criteria andTimestampAclIdIsNull() {
            addCriterion("timestamp_acl_id is null");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdIsNotNull() {
            addCriterion("timestamp_acl_id is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdEqualTo(String value) {
            addCriterion("timestamp_acl_id =", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdNotEqualTo(String value) {
            addCriterion("timestamp_acl_id <>", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdGreaterThan(String value) {
            addCriterion("timestamp_acl_id >", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdGreaterThanOrEqualTo(String value) {
            addCriterion("timestamp_acl_id >=", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdLessThan(String value) {
            addCriterion("timestamp_acl_id <", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdLessThanOrEqualTo(String value) {
            addCriterion("timestamp_acl_id <=", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdLike(String value) {
            addCriterion("timestamp_acl_id like", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdNotLike(String value) {
            addCriterion("timestamp_acl_id not like", value, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdIn(List<String> values) {
            addCriterion("timestamp_acl_id in", values, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdNotIn(List<String> values) {
            addCriterion("timestamp_acl_id not in", values, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdBetween(String value1, String value2) {
            addCriterion("timestamp_acl_id between", value1, value2, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andTimestampAclIdNotBetween(String value1, String value2) {
            addCriterion("timestamp_acl_id not between", value1, value2, "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andMainKeyIsNull() {
            addCriterion("main_key is null");
            return (Criteria) this;
        }

        public Criteria andMainKeyIsNotNull() {
            addCriterion("main_key is not null");
            return (Criteria) this;
        }

        public Criteria andMainKeyEqualTo(String value) {
            addCriterion("main_key =", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyNotEqualTo(String value) {
            addCriterion("main_key <>", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyGreaterThan(String value) {
            addCriterion("main_key >", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyGreaterThanOrEqualTo(String value) {
            addCriterion("main_key >=", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyLessThan(String value) {
            addCriterion("main_key <", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyLessThanOrEqualTo(String value) {
            addCriterion("main_key <=", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyLike(String value) {
            addCriterion("main_key like", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyNotLike(String value) {
            addCriterion("main_key not like", value, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyIn(List<String> values) {
            addCriterion("main_key in", values, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyNotIn(List<String> values) {
            addCriterion("main_key not in", values, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyBetween(String value1, String value2) {
            addCriterion("main_key between", value1, value2, "mainKey");
            return (Criteria) this;
        }

        public Criteria andMainKeyNotBetween(String value1, String value2) {
            addCriterion("main_key not between", value1, value2, "mainKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyIsNull() {
            addCriterion("spare_key is null");
            return (Criteria) this;
        }

        public Criteria andSpareKeyIsNotNull() {
            addCriterion("spare_key is not null");
            return (Criteria) this;
        }

        public Criteria andSpareKeyEqualTo(String value) {
            addCriterion("spare_key =", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyNotEqualTo(String value) {
            addCriterion("spare_key <>", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyGreaterThan(String value) {
            addCriterion("spare_key >", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyGreaterThanOrEqualTo(String value) {
            addCriterion("spare_key >=", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyLessThan(String value) {
            addCriterion("spare_key <", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyLessThanOrEqualTo(String value) {
            addCriterion("spare_key <=", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyLike(String value) {
            addCriterion("spare_key like", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyNotLike(String value) {
            addCriterion("spare_key not like", value, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyIn(List<String> values) {
            addCriterion("spare_key in", values, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyNotIn(List<String> values) {
            addCriterion("spare_key not in", values, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyBetween(String value1, String value2) {
            addCriterion("spare_key between", value1, value2, "spareKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyNotBetween(String value1, String value2) {
            addCriterion("spare_key not between", value1, value2, "spareKey");
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

        public Criteria andTimestampAclIdLikeInsensitive(String value) {
            addCriterion("upper(timestamp_acl_id) like", value.toUpperCase(), "timestampAclId");
            return (Criteria) this;
        }

        public Criteria andMainKeyLikeInsensitive(String value) {
            addCriterion("upper(main_key) like", value.toUpperCase(), "mainKey");
            return (Criteria) this;
        }

        public Criteria andSpareKeyLikeInsensitive(String value) {
            addCriterion("upper(spare_key) like", value.toUpperCase(), "spareKey");
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