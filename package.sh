#!/bin/bash

SIGN_PATH="/Users/shenbingkai/Documents/signApk";

				adb uninstall com.wujia.intellect.terminal

	    ./gradlew clean assembleRelease

		cur_date="`date +%Y%m%d`"

		for f in `ls ./app/build/outputs/apk/release`; do
			# for f in `(ls ./app/build/outputs/apk/release | sed "s:^:`pwd`/: ")` ; do
			echo $f
			if [[ $f == app-release-unsigned.apk ]];then
				APKNAME=`pwd`/app/build/outputs/apk/release/$f

				echo $APKNAME

				cd $SIGN_PATH

				java -jar signapk.jar platform.x509.pem platform.pk8 $APKNAME build/app_signed.apk

				echo "签名完成"

				adb install build/app_signed.apk
		        break
		    fi
		done