package org.cqipc.edu.bean;

import java.math.BigInteger;

public class T_u_r {
    private BigInteger user_id;
    private String username;
    private String password;
    private int status;
    private String create_time;
    private String modify_time;
    private String ssex;
    private int age;
    private BigInteger role_id;
    private String role_name;
    private String remark;

    public BigInteger getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public String getSsex() {
        return ssex;
    }

    public int getAge() {
        return age;
    }

    public BigInteger getRole_id() {
        return role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole_id(BigInteger role_id) {
        this.role_id = role_id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public T_u_r(BigInteger user_id, String username, String password, int status, String create_time, String modify_time, String ssex, int age, BigInteger role_id, String role_name, String remark) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.create_time = create_time;
        this.modify_time = modify_time;
        this.ssex = ssex;
        this.age = age;
        this.role_id = role_id;
        this.role_name = role_name;
        this.remark = remark;
    }

    public T_u_r(String username, String password, int status, String create_time, String modify_time, String ssex, int age, BigInteger role_id, String role_name, String remark) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.create_time = create_time;
        this.modify_time = modify_time;
        this.ssex = ssex;
        this.age = age;
        this.role_id = role_id;
        this.role_name = role_name;
        this.remark = remark;
    }

    public T_u_r() {
    }

    @Override
    public String toString() {
        return "T_u_r{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", ssex='" + ssex + '\'' +
                ", age=" + age +
                ", role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
