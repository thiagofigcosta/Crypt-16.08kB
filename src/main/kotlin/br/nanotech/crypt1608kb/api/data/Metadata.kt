package br.nanotech.crypt1608kb.api.data

class Metadata {

    private var groupificationLevel: Int = 1 // TODO reconstruct this info based on cryptkey for decrypt

    fun getGroupificationLevel(): Int = groupificationLevel

    fun setCompressionLevel(groupificationLevel: Int) {
        this.groupificationLevel = groupificationLevel
    }

    fun groupifyBy(groupSize: Int) {
        groupificationLevel *= groupSize
    }

    fun ungroupifyBy(groupSize: Int) {
        groupificationLevel /= groupSize
    }
}