import utils.parseLongRange
import utils.readAocInput

fun isInvalidIdPart1(id: Long): Boolean {
  val idStr = id.toString()
  if (idStr.length % 2 != 0) {
    return false
  }
  val lastPart = idStr.substring(idStr.length / 2)
  if (idStr == lastPart + lastPart) {
    return true
  }
  return false
}

fun isInvalidIdPart2(id: Long): Boolean {
  val str = id.toString()
  val len = str.length
  for (subStringLen in 1..len / 2) {
    if (len % subStringLen != 0) {
      continue
    }
    val times = len / subStringLen
    val subString = str.substring(0, subStringLen)
    val generatedString = Array(times) { subString}.joinToString("")
    if (generatedString == str) return true
  }
  return false
}

fun main() {
  val ranges =
    readAocInput(2, 1)
      .first()
      .split(",")
      .map{ it.parseLongRange()}

  val sumInvalidIdsPart1 =
    ranges.sumOf { range ->
      range.filter {
        isInvalidIdPart1(it)
      }.sum()
    }
  println(sumInvalidIdsPart1)

  val sumInvalidIdsPart2 =
    ranges.sumOf { range ->
      range.filter {
        isInvalidIdPart2(it)
      }.sum()
    }
  println(sumInvalidIdsPart2)
}
