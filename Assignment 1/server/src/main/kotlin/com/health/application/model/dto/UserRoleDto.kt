package com.health.application.model.dto

import com.health.application.model.entity.UserRoleEntity
import com.health.application.model.entity.enums.RoleEnum

class UserRoleDto(
        var id: Int? = null,
        var roleName: RoleEnum? = null
) {
    companion object {
        fun toDto(userRoleEntity: UserRoleEntity): UserRoleDto {
            return UserRoleDto(
                    id = userRoleEntity.id,
                    roleName = userRoleEntity.roleName
            )
        }

        fun fromDto(userRoleDto: UserRoleDto): UserRoleEntity {
            return UserRoleEntity(
                    id = userRoleDto.id,
                    roleName = userRoleDto.roleName
            )
        }
    }
}