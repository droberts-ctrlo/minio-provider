package blog.davetheitguy.minioprovider.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
data class MinioConfig(
    var endpoint: String = "http://localhost:9000",
    var bucket: String = "test",
    var key: String,
    var secret: String
)