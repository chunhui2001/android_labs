package com.android_labs.todoapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("resource_id")
    @Expose
    private String resourceId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_country")
    @Expose
    private Object userCountry;
    @SerializedName("user_phone")
    @Expose
    private Object userPhone;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("user_status")
    @Expose
    private Object userStatus;
    @SerializedName("user_roles")
    @Expose
    private String userRoles;
    @SerializedName("login_type")
    @Expose
    private String loginType;
    @SerializedName("user_bio")
    @Expose
    private String userBio;
    @SerializedName("user_maxim")
    @Expose
    private String userMaxim;
    @SerializedName("real_name")
    @Expose
    private Object realName;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("idno")
    @Expose
    private Object idno;
    @SerializedName("user_phone_secondary")
    @Expose
    private Object userPhoneSecondary;
    @SerializedName("user_email")
    @Expose
    private Object userEmail;
    @SerializedName("user_address")
    @Expose
    private Object userAddress;
    @SerializedName("user_linkman")
    @Expose
    private Object userLinkman;
    @SerializedName("user_linkphone")
    @Expose
    private Object userLinkphone;
    @SerializedName("created_at")
    @Expose
    private Long createdAt;
    @SerializedName("last_updated_at")
    @Expose
    private Long lastUpdatedAt;
    @SerializedName("last_login_at")
    @Expose
    private Long lastLoginAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(Object userCountry) {
        this.userCountry = userCountry;
    }

    public Object getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Object userPhone) {
        this.userPhone = userPhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Object userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserMaxim() {
        return userMaxim;
    }

    public void setUserMaxim(String userMaxim) {
        this.userMaxim = userMaxim;
    }

    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getIdno() {
        return idno;
    }

    public void setIdno(Object idno) {
        this.idno = idno;
    }

    public Object getUserPhoneSecondary() {
        return userPhoneSecondary;
    }

    public void setUserPhoneSecondary(Object userPhoneSecondary) {
        this.userPhoneSecondary = userPhoneSecondary;
    }

    public Object getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Object userEmail) {
        this.userEmail = userEmail;
    }

    public Object getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Object userAddress) {
        this.userAddress = userAddress;
    }

    public Object getUserLinkman() {
        return userLinkman;
    }

    public void setUserLinkman(Object userLinkman) {
        this.userLinkman = userLinkman;
    }

    public Object getUserLinkphone() {
        return userLinkphone;
    }

    public void setUserLinkphone(Object userLinkphone) {
        this.userLinkphone = userLinkphone;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Long lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}