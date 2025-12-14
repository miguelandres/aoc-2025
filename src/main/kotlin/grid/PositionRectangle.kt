package grid

import kotlin.math.abs

data class PositionRectangle(
  val a: Position,
  val b: Position,
) {
  val cornerPositions: List<Position> by lazy {
    listOf(
      a,
      a.copy(y = b.y),
      b,
      b.copy(y = a.y),
    )
  }

  fun area(): Long {
    return abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
  }
}
