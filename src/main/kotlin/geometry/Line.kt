package geometry

import kotlin.math.abs
import kotlin.math.hypot

fun Segment.toLine(): Line {
  return Line.fromSegment(this)
}

/**
 * Represents an infinite line by the 2d generalized equation of ax+by+c = 0
 */
data class Line(
  val a: Double,
  val b: Double,
  val c: Double,
) {
  /** True if the lines intersect at exactly one point (i.e., not parallel). */
  fun intersects(
    other: Line,
    eps: Double = 1e-12,
  ): Boolean {
    val det = this.a * other.b - other.a * this.b
    return abs(det) > eps
  }

  /** True if the two lines are coincident (infinitely many intersections). */
  fun isCoincidentWith(
    other: Line,
    eps: Double = 1e-12,
  ): Boolean {
    val ab = this.a * other.b - other.a * this.b
    val ac = this.a * other.c - other.a * this.c
    val bc = this.b * other.c - other.b * this.c
    return abs(ab) <= eps && abs(ac) <= eps && abs(bc) <= eps
  }

  /** True if the lines share at least one point. */
  fun intersectsAtLeastOnePoint(
    other: Line,
    eps: Double = 1e-12,
  ): Boolean = intersects(other, eps) || isCoincidentWith(other, eps)

  fun intersectionPoint(
    other: Line,
    eps: Double = 1e-12,
  ): Point? {
    val det = a * other.b - other.a * b
    if (abs(det) <= eps) return null // parallel or coincident

    val x = (b * other.c - other.b * c) / det
    val y = (other.a * c - a * other.c) / det
    return Point(x, y)
  }

  fun contains(
    point: Point,
    eps: Double = 1e-12,
  ): Boolean {
    return abs(a * point.x + b * point.y + c) <= eps
  }

  companion object {
    fun fromPoints(
      p1: Point,
      p2: Point,
    ): Line {
      require(p1 != p2) { "Points must be distinct" }

      val a = p2.y - p1.y
      val b = p1.x - p2.x
      val c = p2.x * p1.y - p1.x * p2.y
      // Optional normalization: scale so sqrt(A² + B²) == 1
      val norm = hypot(a, b)
      return if (norm != 0.0) {
        Line(a / norm, b / norm, b / norm)
      } else {
        Line(a, b, c)
      }
    }

    fun fromSegment(segment: Segment): Line {
      return fromPoints(segment.a, segment.b)
    }
  }
}
