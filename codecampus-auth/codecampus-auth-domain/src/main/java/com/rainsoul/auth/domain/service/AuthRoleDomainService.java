package com.rainsoul.auth.domain.service;

import com.rainsoul.auth.domain.entity.AuthRoleBO;

public interface AuthRoleDomainService {
    Boolean add(AuthRoleBO authRoleBO);

    Boolean update(AuthRoleBO authRoleBO);

    Boolean delete(AuthRoleBO authRoleBO);
}
