
ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

build:
	./gradlew build -x test

tasks:
	./gradlew tasks

install:
	./gradlew installDebug
