import java.io.File
import kotlin.experimental.xor

lateinit var key: List<Byte>
lateinit var buffer: ByteArray

fun xorItems() {
    for (i in buffer.indices)
        buffer[i] = buffer[i] xor key[i]
}

fun main(args: Array<String>) {
    val iPath = if (args.isEmpty()) {
        print("PATH > ")
        readln().trim('\'', '"')
    } else
        args[0]
    val oPath = if (iPath.endsWith(".xor")) iPath.removeSuffix(".xor") else "$iPath.xor"

    println("\n密鑰長度應小於256個字元")
    print("KEY > ")
    key = readln().toByteArray().toMutableList().apply {
        add(0, size.toByte())
    }
    buffer = ByteArray(key.size)

    File(iPath).inputStream().buffered().use { bis ->
        File(oPath).outputStream().buffered().use { bos ->
            while (true) {
                val n = bis.read(buffer)
                if (n == -1)
                    break
                else {
                    xorItems()
                    bos.write(buffer, 0, n)
                }
            }
        }
    }
}