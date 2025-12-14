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
      Point(a.x, b.y),
      b,
      Point(b.x, a.y),
    )
  }

  val area: Double by lazy {
    abs(a.x - b.x) * abs(a.y - b.y)
  }
}
