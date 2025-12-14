package geometry

import grid.Position
import grid.PositionRectangle

fun Rectangle.toPolygon(): Polygon {
  return this.cornerPoints.toPolygon()
}

fun PositionRectangle.toPolygon(): Polygon {
  return this.cornerPositions.toPolygon()
}

fun Collection<Segment>.toPolygon(): Polygon {
  return Polygon.createFromSegments(this)
}

fun Collection<Point>.toPolygon(): Polygon {
  return Polygon.createFromPoints(this)
}

fun Collection<Position>.toPolygon(): Polygon {
  return this.toPoints().toPolygon()
}

@ConsistentCopyVisibility
data class Polygon private constructor(
  val boundary: List<Segment>,
) {
  fun edgesCross(rectangle: Rectangle): Boolean {
    return edgesCross(rectangle.toPolygon())
  }

  private fun edgesCross(other: Polygon): Boolean {
    return other.boundary.any { edgeA ->
      this.boundary.any { edgeB ->
        val intersectionPoint = edgeA.line.intersectionPoint(edgeB.line)
        if (intersectionPoint != null) {
          // check that intersectionPoint does not match any segment end
          if (listOf(edgeA.a, edgeA.b, edgeB.a, edgeB.b).any{ intersectionPoint.equalsWithEpsilon(it)}) {
            false
          } else {
            true
          }
        } else {
          false
        }
      }
    }
  }

  companion object {
    /**
     * Creates a polygon checking that the boundary is consistent, i.e. ends at the starting point
     * and each segment follows the previous one
     */
    fun createFromSegments(boundarySegments: Collection<Segment>): Polygon {
      val boundarySegments = boundarySegments.toList()
      if ((0..boundarySegments.lastIndex).any {
          boundarySegments[it].b != boundarySegments[(it + 1) % boundarySegments.size ]
        }
      ) {
        throw Error("Boundary is inconsistent")
      }
      return Polygon(boundarySegments)
    }

    /**
     * Creates a polygon from a list of points
     */
    fun createFromPoints(points: Collection<Point>): Polygon {
      val points = points.toList()
      return (0..points.lastIndex).map {
        Segment(points[it], points[(it + 1) % points.size])
      }.let { Polygon(it) }
    }
  }
}
