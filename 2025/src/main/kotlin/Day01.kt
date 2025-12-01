import kotlin.math.abs

fun main() {
    val rotations = parseInput(1) { (if (it[0] == 'L') -1 else 1) * it.drop(1).toInt() }

    part1 {
        var pointer = 50
        rotations.sumOf {
            pointer = (pointer + it + 100).mod(100)
            if (pointer == 0) 1 else 0
        }
    }

    part2 {
        var zeroes = 0
        var pointer = 50
        rotations.forEach { d ->
            repeat((0 until abs(d)).count()) {
                pointer = (pointer + (1 * if (d < 0) -1 else 1) + 100).mod(100)
                if (pointer == 0) zeroes++
            }
        }
        zeroes
    }
}
