fun <T> readInput(day: Int, parse: (String) -> T): List<T> =
    object {}.javaClass.getResource("Day${day.toString().padStart(2, '0')}.txt")!!
        .readText()
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .map { parse(it) }

fun part1(solve: () -> Int) = part(1, solve)
fun part2(solve: () -> Int) = part(2, solve)

private fun part(part: Int, solve: () -> Int) {
    val startTime = System.nanoTime()
    val answer = solve()
    val endTime = System.nanoTime()
    println("Kotlin Part $part: $answer in ${String.format("%.3f", (endTime - startTime) / 1_000_000.0)} ms")
}

operator fun MatchResult.get(index: Int) = this.groupValues[index]
