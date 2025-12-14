package geometry

import grid.Position
import grid.PositionRectangle

@Suppress("UNUSED")
fun Rectangle.toPolygon(): Polygon {
  return Polygon.createFromPoints(this.cornerPoints)
}

@Suppress("UNUSED")
fun PositionRectangle.toPolygon(): Polygon {
  return Polygon.createFromPositions(this.cornerPositions)
}

@ConsistentCopyVisibility
data class Polygon private constructor(
  val boundary: List<Segment>,
) {
  companion object {
    /**
     * Creates a polygon checking that the boundary is consistent, i.e. ends at the starting point
     * and each segment follows the previous one
     */
    fun createFromSegments(boundarySegments: Collection<Segment>): Polygon {
      @Suppress("NAME_SHADOWING")
      val boundarySegments = boundarySegments.toList()
      if ((0..boundarySegments.lastIndex).any {
          boundarySegments[it].b != boundarySegments[(it + 1) % boundarySegments.size ].a
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
      @Suppress("NAME_SHADOWING")
      val points = points.toList()
      return (0..points.lastIndex).map {
        Segment(points[it], points[(it + 1) % points.size])
      }.let { Polygon(it) }
    }

    /**
     * Creates a polygon from a list of positions
     */
    fun createFromPositions(positions: Collection<Position>): Polygon {
      return createFromPoints(positions.map {it.toPoint()})
    }
  }
}
