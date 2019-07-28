package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Packet
import br.nanotech.crypt1608kb.api.data.Segment


class Groupify(size:Int, maxValue:Int) : CryptOperation(){

    private val groupSize:Int = size

    private val maxValue:Int = maxValue


    override fun crypt(data: Segment) {
        val original = data.getData()
        data.clearData()
        original.withIndex().groupBy { iByte -> iByte.index/groupSize}
            .map { iByteList -> iByteList.value
                .map { packet -> packet.value.getData() } }
            .forEach{ listOfByteArray ->
                val listOfBytes = mutableListOf<Byte>()
                listOfByteArray.forEach { byteArray ->
                    listOfBytes.addAll(byteArray.toList())
                }
                data.getData().add(Packet(listOfBytes.toByteArray()))
            }
    }

    override fun decrypt(data: Segment) {
        val original = data.getData()
        data.clearData()
        original.withIndex().forEach{ iPacket ->
            iPacket.

        }
    }

}