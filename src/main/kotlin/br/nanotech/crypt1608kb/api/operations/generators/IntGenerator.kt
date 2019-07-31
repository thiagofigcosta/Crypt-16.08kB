package br.nanotech.crypt1608kb.api.operations.generators

abstract class IntGenerator {

    abstract fun evaluate(i: Int): Int

    abstract fun getLastIteration(maxSize: Int): Int
}
