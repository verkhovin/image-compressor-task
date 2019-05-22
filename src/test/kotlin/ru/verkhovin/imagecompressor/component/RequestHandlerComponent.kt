package ru.verkhovin.imagecompressor.component

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.web.multipart.MultipartFile
import ru.verkhovin.imagecompressor.api.dto.CompressRequest
import ru.verkhovin.imagecompressor.converter.Base64Converter
import ru.verkhovin.imagecompressor.converter.FileUrlConverter
import ru.verkhovin.imagecompressor.converter.MultipartFileConverter
import java.io.ByteArrayInputStream
import java.util.Base64

@RunWith(MockitoJUnitRunner::class)
class RequestHandlerComponent {
    @Mock
    private lateinit var multipartFileConverter: MultipartFileConverter
    @Mock
    private lateinit var base64Converter: Base64Converter
    @Mock
    private lateinit var fileUrlConverter: FileUrlConverter
    @Mock
    private lateinit var imageResizingComponent: ImageResizingComponent
    @Mock
    private lateinit var base64Encoder: Base64.Encoder
    @Mock
    private lateinit var multipartFile: MultipartFile

    @InjectMocks
    private lateinit var observable: RequestHandler

    @Before
    fun setUp() {
        whenever(multipartFileConverter.convert(any())).thenReturn(ByteArrayInputStream(ByteArray(5)))
        whenever(imageResizingComponent.resizeImage(any())).thenReturn(ByteArray(5))
        whenever(base64Encoder.encodeToString(any())).thenReturn("")
        whenever(base64Converter.convert(any())).thenReturn(ByteArrayInputStream(ByteArray(5)))
        whenever(fileUrlConverter.convert(any())).thenReturn(ByteArrayInputStream(ByteArray(5)))
    }

    @Test
    fun `when there is multiple files in multipart request then list with the same size should be returned`() {
        val actual =
            observable.handleMultipartRequest(arrayOf(multipartFile, multipartFile, multipartFile))
        assertEquals(3, actual.size)
    }

    @Test
    fun `when both urls and base64 parts presented in request then returning list size must be equals to sum of sizes of this parts`() {
        val actual = observable.handleJsonRequest(
            CompressRequest(
                listOf("1", "2"),
                listOf("1", "2", "3")
            )
        )
        assertEquals(5, actual.size)
    }
}
