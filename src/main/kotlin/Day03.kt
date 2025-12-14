import utils.readAocInput

fun main() {
  val powerBanks =
    readAocInput(3, 1)
      .map { str -> str.map { it.digitToInt() } }

  val part1 =
    powerBanks.sumOf {powerBank ->
      var maxDigit = 0
      var maxDigitPosition = 0
      for (i in (0 until powerBank.size - 1)) {
        if (powerBank[i] > maxDigit) {
          maxDigit = powerBank[i]
          maxDigitPosition = i
        }
      }
      val secondDigit = powerBank.subList(maxDigitPosition + 1, powerBank.size).max()
      maxDigit * 10 + secondDigit
    }
  println(part1)

  val part2 =
    powerBanks.sumOf {powerBank ->
      var runningPowerBank = powerBank
      var runningValue = 0L
      for (i in 1..12) {
        runningValue *= 10
        val candidates = runningPowerBank.subList(0, runningPowerBank.size - 12 + i)
        val maxCandidate = candidates.max()
        runningValue += maxCandidate
        val position = candidates.indexOf(maxCandidate)
        runningPowerBank = runningPowerBank.subList(position + 1, runningPowerBank.size)
      }
      runningValue
    }

  println(part2)
}
