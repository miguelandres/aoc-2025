import utils.parseLongRange
import utils.readAocInput
import utils.removeLinesUntilBlankOrNull

fun main() {
  val input = readAocInput(5, 1).toMutableList()
  val freshRanges = input.removeLinesUntilBlankOrNull().map { it.parseLongRange() }.toList()
  val ids = input.map { it.toLong() }

  val part1 =
    ids.count { id ->
      freshRanges.any { range -> range.contains(id) }
    }
  println(part1)

  val sortedRanges = freshRanges.sortedBy { it.first }

  var count = 0L
  var lastItemInRange = Long.MIN_VALUE
  sortedRanges.forEach {
    if (lastItemInRange >= it.last) {
      // Skip, no need  to do anything
    } else {
      count += it.last - it.first + 1
      if (lastItemInRange >= it.first) {
        count -= lastItemInRange - it.first + 1
      }
      lastItemInRange = it.last
    }
  }

  println(count)
}
