package grid

import kotlin.math.abs

data class PositionRectangle(
  val a: Position,
  val b: Position,
) {
  val cornerPositions: List<Position> by lazy {
    listOf(
      a,
      Position(a.x, b.y),
      b,
      Position(b.x, a.y),
    )
  }

  val area: Long by lazy {
    abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
  }

  @Suppress("UNUSED")
  fun isVerticalOrHorizontalLine(): Boolean {
    return (a.x == b.x) || (a.y == b.y)
  }
}
