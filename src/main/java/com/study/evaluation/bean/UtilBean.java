package com.study.evaluation.bean;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/05/11 9:37
 */
@Data
public class UtilBean {

    private String name;
    private List<UtilBean> children;

    public UtilBean() {
    }

    public UtilBean(String name, List<UtilBean> children) {
        this.name = name;
        this.children = children;
    }
}
