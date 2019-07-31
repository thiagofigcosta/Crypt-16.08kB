package br.nanotech.crypt1608kb.api.operations

import br.nanotech.crypt1608kb.api.data.Packet
import br.nanotech.crypt1608kb.api.data.Segment
import br.nanotech.crypt1608kb.api.exception.InvalidCryptException
import br.nanotech.crypt1608kb.api.operations.enums.InsertSourceType
import br.nanotech.crypt1608kb.api.operations.enums.InsertSourceType.ARRAY
import br.nanotech.crypt1608kb.api.operations.enums.InsertSourceType.HASH
import br.nanotech.crypt1608kb.api.operations.enums.InsertSourceType.RANDOM
import br.nanotech.crypt1608kb.api.operations.generators.IntGenerator
import br.nanotech.crypt1608kb.api.operations.generators.Natural
import java.security.SecureRandom

class Insert(seeder: IntGenerator, iterator: IntGenerator = Natural()) : CryptOperation() {

    private var iterator: IntGenerator = iterator
    private var positionGenerator: IntGenerator = seeder
    private var insertSourceType: InsertSourceType = RANDOM
    private var insertSource: ByteArray = byteArrayOf()
    private var insertSourceSize: Int = 0

    constructor(seeder: IntGenerator, insertSource: ByteArray) : this(seeder) {
        this.insertSource = insertSource
        this.insertSourceSize = insertSource.size
        this.insertSourceType = ARRAY
    }

    constructor(seeder: IntGenerator, iterator: IntGenerator, insertSource: ByteArray) : this(seeder, iterator) {
        this.insertSource = insertSource
        this.insertSourceSize = insertSource.size
        this.insertSourceType = ARRAY
    }

//    constructor(seeder:IntGenerator, hash: **HASHCLASS** ) : this(seeder){ // TODO implement me
//        this.insertSource = hash.getBytes()
//        this.insertSourceType = InsertSourceType.HASH
//    }

    override fun encrypt(data: Segment) {
        if (insertSourceType != RANDOM && insertSource.isEmpty()) {
            throw InvalidCryptException("Insert Exception: cannot insert non random data source without the data source")
        }
        for (i in 0..getLastIteration(data.getSize())) {
            val insertAt = iterator.evaluate(i)
            data.getData().add(insertAt, getCurrentPacket(i, data.getData()[insertAt - 1].getSize()))
        }
    }

    override fun decrypt(data: Segment) {
        if (insertSourceType != RANDOM && insertSourceSize == 0) {
            throw InvalidCryptException("Insert Exception: cannot remove non random data source without the data size")
        }
        for (i in 0..getLastIteration(data.getSize())) {
            val removeAt = iterator.evaluate(i) - i
            data.getData().removeAt(removeAt)
        }
    }

    private fun getCurrentPacket(i: Int, lastPacketSize: Int): Packet {
        return when (insertSourceType) {
            RANDOM -> {
                val bytes = ByteArray(lastPacketSize)
                SecureRandom.getInstanceStrong().nextBytes(bytes)
                Packet(bytes)
            }
            HASH, ARRAY -> if (i < insertSource.size) {
                Packet(insertSource[i])
            } else {
                throw InvalidCryptException("Insert Exception: insert source doesn't contains an item at position: $i")
            }
        }
    }

    private fun getLastIteration(segmentSize: Int): Int {
        return when (insertSourceType) {
            RANDOM -> positionGenerator.getLastIteration(segmentSize)
            HASH, ARRAY -> getInsertSourceSize()
        }
    }

    private fun getInsertSourceSize(): Int {
        return when (insertSourceType) {
            RANDOM -> throw InvalidCryptException("Insert Exception: random input source have infinite size")
            HASH, ARRAY ->
                when {
                    insertSource.isNotEmpty() -> insertSource.size
                    insertSourceSize != 0 -> insertSourceSize
                    else -> throw InvalidCryptException("Insert Exception: source based insert depends on it's int size for decrypt and it's source for encrypt")
                }
        }
    }
}
