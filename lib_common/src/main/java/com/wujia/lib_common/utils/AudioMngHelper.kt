package com.wujia.lib_common.utils

import android.content.Context
import android.media.AudioManager
import androidx.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-17
 * description ：
 */
class AudioMngHelper
/**
 * 初始化，获取音量管理者
 *
 * @param context 上下文
 */
(context: Context) {

    private val TAG = "AudioMngHelper"
    private val OpenLog = true

    private val audioManager: AudioManager
    private var NOW_AUDIO_TYPE = TYPE_MUSIC
    private var NOW_FLAG = FLAG_NOTHING
    private var VOICE_STEP_100 = 2 //0-100的步进。

    val systemMaxVolume: Int
        get() = audioManager.getStreamMaxVolume(NOW_AUDIO_TYPE)

    val systemCurrentVolume: Int
        get() = audioManager.getStreamVolume(NOW_AUDIO_TYPE)

    /**
     * 以0-100为范围，获取当前的音量值
     *
     * @return 获取当前的音量值
     */
    val currentVolumePercentage: Int
        get() = 100 * systemCurrentVolume / systemMaxVolume

    @IntDef(TYPE_MUSIC, TYPE_ALARM, TYPE_RING)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class TYPE

    @IntDef(FLAG_SHOW_UI, FLAG_PLAY_SOUND, FLAG_NOTHING)
    @Retention(RetentionPolicy.SOURCE)
    annotation class FLAG

    init {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    /**
     * 修改步进值
     *
     * @param step step
     * @return this
     */
    fun setVoiceStep100(step: Int): AudioMngHelper {
        VOICE_STEP_100 = step
        return this
    }

    /**
     * 改变当前的模式，对全局API生效
     *
     * @param type
     * @return
     */
    fun setAudioType(@TYPE type: Int): AudioMngHelper {
        NOW_AUDIO_TYPE = type
        return this
    }

    /**
     * 改变当前FLAG，对全局API生效
     *
     * @param flag
     * @return
     */
    fun setFlag(@FLAG flag: Int): AudioMngHelper {
        NOW_FLAG = flag
        return this
    }

    fun addVoiceSystem(): AudioMngHelper {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE, AudioManager.ADJUST_RAISE, NOW_FLAG)
        return this
    }

    fun subVoiceSystem(): AudioMngHelper {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE, AudioManager.ADJUST_LOWER, NOW_FLAG)
        return this
    }

    /**
     * 调整音量，自定义
     *
     * @param num 0-100
     * @return 改完后的音量值
     */
    fun setVoice100(num: Int): Int {
        var a = Math.ceil(num.toDouble() * systemMaxVolume.toDouble() * 0.01).toInt()
        a = if (a <= 0) 0 else a
        a = if (a >= 100) 100 else a
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, 0)
        return currentVolumePercentage
    }

    /**
     * 步进加，步进值可修改
     * 0——100
     *
     * @return 改完后的音量值
     */
    fun addVoice100(): Int {
        var a = Math.ceil((VOICE_STEP_100 + currentVolumePercentage).toDouble() * systemMaxVolume.toDouble() * 0.01).toInt()
        a = if (a <= 0) 0 else a
        a = if (a >= 100) 100 else a
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, NOW_FLAG)
        return currentVolumePercentage
    }

    /**
     * 步进减，步进值可修改
     * 0——100
     *
     * @return 改完后的音量值
     */
    fun subVoice100(): Int {
        var a = Math.floor((currentVolumePercentage - VOICE_STEP_100).toDouble() * systemMaxVolume.toDouble() * 0.01).toInt()
        a = if (a <= 0) 0 else a
        a = if (a >= 100) 100 else a
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, NOW_FLAG)
        return currentVolumePercentage
    }

    companion object {

        /**
         * 封装：STREAM_类型
         */
        const val TYPE_MUSIC = AudioManager.STREAM_MUSIC
        const val TYPE_ALARM = AudioManager.STREAM_ALARM
        const val TYPE_RING = AudioManager.STREAM_RING

        /**
         * 封装：FLAG
         */
        const val FLAG_SHOW_UI = AudioManager.FLAG_SHOW_UI
        const val FLAG_PLAY_SOUND = AudioManager.FLAG_PLAY_SOUND
        const val FLAG_NOTHING = 0
    }
}
