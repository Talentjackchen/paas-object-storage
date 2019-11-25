package com.wondersgroup.cloud.paas.storage.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SslCertificateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SslCertificateExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andSslCertificateIdIsNull() {
            addCriterion("ssl_certificate_id is null");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdIsNotNull() {
            addCriterion("ssl_certificate_id is not null");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdEqualTo(String value) {
            addCriterion("ssl_certificate_id =", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdNotEqualTo(String value) {
            addCriterion("ssl_certificate_id <>", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdGreaterThan(String value) {
            addCriterion("ssl_certificate_id >", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdGreaterThanOrEqualTo(String value) {
            addCriterion("ssl_certificate_id >=", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdLessThan(String value) {
            addCriterion("ssl_certificate_id <", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdLessThanOrEqualTo(String value) {
            addCriterion("ssl_certificate_id <=", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdLike(String value) {
            addCriterion("ssl_certificate_id like", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdNotLike(String value) {
            addCriterion("ssl_certificate_id not like", value, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdIn(List<String> values) {
            addCriterion("ssl_certificate_id in", values, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdNotIn(List<String> values) {
            addCriterion("ssl_certificate_id not in", values, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdBetween(String value1, String value2) {
            addCriterion("ssl_certificate_id between", value1, value2, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdNotBetween(String value1, String value2) {
            addCriterion("ssl_certificate_id not between", value1, value2, "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andRemarksNameIsNull() {
            addCriterion("remarks_name is null");
            return (Criteria) this;
        }

        public Criteria andRemarksNameIsNotNull() {
            addCriterion("remarks_name is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksNameEqualTo(String value) {
            addCriterion("remarks_name =", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameNotEqualTo(String value) {
            addCriterion("remarks_name <>", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameGreaterThan(String value) {
            addCriterion("remarks_name >", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameGreaterThanOrEqualTo(String value) {
            addCriterion("remarks_name >=", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameLessThan(String value) {
            addCriterion("remarks_name <", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameLessThanOrEqualTo(String value) {
            addCriterion("remarks_name <=", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameLike(String value) {
            addCriterion("remarks_name like", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameNotLike(String value) {
            addCriterion("remarks_name not like", value, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameIn(List<String> values) {
            addCriterion("remarks_name in", values, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameNotIn(List<String> values) {
            addCriterion("remarks_name not in", values, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameBetween(String value1, String value2) {
            addCriterion("remarks_name between", value1, value2, "remarksName");
            return (Criteria) this;
        }

        public Criteria andRemarksNameNotBetween(String value1, String value2) {
            addCriterion("remarks_name not between", value1, value2, "remarksName");
            return (Criteria) this;
        }

        public Criteria andGenericNameIsNull() {
            addCriterion("generic_name is null");
            return (Criteria) this;
        }

        public Criteria andGenericNameIsNotNull() {
            addCriterion("generic_name is not null");
            return (Criteria) this;
        }

        public Criteria andGenericNameEqualTo(String value) {
            addCriterion("generic_name =", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameNotEqualTo(String value) {
            addCriterion("generic_name <>", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameGreaterThan(String value) {
            addCriterion("generic_name >", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameGreaterThanOrEqualTo(String value) {
            addCriterion("generic_name >=", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameLessThan(String value) {
            addCriterion("generic_name <", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameLessThanOrEqualTo(String value) {
            addCriterion("generic_name <=", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameLike(String value) {
            addCriterion("generic_name like", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameNotLike(String value) {
            addCriterion("generic_name not like", value, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameIn(List<String> values) {
            addCriterion("generic_name in", values, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameNotIn(List<String> values) {
            addCriterion("generic_name not in", values, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameBetween(String value1, String value2) {
            addCriterion("generic_name between", value1, value2, "genericName");
            return (Criteria) this;
        }

        public Criteria andGenericNameNotBetween(String value1, String value2) {
            addCriterion("generic_name not between", value1, value2, "genericName");
            return (Criteria) this;
        }

        public Criteria andAwardTimeIsNull() {
            addCriterion("award_time is null");
            return (Criteria) this;
        }

        public Criteria andAwardTimeIsNotNull() {
            addCriterion("award_time is not null");
            return (Criteria) this;
        }

        public Criteria andAwardTimeEqualTo(Date value) {
            addCriterionForJDBCDate("award_time =", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("award_time <>", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("award_time >", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("award_time >=", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeLessThan(Date value) {
            addCriterionForJDBCDate("award_time <", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("award_time <=", value, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeIn(List<Date> values) {
            addCriterionForJDBCDate("award_time in", values, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("award_time not in", values, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("award_time between", value1, value2, "awardTime");
            return (Criteria) this;
        }

        public Criteria andAwardTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("award_time not between", value1, value2, "awardTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("expire_time is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expire_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterionForJDBCDate("expire_time =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("expire_time <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("expire_time >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expire_time >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterionForJDBCDate("expire_time <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expire_time <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterionForJDBCDate("expire_time in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("expire_time not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expire_time between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expire_time not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andCertificateIdIsNull() {
            addCriterion("certificate_id is null");
            return (Criteria) this;
        }

        public Criteria andCertificateIdIsNotNull() {
            addCriterion("certificate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateIdEqualTo(String value) {
            addCriterion("certificate_id =", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdNotEqualTo(String value) {
            addCriterion("certificate_id <>", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdGreaterThan(String value) {
            addCriterion("certificate_id >", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_id >=", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdLessThan(String value) {
            addCriterion("certificate_id <", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdLessThanOrEqualTo(String value) {
            addCriterion("certificate_id <=", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdLike(String value) {
            addCriterion("certificate_id like", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdNotLike(String value) {
            addCriterion("certificate_id not like", value, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdIn(List<String> values) {
            addCriterion("certificate_id in", values, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdNotIn(List<String> values) {
            addCriterion("certificate_id not in", values, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdBetween(String value1, String value2) {
            addCriterion("certificate_id between", value1, value2, "certificateId");
            return (Criteria) this;
        }

        public Criteria andCertificateIdNotBetween(String value1, String value2) {
            addCriterion("certificate_id not between", value1, value2, "certificateId");
            return (Criteria) this;
        }

        public Criteria andValidFlagIsNull() {
            addCriterion("valid_flag is null");
            return (Criteria) this;
        }

        public Criteria andValidFlagIsNotNull() {
            addCriterion("valid_flag is not null");
            return (Criteria) this;
        }

        public Criteria andValidFlagEqualTo(String value) {
            addCriterion("valid_flag =", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagNotEqualTo(String value) {
            addCriterion("valid_flag <>", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagGreaterThan(String value) {
            addCriterion("valid_flag >", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagGreaterThanOrEqualTo(String value) {
            addCriterion("valid_flag >=", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagLessThan(String value) {
            addCriterion("valid_flag <", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagLessThanOrEqualTo(String value) {
            addCriterion("valid_flag <=", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagLike(String value) {
            addCriterion("valid_flag like", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagNotLike(String value) {
            addCriterion("valid_flag not like", value, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagIn(List<String> values) {
            addCriterion("valid_flag in", values, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagNotIn(List<String> values) {
            addCriterion("valid_flag not in", values, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagBetween(String value1, String value2) {
            addCriterion("valid_flag between", value1, value2, "validFlag");
            return (Criteria) this;
        }

        public Criteria andValidFlagNotBetween(String value1, String value2) {
            addCriterion("valid_flag not between", value1, value2, "validFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andSslCertificateIdLikeInsensitive(String value) {
            addCriterion("upper(ssl_certificate_id) like", value.toUpperCase(), "sslCertificateId");
            return (Criteria) this;
        }

        public Criteria andRemarksNameLikeInsensitive(String value) {
            addCriterion("upper(remarks_name) like", value.toUpperCase(), "remarksName");
            return (Criteria) this;
        }

        public Criteria andGenericNameLikeInsensitive(String value) {
            addCriterion("upper(generic_name) like", value.toUpperCase(), "genericName");
            return (Criteria) this;
        }

        public Criteria andCertificateIdLikeInsensitive(String value) {
            addCriterion("upper(certificate_id) like", value.toUpperCase(), "certificateId");
            return (Criteria) this;
        }

        public Criteria andValidFlagLikeInsensitive(String value) {
            addCriterion("upper(valid_flag) like", value.toUpperCase(), "validFlag");
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