class FileSystem {

    sealed class FileOrDirectory(val name: String) {
        abstract val size: Int
    }

    class Directory(name: String, var files: Map<String, FileOrDirectory> = emptyMap()) : FileOrDirectory(name) {
        override val size: Int
            get() = files.values.sumOf { it.size }

        fun allFiles(): List<FileOrDirectory> {
            return files.values.flatMap {
                when (it) {
                    is Directory -> it.allFiles() + it
                    is File -> listOf(it)
                }
            }
        }

        override fun toString(): String {
            return "Directory $name:\n" + "\t ${files.values.joinToString("\n")}"
        }
    }

    class File(name: String, override val size: Int) : FileOrDirectory(name) {
        override fun toString(): String {
            return "File $name ($size)"
        }
    }


    val root = Directory("root")

    private var currentPath = mutableListOf(root)
    private val currentDirectory get() = currentPath.last()

    private fun handleCommand(command: String) {
        val command = command.trim()
        if (command.startsWith("cd")) {
            cd(command.substringAfter("cd"))
        }
    }

    private fun cd(path: String) {
        val path = path.trim()
        when (path) {
            "/" -> {
                currentPath = mutableListOf(root)
            }

            ".." -> {
                if (currentPath.size > 1) currentPath.removeLast()
            }

            else -> {
                currentPath += currentDirectory.files.getOrDefault(path, Directory(path)) as Directory
            }
        }
    }

    private fun parseLs(ls: String) {
        val (first, second) = ls.split(" ")
        if (first == "dir") {
            // Is directory
            currentDirectory.files += second to Directory(second)
        } else {
            // Is file
            currentDirectory.files += second to File(second, first.toInt())
        }
    }

    fun handleInput(input: String) {
        if (input.startsWith("$")) {
            // Is command
            handleCommand(input.substringAfter("$"))
        } else {
            // Is output
            parseLs(input)
        }
    }

    override fun toString(): String {
        // Print out the filesystem
        return root.toString()
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val fileSystem = FileSystem()
        input.forEach { fileSystem.handleInput(it) }

        // Get directories with at most 100000
        val directories = fileSystem.root.allFiles().filterIsInstance<FileSystem.Directory>().filter { it.size <= 100000 }

        return directories.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val fileSystem = FileSystem()
        input.forEach { fileSystem.handleInput(it) }

        // Get all directories
        val directories = fileSystem.root.allFiles().filterIsInstance<FileSystem.Directory>()

        val totalSize = fileSystem.root.size

        val currentFreeSize = 70000000 - totalSize
        val targetFreeSize = 30000000
        val sizeNeeded = targetFreeSize - currentFreeSize

        val directoryToDelete = directories.sortedBy { it.size }.first { it.size >= sizeNeeded }
        return directoryToDelete.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
