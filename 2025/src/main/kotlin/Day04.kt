import kotlin.math.max
import kotlin.math.min

fun main() {
    /* The rolls of paper (@) are arranged on a large grid */
    val grid = parseInput(4) { it.toCharArray() }

    /*
    The forklifts can only access a roll of paper if there are fewer than four rolls of paper in the eight adjacent positions
    How many rolls of paper can be accessed by a forklift?
     */
    part1 {
        grid.indices.sumOf { y ->
            grid[y].indices.count { x ->
                grid.canAccess(x, y)
            }
        }.toLong()
    }

    /*
    Once a roll of paper can be accessed by a forklift, it can be removed.
    Once a roll of paper is removed, the forklifts might be able to access more rolls of paper, which they might also be able to remove.
    How many total rolls of paper could the Elves remove if they keep repeating this process?
     */
    part2 {
        generateSequence {
            grid.indices.sumOf { y ->
                grid[y].indices.count { x ->
                    grid.canAccess(x, y).also { if (it) grid[y][x] = 'x' }
                }
            }
        }.takeWhile { it > 0 }.sum().toLong()
    }
}

private fun List<CharArray>.canAccess(x: Int, y: Int) =
    this[y][x] == '@' && this.adjacentRolls(x, y) < 4

private fun List<CharArray>.adjacentRolls(x: Int, y: Int) =
    (max(0, y - 1)..min(y + 1, this.size - 1)).sumOf { yy ->
        (max(0, x - 1)..(min(x + 1, this[y].size - 1))).count { xx ->
            (yy != y || xx != x) && this[yy][xx] == '@'
        }
    }
