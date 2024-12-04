import kotlin.math.abs

fun main() {
    val (left, right) = readInput(1) { it.split("\\s+".toRegex()).map { it.toInt() }.let { it[0] to it[1] } }.unzip()
    part1 { left.sorted().zip(right.sorted()).sumOf { (a, b) -> abs(a - b) } }
    part2 { left.sumOf { a -> a * right.count { b -> b == a } } }
}
