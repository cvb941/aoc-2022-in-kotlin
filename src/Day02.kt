enum class Response {
    ROCK, PAPER, SCISSORS;

    fun score(): Int {
        return when (this) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
        }
    }

    fun matchPointsAgainst(opponent: Response): Int {
        return when (this) {
            ROCK -> when (opponent) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }

            PAPER -> {
                when (opponent) {
                    ROCK -> 6
                    PAPER -> 3
                    SCISSORS -> 0
                }
            }

            SCISSORS -> {
                when (opponent) {
                    ROCK -> 0
                    PAPER -> 6
                    SCISSORS -> 3
                }
            }
        }
    }

    companion object {
        fun from(input: Char): Response {
            return when (input) {
                'A' -> ROCK
                'B' -> PAPER
                'C' -> SCISSORS
                'X' -> ROCK
                'Y' -> PAPER
                'Z' -> SCISSORS
                else -> throw IllegalArgumentException("Invalid input: $input")
            }
        }
    }

    fun responseAgainst(wantedResult: Char): Response {
        return when (this) {
            ROCK -> when (wantedResult) {
                'X' -> SCISSORS
                'Y' -> ROCK
                'Z' -> PAPER
                else -> throw IllegalArgumentException("Invalid input: $wantedResult")
            }

            PAPER -> {
                when (wantedResult) {
                    'X' -> ROCK
                    'Y' -> PAPER
                    'Z' -> SCISSORS
                    else -> throw IllegalArgumentException("Invalid input: $wantedResult")
                }
            }

            SCISSORS -> {
                when (wantedResult) {
                    'X' -> PAPER
                    'Y' -> SCISSORS
                    'Z' -> ROCK
                    else -> throw IllegalArgumentException("Invalid input: $wantedResult")
                }
            }
        }
    }

}

fun main() {

    fun part1(input: List<String>): Int {
        return input.map {
            val (them, you) = it.split(" ").map { Response.from(it.first()) }
            you.score() + you.matchPointsAgainst(them)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val (themChar, youChar) = it.split(" ").map { it.first() }

            val them = Response.from(themChar)
            val you = them.responseAgainst(youChar)
            you.score() + you.matchPointsAgainst(them)
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
