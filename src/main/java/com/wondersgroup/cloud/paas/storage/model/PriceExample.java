package com.wondersgroup.cloud.paas.storage.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PriceExample() {
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

        public Criteria andPriceIdIsNull() {
            addCriterion("price_id is null");
            return (Criteria) this;
        }

        public Criteria andPriceIdIsNotNull() {
            addCriterion("price_id is not null");
            return (Criteria) this;
        }

        public Criteria andPriceIdEqualTo(String value) {
            addCriterion("price_id =", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdNotEqualTo(String value) {
            addCriterion("price_id <>", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdGreaterThan(String value) {
            addCriterion("price_id >", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdGreaterThanOrEqualTo(String value) {
            addCriterion("price_id >=", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdLessThan(String value) {
            addCriterion("price_id <", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdLessThanOrEqualTo(String value) {
            addCriterion("price_id <=", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdLike(String value) {
            addCriterion("price_id like", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdNotLike(String value) {
            addCriterion("price_id not like", value, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdIn(List<String> values) {
            addCriterion("price_id in", values, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdNotIn(List<String> values) {
            addCriterion("price_id not in", values, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdBetween(String value1, String value2) {
            addCriterion("price_id between", value1, value2, "priceId");
            return (Criteria) this;
        }

        public Criteria andPriceIdNotBetween(String value1, String value2) {
            addCriterion("price_id not between", value1, value2, "priceId");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andSubcodeIsNull() {
            addCriterion("subCode is null");
            return (Criteria) this;
        }

        public Criteria andSubcodeIsNotNull() {
            addCriterion("subCode is not null");
            return (Criteria) this;
        }

        public Criteria andSubcodeEqualTo(String value) {
            addCriterion("subCode =", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeNotEqualTo(String value) {
            addCriterion("subCode <>", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeGreaterThan(String value) {
            addCriterion("subCode >", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeGreaterThanOrEqualTo(String value) {
            addCriterion("subCode >=", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeLessThan(String value) {
            addCriterion("subCode <", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeLessThanOrEqualTo(String value) {
            addCriterion("subCode <=", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeLike(String value) {
            addCriterion("subCode like", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeNotLike(String value) {
            addCriterion("subCode not like", value, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeIn(List<String> values) {
            addCriterion("subCode in", values, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeNotIn(List<String> values) {
            addCriterion("subCode not in", values, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeBetween(String value1, String value2) {
            addCriterion("subCode between", value1, value2, "subcode");
            return (Criteria) this;
        }

        public Criteria andSubcodeNotBetween(String value1, String value2) {
            addCriterion("subCode not between", value1, value2, "subcode");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("`value` is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("`value` is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(BigDecimal value) {
            addCriterion("`value` =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(BigDecimal value) {
            addCriterion("`value` <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(BigDecimal value) {
            addCriterion("`value` >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("`value` >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(BigDecimal value) {
            addCriterion("`value` <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("`value` <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<BigDecimal> values) {
            addCriterion("`value` in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<BigDecimal> values) {
            addCriterion("`value` not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`value` between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`value` not between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andScopeBeginIsNull() {
            addCriterion("scope_begin is null");
            return (Criteria) this;
        }

        public Criteria andScopeBeginIsNotNull() {
            addCriterion("scope_begin is not null");
            return (Criteria) this;
        }

        public Criteria andScopeBeginEqualTo(Long value) {
            addCriterion("scope_begin =", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginNotEqualTo(Long value) {
            addCriterion("scope_begin <>", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginGreaterThan(Long value) {
            addCriterion("scope_begin >", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginGreaterThanOrEqualTo(Long value) {
            addCriterion("scope_begin >=", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginLessThan(Long value) {
            addCriterion("scope_begin <", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginLessThanOrEqualTo(Long value) {
            addCriterion("scope_begin <=", value, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginIn(List<Long> values) {
            addCriterion("scope_begin in", values, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginNotIn(List<Long> values) {
            addCriterion("scope_begin not in", values, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginBetween(Long value1, Long value2) {
            addCriterion("scope_begin between", value1, value2, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeBeginNotBetween(Long value1, Long value2) {
            addCriterion("scope_begin not between", value1, value2, "scopeBegin");
            return (Criteria) this;
        }

        public Criteria andScopeEndIsNull() {
            addCriterion("scope_end is null");
            return (Criteria) this;
        }

        public Criteria andScopeEndIsNotNull() {
            addCriterion("scope_end is not null");
            return (Criteria) this;
        }

        public Criteria andScopeEndEqualTo(Long value) {
            addCriterion("scope_end =", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndNotEqualTo(Long value) {
            addCriterion("scope_end <>", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndGreaterThan(Long value) {
            addCriterion("scope_end >", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndGreaterThanOrEqualTo(Long value) {
            addCriterion("scope_end >=", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndLessThan(Long value) {
            addCriterion("scope_end <", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndLessThanOrEqualTo(Long value) {
            addCriterion("scope_end <=", value, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndIn(List<Long> values) {
            addCriterion("scope_end in", values, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndNotIn(List<Long> values) {
            addCriterion("scope_end not in", values, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndBetween(Long value1, Long value2) {
            addCriterion("scope_end between", value1, value2, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andScopeEndNotBetween(Long value1, Long value2) {
            addCriterion("scope_end not between", value1, value2, "scopeEnd");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelIsNull() {
            addCriterion("prefixLabel is null");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelIsNotNull() {
            addCriterion("prefixLabel is not null");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelEqualTo(String value) {
            addCriterion("prefixLabel =", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelNotEqualTo(String value) {
            addCriterion("prefixLabel <>", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelGreaterThan(String value) {
            addCriterion("prefixLabel >", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelGreaterThanOrEqualTo(String value) {
            addCriterion("prefixLabel >=", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelLessThan(String value) {
            addCriterion("prefixLabel <", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelLessThanOrEqualTo(String value) {
            addCriterion("prefixLabel <=", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelLike(String value) {
            addCriterion("prefixLabel like", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelNotLike(String value) {
            addCriterion("prefixLabel not like", value, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelIn(List<String> values) {
            addCriterion("prefixLabel in", values, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelNotIn(List<String> values) {
            addCriterion("prefixLabel not in", values, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelBetween(String value1, String value2) {
            addCriterion("prefixLabel between", value1, value2, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelNotBetween(String value1, String value2) {
            addCriterion("prefixLabel not between", value1, value2, "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelIsNull() {
            addCriterion("suffixLabel is null");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelIsNotNull() {
            addCriterion("suffixLabel is not null");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelEqualTo(String value) {
            addCriterion("suffixLabel =", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelNotEqualTo(String value) {
            addCriterion("suffixLabel <>", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelGreaterThan(String value) {
            addCriterion("suffixLabel >", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelGreaterThanOrEqualTo(String value) {
            addCriterion("suffixLabel >=", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelLessThan(String value) {
            addCriterion("suffixLabel <", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelLessThanOrEqualTo(String value) {
            addCriterion("suffixLabel <=", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelLike(String value) {
            addCriterion("suffixLabel like", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelNotLike(String value) {
            addCriterion("suffixLabel not like", value, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelIn(List<String> values) {
            addCriterion("suffixLabel in", values, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelNotIn(List<String> values) {
            addCriterion("suffixLabel not in", values, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelBetween(String value1, String value2) {
            addCriterion("suffixLabel between", value1, value2, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelNotBetween(String value1, String value2) {
            addCriterion("suffixLabel not between", value1, value2, "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andPriceIdLikeInsensitive(String value) {
            addCriterion("upper(price_id) like", value.toUpperCase(), "priceId");
            return (Criteria) this;
        }

        public Criteria andCodeLikeInsensitive(String value) {
            addCriterion("upper(code) like", value.toUpperCase(), "code");
            return (Criteria) this;
        }

        public Criteria andSubcodeLikeInsensitive(String value) {
            addCriterion("upper(subCode) like", value.toUpperCase(), "subcode");
            return (Criteria) this;
        }

        public Criteria andPrefixlabelLikeInsensitive(String value) {
            addCriterion("upper(prefixLabel) like", value.toUpperCase(), "prefixlabel");
            return (Criteria) this;
        }

        public Criteria andSuffixlabelLikeInsensitive(String value) {
            addCriterion("upper(suffixLabel) like", value.toUpperCase(), "suffixlabel");
            return (Criteria) this;
        }

        public Criteria andDescLikeInsensitive(String value) {
            addCriterion("upper(`desc`) like", value.toUpperCase(), "desc");
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