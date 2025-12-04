fun readInput(day: Int): String =
    object {}.javaClass.getResource("Day${day.toString().padStart(2, '0')}.txt")!!
        .readText()
        .trim()

fun <T> parseInput(day: Int, parse: (String) -> T): List<T> =
    readInput(day)
        .split('\n')
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .map { parse(it) }

fun part1(solve: () -> Long) = part(1, solve)
fun part2(solve: () -> Long) = part(2, solve)

private fun part(part: Int, solve: () -> Long) {
    val startTime = System.nanoTime()
    val answer = solve()
    val endTime = System.nanoTime()
    println("Kotlin Part $part: $answer in ${String.format("%.3f", (endTime - startTime) / 1_000_000.0)} ms")
}
