package blog.davetheitguy.minioprovider.config

import io.minio.MinioClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@EnableWebSecurity
@Configuration
class DefaultSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsManager::class)
    fun userDetailsService(datasource: DataSource): UserDetailsManager {
        val encoder = passwordEncoder()
        val generatedPassword = encoder.encode("password")
        val user = User.withUsername("user")
            .password(generatedPassword)
            .roles("USER")
            .build()
        val manager = JdbcUserDetailsManager(datasource)
        if(manager.userExists("user")) manager.createUser(user)
        return manager
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher::class)
    fun defaultAuthenticationEventPublisher(delegate: ApplicationEventPublisher): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(delegate)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers("/login")
                .permitAll()
                .anyRequest().authenticated()
        }.formLogin(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    @ConditionalOnMissingBean(MinioClient::class)
    fun minioClient(minioConfig: MinioConfig): MinioClient {
        return MinioClient.builder()
            .endpoint(minioConfig.endpoint)
            .credentials(minioConfig.key, minioConfig.secret)
            .build()
    }
}