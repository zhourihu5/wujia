package com.abctime.lib_common.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.IntDef;

import com.abctime.lib_common.R;

import java.util.HashMap;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/11.
 * email :renxiaomin@100tal.com
 */

public class SoundPoolManager {
    public static final int SOUND_BACK_ID = 0;
    public static final int SOUND_REC_BTN = 1;
    public static final int SOUND_RIGHT_RES = 2;
    public static final int SOUND_WRONG_RES = 3;
    public static final int SOUND_SHOT = 4;
    public static final int SOUND_BOSS_START = 5;

    private Map<Integer, Integer> soundMap;

    private static SoundPoolManager INSTANCE;

    public static SoundPoolManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SoundPoolManager();
        }
        return INSTANCE;
    }

    private SoundPoolManager() {

    }

    @IntDef(SOUND_BACK_ID)
    public @interface SoundId {
    }

    private SoundPool soundPool;
    public static final int MAX_POOL_NUM = 10;

    public void initSound(Context context) {
        soundMap = new HashMap<>();
        Context con = context.getApplicationContext();
        soundPool = new SoundPool(MAX_POOL_NUM, AudioManager.STREAM_MUSIC, 0);
        int backSoundId = soundPool.load(con, R.raw.back, 1);
        int recBtnId = soundPool.load(con, R.raw.recorder, 1);
        int rightId = soundPool.load(con, R.raw.right, 1);
        int wrongId = soundPool.load(con, R.raw.wrong, 1);
        int shotId = soundPool.load(con, R.raw.shot, 1);
        int bossStartId = soundPool.load(con, R.raw.boss_start, 1);


        soundMap.put(SOUND_BACK_ID, backSoundId);
        soundMap.put(SOUND_REC_BTN, recBtnId);
        soundMap.put(SOUND_RIGHT_RES, rightId);
        soundMap.put(SOUND_WRONG_RES, wrongId);
        soundMap.put(SOUND_SHOT, shotId);
        soundMap.put(SOUND_BOSS_START, bossStartId);

    }

    public void play(@SoundId int sound) {
        if (soundMap != null && soundMap.containsKey(sound)) {
            soundPool.play(soundMap.get(sound), 1, 1, 1, 0, 1f);
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
