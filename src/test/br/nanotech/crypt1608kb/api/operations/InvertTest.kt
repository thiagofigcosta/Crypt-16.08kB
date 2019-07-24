package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.experimental.and

internal class InvertTest {

    private val invert = Invert()

    @Test
    fun crypt() {
        val data=Segment(String(byteArrayOf(0b00001111.toByte()),Charsets.US_ASCII),Charsets.US_ASCII)
        invert.crypt(data)
        assertEquals(String(byteArrayOf(0b11110000.toByte()),Charsets.US_ASCII),data.toString())
    }

    @Test
    fun decrypt() {
        val data=Segment(String(byteArrayOf(0b00101010.toByte()),Charsets.US_ASCII),Charsets.US_ASCII)
        invert.decrypt(data)
        assertEquals(String(byteArrayOf(0b11010101.toByte()),Charsets.US_ASCII),data.toString())
    }


    @Test
    fun cryptAndDecrypt(){
        val rawData = "Teste de inversão"
        val segment = Segment(rawData)
        val originalSegment = Segment(rawData)
        invert.crypt(segment)
        assertEquals(segment.getSize(),originalSegment.getSize())
        for (i in 0 until segment.getSize()){
            assertEquals(segment.getData()[0].getSize(),originalSegment.getData()[0].getSize())
        }
        invert.decrypt(segment)
        assertEquals(rawData,segment.toString())
        assertEquals(segment.getSize(),originalSegment.getSize())
        for (i in 0 until segment.getSize()){
            assertEquals(segment.getData()[0].getSize(),originalSegment.getData()[0].getSize())
        }
    }
}