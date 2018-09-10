package com.xuzhong.sparkproject.domain;

import java.io.Serializable;

public class SessionRandomRxtract implements Serializable{
	private static final long serialVersionUID = 6853092054739860419L;

	private Integer taskId;

    private String sessionId;

    private String startTime;

    private String endTime;

    private String searchKeywords;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords == null ? null : searchKeywords.trim();
    }
}