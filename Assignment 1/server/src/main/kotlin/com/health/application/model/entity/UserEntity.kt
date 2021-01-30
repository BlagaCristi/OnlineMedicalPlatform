package com.health.application.model.entity

import com.health.application.config.PostgreSQLGenderEnumType
import com.health.application.model.entity.enums.GenderEnum
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "user_table")
@TypeDef(
        name = "gender_type",
        typeClass = PostgreSQLGenderEnumType::class
)
class UserEntity(
        @Column(name = "gender")
        @Enumerated(EnumType.STRING)
        @Type(type = "gender_type")
        var gender: GenderEnum? = null,

        @Column(name = "username", unique = true)
        var username: String? = null,

        @Column(name = "password")
        var password: String? = null,

        @Column(name = "person_name")
        var personName: String? = null,

        @Column(name = "birth_date")
        var birthDate: Date? = null,

        @Column(name = "email")
        var email: String? = null,

        @Column(name = "address")
        var address: String? = null,

        @ManyToOne
        @JoinColumn(name = "role_id")
        var userRole: UserRoleEntity? = null,

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_table_sequence_generator")
        @SequenceGenerator(name = "user_table_sequence_generator", sequenceName = "user_table_sequence", allocationSize = 50)
        var id: Int? = null
)