package com.rainsoul.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthRole implements Serializable {
    private static final long serialVersionUID = 422256240999600735L;

    private Long id;
    private String roleName;
    private String roleKey;
    private String createdBy;
    private Date createdTime;
    private String updatedBy;
    private Date updatedTime;
    private Integer idDeleted;
}
