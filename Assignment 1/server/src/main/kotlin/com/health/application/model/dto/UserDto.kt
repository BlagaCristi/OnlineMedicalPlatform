package com.health.application.model.dto

import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.UserRoleEntity
import com.health.application.model.entity.enums.GenderEnum
import java.sql.Date

class UserDto(
        var id: Int? = null,
        var username: String? = null,
        var password: String? = null,
        var personName: String? = null,
        var birthDate: Date? = null,
        var gender: GenderEnum? = null,
        var address: String? = null,
        var roleId: Int? = null,
        var email: String? = null
) {
    companion object {
        fun fromDto(userDto: UserDto): UserEntity {
            return UserEntity(
                    id = userDto.id,
                    username = userDto.username,
                    password = userDto.password,
                    personName = userDto.personName,
                    birthDate = userDto.birthDate,
                    gender = userDto.gender,
                    address = userDto.address,
                    userRole =  if (userDto.roleId != null) UserRoleEntity(id = userDto.roleId) else null,
                    email = userDto.email
            )
        }

        fun toDto(userEntity: UserEntity): UserDto {
            return UserDto(
                    id = userEntity.id,
                    username = userEntity.username,
                    password = userEntity.password,
                    personName = userEntity.personName,
                    birthDate = userEntity.birthDate,
                    gender = userEntity.gender,
                    address = userEntity.address,
                    roleId = if (userEntity.userRole != null) userEntity.userRole!!.id else null,
                    email = userEntity.email
            )
        }
    }
}