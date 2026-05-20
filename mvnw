#!/usr/bin/env sh
set -e

BASE_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROPS="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$WRAPPER_PROPS" ]; then
  echo "[ERROR] $WRAPPER_PROPS not found" >&2
  exit 1
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  WRAPPER_URL="$(grep '^wrapperUrl=' "$WRAPPER_PROPS" | sed 's/wrapperUrl=//')"
  if [ -z "$WRAPPER_URL" ]; then
    echo "[ERROR] wrapperUrl missing in maven-wrapper.properties" >&2
    exit 1
  fi
  echo "Downloading Maven wrapper jar..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$WRAPPER_URL" -O "$WRAPPER_JAR"
  else
    echo "[ERROR] Need curl or wget to download wrapper jar" >&2
    exit 1
  fi
fi

exec java -classpath "$WRAPPER_JAR" \
  "-Dmaven.multiModuleProjectDirectory=$BASE_DIR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
