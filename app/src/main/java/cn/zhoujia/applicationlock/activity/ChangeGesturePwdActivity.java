package cn.zhoujia.applicationlock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.star.lockpattern.util.LockPatternUtil;
import com.star.lockpattern.widget.LockPatternView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhoujia.applicationlock.R;
import cn.zhoujia.applicationlock.util.cache.ACache;
import cn.zhoujia.applicationlock.util.constant.Constant;

/**
 * Created by Zhoujia on 2016/5/26.
 */
public class ChangeGesturePwdActivity extends Activity {

    @Bind(R.id.messageTv)
    TextView messageTv;
    @Bind(R.id.lockPatternView)
    LockPatternView lockPatternView;

    private ACache aCache;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_gesturepwd);

        ButterKnife.bind(this);
        this.init();
    }

    private void init() {
        aCache = ACache.get(ChangeGesturePwdActivity.this);
        //得到当前用户的手势密码
        gesturePassword = aCache.getAsBinary(Constant.GESTURE_PASSWORD);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        Intent intent = new Intent(ChangeGesturePwdActivity.this, CreateGesturePwdActivity.class);
        startActivity(intent);
        aCache.clear();
        ChangeGesturePwdActivity.this.finish();
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
