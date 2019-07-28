package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment
import kotlin.math.ceil
import kotlin.math.log2

abstract class CryptOperation {

    abstract fun crypt(data:Segment)

    abstract fun decrypt(data:Segment)

    protected fun getBitSize(maxValue:Int, isUnsigned:Boolean = false):Int{
        val unsignedBitSize = ceil(log2(maxValue.toDouble())).toInt()
        return if(isUnsigned){
            unsignedBitSize
        } else {
            unsignedBitSize + 1
        }
    }

}