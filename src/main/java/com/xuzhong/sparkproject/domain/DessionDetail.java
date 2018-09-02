package com.xuzhong.sparkproject.domain;

public class DessionDetail {
    private Integer taskId;

    private Integer userId;

    private String sessionId;

    private Integer pageId;

    private String actionTime;

    private String searchKeyword;

    private Integer clickCategoryId;

    private Integer clickProductId;

    private String orderCategoryIds;

    private String orderProductIds;

    private String payCategoryIds;

    private String payProductIds;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime == null ? null : actionTime.trim();
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword == null ? null : searchKeyword.trim();
    }

    public Integer getClickCategoryId() {
        return clickCategoryId;
    }

    public void setClickCategoryId(Integer clickCategoryId) {
        this.clickCategoryId = clickCategoryId;
    }

    public Integer getClickProductId() {
        return clickProductId;
    }

    public void setClickProductId(Integer clickProductId) {
        this.clickProductId = clickProductId;
    }

    public String getOrderCategoryIds() {
        return orderCategoryIds;
    }

    public void setOrderCategoryIds(String orderCategoryIds) {
        this.orderCategoryIds = orderCategoryIds == null ? null : orderCategoryIds.trim();
    }

    public String getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(String orderProductIds) {
        this.orderProductIds = orderProductIds == null ? null : orderProductIds.trim();
    }

    public String getPayCategoryIds() {
        return payCategoryIds;
    }

    public void setPayCategoryIds(String payCategoryIds) {
        this.payCategoryIds = payCategoryIds == null ? null : payCategoryIds.trim();
    }

    public String getPayProductIds() {
        return payProductIds;
    }

    public void setPayProductIds(String payProductIds) {
        this.payProductIds = payProductIds == null ? null : payProductIds.trim();
    }
}