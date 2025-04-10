package blog.davetheitguy.minioprovider.controllers

import blog.davetheitguy.minioprovider.services.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller("/api/v1")
class FileController(private val service: StorageService) {
    @ResponseBody
    @GetMapping("/download/{filename}")
    fun downloadFile(@PathVariable("filename") fileName: String): ResponseEntity<ByteArray> {
        return try {
            ResponseEntity.ok(service.download(fileName))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam file: MultipartFile): ResponseEntity<Nothing> {
        service.upload(file.inputStream, file.name)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/delete/{filename}")
    fun deleteFile(@PathVariable("filename") fileName: String): ResponseEntity<Nothing> {
        return try {
            service.delete(fileName)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.noContent().build()
        }
    }
}