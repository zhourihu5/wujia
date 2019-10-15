#!/bin/bash

adb disconnect
#modify by your ip
adb connect 192.168.250.89:5555
#
adb root
adb remount
adb shell rm -rf system/app/kxlaucher
adb shell rm -rf system/app/appupdate
adb shell rm -rf system/app/smartlife
adb shell rm -rf system/app/door
adb shell rm -rf system/app/smartlifeprotectservice
adb shell rm -rf system/app/kxcamera
adb uninstall com.jingxi.smartlife.pad
adb uninstall com.smartlife.appupdate
adb uninstall com.smartlife.protectservice
adb uninstall com.jingxi.lightweightsmartlife.lightweightsmartlife
adb uninstall com.smartlife.camera
adb uninstall com.smartlife.door_manager_application
adb uninstall com.android.settings
adb uninstall com.android.deskclock
adb push Whitelist.dat  /metadata/mConfig/properties/Whitelist.dat
adb shell chmod 777 /metadata/mConfig/properties/Whitelist.dat

#modify by your path
adb install app/build/outputs/apk/release/wujia_release_c2_v1.2.9-20191010_1850.apk
#pause
