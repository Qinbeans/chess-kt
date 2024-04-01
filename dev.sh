# these 2 must be run at the same time
# ./gradlew run
# Create a background process for the following command
# pnpm build:css && ./gradlew -t build -x test -i

if [ "$1" == "start" ]; then
  if [ ! -d "logs" ]; then
    mkdir logs
  fi
  echo -e "<\e[1;32mInfo\e[0m> Starting the server and the build process..."
  pnpm build:css >logs/out.log 2>logs/err.log && ./gradlew -t build -x test -i >>logs/out.log 2>>logs/err.log &
  ./gradlew run >>logs/out.log 2>>logs/err.log &
  echo -e "<\e[1;32mInfo\e[0m> Server and build process started"
elif [ "$1" == "stop" ]; then
  echo -e "<\e[1;32mInfo\e[0m> Stopping the server and the build process..."
  pkill -f "gradle"
  pkill -f "pnpm"
  echo -e "<\e[1;32mInfo\e[0m> Server and build process stopped"
else
  echo "Invalid argument"
fi