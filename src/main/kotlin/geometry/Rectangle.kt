package geometry

import grid.PositionRectangle
import kotlin.math.abs

fun PositionRectangle.toRectangle(): Rectangle {
  return Rectangle(this.a.toPoint(), this.b.toPoint())
}

data class Rectangle(
  val a: Point,
  val b: Point,
) {
  val cornerPoints: List<Point> by lazy {
    listOf(
      a,
      a.copy(y = b.y),
      b,
      b.copy(y = a.y),
    )
  }

  fun area(): Double {
    return abs(a.x - b.x) * abs(a.y - b.y)
  }
}
