import grid.Position3D
import utils.readAocInput

data class DistanceInfo(
  val from: Int,
  val to: Int,
  val distanceSquared: Double,
)

fun main() {
  val positions =
    readAocInput(8, 1)
      .map { s ->
        s.split(",")
          .map { it.toInt() }
          .let { Position3D(it[0], it[1], it[2]) }
      }.toTypedArray()

  var distanceList = listOf<DistanceInfo>()

  for (i in 0..positions.lastIndex) {
    val from = positions[i]
    for (j in i + 1..positions.lastIndex) {
      val to = positions[j]
      val distance = from.distanceSquared(to)
      if (distance < 1000000000) {
        distanceList = distanceList + DistanceInfo(i, j, distance)
      }
    }
  }

  distanceList = distanceList.sortedBy { it.distanceSquared }

  val adjacencyList: Array<MutableList<Int>> = Array(positions.size){ mutableListOf()}
  var usedNodes = setOf<Int>()

  val connections = distanceList.take(1000)

  for ((from, to) in connections) {
    adjacencyList[from].add(to)
    adjacencyList[to].add(from)
    usedNodes = usedNodes + listOf(to, from)
  }

  var processedNodes = setOf<Int>()
  var circuitSizes = listOf<Int>()

  for (nodeToProcess in usedNodes) {
    if (processedNodes.contains(nodeToProcess)) {
      continue
    }
    val queue = mutableListOf(nodeToProcess)
    var size = 0
    while (queue.isNotEmpty()) {
      val current = queue.removeFirst()
      processedNodes = processedNodes + current
      size++
      adjacencyList[current].filterNot {processedNodes.contains(it) }.let {
        queue.addAll(it)
        processedNodes = processedNodes + it
      }
    }
    circuitSizes = circuitSizes + size
  }

  val part1 = circuitSizes.sortedDescending().take(3).also { println(it)}.reduce { a, b -> a * b }
  println(part1)

  var tree = setOf(distanceList.first().from)
  for ((from, to) in distanceList) {
    if (tree.contains(from) && tree.contains(to)) {
      // skip, no need to do anything
    } else {
      // add to the tree
      tree = tree + setOf(to, from)
      if (tree.size == positions.size) {
        val part2Solution = positions[from].x * positions[to].x
        println("part2 $part2Solution")
      }
    }
  }
  println("complete")
}
