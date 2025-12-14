package geometry

data class Segment(
  val a: Point,
  val b: Point,
) {
  @Suppress("UNUSED")
  val line: Line by lazy {
    this.toLine()
  }

  @Suppress("UNUSED")
  val boundingRectangle: Rectangle by lazy {
    Rectangle(a, b)
  }
}
