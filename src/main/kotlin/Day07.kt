import grid.Map2D
import grid.Position
import utils.readAocInput

interface TachyonInteraction {
  fun interact(
    column: Int,
    map: Map2D<TachyonLocation>,
  ): Set<Int>
}

enum class TachyonLocation : TachyonInteraction {
  Empty {
    override fun interact(
      column: Int,
      map: Map2D<TachyonLocation>,
    ): Set<Int> {
      return setOf(column)
    }
  },
  Start {
    override fun interact(
      column: Int,
      map: Map2D<TachyonLocation>,
    ): Set<Int> {
      return setOf(column)
    }
  },
  Splitter {
    override fun interact(
      column: Int,
      map: Map2D<TachyonLocation>,
    ): Set<Int> {
      return listOf(column + 1, column - 1).filter { map.range.rangeX.contains(it) }.toSet()
    }
  },
}

fun main() {
  val mapArray =
    readAocInput(7, 1).map { s ->
      s.map {
        when (it) {
          'S', 's' -> TachyonLocation.Start
          '^' -> TachyonLocation.Splitter
          else -> TachyonLocation.Empty
        }
      }.toTypedArray()
    }.toTypedArray()
  val map = Map2D(mapArray)

  val startPosition = map.range.first { pos -> map[pos] == TachyonLocation.Start }
  var tachyonColumns = setOf(startPosition.x)
  var countSplits = 0
  for (y in startPosition.y + 1..map.range.rangeY.last) {
    countSplits +=
      tachyonColumns.count {
        val pos = Position(it, y)
        map[pos] == TachyonLocation.Splitter
      }
    tachyonColumns =
      tachyonColumns.map {
        val pos = Position(it, y)
        map[pos].interact(it, map)
      }.fold(emptySet<Int>()) { acc, cols -> acc + cols}
  }
  println(countSplits)

  var beamCounter = mutableMapOf(startPosition.x to 1L).withDefault { 0L }
  for (y in (0..map.range.rangeY.last step 2)) {
    map.range.rangeX.forEach { x ->
      val position = Position(x, y)
      val currentValue = beamCounter.getValue(x)
      if (map[position] == TachyonLocation.Splitter) {
        beamCounter[x - 1] = beamCounter.getValue(x - 1) + currentValue
        beamCounter[x + 1] = beamCounter.getValue(x + 1) + currentValue
        beamCounter.remove(x)
      }
    }
  }

  val part2 = beamCounter.values.sum()
  println(part2)
}
