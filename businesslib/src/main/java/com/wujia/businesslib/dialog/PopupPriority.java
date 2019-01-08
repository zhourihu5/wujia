package com.wujia.businesslib.dialog;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/10/22 下午7:36
 */

public class PopupPriority {

    public static final int PRIORITY_NORMAL = 0;//默认弹窗优先级为0
    public static final int PRIORITY_LOGIN = 100;//异地登陆弹窗优先级为100
    public static final int PRIORITY_UPDATE = 200;//更新弹窗优先级为200

    private static PopupPriority mInstance;

    public static PopupPriority get() {
        if (mInstance == null) {
            synchronized (PopupPriority.class) {
                if (mInstance == null) {
                    mInstance = new PopupPriority();
                }
            }
        }
        return mInstance;
    }

    private ArrayList<Priority> map;

    private PopupPriority() {
    }

    public boolean addPopup(IPopupPriority priority) {
        if (priority == null)
            return false;
        if (map == null)
            map = new ArrayList<>();
        Priority priorityEntity = getPriority(priority);
        if (map.size() == 0) {
            map.add(priorityEntity);
            return true;
        } else {
            int p = priorityEntity.priority;

            for (Priority pI : map) {
                if (pI.priority > p) {
                    return false;
                }
            }
            map.add(priorityEntity);
            return true;
        }
    }

    public boolean removePopup(IPopupPriority priority) {
        if (priority == null)
            return false;
        if (map == null || map.size() == 0)
            return false;
        ArrayList<Priority> target = null;
        for (Priority pI : map) {
            if (TextUtils.equals(pI.tag, priority.getTag())) {
                if (target == null)
                    target = new ArrayList<>();
                target.add(pI);
            }
        }
        if (target != null) {
            map.removeAll(target);
            return true;
        }
        return false;
    }

    private Priority getPriority(@NonNull IPopupPriority priority) {
        return new Priority(priority);
    }

    private static class Priority {
        int priority;
        String tag;

        Priority(IPopupPriority priority) {
            this.priority = priority.getPriority();
            tag = priority.getTag();
        }
    }
}
