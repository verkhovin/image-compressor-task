package ru.verkhovin.imagecompressor.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "compressor")
class SizeCalculatingConfiguration(
    var imageSideSize: Int = 100
)
