package grid

data class Range2D(
  val rangeX: IntRange,
  val rangeY: IntRange,
) : Collection<Position> {
  override fun contains(element: Position): Boolean = rangeX.contains(element.x) && rangeY.contains(element.y)

  override val size: Int = rangeX.count() * rangeY.count()

  override fun isEmpty(): Boolean = rangeX.isEmpty() || rangeY.isEmpty()

  inner class Range2DIterator : Iterator<Position> {
    private val allPositions = rangeY.flatMap { y -> rangeX.map { x -> Position(x, y) } }
    private var index = 0

    override fun hasNext(): Boolean = index in allPositions.indices

    override fun next(): Position = allPositions[index].also { index++ }
  }

  override fun iterator(): Iterator<Position> = Range2DIterator()

  override fun containsAll(elements: Collection<Position>): Boolean = elements.all { this.contains(it) }
}
