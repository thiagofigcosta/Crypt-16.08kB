package br.nanotech.crypt1608kb.api.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

internal class PacketTest {

    @Test
    fun clone1() {
        val packet1 = Packet(0xFF.toByte())
        val packet2 = packet1
        val clone = packet1.clone()
        assertEquals(packet1, packet2)
        assertEquals(packet1, clone)
        packet1.setData(byteArrayOf(10))
        assertEquals(packet1, packet2)
        assertNotEquals(packet1, clone)
    }
}
