fun main() {
    fun part1(input: List<String>): Int {
        var maxCalories = 0
        var calorieSum = 0
        input.forEach {
            if (it.isEmpty()) {
                maxCalories = maxOf(maxCalories, calorieSum)
                calorieSum = 0
            } else {
                calorieSum += it.toInt()
            }
        }
        return maxCalories
    }

    fun part2(input: List<String>): Int {
        val caloriesList = mutableListOf<Int>()
        var calorieSum = 0
        input.forEach {
            if (it.isEmpty()) {
                caloriesList += calorieSum
                calorieSum = 0
            } else {
                calorieSum += it.toInt()
            }
        }
        return caloriesList.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
