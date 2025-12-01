import kotlin.to

private enum class Direction {
    North,
    East,
    South,
    West;

    val right: Direction
        get() = when (this) {
            North -> East
            East -> South
            South -> West
            West -> North
            else -> this
        }
    val left: Direction
        get() = when (this) {
            North -> West
            East -> North
            South -> East
            West -> South
            else -> this
        }
}

private fun P.move(direction: Direction): P = when (direction) {
    Direction.North -> north
    Direction.East -> east
    Direction.South -> south
    Direction.West -> west
    else -> error("wtf")
}

private val Char.direction: Direction
    get() = when (this) {
        'N', '^' -> Direction.North
        'E', '>' -> Direction.East
        'S', 'v' -> Direction.South
        'W', '<' -> Direction.West
        else -> error("Invalid direction '$this'")
    }

fun main() {
    val map = parseInput(6) { it.toCharArray() }
    fun List<CharArray>.findGuard() = map.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            when (c) {
                '^', '>', '<', 'v' -> P(x, y) to c.direction
                else -> null
            }
        }.filterNotNull().firstOrNull()
    }.filterNotNull().first()


    fun P.shouldTurn(d: Direction) = when (d) {
        Direction.North -> y > 0 && map[y - 1][x] == '#'
        Direction.East -> x < map[y].size - 1 && map[y][x + 1] == '#'
        Direction.South -> y < map.size - 1 && map[y + 1][x] == '#'
        Direction.West -> x > 0 && map[y][x - 1] == '#'
        else -> false
    }

    fun P.turnRight(c: Direction) = c.right
    fun P.isWithinBounds() = y in map.indices && x in map[y].indices

    part1 {
        var (guard, direction) = map.findGuard()
        var visited = mutableSetOf<P>()
        while (guard.isWithinBounds()) {
            visited.add(guard)
            if (guard.shouldTurn(direction)) direction = guard.turnRight(direction)
            guard = guard.move(direction)
        }
        visited.size
    }

    part2 {
        fun isLoop(
            p: P,
            direction: Direction,
            visited: Set<P>,
            isLooping: Boolean = false,
            hasTried: MutableSet<P>
        ): Boolean {
            if (!hasTried.add(p)) return false
            if (isLooping && p in visited && p.shouldTurn(direction)) return true
            val projectedDirection = p.turnRight(direction)
            var projection = p.move(projectedDirection)
            while (projection.y in map.indices && projection.x in map[projection.y].indices) {
                if (projection.shouldTurn(projectedDirection)) {
                    return isLoop(projection, projectedDirection, visited, true, hasTried)
                }
                projection = projection.move(projectedDirection)
            }
            return false
        }

        var (guard, direction) = map.findGuard()
        var possibleObstructions = mutableSetOf<P>()
        var visited = mutableSetOf<P>()
        while (guard.isWithinBounds()) {
            visited.add(guard)
            val possibleObstruction = guard.move(direction)
            if (isLoop(guard, direction, visited, false, mutableSetOf())) possibleObstructions += possibleObstruction
            if (guard.shouldTurn(direction)) direction = guard.turnRight(direction)
            guard = guard.move(direction)
        }
        possibleObstructions.size
    }

    val Obstacles = setOf<P>()
    map.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c == '#') P(x, y)
        }
    }
    //    val obstacles =
//        map.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == '#') P(x, y) else null }.filterNotNull() }
//            .flatten()
    fun willTrapGuard(guardPosition: P, guardDirection: Direction, obstactlePosition: P): Boolean {
        val turns = mutableListOf<P>()
        var position = guardPosition
        var direction = guardDirection
        var obstacles = Obstacles.toMutableSet()
        obstacles += obstactlePosition

        while (position.isWithinBounds()) {
            var nextPosition = position.move(direction)
            if (nextPosition in obstacles) {
                // We've already turned right at this poisition, so we've got a loop
                if (position in turns) return true

                // Turn right until the path is clear
                val exploredNextPositions = mutableSetOf(nextPosition)
                var foundNextSpot = false
                while (!foundNextSpot) {
                    direction = direction.right
                    nextPosition = position.move(direction)
                    exploredNextPositions += nextPosition
                    if (nextPosition !in obstacles) {
                        foundNextSpot = true
                        // Taake note of when we made this turn to detect loops
                        turns += position
                    }
                    if (exploredNextPositions.size == 4 && !foundNextSpot) return true // Single-spot loop
                }
            }
            position = nextPosition
        }
        return false
    }








    part2 {
        val visitedSpots = mutableSetOf<P>()
        var (position, direction) = map.findGuard()
        val guardTraps = mutableSetOf<P>()
        while (position.isWithinBounds() && position !in Obstacles) {
            visitedSpots += position
            var nextPosition = position.move(direction)

            // When an obstacle is encountered, turn right until the path is clear
            if (nextPosition in Obstacles) {
                val exploredNextPositions = mutableSetOf(nextPosition)
                var foundNextSpot = false
                while (!foundNextSpot) {
                    direction = direction.right
                    nextPosition = position.move(direction)
                    exploredNextPositions += nextPosition
                    if (nextPosition !in Obstacles) foundNextSpot = true
                }
                // Try placing an obstacle at the next position
                if (nextPosition !in visitedSpots && willTrapGuard(position, direction, nextPosition)) {
                    guardTraps += nextPosition
                }
                position = nextPosition
            }
        }
        guardTraps.size
    }
}
