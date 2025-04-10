package blog.davetheitguy.minioprovider.services

import java.io.InputStream

interface StorageService {
    fun upload(file: ByteArray, filename: String)
    fun upload(stream: InputStream, fileName: String)
    fun download(fileName: String): ByteArray
    fun delete(fileName: String)
}