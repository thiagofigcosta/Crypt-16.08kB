package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Packet
import br.nanotech.crypt1608kb.api.data.Segment
import br.nanotech.crypt1608kb.api.exception.InvalidCryptException

class Groupify(packetSize: Int) : CryptOperation() {

    private val groupSize: Int = packetSize

    override fun encrypt(data: Segment) {
        if (data.getSize() < groupSize) {
            // TODO auto-remove this operation of encrypt process to not stop the program
            throw InvalidCryptException("Groupify Exception: cannot compress a Segment(${data.getSize()}) smaller than groupSize($groupSize)")
        }
        val original = data.getData()
        data.clearData()
        data.getMeta().groupifyBy(groupSize)
        original.withIndex().groupBy { iPacket -> iPacket.index / groupSize }
            .map { iPacketList ->
                iPacketList.value
                    .map { packet -> packet.value.getData() }
            }
            .forEach { listOfByteArray ->
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
        data.getMeta().ungroupifyBy(groupSize)
        original.map { packet -> packet.getData() }.forEach { byteArray ->
            val byte2DList = byteArray.withIndex()
                .groupBy { iByteArrayList -> iByteArrayList.index / data.getMeta().getGroupificationLevel() }
                .map { iPacketList ->
                    iPacketList.value
                        .map { packet -> packet.value }
                }
            byte2DList.forEach { byteList ->
                data.getData().add(Packet(byteList.toByteArray()))
            }
        }
    }
}
