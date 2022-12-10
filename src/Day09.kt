import kotlin.math.absoluteValue

fun main() {

    fun getDirVector(vectorChar: Char): Pair<Int, Int> {
        return when (vectorChar) {
            'U' -> Pair(0, 1)
            'D' -> Pair(0, -1)
            'L' -> Pair(-1, 0)
            'R' -> Pair(1, 0)
            else -> throw IllegalArgumentException("Invalid input: $vectorChar")
        }
    }

    fun getTailMovement(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val headRelative = head - tail
        return when {
            headRelative == 0 to 0 -> 0 to 0 // On top, do not move
            headRelative.toList().maxOf { it.absoluteValue } == 1 -> 0 to 0  // Touching, do not move
            else -> {
                headRelative.first.coerceIn(-1..1) to headRelative.second.coerceIn(-1..1)
            }
        }
    }

    class Part1 {
        val visited = mutableSetOf(0 to 0)

        var tail = Pair(0, 0)
        var head = Pair(0, 0)

        fun solve(input: List<String>): Int {

            input.forEach {
                val (dir, steps) = it.split(" ")
                val dirVector = getDirVector(dir[0])
                repeat(steps.toInt()) {
                    head += dirVector

                    val tailMovement = getTailMovement(head, tail)
                    tail += tailMovement

                    visited += tail
                }
            }

            return visited.size
        }
    }

    class Part2(size: Int) {

        val visited = Array(size) { mutableSetOf(0 to 0) }
        val positions = Array(size) { 0 to 0 }

        fun solve(input: List<String>): Int {

            input.forEach {
                val (dir, steps) = it.split(" ")
                val dirVector = getDirVector(dir[0])

                repeat(steps.toInt()) {
                    // Move head
                    val newHeadPosition = positions[0] + dirVector
                    positions[0] = newHeadPosition

                    positions.indices.zipWithNext { headIndex, tailIndex ->
                        // Move tail
                        val tailMovement = getTailMovement(positions[headIndex], positions[tailIndex])
                        positions[tailIndex] += tailMovement
                        visited[tailIndex] += positions[tailIndex]
                    }
                }
            }

            return visited.last().size
        }
    }

    fun part1(input: List<String>): Int {
        return Part1().solve(input)
    }

    fun part2(input: List<String>): Int {
        return Part2(10).solve(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
