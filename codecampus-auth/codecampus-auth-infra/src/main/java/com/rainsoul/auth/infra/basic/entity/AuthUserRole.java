package com.rainsoul.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthUserRole)实体类
 *
 * @author makejava
 * @since 2024-04-25 19:03:32
 */
@Data
public class AuthUserRole implements Serializable {
    private static final long serialVersionUID = -65713461906692705L;

    private Long id;

    private Long userId;

    private Long roleId;
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

