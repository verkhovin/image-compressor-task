package ru.verkhovin.imagecompressor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImagecompressorApplication

fun main(args: Array<String>) {
    runApplication<ImagecompressorApplication>(*args)
}
