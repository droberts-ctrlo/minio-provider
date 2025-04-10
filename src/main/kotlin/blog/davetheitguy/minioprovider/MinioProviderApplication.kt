package blog.davetheitguy.minioprovider

import blog.davetheitguy.minioprovider.config.MinioConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MinioConfig::class)
class MinioProviderApplication

fun main(args: Array<String>) {
    runApplication<MinioProviderApplication>(*args)
}
