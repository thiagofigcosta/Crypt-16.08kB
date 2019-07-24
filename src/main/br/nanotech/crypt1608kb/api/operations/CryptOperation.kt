package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment

abstract class CryptOperation {

    abstract fun crypt(data:Segment)

    abstract fun decrypt(data:Segment)

}