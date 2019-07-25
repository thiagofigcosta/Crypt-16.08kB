package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment
import kotlin.experimental.inv

class Invert : CryptOperation(){
    override fun crypt(data: Segment) {
        invert(data)
    }

    override fun decrypt(data: Segment) {
        invert(data)
    }


    private fun invert(data: Segment){
        data.getData().forEach { packet ->
            packet.setData(packet.getData().map { byte -> byte.inv() }.toByteArray())
        }
    }

}