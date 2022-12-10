import kotlin.math.absoluteValue

fun main() {

    class Crt {
        private var X = 1

        fun processCommands(commands: List<String>): Sequence<Int> = sequence {
            commands.forEach {
                val sequence = processCommand(it)
                yieldAll(sequence)
            }
        }

        fun processCommand(it: String): Sequence<Int> = sequence {
            // Update X
            // Read instruction
            when {
                it == "noop" -> {
                    // Count score
                    yield(X)
                }

                it.split(" ").first() == "addx" -> {
                    val number = it.split(" ")[1].toInt()

                    // Do one empty tick
                    yield(X)

                    // Update X after the second tick
                    yield(X)
                    X += number
                }
            }
        }
    }


    fun part1(input: List<String>): Int {
        var score = 0

        val crt = Crt()
        val history = crt.processCommands(input).toList()
        history.forEachIndexed { clock, X ->
            val clock = clock + 1
            if ((clock + 20) % 40 == 0) {
                score += X * clock
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        val crt = Crt()
        val spritePositions = crt.processCommands(input).toList()

        val buffer = List(spritePositions.size) { '.' }.toMutableList()
        spritePositions.forEachIndexed { clock, spritePos ->
            val x = clock % 40

            if ((x - spritePos).absoluteValue <= 1) {
                buffer[clock] = '#'
            }
        }

        // Render buffer
        buffer.chunked(40).forEach {
            println(it.joinToString(""))
        }

        return 0
    }

// test if implementation meets criteria from the description, like:
    val smallInput = readInput("Day10_small")
//    part1(smallInput)

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
