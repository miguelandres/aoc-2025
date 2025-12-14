package geometry

import grid.PositionRectangle
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

  @Suppress("UNUSED")
  val area: Double by lazy {
    abs(a.x - b.x) * abs(a.y - b.y)
  }

  fun contains(
    point: Point,
    eps: Double = 1e-12,
  ): Boolean {
    val withinX =
      point.x >= min(a.x, b.x) - eps &&
        point.x <= max(a.x, b.x) + eps

    val withinY =
      point.y >= min(a.y, b.y) - eps &&
        point.y <= max(a.y, b.y) + eps
    return withinY && withinX
  }
}
