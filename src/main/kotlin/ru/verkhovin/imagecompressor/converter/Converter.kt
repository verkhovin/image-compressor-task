package ru.verkhovin.imagecompressor.converter

import java.io.InputStream

interface Converter<T> {
    fun convert(source: T): InputStream
}
