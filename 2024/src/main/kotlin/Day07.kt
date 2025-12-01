fun main() {
    val input = parseInput(7) {
        it.split(": ").let { (testValue, values) -> testValue.toInt() to values.split(" ").map { it.toInt() } }
    }
    part1 {
//        input.sumOf { (testValue, values) ->
//            0
//        }
        0
    }
    part2 { 0 }
}
//
//fun dfs(node: Int, visited: Set<Int>, graph: Map<Int, List<Int>>): Int {
//    // Implement DFS algorithm
//    0
//}