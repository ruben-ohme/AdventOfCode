fun main() {
    /* The database operates on ingredient IDs. It consists of a list of fresh ingredient ID ranges, a blank line, and a list of available ingredient IDs. */
    val (first, second) = readInput(5).split("\n\n")
    val ranges = first.split('\n').filter { it.isNotBlank() }
        .map { line -> line.split('-').map { it.toLong() }.let { it[0]..it[1] } }
        .mergeOverlappingRanges()
    val ingredients = second.split('\n').filter { it.isNotBlank() }.map { it.toLong() }

    /* How many of the available ingredient IDs are fresh? */
    part1 { ingredients.count { id -> ranges.any { id in it } }.toLong() }

    /* the Elves would like to know all of the IDs that the fresh ingredient ID ranges consider to be fresh. */
    part2 { ranges.sumOf { it.last - it.first + 1 } }
}

private fun List<LongRange>.mergeOverlappingRanges(): List<LongRange> {
    val sorted = sortedBy { it.first }
    val result = mutableListOf<LongRange>()
    var current = sorted.first()
    (1 until sorted.size).forEach { i ->
        val next = sorted[i]
        if (next.first <= current.last + 1) {
            current = current.first..maxOf(current.last, next.last)
        } else {
            result += current
            current = next
        }
    }
    result += current
    return result
}
