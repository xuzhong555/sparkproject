package com.xuzhong.sparkproject.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SessionDetailExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Integer value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Integer value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Integer value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Integer value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Integer> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Integer> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSessionIdIsNull() {
            addCriterion("session_id is null");
            return (Criteria) this;
        }

        public Criteria andSessionIdIsNotNull() {
            addCriterion("session_id is not null");
            return (Criteria) this;
        }

        public Criteria andSessionIdEqualTo(String value) {
            addCriterion("session_id =", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotEqualTo(String value) {
            addCriterion("session_id <>", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdGreaterThan(String value) {
            addCriterion("session_id >", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdGreaterThanOrEqualTo(String value) {
            addCriterion("session_id >=", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdLessThan(String value) {
            addCriterion("session_id <", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdLessThanOrEqualTo(String value) {
            addCriterion("session_id <=", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdLike(String value) {
            addCriterion("session_id like", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotLike(String value) {
            addCriterion("session_id not like", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdIn(List<String> values) {
            addCriterion("session_id in", values, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotIn(List<String> values) {
            addCriterion("session_id not in", values, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdBetween(String value1, String value2) {
            addCriterion("session_id between", value1, value2, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotBetween(String value1, String value2) {
            addCriterion("session_id not between", value1, value2, "sessionId");
            return (Criteria) this;
        }

        public Criteria andPageIdIsNull() {
            addCriterion("page_id is null");
            return (Criteria) this;
        }

        public Criteria andPageIdIsNotNull() {
            addCriterion("page_id is not null");
            return (Criteria) this;
        }

        public Criteria andPageIdEqualTo(Integer value) {
            addCriterion("page_id =", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotEqualTo(Integer value) {
            addCriterion("page_id <>", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThan(Integer value) {
            addCriterion("page_id >", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_id >=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThan(Integer value) {
            addCriterion("page_id <", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThanOrEqualTo(Integer value) {
            addCriterion("page_id <=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdIn(List<Integer> values) {
            addCriterion("page_id in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotIn(List<Integer> values) {
            addCriterion("page_id not in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdBetween(Integer value1, Integer value2) {
            addCriterion("page_id between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("page_id not between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andActionTimeIsNull() {
            addCriterion("action_time is null");
            return (Criteria) this;
        }

        public Criteria andActionTimeIsNotNull() {
            addCriterion("action_time is not null");
            return (Criteria) this;
        }

        public Criteria andActionTimeEqualTo(String value) {
            addCriterion("action_time =", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeNotEqualTo(String value) {
            addCriterion("action_time <>", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeGreaterThan(String value) {
            addCriterion("action_time >", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeGreaterThanOrEqualTo(String value) {
            addCriterion("action_time >=", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeLessThan(String value) {
            addCriterion("action_time <", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeLessThanOrEqualTo(String value) {
            addCriterion("action_time <=", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeLike(String value) {
            addCriterion("action_time like", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeNotLike(String value) {
            addCriterion("action_time not like", value, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeIn(List<String> values) {
            addCriterion("action_time in", values, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeNotIn(List<String> values) {
            addCriterion("action_time not in", values, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeBetween(String value1, String value2) {
            addCriterion("action_time between", value1, value2, "actionTime");
            return (Criteria) this;
        }

        public Criteria andActionTimeNotBetween(String value1, String value2) {
            addCriterion("action_time not between", value1, value2, "actionTime");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordIsNull() {
            addCriterion("search_keyword is null");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordIsNotNull() {
            addCriterion("search_keyword is not null");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordEqualTo(String value) {
            addCriterion("search_keyword =", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordNotEqualTo(String value) {
            addCriterion("search_keyword <>", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordGreaterThan(String value) {
            addCriterion("search_keyword >", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("search_keyword >=", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordLessThan(String value) {
            addCriterion("search_keyword <", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordLessThanOrEqualTo(String value) {
            addCriterion("search_keyword <=", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordLike(String value) {
            addCriterion("search_keyword like", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordNotLike(String value) {
            addCriterion("search_keyword not like", value, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordIn(List<String> values) {
            addCriterion("search_keyword in", values, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordNotIn(List<String> values) {
            addCriterion("search_keyword not in", values, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordBetween(String value1, String value2) {
            addCriterion("search_keyword between", value1, value2, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andSearchKeywordNotBetween(String value1, String value2) {
            addCriterion("search_keyword not between", value1, value2, "searchKeyword");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdIsNull() {
            addCriterion("click_category_id is null");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdIsNotNull() {
            addCriterion("click_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdEqualTo(Integer value) {
            addCriterion("click_category_id =", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdNotEqualTo(Integer value) {
            addCriterion("click_category_id <>", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdGreaterThan(Integer value) {
            addCriterion("click_category_id >", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("click_category_id >=", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdLessThan(Integer value) {
            addCriterion("click_category_id <", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("click_category_id <=", value, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdIn(List<Integer> values) {
            addCriterion("click_category_id in", values, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdNotIn(List<Integer> values) {
            addCriterion("click_category_id not in", values, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("click_category_id between", value1, value2, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("click_category_id not between", value1, value2, "clickCategoryId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdIsNull() {
            addCriterion("click_product_id is null");
            return (Criteria) this;
        }

        public Criteria andClickProductIdIsNotNull() {
            addCriterion("click_product_id is not null");
            return (Criteria) this;
        }

        public Criteria andClickProductIdEqualTo(Integer value) {
            addCriterion("click_product_id =", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdNotEqualTo(Integer value) {
            addCriterion("click_product_id <>", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdGreaterThan(Integer value) {
            addCriterion("click_product_id >", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("click_product_id >=", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdLessThan(Integer value) {
            addCriterion("click_product_id <", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("click_product_id <=", value, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdIn(List<Integer> values) {
            addCriterion("click_product_id in", values, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdNotIn(List<Integer> values) {
            addCriterion("click_product_id not in", values, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdBetween(Integer value1, Integer value2) {
            addCriterion("click_product_id between", value1, value2, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andClickProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("click_product_id not between", value1, value2, "clickProductId");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsIsNull() {
            addCriterion("order_category_ids is null");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsIsNotNull() {
            addCriterion("order_category_ids is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsEqualTo(String value) {
            addCriterion("order_category_ids =", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsNotEqualTo(String value) {
            addCriterion("order_category_ids <>", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsGreaterThan(String value) {
            addCriterion("order_category_ids >", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsGreaterThanOrEqualTo(String value) {
            addCriterion("order_category_ids >=", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsLessThan(String value) {
            addCriterion("order_category_ids <", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsLessThanOrEqualTo(String value) {
            addCriterion("order_category_ids <=", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsLike(String value) {
            addCriterion("order_category_ids like", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsNotLike(String value) {
            addCriterion("order_category_ids not like", value, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsIn(List<String> values) {
            addCriterion("order_category_ids in", values, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsNotIn(List<String> values) {
            addCriterion("order_category_ids not in", values, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsBetween(String value1, String value2) {
            addCriterion("order_category_ids between", value1, value2, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIdsNotBetween(String value1, String value2) {
            addCriterion("order_category_ids not between", value1, value2, "orderCategoryIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsIsNull() {
            addCriterion("order_product_ids is null");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsIsNotNull() {
            addCriterion("order_product_ids is not null");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsEqualTo(String value) {
            addCriterion("order_product_ids =", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsNotEqualTo(String value) {
            addCriterion("order_product_ids <>", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsGreaterThan(String value) {
            addCriterion("order_product_ids >", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsGreaterThanOrEqualTo(String value) {
            addCriterion("order_product_ids >=", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsLessThan(String value) {
            addCriterion("order_product_ids <", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsLessThanOrEqualTo(String value) {
            addCriterion("order_product_ids <=", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsLike(String value) {
            addCriterion("order_product_ids like", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsNotLike(String value) {
            addCriterion("order_product_ids not like", value, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsIn(List<String> values) {
            addCriterion("order_product_ids in", values, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsNotIn(List<String> values) {
            addCriterion("order_product_ids not in", values, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsBetween(String value1, String value2) {
            addCriterion("order_product_ids between", value1, value2, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andOrderProductIdsNotBetween(String value1, String value2) {
            addCriterion("order_product_ids not between", value1, value2, "orderProductIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsIsNull() {
            addCriterion("pay_category_ids is null");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsIsNotNull() {
            addCriterion("pay_category_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsEqualTo(String value) {
            addCriterion("pay_category_ids =", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsNotEqualTo(String value) {
            addCriterion("pay_category_ids <>", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsGreaterThan(String value) {
            addCriterion("pay_category_ids >", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsGreaterThanOrEqualTo(String value) {
            addCriterion("pay_category_ids >=", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsLessThan(String value) {
            addCriterion("pay_category_ids <", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsLessThanOrEqualTo(String value) {
            addCriterion("pay_category_ids <=", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsLike(String value) {
            addCriterion("pay_category_ids like", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsNotLike(String value) {
            addCriterion("pay_category_ids not like", value, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsIn(List<String> values) {
            addCriterion("pay_category_ids in", values, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsNotIn(List<String> values) {
            addCriterion("pay_category_ids not in", values, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsBetween(String value1, String value2) {
            addCriterion("pay_category_ids between", value1, value2, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayCategoryIdsNotBetween(String value1, String value2) {
            addCriterion("pay_category_ids not between", value1, value2, "payCategoryIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsIsNull() {
            addCriterion("pay_product_ids is null");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsIsNotNull() {
            addCriterion("pay_product_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsEqualTo(String value) {
            addCriterion("pay_product_ids =", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsNotEqualTo(String value) {
            addCriterion("pay_product_ids <>", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsGreaterThan(String value) {
            addCriterion("pay_product_ids >", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsGreaterThanOrEqualTo(String value) {
            addCriterion("pay_product_ids >=", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsLessThan(String value) {
            addCriterion("pay_product_ids <", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsLessThanOrEqualTo(String value) {
            addCriterion("pay_product_ids <=", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsLike(String value) {
            addCriterion("pay_product_ids like", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsNotLike(String value) {
            addCriterion("pay_product_ids not like", value, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsIn(List<String> values) {
            addCriterion("pay_product_ids in", values, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsNotIn(List<String> values) {
            addCriterion("pay_product_ids not in", values, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsBetween(String value1, String value2) {
            addCriterion("pay_product_ids between", value1, value2, "payProductIds");
            return (Criteria) this;
        }

        public Criteria andPayProductIdsNotBetween(String value1, String value2) {
            addCriterion("pay_product_ids not between", value1, value2, "payProductIds");
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