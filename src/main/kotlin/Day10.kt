import utils.readAocInput
import utils.removeAffixes
import utils.splitWithAffixes

enum class LightStatus {
  ON,
  OFF,
  ;

  fun flip(): LightStatus {
    return when (this) {
      ON -> OFF
      OFF -> ON
    }
  }
}

data class MachineStatus(
  val lights: List<LightStatus>,
) {
  fun pressButton(button: List<Int>): MachineStatus {
    val newLights = lights.toTypedArray()
    button.forEach {
      newLights[it] = newLights[it].flip()
    }
    return MachineStatus(newLights.toList())
  }

  companion object {
    fun new(numberOfLights: Int): MachineStatus {
      return MachineStatus(Array(numberOfLights) {LightStatus.OFF }.toList())
    }
  }
}

data class MachineJoltage(
  val joltages: List<Int>,
) {
  fun pressButton(button: List<Int>): MachineJoltage {
    val newJoltages = joltages.toTypedArray()
    button.forEach {
      newJoltages[it]++
    }
    return MachineJoltage(newJoltages.toList())
  }

  fun isDisqualifiedFor(requirements: MachineJoltage): Boolean {
    return joltages.zip(requirements.joltages).any {
      it.first > it.second
    }
  }
  companion object {
    fun new(numberOfJoltages: Int): MachineJoltage {
      return MachineJoltage(Array(numberOfJoltages) {0 }.toList())
    }
  }
}

fun main() {
  val splitLines = readAocInput(10, 1).map { s -> s.split(" ") }
  val targetStatesForMachine =
    splitLines.map { line ->
      MachineStatus(
        line
          .first()
          .removeAffixes("[", "]")
          .map {
            when (it) {
              '#' -> LightStatus.ON
              else -> LightStatus.OFF
            }
          },
      )
    }

  val joltageRequirementsForMachine =
    splitLines.map { parts ->
      parts.last().splitWithAffixes("{", ",", "}").map {
        it.toInt()
      }
    }

  val buttonsForMachine =
    splitLines.map { parts ->
      parts.drop(1).dropLast(1).map {
        it.splitWithAffixes("(", ",", ")").map { light ->
          light.toInt()
        }
      }
    }

  val part1 =
    (0..targetStatesForMachine.lastIndex).sumOf { i ->
      val target = targetStatesForMachine[i]
      val buttons = buttonsForMachine[i]
      var currentCandidates = setOf(MachineStatus.new(target.lights.size))
      var seenStatuses = emptySet<MachineStatus>()
      var count = 0
      while (!currentCandidates.contains(target)) {
        count++
        currentCandidates =
          currentCandidates.flatMap { from ->
            buttons
              .map { from.pressButton(it) }
              .toSet()
              .filterNot { seenStatuses.contains(it)}
          }.toSet()
        seenStatuses = seenStatuses + currentCandidates
      }
      count
    }

  println(part1)

  val part2 =
    (0..targetStatesForMachine.lastIndex).sumOf { i ->
      val requirements = MachineJoltage(joltageRequirementsForMachine[i])
      val buttons = buttonsForMachine[i]
      var currentCandidates = setOf(MachineJoltage.new(requirements.joltages.size))
      var seenStatuses = emptySet<MachineJoltage>()
      var count = 0
      while (!currentCandidates.contains(requirements)) {
        count++
        currentCandidates =
          currentCandidates
            .flatMap { from ->
              buttons
                .map { from.pressButton(it) }
                .toSet()
                .filterNot {
                  seenStatuses.contains(it)
                }
            }.toSet()
        seenStatuses = seenStatuses + currentCandidates
        currentCandidates = currentCandidates.filterNot { it.isDisqualifiedFor(requirements) }.toSet()
      }
      count
    }
  println(part2)
}
