fun main() {
    /* The batteries are arranged into banks; each line of digits in your input corresponds to a single bank of batteries. */
    val banks = parseInput(3) { bank -> bank.map { it.digitToInt() }.toIntArray() }

    /* turn on exactly two batteries; the joltage that the bank produces is equal to the number formed by the digits on the batteries you've turned on. */
    part1 { banks.sumOf { it.getJoltage(2) } }
    part2 { banks.sumOf { it.getJoltage(12) } }
}

private fun IntArray.getJoltage(digits: Int): Long {
    val batteries = IntArray(digits)
    var lastIndex = 0
    batteries.indices.forEach { i ->
        val remaining = this.sliceArray(lastIndex++..this.size + i - digits)
        batteries[i] = remaining.max()
        lastIndex += remaining.indexOf(batteries[i])
    }
    return batteries.joinToString("").toLong()
}
