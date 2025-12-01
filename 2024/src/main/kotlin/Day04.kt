fun main() {
    val input = parseInput(4) { it }.map { it.toCharArray() }
    val xmas = "XMAS".toCharArray()
    val height = input.size
    val width = input[0].size
    val l = xmas.size

    part1 {
        fun P.countXmases() = let { (x, y) ->
            Direction4.entries.count {
                when (it) {
                    Direction4.NORTH -> if (y >= l - 1) xmas.indices.map { input[y - it][x] } else null
                    Direction4.NORTHEAST -> if (y >= l - 1 && x <= width - l) xmas.indices.map { input[y - it][x + it] } else null
                    Direction4.EAST -> if (x <= width - l) xmas.indices.map { input[y][x + it] } else null
                    Direction4.SOUTHEAST -> if (x <= width - l && y <= height - l) xmas.indices.map { input[y + it][x + it] } else null
                    Direction4.SOUTH -> if (y <= height - l) xmas.indices.map { input[y + it][x] } else null
                    Direction4.SOUTHWEST -> if (y <= height - l && x >= l - 1) xmas.indices.map { input[y + it][x - it] } else null
                    Direction4.WEST -> if (x >= l - 1) xmas.indices.map { input[y][x - it] } else null
                    Direction4.NORTHWEST -> if (x >= l - 1 && y >= l - 1) xmas.indices.map { input[y - it][x - it] } else null
                }?.toCharArray() contentEquals xmas
            }
        }

        var count = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'X') count += P(x, y).countXmases()
            }
        }
        count
    }

    val grid = input.mapIndexed { y, row -> row.mapIndexed { x, c -> P(x, y) to c } }.flatten().toMap()
    val directions = (-1..1).flatMap { y -> (-1..1).map { x -> P(x, y) } }
    part1 {
        grid.entries.sumOf { (p, c) ->
            directions.count { d ->
                xmas.indices.mapNotNull { i ->
//                    input[p.y + d.y * i][p.x + d.x * i]
                    grid[P(p.x + d.x * i, p.y + d.y * i)] }.toCharArray() contentEquals xmas
            }
        }
    }

    part2 {
        grid.entries.count { (p, c) ->
            if (c != 'A') return@count false
            val nw = grid[P(p.x - 1, p.y + 1)]
            val ne = grid[P(p.x + 1, p.y + 1)]
            val se = grid[P(p.x + 1, p.y - 1)]
            val sw = grid[P(p.x - 1, p.y - 1)]
            ((nw == 'M' && se == 'S') || (se == 'M' && nw == 'S')) &&
                    ((ne == 'M' && sw == 'S') || (sw == 'M' && ne == 'S'))
        }
    }
}

private enum class Direction4 {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
}
