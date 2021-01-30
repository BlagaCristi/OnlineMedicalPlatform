package com.health.application.model.entity

import com.health.application.config.PostgreSQLRoleEnumType
import com.health.application.model.entity.enums.RoleEnum
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "user_role")
@TypeDef(
        name = "role_type",
        typeClass = PostgreSQLRoleEnumType::class
)
data class UserRoleEntity(
        @Column(name = "role_name")
        @Enumerated(EnumType.STRING)
        @Type(type = "role_type")
        var roleName: RoleEnum? = null,

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence_generator")
        @SequenceGenerator(name = "user_role_sequence_generator", sequenceName = "user_role_sequence", allocationSize = 50)
        var id: Int? = null
)