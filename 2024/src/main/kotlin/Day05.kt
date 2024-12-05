import kotlin.text.split

fun main() {
    val (grid, updates) = readInput(5).split("\n\n").let { (rules, updates) ->
        val grid = Array<BooleanArray>(100) { BooleanArray(100) { false } }
        rules.split("\n").map { it.split('|').map { it.toInt() }.let { (y, x) -> grid[y][x] = true } }
        val updates = updates.split('\n').map { it.split(',').map { it.toInt() } }
        grid to updates
    }

    fun List<Int>.isValid(): Boolean = windowed(2).all { (y, x) -> grid[y][x] }

    part1 { updates.sumOf { if (it.isValid()) it.median else 0 } }
    part2 {
        updates.sumOf {
            if (it.isValid()) 0 else it.toMutableList().also {
                var i= 0
                while (i < it.size - 1) {
                    if (!grid[it[i]][it[i + 1]]) {
                        if (grid[it[i + 1]][it[i]]) {
                            (it[i] to it[i + 1]).apply {
                                it[i] = second
                                it[i + 1] = first
                            }
                            if (i > 0) i -= 2
                        }
                    }
                    i++
                }
            }.median
        }
    }
}
