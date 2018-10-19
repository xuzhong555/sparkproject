package com.xuzhong.sparkproject.domain;

public class PageSplitConvertRate {
    private Long taskid;

    private String convertRate;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(String convertRate) {
        this.convertRate = convertRate == null ? null : convertRate.trim();
    }
}