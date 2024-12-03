fun main() {
    val memory = readInput(3) { it }.joinToString("")
    val r = """(do\(\)|don't\(\)|mul\((\d{1,3}),(\d{1,3})\))""".toRegex()
    fun Sequence<MatchResult>.sumProducts() = filterNot { it[2].isBlank() }.sumOf { it[2].toInt() * it[3].toInt() }
    part1 { r.findAll(memory).sumProducts() }
    part2 {
        var accept = true
        var sum = 0
        r.findAll(memory).forEach {
            when (it[1]) {
                "do()" -> accept = true
                "don't()" -> accept = false
                else -> if (accept) sum += it[2].toInt() * it[3].toInt()
            }
        }
        sum
    }
}
