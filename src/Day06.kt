fun main() {

    fun part1(input: List<String>): Int {
        input.first().windowed(4).forEachIndexed { index, s ->
            if (s.toSet().size == 4) return index + 4
        }
        throw IllegalStateException("No solution found")
    }

    fun part2(input: List<String>): Int {
        input.first().windowed(14).forEachIndexed { index, s ->
            if (s.toSet().size == 14) return index + 14
        }
        throw IllegalStateException("No solution found")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
