package br.nanotech.crypt1608kb.api.operations.generators

class Natural : IntGenerator() {

    override fun getLastIteration(maxSize: Int): Int {
        return maxSize
    }

    override fun evaluate(i: Int): Int {
        return i
    }
}