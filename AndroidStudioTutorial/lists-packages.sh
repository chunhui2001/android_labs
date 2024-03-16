#!/bin/sh
adb -s emulator-5554 shell pm list packages -3 | grep labs --color
adb -s emulator-5554 shell pm list packages -3 | grep labs | awk -F':' '{cmd="adb -s emulator-5554 uninstall " $2; print cmd; system(cmd)}'
