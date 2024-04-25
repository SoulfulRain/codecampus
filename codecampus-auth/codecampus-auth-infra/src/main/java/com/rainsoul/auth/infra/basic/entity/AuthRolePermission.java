package com.rainsoul.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthRolePremission)实体类
 *
 * @author makejava
 * @since 2024-04-25 19:02:51
 */
@Data
public class AuthRolePermission implements Serializable {
    private static final long serialVersionUID = -37963604820920612L;

    private Long id;

    private Long roleId;

    private Long permissionId;
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

