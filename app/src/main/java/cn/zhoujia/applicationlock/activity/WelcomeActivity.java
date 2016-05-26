package cn.zhoujia.applicationlock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.zhoujia.applicationlock.R;
import cn.zhoujia.applicationlock.util.cache.ACache;
import cn.zhoujia.applicationlock.util.constant.Constant;

/**
 * Created by Zhoujia on 2016/5/26.
 */
public class WelcomeActivity extends Activity {

    private Handler handler = new Handler() {
    };
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /**判断是否有密码
         * 没有则直接跳转到MainActivity
         * 有则跳转到输入密码界面
         **/
        this.doJump();
    }

    private void doJump() {
        aCache = ACache.get(this);
        //线程2秒后执行
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                if (gesturePassword == null || "".equals(gesturePassword)) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, GesturePwdLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
