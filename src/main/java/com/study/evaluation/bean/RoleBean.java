package com.study.evaluation.bean;

import lombok.Data;

/**
 * description
 *
 * @author Hu.Wang 2020/05/02 9:49
 */
@Data
public class RoleBean {

    private int roleId;         // 角色id
    private String roleName;    // 角色名
    private double rolePercent; // 角色权重
}
