package br.nanotech.crypt1608kb.api.data

import java.nio.charset.Charset

class Segment{

    private var data:MutableList<Packet> = mutableListOf()

    private var charset: Charset

    constructor(input: String) {
        charset=Charsets.UTF_8
        setData(input)
    }

    constructor(input: String, charset: Charset) {
        this.charset=charset
        setData(input)
    }

    constructor(input: String, packetSize:Int) {
        charset=Charsets.UTF_8
        data = mutableListOf()
        val byteData = input.toByteArray(charset)

        byteData.withIndex()
            .groupBy { iByte -> iByte.index/packetSize}
                .map { iByteList -> iByteList.value
                    .map { byte -> byte.value } }
                        .forEach{ list -> data.add(Packet(list.toByteArray())) }
    }

    private constructor(data:MutableList<Packet>, charset: Charset){
        this.data=data
        this.charset=charset
    }

    override fun toString():String{
        val uncompressedData:MutableList<Byte> = mutableListOf()
        data.forEach { packet ->
            uncompressedData.addAll(packet.getData().asList())
        }
        return uncompressedData.toByteArray().toString(charset)
    }

    fun getSize():Int = data.size

    fun getData():MutableList<Packet> = data

    fun setData(rawData:String){
        data = mutableListOf()
        val byteData = rawData.toByteArray(charset)

        byteData.forEach { byte -> data.add(Packet(byte)) }
    }

    fun clone():Segment{
        return Segment(data.toMutableList(),Charset.forName(charset.name()))
    }

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Segment
        if (charset.name() == other.charset.name()) {
            return (data.toTypedArray().contentEquals(other.data.toTypedArray()))
        }
        return false
    }
}