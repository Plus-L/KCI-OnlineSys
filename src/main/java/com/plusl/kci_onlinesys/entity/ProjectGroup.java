package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: kci_onlinesys
 * @description: 项目组实体类
 * @author: PlusL
 * @create: 2022-03-22 21:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGroup {

    /**
     * 项目组ID
     */
    private int id;

    /**
     * 项目名字
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;
}
