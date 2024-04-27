package com.rainsoul.auth.domain.converter;

import com.rainsoul.auth.infra.basic.entity.AuthPermission;
import com.rainsoul.auth.domain.entity.AuthPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthPermissionBOConverter {

    AuthPermissionBOConverter INSTANCE =  Mappers.getMapper(AuthPermissionBOConverter.class);

    AuthPermission convertBOToEntity(AuthPermissionBO authPermissionBO);

}
