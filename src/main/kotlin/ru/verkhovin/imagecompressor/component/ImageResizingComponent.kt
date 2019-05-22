package ru.verkhovin.imagecompressor.component

import org.springframework.stereotype.Component
import java.awt.AlphaComposite
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

@Component
class ImageResizingComponent(
    private val calculatingComponent: NewImageSizeCalculatingComponent
) {
    fun resizeImage(imageStream: InputStream): ByteArray {
        val originalImage = ImageIO.read(imageStream)
        val (width, height) = calculatingComponent.calculateNewImageSize(originalImage.width, originalImage.height)
        val newImage = createNewImage(width, height, originalImage)
        return packByteArray(newImage)
    }

    private fun packByteArray(newImage: BufferedImage): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(newImage, "png", outputStream)
        return outputStream.toByteArray()
    }

    private fun createNewImage(width: Int, height: Int, originalImage: BufferedImage): BufferedImage {
        val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics = resizedImage.createGraphics()
        graphics.drawImage(originalImage, 0, 0, width, height, null)
        graphics.dispose()
        graphics.composite = AlphaComposite.Src;
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage
    }
}
