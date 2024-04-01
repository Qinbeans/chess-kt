# Kotlin version of my ongoing Chess-htmx project

This is a Kotlin version of my ongoing Chess-htmx project. The original project is written in Go and HTMX. I'm using Kotlin and Ktor for this version.

## Running the project

To run the project, execute the following command:

```shell
./gradlew run
```

However, there are a few ways to run this in development mode.

### Development mode

PNPM
```shell
pnpm dev
pnpm stop
```

BASH
```shell
./dev.sh start
./dev.sh stop
```

Gradle
```shell
pnpm build:css >logs/out.log 2>logs/err.log && ./gradlew -t build -x test -i >>logs/out.log 2>>logs/err.log &
./gradlew run >>logs/out.log 2>>logs/err.log &
```