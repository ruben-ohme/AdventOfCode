import kotlin.math.abs

fun main() {
    /*
     a sequence of rotations, one per line, which tell you how to open the safe.
     A rotation starts with an L or R which indicates whether the rotation should be to the left (toward lower numbers) or to the right (toward higher numbers).
     Then, the rotation has a distance value which indicates how many clicks the dial should be rotated in that direction.
     */
    val rotations = parseInput(1) { (if (it[0] == 'L') -1 else 1) * it.drop(1).toInt() }

    /* The actual password is the number of times the dial is left pointing at 0 after any rotation in the sequence */
    part1 {
        var pointer = 50
        rotations.sumOf {
            pointer = (pointer + it).rem(100)
            if (pointer == 0) 1L else 0L
        }
    }

    /* count the number of times any click causes the dial to point at 0, regardless of whether it happens during a rotation or at the end of one. */
    part2 {
        var zeroes = 0L
        var pointer = 50
        rotations.forEach { d ->
            repeat((0 until abs(d)).count()) {
                pointer = (pointer + (1 * if (d < 0) -1 else 1)).rem(100)
                if (pointer == 0) zeroes++
            }
        }
        zeroes
    }
}
