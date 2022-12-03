fun main() {

    fun Char.score(): Int {
        return when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> error("Invalid character: $this")
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { case ->
            // Split list into two halves
            val first = case.take(case.length / 2)
            val second = case.drop(case.length / 2)

            val inBoth = first.toSet().intersect(second.toSet()).first()

            inBoth.score()
        }.sum()

    }

    fun part2(input: List<String>): Int {
        return input.map { it.toSet() }.chunked(3).map {
            it[0].intersect(it[1].intersect(it[2])).first().score()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
