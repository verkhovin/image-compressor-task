package ru.verkhovin.imagecompressor.component

import org.springframework.stereotype.Component
import ru.verkhovin.imagecompressor.configuration.SizeCalculatingConfiguration
import kotlin.math.ceil

@Component
class NewImageSizeCalculatingComponent(private val config: SizeCalculatingConfiguration) {
    fun calculateNewImageSize(originalWidth: Int, originalHeight: Int): Pair<Int, Int> {
        val imageSideSize = config.imageSideSize
        return if (originalWidth > originalHeight) {
            imageSideSize to ceil(originalHeight / (originalWidth / imageSideSize.toDouble())).toInt()
        } else {
            ceil(originalWidth / (originalHeight / imageSideSize.toDouble())).toInt() to imageSideSize
        }
    }
}

