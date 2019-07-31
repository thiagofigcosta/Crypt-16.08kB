package br.nanotech.crypt1608kb.api.data

class Packet {

    private var data: ByteArray = ByteArray(0)


    constructor(input: Byte) {
        data = byteArrayOf(input)
    }

    constructor(input: ByteArray) {
        data = input
    }


    fun getSize(): Int = data.size

    fun getData(): ByteArray = data

    fun setData(data: ByteArray) {
        this.data = data
    }

    fun clone(): Packet {
        return Packet(data.copyOf())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Packet
        return data.contentEquals(other.data)
    }
}
