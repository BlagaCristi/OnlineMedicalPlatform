package com.health.application.config

import com.health.application.model.entity.enums.RoleEnum
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.type.EnumType
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Types

class PostgreSQLRoleEnumType : EnumType<RoleEnum>() {

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(
            st: PreparedStatement,
            value: Any?,
            index: Int,
            session: SharedSessionContractImplementor?) {
        st.setObject(
                index,
                if (value != null)
                    (value as Enum<*>).name
                else
                    null,
                Types.OTHER
        )
    }
}