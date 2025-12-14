package geometry

data class Segment(
  val a: Point,
  val b: Point,
) {
  val line: Line by lazy {
    this.toLine()
  }
}
