fun main() {

    fun processInput(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map {
            val (first, second) = it.split(",")
            val (firstFirst, firstSecond) = first.split("-")
            val (secondFirst, secondSecond) = second.split("-")

            (firstFirst.toInt()..firstSecond.toInt()) to (secondFirst.toInt()..secondSecond.toInt())
        }
    }


    fun part1(input: List<String>): Int {
        return processInput(input).map { (first, second) ->
            first.all { it in second } || second.all { it in first }
        }.map { if (it) 1 else 0 }.sum()
    }

    fun part2(input: List<String>): Int {
        return processInput(input).map { (first, second) ->
            first.any { it in second } || second.any { it in first }
        }.map { if (it) 1 else 0 }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
