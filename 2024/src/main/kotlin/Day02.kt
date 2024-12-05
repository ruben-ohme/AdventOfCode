import kotlin.collections.zipWithNext
import kotlin.math.abs

fun main() {
    val reports = parseInput(2) { it.split(' ').map { it.toInt() } }
    part1 { reports.count { it.isSafe } }
    part2 {
        reports.count {
            it.isSafe || it.indices.map { i -> it.toMutableList().also { p -> p.removeAt(i) } }.any { p -> p.isSafe }
        }
    }
}

private val Collection<Int>.isSafe get() = isIncreasing || isDecreasing
private val Collection<Int>.isIncreasing get() = zipWithNext().all { (a, b) -> a < b && abs(a - b) in 1..3 }
private val Collection<Int>.isDecreasing get() = zipWithNext().all { (a, b) -> a > b && abs(a - b) in 1..3 }
