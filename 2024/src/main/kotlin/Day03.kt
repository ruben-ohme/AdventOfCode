fun main() {
    val memory = readInput(3) { it }.joinToString("")
    val r = """(mul\((\d{1,3}),(\d{1,3})\))""".toRegex()
    fun Sequence<MatchResult>.sumProducts() = sumOf { it[2].toInt() * it[3].toInt() }
    part1 { r.findAll(memory).sumProducts() }
    part2 { r.findAll(memory.split("do()").map { it.split("don't()")[0] }.joinToString("")).sumProducts() }
}
