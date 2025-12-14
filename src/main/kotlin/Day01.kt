import utils.readAocInput

data class LockCounter(
  val lockPointer: Int,
  val counter: Int,
)

fun main() {
  val lines = readAocInput(1, 1)
  val input =
    lines
      .map { s -> (if (s.startsWith("L")) -1 else 1) * s.substring(1).toInt() }

  val part1Result =
    input.fold(LockCounter(50, 0)) { acc, move ->
      val nextPointer = ((acc.lockPointer + move) % 100).let { if (it < 0) 100 + it else it }
      LockCounter(nextPointer, if (nextPointer == 0) acc.counter + 1 else acc.counter)
    }
  println(part1Result)

  val part2Result =
    input.fold(LockCounter(50, 0)) { acc, move ->
      val sum = (acc.lockPointer + move)
      val count =
        if (sum <= 0) {
          if (acc.lockPointer == 0) {
            Math.abs(sum) / 100
          } else {
            1 + Math.abs(sum) / 100
          }
        } else {
          sum / 100
        }
      val nextPointer = (sum % 100).let { if (it < 0) 100 + it else it }
      LockCounter(nextPointer, count + acc.counter)
    }
  println(part2Result)
}
