# Miguel's Advent of Code 2025

Doing this again in Kotlin and using IntelliJ and gradle.kts for build and config

## Environment Setup on macOS

```sh
brew install temurin21 kotlin pre-commit
brew install --cask intellij-idea-ce
pre-commit install
```

## Start a new day

```sh
./nextday.sh dayNumber
```

## Run a day from console

```sh
./gradlew runDay -Pday=01
```
