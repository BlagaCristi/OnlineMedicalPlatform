package com.health.application.config

import com.health.application.util.AppProperties
import com.health.application.util.AppProperties.Companion.CAREGIVER
import com.health.application.util.AppProperties.Companion.CAREGIVER_PATH
import com.health.application.util.AppProperties.Companion.DOCTOR
import com.health.application.util.AppProperties.Companion.DOCTOR_PATH
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_INTAKE_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PLAN_DRUGS_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PLAN_PATH
import com.health.application.util.AppProperties.Companion.PATIENT
import com.health.application.util.AppProperties.Companion.PATIENT_PATH
import com.health.application.util.AppProperties.Companion.USER_PATH
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class EndpointSecurity : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    //    Enable jdbc authentication
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username as principal, password as credential, true FROM user_table" + " WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username as principal, role_name as role FROM user_table" +
                        " JOIN user_role ON user_role.id = user_table.role_id" +
                        " WHERE username = ?")
                .rolePrefix("ROLE_")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.headers().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HEALTH_PATH + DOCTOR_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + MEDICATION_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + MEDICATION_PLAN_DRUGS_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + MEDICATION_PLAN_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + MEDICATION_INTAKE_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + PATIENT_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + USER_PATH).hasAnyRole(PATIENT, DOCTOR, CAREGIVER)
                .antMatchers(HEALTH_PATH + CAREGIVER_PATH).hasAnyRole(DOCTOR)
                .antMatchers("/health/userDto-role/**").hasAnyRole(DOCTOR)
                .antMatchers("/health/login/**").hasAnyRole(DOCTOR, PATIENT, CAREGIVER).anyRequest().permitAll()
                .and()
                .httpBasic()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
