package ru.verkhovin.imagecompressor.component

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.verkhovin.imagecompressor.configuration.SizeCalculatingConfiguration

class NewImageSizeCalculatingComponentTest {
    private val observable = NewImageSizeCalculatingComponent(SizeCalculatingConfiguration(100))

    @Test
    fun `when width more than height then new width must be 100 and new height must be changed proportionally`() {
        val (newWidth, newHeight) = observable.calculateNewImageSize(1000, 500)
        assertEquals(100, newWidth)
        assertEquals(50, newHeight)
    }

    @Test
    fun `when height more than width then new height must be 100 and new width must be changed proportionally`() {
        val (newWidth, newHeight) = observable.calculateNewImageSize(500, 1000)
        assertEquals(50, newWidth)
        assertEquals(100, newHeight)
    }

    @Test
    fun `when width and height are the same then new width and new height must be equals to 100`() {
        val (newWidth, newHeight) = observable.calculateNewImageSize(500, 500)
        assertEquals(100, newWidth)
        assertEquals(100, newHeight)
    }
}
