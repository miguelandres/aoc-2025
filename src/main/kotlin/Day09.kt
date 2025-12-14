import geometry.Polygon
import grid.PositionRectangle
import utils.parsePosition
import utils.readAocInput
import kotlin.math.max

fun main() {
  val positions = readAocInput(9, 0).map { it.parsePosition() }

  val polygon = Polygon.createFromPositions(positions)
  assert(polygon.boundary.all { it.a != it.b })
  var maxArea = 0L
  for (i in 0..positions.lastIndex) {
    for (j in i + 1..positions.lastIndex) {
      val positionRectangle = PositionRectangle(positions[i], positions[j])
      maxArea = max(maxArea, positionRectangle.area)
    }
  }
  println(maxArea)
}
