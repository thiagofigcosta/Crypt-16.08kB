package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment
import br.nanotech.crypt1608kb.api.exception.InvalidCryptException
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ceil

internal class GroupifyTest {

    private val groupify3 = Groupify(3)

    @Test
    fun cryptAndDecryptOneTime() {
        val data = Segment("oie",1)
        val original = data.clone()
        groupify3.encrypt(data)
        val encrypted = data.clone()
        groupify3.decrypt(data)
        val decrypted = data.clone()

        assertEquals(original.toString(),encrypted.toString())
        assertEquals(original.toString(),decrypted.toString())

        assertEquals(3,original.getSize())
        assertEquals(1,encrypted.getSize())
        assertEquals(3,decrypted.getSize())
    }

    @Test
    fun cryptAndDecryptTwoTimes() {
        val data = Segment("0123456789",1)
        val original = data.clone()
        groupify3.encrypt(data)
        val encrypted1 = data.clone()
        groupify3.encrypt(data)
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

    @Test
    fun cryptAndDecryptWithMultipleInstances(){
        val groupify4 = Groupify(4)
        val groupify30 = Groupify(30)
        val groupify17 = Groupify(17)
        val input = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla rutrum risus eu neque facilisis, et tristique felis hendrerit. Suspendisse hendrerit erat velit, eu vestibulum tellus pellentesque non. Sed pellentesque pulvinar gravida. Cras bibendum elit non turpis ullamcorper rutrum. Nunc dignissim egestas augue ac sollicitudin. Maecenas nisl lectus, tincidunt at velit eu, dignissim dictum lorem. Morbi porttitor malesuada dignissim. Aliquam eros leo, iaculis eget porta vitae, malesuada gravida nibh. Donec sed ante non nunc lacinia tincidunt. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vivamus at aliquam purus.

            Etiam ut vulputate lacus. Pellentesque volutpat ipsum vitae velit porta ultrices. In ornare sem ut tortor dapibus posuere. Nulla ac luctus mauris. Donec ex ante, sodales ac egestas non, pretium quis lorem. Aenean pretium leo vitae leo ullamcorper rutrum. Curabitur semper malesuada efficitur. Sed quis turpis sed magna rutrum iaculis. Pellentesque a consequat diam. Phasellus quis arcu sagittis, dictum justo ut, tincidunt libero. Sed varius tincidunt magna. Aenean id suscipit nisi, sit amet porttitor massa. Fusce vitae purus orci. Nam commodo enim nec elementum venenatis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut pulvinar aliquam elit vel semper.

            Integer et nulla faucibus, rutrum ante ut, ullamcorper est. Integer consequat semper facilisis. Suspendisse potenti. Pellentesque convallis laoreet leo, a fringilla leo maximus ut. Nunc interdum est nulla, in bibendum ipsum malesuada vitae. Sed rhoncus mi libero, at convallis tortor facilisis et. Donec enim leo, efficitur non ultricies ac, hendrerit vel elit. Cras tristique mattis nulla, quis sagittis quam tempor quis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aenean faucibus sollicitudin elit nec posuere. Vestibulum a nibh hendrerit, lacinia massa blandit, dictum mi. Sed lacus dui, maximus sed bibendum in, luctus a purus. Etiam in tellus sit amet lectus mollis pulvinar in rhoncus nibh.
            """
        val data = Segment(input,1)
        val original = data.clone()
        groupify30.encrypt(data)
        val encrypted1 = data.clone()
        groupify17.encrypt(data)
        val encrypted2 = data.clone()
        groupify4.encrypt(data)
        val encrypted3 = data.clone()
        groupify4.decrypt(data)
        val decrypted1 = data.clone()
        groupify17.decrypt(data)
        val decrypted2 = data.clone()
        groupify30.decrypt(data)
        val decrypted3 = data.clone()

        assertEquals(original.toString(),encrypted1.toString())
        assertEquals(original.toString(),encrypted2.toString())
        assertEquals(original.toString(),encrypted3.toString())
        assertEquals(original.toString(),decrypted1.toString())
        assertEquals(original.toString(),decrypted2.toString())
        assertEquals(original.toString(),decrypted3.toString())

        assertEquals(input.length,original.getSize())
        assertEquals(ceil(input.length/30.0).toInt(),encrypted1.getSize())
        assertEquals(ceil(input.length/30.0/17.0).toInt(),encrypted2.getSize())
        assertEquals(ceil(input.length/30.0/17.0/4.0).toInt(),encrypted3.getSize())
        assertEquals(encrypted2.getSize(),decrypted1.getSize())
        assertEquals(encrypted1.getSize(),decrypted2.getSize())
        assertEquals(original.getSize(),decrypted3.getSize())
    }

    @Test(expected = InvalidCryptException::class)
    fun groupDataSmallerThanPacketSize_thenThrowException() {
        val data = Segment("oi",1)
        groupify3.encrypt(data)
    }
}