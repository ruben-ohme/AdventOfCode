fun main() {
    val input = readInput(4) { it }.map { it.toCharArray() }
    val xmas = "XMAS".toCharArray()
    val height = input.size
    val width = input[0].size
    val l = xmas.size

    part1 {
        fun Point.countXmases() = let { (x, y) ->
            Direction.entries.count {
                when (it) {
                    Direction.NORTH -> if (y >= l - 1) xmas.indices.map { input[y - it][x] } else null
                    Direction.NORTHEAST -> if (y >= l - 1 && x <= width - l) xmas.indices.map { input[y - it][x + it] } else null
                    Direction.EAST -> if (x <= width - l) xmas.indices.map { input[y][x + it] } else null
                    Direction.SOUTHEAST -> if (x <= width - l && y <= height - l) xmas.indices.map { input[y + it][x + it] } else null
                    Direction.SOUTH -> if (y <= height - l) xmas.indices.map { input[y + it][x] } else null
                    Direction.SOUTHWEST -> if (y <= height - l && x >= l - 1) xmas.indices.map { input[y + it][x - it] } else null
                    Direction.WEST -> if (x >= l - 1) xmas.indices.map { input[y][x - it] } else null
                    Direction.NORTHWEST -> if (x >= l - 1 && y >= l - 1) xmas.indices.map { input[y - it][x - it] } else null
                }?.toCharArray() contentEquals xmas
            }
        }

        var count = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'X') count += (x to y).countXmases()
            }
        }
        count
    }

    part2 {
        val mas = "MAS".toCharArray()
        val sam = "SAM".toCharArray()
        fun List<Char>.isMasOrSam() = with(toCharArray()) { this contentEquals mas || this contentEquals sam }
        fun Point.isXmas() = let { (x, y) ->
            input[y][x] == 'A' && x > 0 && y > 0 && x < width - 1 && y < height - 1 &&
                    (0 until l - 1).map { input[y - 1 + it][x - 1 + it] }.isMasOrSam() &&
                    (0 until l - 1).map { input[y + 1 - it][x - 1 + it] }.isMasOrSam()
        }

        var count = 0
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                if ((x to y).isXmas()) count++
            }
        }
        count
    }
}

enum class Direction {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
}
