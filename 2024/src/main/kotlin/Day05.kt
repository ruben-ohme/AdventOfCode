import kotlin.text.split

fun main() {
    val (rules, updates) = readInput(5).split("\n\n").let { (rules, updates) ->
        val rules = rules.split("\n")
            .map { it.split('|').map { it.toInt() }.let { (a, b) -> a to b } }
            .groupBy { it.first }
            .mapValues { it.value.map { it.second }.toSet() }
        val updates = updates.split('\n').map { it.split(',').map { it.toInt() } }
        rules to updates
    }

    fun List<Int>.isValid(index: Int = 0): Boolean {
        if (index == size) return true
        val before = rules.filterValues { this[index] in it }.keys
        val after = rules[this[index]] ?: emptySet()
        return if ((before.isEmpty() || subList(0, index).all { it in before }) &&
            (after.isEmpty() || subList(index + 1, this.size).all { it in after })
        ) isValid(index + 1) else false
    }

    part1 { updates.sumOf { if (it.isValid()) it[it.size / 2] else 0 } }
    part2 {
        updates.filterNot { it.isValid() }
            .map { it.sortedWith { a, b -> if (b in (rules[a] ?: emptySet())) -1 else 1 } }
            .sumOf { it[it.size / 2] }
    }
}
