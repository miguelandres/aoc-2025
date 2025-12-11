import utils.Map2D
import utils.Position
import utils.readAocInput

interface Operation<T : Number> {
  fun apply(
    a: T,
    b: T,
  ): T
}

enum class CephalopodOperation : Operation<Long> {
  Multiply() {
    override fun apply(
      a: Long,
      b: Long,
    ): Long {
      return a * b
    }
  },
  Add {
    override fun apply(
      a: Long,
      b: Long,
    ): Long {
      return a + b
    }
  },
}

fun main() {
  val lines = readAocInput(6, 1)
  val whitespaceRegex = "\\s+".toRegex()
  val numbersStr = lines.subList(0, lines.size - 1)
  val numbers =
    numbersStr.map {
      it.trim().split(whitespaceRegex).map { if (it == "") 0L else it.toLong() }.toTypedArray()
    }.toTypedArray().let {
      Map2D(it)
    }

  val operations =
    lines.last().split(whitespaceRegex).map {
      when (it) {
        "+" -> CephalopodOperation.Add
        "*" -> CephalopodOperation.Multiply
        else -> throw Exception("Got unexpected operation `$it`")
      }
    }

  val part1 =
    operations.mapIndexed { i, operation ->
      numbers.range.rangeY.map { j -> numbers[Position(i, j)] }.reduce { a, b -> operation.apply(a, b) }
    }.sum()
  println(part1)
}
