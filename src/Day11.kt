data class Monkey(
    var items: List<Long>,
    val operation: (Long) -> Long,
    val divisibleBy: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspectCounter: Long = 0
) {

}

class Game(val monkeys: List<Monkey>, val worryReductionEnabled: Boolean) {
    companion object {
        fun parse(input: List<String>, worryReductionEnabled: Boolean): Game {
            val monkeys = input.chunked(7) {
                val items = it[1].substringAfter(":").split(",").map { it.trim().toLong() }
                val operation = it[2].substringAfter("=").trim().split(" ").let {
                    when (it[1]) {
                        "+" -> { x: Long -> x + (it[2].toLongOrNull() ?: x) }
                        "*" -> { x: Long -> x * (it[2].toLongOrNull() ?: x) }
                        else -> throw IllegalArgumentException("Invalid operation: ${it[1]}")
                    }
                }
                val divisibleBy = it[3].split(" ").last().toLong()
                val trueMonkey = it[4].split(" ").last().toInt()
                val falseMonkey = it[5].split(" ").last().toInt()

                Monkey(items, operation, divisibleBy, trueMonkey, falseMonkey)
            }
            return Game(monkeys, worryReductionEnabled)
        }
    }

    val modulo = monkeys.map { it.divisibleBy }.reduce(Long::times)
    fun round() {
        monkeys.forEach { monkey ->
            // Execute monkey turn
            monkey.items.forEachIndexed { index, worryLevel ->
                var newWorryLevel = monkey.operation(worryLevel)
                if (worryReductionEnabled) newWorryLevel /= 3
                val isDivisible = newWorryLevel % monkey.divisibleBy == 0L
                val monkeyToThrowTo = if (isDivisible) monkey.trueMonkey else monkey.falseMonkey
                monkeys[monkeyToThrowTo].items += newWorryLevel % modulo
                monkey.inspectCounter++
            }

            monkey.items = emptyList()
        }
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        val game = Game.parse(input, true)

        repeat(20) {
            game.round()
        }

        val score =
            game.monkeys.sortedByDescending { it.inspectCounter }.take(2).map { it.inspectCounter }.reduce(Long::times)

        return score

    }

    fun part2(input: List<String>): Long {
        val game = Game.parse(input, false)

        repeat(10000) {
            game.round()

            if (it % 1000 == 0) {
                println("Round $it")
                game.monkeys.forEachIndexed { index, monkey ->
                    println("Monkey $index: ${monkey.inspectCounter}")
                }
            }
        }

        val score =
            game.monkeys.sortedByDescending { it.inspectCounter }.take(2).map { it.inspectCounter }.reduce(Long::times)

        return score
    }


    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
