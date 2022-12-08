fun main() {

    fun part1(input: List<String>): Int {
        val rows = input.size
        val columns = input.first().length

        val field = Array(rows) { IntArray(columns) }

        input.forEachIndexed() { rowIndex, row ->
            row.forEachIndexed() { columnIndex, column ->
                field[rowIndex][columnIndex] = column.digitToInt()
            }
        }

        var count = 0
        field.forEachIndexed() { rowIndex, row ->
            row.forEachIndexed() { columnIndex, tree ->

                val leftOk = row.toList().subList(0, columnIndex).all { it < tree }
                val rightOk = row.toList().subList(columnIndex + 1, row.size).all { it < tree }
                val topOk = field.toList().subList(0, rowIndex).all { it[columnIndex] < tree }
                val bottomOk = field.toList().subList(rowIndex + 1, field.size).all { it[columnIndex] < tree }

                if (leftOk || rightOk || topOk || bottomOk) count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val rows = input.size
        val columns = input.first().length

        val field = Array(rows) { IntArray(columns) }

        input.forEachIndexed() { rowIndex, row ->
            row.forEachIndexed() { columnIndex, column ->
                field[rowIndex][columnIndex] = column.digitToInt()
            }
        }

        var best = 0
        field.forEachIndexed() { rowIndex, row ->
            row.forEachIndexed() { columnIndex, tree ->

                // Krajne nechceme lebo su za 0 a sekcia dolu s nimi pocita zle
                if (rowIndex == 0 || rowIndex == rows - 1 || columnIndex == 0 || columnIndex == columns - 1) return@forEachIndexed

                val left = row.toList().subList(0, columnIndex).drop(1).reversed().takeWhile { it < tree }.count() + 1
                val right = row.toList().subList(columnIndex + 1, row.size).dropLast(1).takeWhile { it < tree }.count() + 1
                val top = field.toList().subList(0, rowIndex).drop(1).reversed().takeWhile { it[columnIndex] < tree }.count() + 1
                val bottom = field.toList().subList(rowIndex + 1, field.size).dropLast(1).takeWhile { it[columnIndex] < tree }.count() + 1

                val score = left * right * top * bottom
                if (score > best)
                    best = score
            }
        }

        return best
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
