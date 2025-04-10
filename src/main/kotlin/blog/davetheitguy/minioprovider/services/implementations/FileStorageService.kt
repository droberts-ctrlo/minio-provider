package blog.davetheitguy.minioprovider.services.implementations

import blog.davetheitguy.minioprovider.services.StorageService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream
import kotlin.io.path.Path

@Service
@Profile("test")
class FileStorageService : StorageService {
    private val storageFolder = File(Path("storage").toUri())

    init {
        if (!storageFolder.exists()) {
            storageFolder.mkdirs()
        }
    }

    override fun upload(file: ByteArray, filename: String) {
        File(storageFolder, filename).writeBytes(file)
    }

    override fun upload(stream: InputStream, fileName: String) {
        File(storageFolder, fileName).outputStream().use { output ->
            stream.copyTo(output)
        }
    }

    override fun download(fileName: String): ByteArray {
        File(storageFolder, fileName).let {
            if(it.exists()) {
                return it.readBytes()
            } else {
                throw IllegalArgumentException("File not found: $fileName")
            }
        }
    }

    override fun delete(fileName: String) {
        File(storageFolder, fileName).let {
            if(it.exists()) {
                it.delete()
            } else {
                throw IllegalArgumentException("File not found: $fileName")
            }
        }
    }
}