package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "u_id", type = IdType.AUTO)
    private Long uId;

    private String uPassword;
    private String rePassword;

    private String uName;

    private Integer uAuthority;

    private String uHeadPortrait;

    private String uPhone;

    private String uEmail;

    private String uSex;

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @TableField("u_creatorId")
    private Integer uCreatorId;

    @TableField("u_modifierId")
    private Integer uModifierId;

    @TableField("u_modificationTime")
    private String uModificationTime;

    @TableField("u_creationTime")
    private String uCreationTime;

    private User user;

    private String token;

    public String getuHeadPortrait() {
        return uHeadPortrait;
    }

    public void setuHeadPortrait(String uHeadPortrait) {
        this.uHeadPortrait = uHeadPortrait;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getuAuthority() {
        return uAuthority;
    }

    public void setuAuthority(Integer uAuthority) {
        this.uAuthority = uAuthority;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public Integer getuCreatorId() {
        return uCreatorId;
    }

    public void setuCreatorId(Integer uCreatorId) {
        this.uCreatorId = uCreatorId;
    }

    public Integer getuModifierId() {
        return uModifierId;
    }

    public void setuModifierId(Integer uModifierId) {
        this.uModifierId = uModifierId;
    }

    public String getuModificationTime() {
        return uModificationTime;
    }

    public void setuModificationTime(String uModificationTime) {
        this.uModificationTime = uModificationTime;
    }

    public String getuCreationTime() {
        return uCreationTime;
    }

    public void setuCreationTime(String uCreationTime) {
        this.uCreationTime = uCreationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
