data class Point2D(val x: Int, val y: Int) {

    operator fun plus(other: Point2D) = Point2D(x + other.x, y + other.y)
    operator fun minus(other: Point2D) = Point2D(x - other.x, y - other.y)
}
typealias P = Point2D

data class Point3D(val x: Int, val y: Int, val z: Int)

fun readInput(day: Int): String =
    object {}.javaClass.getResource("Day${day.toString().padStart(2, '0')}.txt")!!
        .readText()
        .trim()

fun <T> parseInput(day: Int, parse: (String) -> T): List<T> =
    readInput(day)
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
