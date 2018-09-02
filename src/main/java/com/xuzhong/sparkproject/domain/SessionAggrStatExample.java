package com.xuzhong.sparkproject.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionAggrStatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SessionAggrStatExample() {
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

        public Criteria andSessionCountIsNull() {
            addCriterion("session_count is null");
            return (Criteria) this;
        }

        public Criteria andSessionCountIsNotNull() {
            addCriterion("session_count is not null");
            return (Criteria) this;
        }

        public Criteria andSessionCountEqualTo(Integer value) {
            addCriterion("session_count =", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountNotEqualTo(Integer value) {
            addCriterion("session_count <>", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountGreaterThan(Integer value) {
            addCriterion("session_count >", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("session_count >=", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountLessThan(Integer value) {
            addCriterion("session_count <", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountLessThanOrEqualTo(Integer value) {
            addCriterion("session_count <=", value, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountIn(List<Integer> values) {
            addCriterion("session_count in", values, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountNotIn(List<Integer> values) {
            addCriterion("session_count not in", values, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountBetween(Integer value1, Integer value2) {
            addCriterion("session_count between", value1, value2, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andSessionCountNotBetween(Integer value1, Integer value2) {
            addCriterion("session_count not between", value1, value2, "sessionCount");
            return (Criteria) this;
        }

        public Criteria andC1s3sIsNull() {
            addCriterion("c1s_3s is null");
            return (Criteria) this;
        }

        public Criteria andC1s3sIsNotNull() {
            addCriterion("c1s_3s is not null");
            return (Criteria) this;
        }

        public Criteria andC1s3sEqualTo(Double value) {
            addCriterion("c1s_3s =", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sNotEqualTo(Double value) {
            addCriterion("c1s_3s <>", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sGreaterThan(Double value) {
            addCriterion("c1s_3s >", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sGreaterThanOrEqualTo(Double value) {
            addCriterion("c1s_3s >=", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sLessThan(Double value) {
            addCriterion("c1s_3s <", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sLessThanOrEqualTo(Double value) {
            addCriterion("c1s_3s <=", value, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sIn(List<Double> values) {
            addCriterion("c1s_3s in", values, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sNotIn(List<Double> values) {
            addCriterion("c1s_3s not in", values, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sBetween(Double value1, Double value2) {
            addCriterion("c1s_3s between", value1, value2, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC1s3sNotBetween(Double value1, Double value2) {
            addCriterion("c1s_3s not between", value1, value2, "c1s3s");
            return (Criteria) this;
        }

        public Criteria andC4s6sIsNull() {
            addCriterion("c4s_6s is null");
            return (Criteria) this;
        }

        public Criteria andC4s6sIsNotNull() {
            addCriterion("c4s_6s is not null");
            return (Criteria) this;
        }

        public Criteria andC4s6sEqualTo(Double value) {
            addCriterion("c4s_6s =", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sNotEqualTo(Double value) {
            addCriterion("c4s_6s <>", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sGreaterThan(Double value) {
            addCriterion("c4s_6s >", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sGreaterThanOrEqualTo(Double value) {
            addCriterion("c4s_6s >=", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sLessThan(Double value) {
            addCriterion("c4s_6s <", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sLessThanOrEqualTo(Double value) {
            addCriterion("c4s_6s <=", value, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sIn(List<Double> values) {
            addCriterion("c4s_6s in", values, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sNotIn(List<Double> values) {
            addCriterion("c4s_6s not in", values, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sBetween(Double value1, Double value2) {
            addCriterion("c4s_6s between", value1, value2, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC4s6sNotBetween(Double value1, Double value2) {
            addCriterion("c4s_6s not between", value1, value2, "c4s6s");
            return (Criteria) this;
        }

        public Criteria andC7s9sIsNull() {
            addCriterion("c7s_9s is null");
            return (Criteria) this;
        }

        public Criteria andC7s9sIsNotNull() {
            addCriterion("c7s_9s is not null");
            return (Criteria) this;
        }

        public Criteria andC7s9sEqualTo(Double value) {
            addCriterion("c7s_9s =", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sNotEqualTo(Double value) {
            addCriterion("c7s_9s <>", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sGreaterThan(Double value) {
            addCriterion("c7s_9s >", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sGreaterThanOrEqualTo(Double value) {
            addCriterion("c7s_9s >=", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sLessThan(Double value) {
            addCriterion("c7s_9s <", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sLessThanOrEqualTo(Double value) {
            addCriterion("c7s_9s <=", value, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sIn(List<Double> values) {
            addCriterion("c7s_9s in", values, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sNotIn(List<Double> values) {
            addCriterion("c7s_9s not in", values, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sBetween(Double value1, Double value2) {
            addCriterion("c7s_9s between", value1, value2, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC7s9sNotBetween(Double value1, Double value2) {
            addCriterion("c7s_9s not between", value1, value2, "c7s9s");
            return (Criteria) this;
        }

        public Criteria andC10s30sIsNull() {
            addCriterion("c10s_30s is null");
            return (Criteria) this;
        }

        public Criteria andC10s30sIsNotNull() {
            addCriterion("c10s_30s is not null");
            return (Criteria) this;
        }

        public Criteria andC10s30sEqualTo(Double value) {
            addCriterion("c10s_30s =", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sNotEqualTo(Double value) {
            addCriterion("c10s_30s <>", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sGreaterThan(Double value) {
            addCriterion("c10s_30s >", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sGreaterThanOrEqualTo(Double value) {
            addCriterion("c10s_30s >=", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sLessThan(Double value) {
            addCriterion("c10s_30s <", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sLessThanOrEqualTo(Double value) {
            addCriterion("c10s_30s <=", value, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sIn(List<Double> values) {
            addCriterion("c10s_30s in", values, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sNotIn(List<Double> values) {
            addCriterion("c10s_30s not in", values, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sBetween(Double value1, Double value2) {
            addCriterion("c10s_30s between", value1, value2, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC10s30sNotBetween(Double value1, Double value2) {
            addCriterion("c10s_30s not between", value1, value2, "c10s30s");
            return (Criteria) this;
        }

        public Criteria andC30s60sIsNull() {
            addCriterion("c30s_60s is null");
            return (Criteria) this;
        }

        public Criteria andC30s60sIsNotNull() {
            addCriterion("c30s_60s is not null");
            return (Criteria) this;
        }

        public Criteria andC30s60sEqualTo(Double value) {
            addCriterion("c30s_60s =", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sNotEqualTo(Double value) {
            addCriterion("c30s_60s <>", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sGreaterThan(Double value) {
            addCriterion("c30s_60s >", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sGreaterThanOrEqualTo(Double value) {
            addCriterion("c30s_60s >=", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sLessThan(Double value) {
            addCriterion("c30s_60s <", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sLessThanOrEqualTo(Double value) {
            addCriterion("c30s_60s <=", value, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sIn(List<Double> values) {
            addCriterion("c30s_60s in", values, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sNotIn(List<Double> values) {
            addCriterion("c30s_60s not in", values, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sBetween(Double value1, Double value2) {
            addCriterion("c30s_60s between", value1, value2, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC30s60sNotBetween(Double value1, Double value2) {
            addCriterion("c30s_60s not between", value1, value2, "c30s60s");
            return (Criteria) this;
        }

        public Criteria andC1m3mIsNull() {
            addCriterion("c1m_3m is null");
            return (Criteria) this;
        }

        public Criteria andC1m3mIsNotNull() {
            addCriterion("c1m_3m is not null");
            return (Criteria) this;
        }

        public Criteria andC1m3mEqualTo(Double value) {
            addCriterion("c1m_3m =", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mNotEqualTo(Double value) {
            addCriterion("c1m_3m <>", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mGreaterThan(Double value) {
            addCriterion("c1m_3m >", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mGreaterThanOrEqualTo(Double value) {
            addCriterion("c1m_3m >=", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mLessThan(Double value) {
            addCriterion("c1m_3m <", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mLessThanOrEqualTo(Double value) {
            addCriterion("c1m_3m <=", value, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mIn(List<Double> values) {
            addCriterion("c1m_3m in", values, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mNotIn(List<Double> values) {
            addCriterion("c1m_3m not in", values, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mBetween(Double value1, Double value2) {
            addCriterion("c1m_3m between", value1, value2, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC1m3mNotBetween(Double value1, Double value2) {
            addCriterion("c1m_3m not between", value1, value2, "c1m3m");
            return (Criteria) this;
        }

        public Criteria andC3m10mIsNull() {
            addCriterion("c3m_10m is null");
            return (Criteria) this;
        }

        public Criteria andC3m10mIsNotNull() {
            addCriterion("c3m_10m is not null");
            return (Criteria) this;
        }

        public Criteria andC3m10mEqualTo(Double value) {
            addCriterion("c3m_10m =", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mNotEqualTo(Double value) {
            addCriterion("c3m_10m <>", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mGreaterThan(Double value) {
            addCriterion("c3m_10m >", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mGreaterThanOrEqualTo(Double value) {
            addCriterion("c3m_10m >=", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mLessThan(Double value) {
            addCriterion("c3m_10m <", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mLessThanOrEqualTo(Double value) {
            addCriterion("c3m_10m <=", value, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mIn(List<Double> values) {
            addCriterion("c3m_10m in", values, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mNotIn(List<Double> values) {
            addCriterion("c3m_10m not in", values, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mBetween(Double value1, Double value2) {
            addCriterion("c3m_10m between", value1, value2, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC3m10mNotBetween(Double value1, Double value2) {
            addCriterion("c3m_10m not between", value1, value2, "c3m10m");
            return (Criteria) this;
        }

        public Criteria andC10m30mIsNull() {
            addCriterion("c10m_30m is null");
            return (Criteria) this;
        }

        public Criteria andC10m30mIsNotNull() {
            addCriterion("c10m_30m is not null");
            return (Criteria) this;
        }

        public Criteria andC10m30mEqualTo(Double value) {
            addCriterion("c10m_30m =", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mNotEqualTo(Double value) {
            addCriterion("c10m_30m <>", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mGreaterThan(Double value) {
            addCriterion("c10m_30m >", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mGreaterThanOrEqualTo(Double value) {
            addCriterion("c10m_30m >=", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mLessThan(Double value) {
            addCriterion("c10m_30m <", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mLessThanOrEqualTo(Double value) {
            addCriterion("c10m_30m <=", value, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mIn(List<Double> values) {
            addCriterion("c10m_30m in", values, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mNotIn(List<Double> values) {
            addCriterion("c10m_30m not in", values, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mBetween(Double value1, Double value2) {
            addCriterion("c10m_30m between", value1, value2, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC10m30mNotBetween(Double value1, Double value2) {
            addCriterion("c10m_30m not between", value1, value2, "c10m30m");
            return (Criteria) this;
        }

        public Criteria andC30mIsNull() {
            addCriterion("c30m is null");
            return (Criteria) this;
        }

        public Criteria andC30mIsNotNull() {
            addCriterion("c30m is not null");
            return (Criteria) this;
        }

        public Criteria andC30mEqualTo(Double value) {
            addCriterion("c30m =", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mNotEqualTo(Double value) {
            addCriterion("c30m <>", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mGreaterThan(Double value) {
            addCriterion("c30m >", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mGreaterThanOrEqualTo(Double value) {
            addCriterion("c30m >=", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mLessThan(Double value) {
            addCriterion("c30m <", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mLessThanOrEqualTo(Double value) {
            addCriterion("c30m <=", value, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mIn(List<Double> values) {
            addCriterion("c30m in", values, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mNotIn(List<Double> values) {
            addCriterion("c30m not in", values, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mBetween(Double value1, Double value2) {
            addCriterion("c30m between", value1, value2, "c30m");
            return (Criteria) this;
        }

        public Criteria andC30mNotBetween(Double value1, Double value2) {
            addCriterion("c30m not between", value1, value2, "c30m");
            return (Criteria) this;
        }

        public Criteria andC13IsNull() {
            addCriterion("c1_3 is null");
            return (Criteria) this;
        }

        public Criteria andC13IsNotNull() {
            addCriterion("c1_3 is not null");
            return (Criteria) this;
        }

        public Criteria andC13EqualTo(Double value) {
            addCriterion("c1_3 =", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13NotEqualTo(Double value) {
            addCriterion("c1_3 <>", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13GreaterThan(Double value) {
            addCriterion("c1_3 >", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13GreaterThanOrEqualTo(Double value) {
            addCriterion("c1_3 >=", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13LessThan(Double value) {
            addCriterion("c1_3 <", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13LessThanOrEqualTo(Double value) {
            addCriterion("c1_3 <=", value, "c13");
            return (Criteria) this;
        }

        public Criteria andC13In(List<Double> values) {
            addCriterion("c1_3 in", values, "c13");
            return (Criteria) this;
        }

        public Criteria andC13NotIn(List<Double> values) {
            addCriterion("c1_3 not in", values, "c13");
            return (Criteria) this;
        }

        public Criteria andC13Between(Double value1, Double value2) {
            addCriterion("c1_3 between", value1, value2, "c13");
            return (Criteria) this;
        }

        public Criteria andC13NotBetween(Double value1, Double value2) {
            addCriterion("c1_3 not between", value1, value2, "c13");
            return (Criteria) this;
        }

        public Criteria andC46IsNull() {
            addCriterion("c4_6 is null");
            return (Criteria) this;
        }

        public Criteria andC46IsNotNull() {
            addCriterion("c4_6 is not null");
            return (Criteria) this;
        }

        public Criteria andC46EqualTo(Double value) {
            addCriterion("c4_6 =", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46NotEqualTo(Double value) {
            addCriterion("c4_6 <>", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46GreaterThan(Double value) {
            addCriterion("c4_6 >", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46GreaterThanOrEqualTo(Double value) {
            addCriterion("c4_6 >=", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46LessThan(Double value) {
            addCriterion("c4_6 <", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46LessThanOrEqualTo(Double value) {
            addCriterion("c4_6 <=", value, "c46");
            return (Criteria) this;
        }

        public Criteria andC46In(List<Double> values) {
            addCriterion("c4_6 in", values, "c46");
            return (Criteria) this;
        }

        public Criteria andC46NotIn(List<Double> values) {
            addCriterion("c4_6 not in", values, "c46");
            return (Criteria) this;
        }

        public Criteria andC46Between(Double value1, Double value2) {
            addCriterion("c4_6 between", value1, value2, "c46");
            return (Criteria) this;
        }

        public Criteria andC46NotBetween(Double value1, Double value2) {
            addCriterion("c4_6 not between", value1, value2, "c46");
            return (Criteria) this;
        }

        public Criteria andC79IsNull() {
            addCriterion("c7_9 is null");
            return (Criteria) this;
        }

        public Criteria andC79IsNotNull() {
            addCriterion("c7_9 is not null");
            return (Criteria) this;
        }

        public Criteria andC79EqualTo(Double value) {
            addCriterion("c7_9 =", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79NotEqualTo(Double value) {
            addCriterion("c7_9 <>", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79GreaterThan(Double value) {
            addCriterion("c7_9 >", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79GreaterThanOrEqualTo(Double value) {
            addCriterion("c7_9 >=", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79LessThan(Double value) {
            addCriterion("c7_9 <", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79LessThanOrEqualTo(Double value) {
            addCriterion("c7_9 <=", value, "c79");
            return (Criteria) this;
        }

        public Criteria andC79In(List<Double> values) {
            addCriterion("c7_9 in", values, "c79");
            return (Criteria) this;
        }

        public Criteria andC79NotIn(List<Double> values) {
            addCriterion("c7_9 not in", values, "c79");
            return (Criteria) this;
        }

        public Criteria andC79Between(Double value1, Double value2) {
            addCriterion("c7_9 between", value1, value2, "c79");
            return (Criteria) this;
        }

        public Criteria andC79NotBetween(Double value1, Double value2) {
            addCriterion("c7_9 not between", value1, value2, "c79");
            return (Criteria) this;
        }

        public Criteria andC1030IsNull() {
            addCriterion("c10_30 is null");
            return (Criteria) this;
        }

        public Criteria andC1030IsNotNull() {
            addCriterion("c10_30 is not null");
            return (Criteria) this;
        }

        public Criteria andC1030EqualTo(Double value) {
            addCriterion("c10_30 =", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030NotEqualTo(Double value) {
            addCriterion("c10_30 <>", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030GreaterThan(Double value) {
            addCriterion("c10_30 >", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030GreaterThanOrEqualTo(Double value) {
            addCriterion("c10_30 >=", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030LessThan(Double value) {
            addCriterion("c10_30 <", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030LessThanOrEqualTo(Double value) {
            addCriterion("c10_30 <=", value, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030In(List<Double> values) {
            addCriterion("c10_30 in", values, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030NotIn(List<Double> values) {
            addCriterion("c10_30 not in", values, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030Between(Double value1, Double value2) {
            addCriterion("c10_30 between", value1, value2, "c1030");
            return (Criteria) this;
        }

        public Criteria andC1030NotBetween(Double value1, Double value2) {
            addCriterion("c10_30 not between", value1, value2, "c1030");
            return (Criteria) this;
        }

        public Criteria andC3060IsNull() {
            addCriterion("c30_60 is null");
            return (Criteria) this;
        }

        public Criteria andC3060IsNotNull() {
            addCriterion("c30_60 is not null");
            return (Criteria) this;
        }

        public Criteria andC3060EqualTo(Double value) {
            addCriterion("c30_60 =", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060NotEqualTo(Double value) {
            addCriterion("c30_60 <>", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060GreaterThan(Double value) {
            addCriterion("c30_60 >", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060GreaterThanOrEqualTo(Double value) {
            addCriterion("c30_60 >=", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060LessThan(Double value) {
            addCriterion("c30_60 <", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060LessThanOrEqualTo(Double value) {
            addCriterion("c30_60 <=", value, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060In(List<Double> values) {
            addCriterion("c30_60 in", values, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060NotIn(List<Double> values) {
            addCriterion("c30_60 not in", values, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060Between(Double value1, Double value2) {
            addCriterion("c30_60 between", value1, value2, "c3060");
            return (Criteria) this;
        }

        public Criteria andC3060NotBetween(Double value1, Double value2) {
            addCriterion("c30_60 not between", value1, value2, "c3060");
            return (Criteria) this;
        }

        public Criteria andC60IsNull() {
            addCriterion("c60 is null");
            return (Criteria) this;
        }

        public Criteria andC60IsNotNull() {
            addCriterion("c60 is not null");
            return (Criteria) this;
        }

        public Criteria andC60EqualTo(Double value) {
            addCriterion("c60 =", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60NotEqualTo(Double value) {
            addCriterion("c60 <>", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60GreaterThan(Double value) {
            addCriterion("c60 >", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60GreaterThanOrEqualTo(Double value) {
            addCriterion("c60 >=", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60LessThan(Double value) {
            addCriterion("c60 <", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60LessThanOrEqualTo(Double value) {
            addCriterion("c60 <=", value, "c60");
            return (Criteria) this;
        }

        public Criteria andC60In(List<Double> values) {
            addCriterion("c60 in", values, "c60");
            return (Criteria) this;
        }

        public Criteria andC60NotIn(List<Double> values) {
            addCriterion("c60 not in", values, "c60");
            return (Criteria) this;
        }

        public Criteria andC60Between(Double value1, Double value2) {
            addCriterion("c60 between", value1, value2, "c60");
            return (Criteria) this;
        }

        public Criteria andC60NotBetween(Double value1, Double value2) {
            addCriterion("c60 not between", value1, value2, "c60");
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