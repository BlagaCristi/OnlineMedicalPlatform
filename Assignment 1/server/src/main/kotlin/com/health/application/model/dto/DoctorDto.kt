package com.health.application.model.dto

import com.health.application.model.entity.types.DoctorEntity

class DoctorDto(
        var id: Int? = null,
        var userDto: UserDto? = null
) {
    companion object {
        fun toDto(doctorEntity: DoctorEntity): DoctorDto {
            return DoctorDto(
                    id = doctorEntity.id,
                    userDto = if (doctorEntity.user != null) UserDto.toDto(doctorEntity.user!!) else null
            )
        }

        fun fromDto(doctorDto: DoctorDto): DoctorEntity {
            return DoctorEntity(
                    id = doctorDto.id,
                    user = if (doctorDto.userDto != null) UserDto.fromDto(doctorDto.userDto!!) else null
            )
        }
    }
}