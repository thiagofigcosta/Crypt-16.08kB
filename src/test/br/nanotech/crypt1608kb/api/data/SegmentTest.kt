package br.nanotech.crypt1608kb.api.data

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SegmentTest {

    private val rawData = "Oi, como vai você?"

    @Test
    fun toString1() {
        val data=Segment(rawData)
        assertEquals(rawData,data.toString())
    }

    @Test
    fun toString2() {
        val packetSize = 2
        val data=Segment(rawData,packetSize)
        assertEquals(rawData,data.toString())
    }

    @Test
    fun clone1() {
        val segment1 = Segment("lala")
        val segment2 = segment1
        val clone = segment1.clone()
        assertEquals(segment1, segment2)
        assertEquals(segment1, clone)
        segment1.setData("lele")
        assertEquals(segment1, segment2)
        assertNotEquals(segment1, clone)
    }
}