package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GroupifyTest {

    private val groupify3 = Groupify(3,128)

    @Test
    fun cryptAndDecryptOneTime() {
        val data = Segment("oi",1)
        val original = data.clone()
        groupify3.crypt(data)
        val encrypted = data.clone()
        groupify3.decrypt(data)
        val decrypted = data.clone()

        assertEquals(original.toString(),encrypted.toString())
        assertEquals(original.toString(),decrypted.toString())

        assertEquals(2,original.getSize())
        assertEquals(1,encrypted.getSize())
        assertEquals(2,decrypted.getSize())
    }

    @Test
    fun cryptAndDecryptTwoTimes() {
        val data = Segment("0123456789",1)
        val original = data.clone()
        groupify3.crypt(data)
        val encrypted1 = data.clone()
        groupify3.crypt(data)
        val encrypted2 = data.clone()
        groupify3.decrypt(data)
        val decrypted1 = data.clone()
        groupify3.decrypt(data)
        val decrypted2 = data.clone()

        assertEquals(original.toString(),encrypted1.toString())
        assertEquals(original.toString(),encrypted2.toString())
        assertEquals(original.toString(),decrypted1.toString())
        assertEquals(original.toString(),decrypted2.toString())

        assertEquals(10,original.getSize())
        assertEquals(4,encrypted1.getSize())
        assertEquals(2,encrypted2.getSize())
        assertEquals(4,decrypted1.getSize())
        assertEquals(10,decrypted2.getSize())
    }
}