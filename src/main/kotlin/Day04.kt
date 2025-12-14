import grid.Map2D
import utils.readAocInput

enum class PaperWarehouseContents {
  Empty,
  Roll,
  Removing,
}

fun main() {
  val map: Map2D<PaperWarehouseContents> =
    readAocInput(4, 1)
      .map {
        it.toList().map { c ->
          when (c) {
            '.' -> PaperWarehouseContents.Empty
            '@' -> PaperWarehouseContents.Roll
            else -> PaperWarehouseContents.Empty
          }
        }.toTypedArray()
      }.toTypedArray().let { Map2D(it) }

  val part1 =
    map.mapWithPosition { pos, it ->
      if (it == PaperWarehouseContents.Empty) {
        0
      } else {
        val countPaperRollNeighbors =
          pos.mapDiagonalNeighborsInRange(map.range) {neighbor ->
            return@mapDiagonalNeighborsInRange when (map[neighbor]) {
              PaperWarehouseContents.Empty, PaperWarehouseContents.Removing -> 0
              PaperWarehouseContents.Roll -> 1
            }
          }.sum()
        if (countPaperRollNeighbors < 4) {
          1
        } else {
          0
        }
      }
    }.sum()
  println(part1)

  var hasModifiedMap: Boolean
  var iteration = 0
  var countRemoved = 0
  do {
    hasModifiedMap = false
    for (position in map.range) {
      if (map[position] == PaperWarehouseContents.Roll) {
        val countNeighborsStillPresent =
          position.mapDiagonalNeighborsInRange(map.range){ neighborPos ->
            when (map[neighborPos]) {
              PaperWarehouseContents.Empty -> 0
              PaperWarehouseContents.Roll, PaperWarehouseContents.Removing -> 1
            }
          }.sum()
        if (countNeighborsStillPresent < 4) {
          // can remove in this iteration
          hasModifiedMap = true
          map[position] = PaperWarehouseContents.Removing
          countRemoved++
        }
      }
    }
    for (position in map.range) {
      if (map[position] == PaperWarehouseContents.Removing) {
        map[position] = PaperWarehouseContents.Empty
      }
    }
    iteration++
  } while (hasModifiedMap)
  println(countRemoved)
}
