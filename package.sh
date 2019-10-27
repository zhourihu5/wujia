#!/bin/bash
# create by shenbingkai


SIGN_PATH="/Users/shenbingkai/Documents/signApk";

	    ./gradlew clean assembleRelease

		for f in `ls ./app/build/outputs/apk/release`; do
			# for f in `(ls ./app/build/outputs/apk/release | sed "s:^:`pwd`/: ")` ; do
			echo $f
			if [[ $f == wujia_release_* && $f == *.apk ]];then
				APKNAME=app/build/outputs/apk/release/$f

				echo $APKNAME

				echo "打包完成"

				adb uninstall com.jingxi.smartlife.pad

				adb install APKNAME

				echo "安装完成"

		        break
		    fi
		done