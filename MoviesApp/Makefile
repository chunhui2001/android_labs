### 当前 Makefile 文件物理路径
ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

build:
	./gradlew build -x test

tasks:
	./gradlew tasks

install:
	./gradlew installDebug
