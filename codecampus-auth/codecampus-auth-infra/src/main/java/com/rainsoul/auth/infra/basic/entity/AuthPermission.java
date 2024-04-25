package com.rainsoul.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthPermission)实体类
 *
 * @author makejava
 * @since 2024-04-25 19:02:16
 */
@Data
public class AuthPermission implements Serializable {
    private static final long serialVersionUID = -30714508084159210L;

    private Long id;

    private String name;

    private Long parentId;

    private Integer type;

    private String menuUrl;

    private Integer status;

    private Integer show;

    private String icon;

    private String permissionKey;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer isDeleted;

}

