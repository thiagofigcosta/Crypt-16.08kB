package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Segment

// Shift bits, group or bytes
class Shift : CryptOperation() {
    override fun encrypt(data: Segment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decrypt(data: Segment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var rightShift: Boolean = false

    enum class SHIFT_TYPE {
        BIT, BYTE, GROUP
    }
}