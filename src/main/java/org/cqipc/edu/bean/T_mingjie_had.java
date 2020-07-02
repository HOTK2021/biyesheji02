package org.cqipc.edu.bean;

import java.math.BigInteger;

public class T_mingjie_had {
    private BigInteger id;
    private BigInteger userId;
    private String username;
    private double total;
    private String time;
    private String info;
    private String createTime;
    private int status;

    public BigInteger getId() {
        return id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public double getTotal() {
        return total;
    }

    public String getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T_mingjie_had(BigInteger id, BigInteger userId, String username, double total, String time, String info, String createTime, int status) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.total = total;
        this.time = time;
        this.info = info;
        this.createTime = createTime;
        this.status = status;
    }

    public T_mingjie_had(BigInteger userId, String username, double total, String time, String info, String createTime, int status) {
        this.userId = userId;
        this.username = username;
        this.total = total;
        this.time = time;
        this.info = info;
        this.createTime = createTime;
        this.status = status;
    }

    public T_mingjie_had() {
    }

    @Override
    public String toString() {
        return "T_mingjie_had{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", total=" + total +
                ", time='" + time + '\'' +
                ", info='" + info + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
