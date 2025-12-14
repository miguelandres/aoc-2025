package utils

import java.io.File

private fun padInputNumber(n: Int) = n.toString().padStart(2, '0')

fun readAocInput(
  day: Int,
  part: Int = 1,
) = File("input/d${padInputNumber(day)}p${padInputNumber(part)}.txt").readLines()

fun String.splitWithPrefix(
  prefix: String,
  delimiter: String,
) = this.drop(prefix.length).split(delimiter)

fun MutableList<String>.removeLinesUntilBlankOrNull(): List<String> {
  val result = mutableListOf<String>()
  var line = this.removeFirstOrNull()
  while (line != null) {
    if (line.isEmpty()) {
      break
    }
    result.add(line)
    line = this.removeFirstOrNull()
  }
  return result
}

fun String.parseRange(): IntRange {
  val parts = this.split("-").map { x -> x.toInt() }
  return parts[0]..parts[1]
}

fun String.parseLongRange(): LongRange {
  val parts = this.split("-").map { x -> x.toLong() }
  return parts[0]..parts[1]
}

fun String.parsePosition(): Position {
  return this.split(",").map { it.toInt() }.let { Position(it[0], it[1]) }
}
