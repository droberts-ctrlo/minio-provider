package blog.davetheitguy.minioprovider.services.implementations

import blog.davetheitguy.minioprovider.config.MinioConfig
import blog.davetheitguy.minioprovider.services.StorageService
import io.minio.*
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class MinioStorageService(
    private val minioConfig: MinioConfig,
    private val minioClient: MinioClient
) : StorageService {
    init {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.bucket).build())) {
            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(minioConfig.bucket)
                    .build()
            )
        }
    }

    override fun upload(file: ByteArray, filename: String) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(minioConfig.bucket)
                .`object`(filename)
                .stream(file.inputStream(), file.size.toLong(), -1)
                .build()
        )
    }

    override fun upload(stream: InputStream, fileName: String) {
        this.upload(stream.readAllBytes(), fileName)
    }

    override fun download(fileName: String): ByteArray {
        val args = GetObjectArgs.builder()
            .bucket(minioConfig.bucket)
            .`object`(fileName)
            .build()
        minioClient.getObject(args).use { stream ->
            return stream.readAllBytes()
        }
    }

    override fun delete(fileName: String) {
        try {
            val args = RemoveObjectArgs.builder()
                .bucket(minioConfig.bucket)
                .`object`(fileName)
                .build()
            minioClient.removeObject(args)
        } catch (e: Exception) {
            // noop
        }
    }
}