package ru.verkhovin.imagecompressor.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyArray
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.verkhovin.imagecompressor.api.dto.CompressRequest
import ru.verkhovin.imagecompressor.api.CompressorApiController
import ru.verkhovin.imagecompressor.api.dto.Response
import ru.verkhovin.imagecompressor.api.dto.ResponseStatus
import ru.verkhovin.imagecompressor.component.RequestHandler
import java.lang.RuntimeException

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [CompressorApiController::class])
class CompressorApiControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var requestHandler: RequestHandler

    @Test
    fun `when multipart media type accepted multipart handler must be called`() {
        whenever(requestHandler.handleMultipartRequest(anyArray())).thenReturn(listOf("expected"))
        val responseString = mockMvc.perform(
            post("/compress")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("file", "DUMMY_DATA")
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().response.contentAsString
        val response = ObjectMapper().readValue(responseString, Response::class.java)
        assertEquals("expected", response.imagesBase64[0])
        assertEquals(ResponseStatus.OK, response.status)
    }

    @Test
    fun `when multipart request handling failed them error message should be filled`() {
        val errorMessage = "Expected Message"
        whenever(requestHandler.handleMultipartRequest(any())).thenThrow(RuntimeException(errorMessage))
        val responseString = mockMvc.perform(
            post("/compress")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("file", "DUMMY_DATA"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().response.contentAsString
        val response = ObjectMapper().readValue(responseString, Response::class.java)
        assertEquals(ResponseStatus.ERROR, response.status)
        assertEquals(
            "Failed to process multipart/form-data request: $errorMessage",
            response.errorMessage
        )
    }

    @Test
    fun `when json media type accepted json handler must be called`() {
        val request = CompressRequest()
        whenever(requestHandler.handleJsonRequest(any())).thenReturn(listOf("expected"))
        val responseString = mockMvc.perform(
            post("/compress")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().response.contentAsString
        val response = ObjectMapper().readValue(responseString, Response::class.java)
        assertEquals("expected", response.imagesBase64[0])
        assertEquals(ResponseStatus.OK, response.status)
    }

    @Test
    fun `when json request handling failed them error message should be filled`() {
        val errorMessage = "Expected Message"
        val request = CompressRequest()
        whenever(requestHandler.handleJsonRequest(any())).thenThrow(RuntimeException(errorMessage))
        val responseString = mockMvc.perform(
            post("/compress")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().response.contentAsString
        val response = ObjectMapper().readValue(responseString, Response::class.java)
        assertEquals(ResponseStatus.ERROR, response.status)
        assertEquals(
            "Failed to process application/json request: $errorMessage",
            response.errorMessage
        )
    }
}

