package br.nanotech.crypt1608kb.api.operations.generators

import br.nanotech.crypt1608kb.api.exception.InvalidEquationException

class LinearEquation(a: Int, b: Int) : IntGenerator() {
    private var angular: Int = a
    private var linear: Int = b

    init {
        if (this.angular == 0) {
            throw InvalidEquationException("LinearEquation Exception: \"angular\" cannot be zero")
        }
    }

    override fun evaluate(i: Int): Int {
        return angular * i + linear
    }

    override fun getLastIteration(maxSize: Int): Int {
        return (maxSize - linear) / angular
    }
}