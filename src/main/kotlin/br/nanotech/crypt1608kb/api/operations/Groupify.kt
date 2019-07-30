package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Packet
import br.nanotech.crypt1608kb.api.data.Segment


class Groupify(packetSize:Int, maxValue:Int) : CryptOperation(){

    private val groupSize:Int = packetSize

    private val maxValue:Int = maxValue


    override fun crypt(data: Segment) { // TODO optimize me
        val original = data.getData()
        data.clearData()
        original.withIndex().groupBy { iPacket -> iPacket.index/groupSize}
            .map { iPacketList -> iPacketList.value
                .map { packet -> packet.value.getData() } }
            .forEach{ listOfByteArray ->
                val listOfBytes = mutableListOf<Byte>()
                listOfByteArray.forEach { byteArray ->
                    listOfBytes.addAll(byteArray.toList())
                }
                data.getData().add(Packet(listOfBytes.toByteArray()))
            }
    }

    override fun decrypt(data: Segment) { // TODO optimize me
        val original = data.getData()
        data.clearData()
        original.map { packet -> packet.getData() }.forEach { byteArray ->
            val byte2DList=byteArray.withIndex().groupBy { iByteArrayList -> iByteArrayList.index/groupSize}
                .map { iPacketList -> iPacketList.value
                    .map { packet -> packet.value } }
            if (byte2DList.size == 1){
                byte2DList[0].forEach{ byte ->
                    data.getData().add(Packet(byte))
                }
            }else{
                byte2DList.forEach { byteList ->
                    data.getData().add(Packet(byteList.toByteArray()))
                }
            }

        }
    }

}