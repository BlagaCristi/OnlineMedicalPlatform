package com.health.application.model.dto

import com.health.application.model.entity.types.CaregiverEntity

class CaregiverDto(
        var id: Int? = null,
        var userDto: UserDto? = null
) {
    companion object {
        fun toDto(caregiverEntity: CaregiverEntity): CaregiverDto {
            return CaregiverDto(
                    id = caregiverEntity.id,
                    userDto = if (caregiverEntity.user != null) UserDto.toDto(caregiverEntity.user!!) else null
            )
        }

        fun fromDto(caregiverDto: CaregiverDto): CaregiverEntity {
            return CaregiverEntity(
                    id = caregiverDto.id,
                    user = if (caregiverDto.userDto != null) UserDto.fromDto(caregiverDto.userDto!!) else null
            )
        }
    }
}