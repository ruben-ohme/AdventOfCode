import kotlin.text.split

fun main() {
    val (rules, updates) = readInput(5).split("\n\n").let {
        val rules = it[0].split("\n").map { line ->
            val (a, b) = line.split('|').map { it.toInt() }
            a to b
        }
        val updates = it[1].split('\n').map { it.split(',').map(String::toInt) }
        rules to updates
    }

    fun List<Int>.isValid(index: Int = 0): Boolean {
        if (index == size) return true
        val before = rules.filter { it.second == this[index] }.map { it.first }
        val after = rules.filter { it.first == this[index] }.map { it.second }
        return if ((before.isEmpty() || this.subList(0, index).all { it in before }) &&
            (after.isEmpty() || this.subList(index + 1, this.size).all { it in after })
        ) isValid(index + 1) else false
    }

    val followings = rules.groupBy { it.first }.mapValues { it.value.map { it.second } }
    part1 { updates.sumOf { if (it.isValid()) it[it.size / 2] else 0 } }
    part2 {
        updates.filterNot { it.isValid() }
            .map { it.sortedWith { a, b -> if (b in (followings[a] ?: emptyList())) -1 else 1 } }
            .sumOf { it[it.size / 2] }
    }
}

