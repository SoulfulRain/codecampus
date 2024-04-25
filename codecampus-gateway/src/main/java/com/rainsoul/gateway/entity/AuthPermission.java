package com.rainsoul.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthPermission implements Serializable {

    private static final long serialVersionUID = -56518358607843924L;

    private Long id;
    private String name;
    private Long ParentId;
    private Integer type;
    private String memuUrl;
    private Integer status;
    private Integer show;
    private String icon;
    private String permissionKey;
    private String updatedBy;
    private Date updatedTime;
    private String createdBy;
    private Date createdTime;
    private Integer idDeleted;

}
