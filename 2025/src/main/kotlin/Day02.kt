fun main() {
    /* The ranges are separated by commas (,); each range gives its first ID and last ID separated by a dash (-). */
    val ranges = parseInput(2) { it }.first().split(",").map { range ->
        val (start, end) = range.split("-").map { it.toLong() }
        start..end
    }

    /* find the invalid IDs by looking for any ID which is made only of some sequence of digits repeated twice. */
    part1 { ranges.findInvalidIds("^([0-9]+)\\1$") }

    /* Now, an ID is invalid if it is made only of some sequence of digits repeated at least twice. */
    part2 { ranges.findInvalidIds("^([0-9]+)\\1+$") }
}

private fun List<LongRange>.findInvalidIds(pattern: String): Long {
    val r = pattern.toPattern().asMatchPredicate()
    return this.sumOf { range -> range.sumOf { n -> n.takeIf { r.test(it.toString()) } ?: 0 } }
}
