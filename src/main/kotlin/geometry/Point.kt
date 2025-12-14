package geometry

import grid.Position
import kotlin.math.abs

fun Collection<Position>.toPoints(): List<Point> {
  return this.map { it.toPoint() }
}

fun Position.toPoint(): Point {
  return Point.fromPosition(this)
}

data class Point(
  val x: Double,
  val y: Double,
) {
  fun equalsWithEpsilon(
    other: Point,
    eps: Double = 1e-12,
  ): Boolean {
    return abs(x - other.x) < eps && abs(y - other.y) < eps
  }

  fun equalsWithEpsilon(
    other: Position,
    eps: Double = 1e-12,
  ): Boolean {
    return abs(x - other.x) < eps && abs(y - other.y) < eps
  }

  companion object {
    fun fromPosition(position: Position): Point {
      return Point(position.x.toDouble(), position.y.toDouble())
    }
  }
}
