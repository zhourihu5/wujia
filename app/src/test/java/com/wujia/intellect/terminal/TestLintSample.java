package com.wujia.intellect.terminal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TestLintSample extends Activity {
    private static final String TAG = "aa";
    // In a comment, mentioning "lint" has no effect" +
                            private static String s1 = "Ignore non-word usages: linting";
                                private static String s2 = "Let's say it: lint";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testLogUsage();
    }

    /**
     * 注意：
     * LagDetector
     * 这是一个 Error 级别的 Lint 警告
     * 编译时会终止编译
     * 运行时可以注释掉，或者配置 LintOptions
     */
    private void testLogUsage() {

        //Android 自带的 ToastDetector
        Toast.makeText(this, "", Toast.LENGTH_SHORT);

        //SampleCodeDetector
        System.out.println("Omooo");

        //PngDetector
//        new ImageView(this).setImageResource(R.drawable.google);

        //LogDetector
        Log.i(TAG, "啊啊啊啊，我被发现了！");

        //ThreadDetector
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
}
