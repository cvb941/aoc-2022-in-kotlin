import java.io.File

class Crates(columns: Int) {

    companion object {
        fun from(input: String): Crates {
            val rows = input.split("\n")
            val columns = rows.last().split(" ").mapNotNull { it.toIntOrNull() }.max()
            val crates = Crates(columns)

            // Fill out crates
            rows.dropLast(1).forEach { row ->
                for (i in 0 until columns) {
                    val char = row[1 + i * 4]

                    if (!char.isWhitespace()) crates.crates[i].add(0, char)
                }
            }

            return crates
        }
    }

    val crates = Array(columns) { mutableListOf<Char>() }

    fun runCommand(command: Command) {
        repeat(command.amount) {
            crates[command.to - 1].add(crates[command.from - 1].removeLast())
        }
    }
    
    fun runCommandPreserveOrder(command: Command) {
        val cratesToMove = crates[command.from - 1].takeLast(command.amount)
        crates[command.to - 1].addAll(cratesToMove)
        repeat(command.amount) {
            crates[command.from - 1].removeLast()
        }
    }

    fun answer(): String {
        return String(crates.map { it.last() }.toCharArray())
    }
}

data class Command(
    val amount: Int,
    val from: Int,
    val to: Int
) {
    companion object {
        fun parse(input: String): Command {
            val splits = input.split(" ")
            val amount = splits[1].toInt()
            val from = splits[3].toInt()
            val to = splits[5].toInt()
            return Command(amount, from, to)
        }
    }
}

fun main() {


    fun part1(input: String): String {
        val (input, commandString) = input.split("\n\n")

        val crates = Crates.from(input)
        val commands = commandString.split("\n").map { Command.parse(it) }

        commands.forEach {
            crates.runCommand(it)
        }

        return crates.answer()
    }

    fun part2(input: String): String {
        val (input, commandString) = input.split("\n\n")

        val crates = Crates.from(input)
        val commands = commandString.split("\n").map { Command.parse(it) }

        commands.forEach {
            crates.runCommandPreserveOrder(it)
        }

        return crates.answer()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "Day05_test.txt").readText()
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = File("src", "Day05.txt").readText()
    println(part1(input))
    println(part2(input))
}
