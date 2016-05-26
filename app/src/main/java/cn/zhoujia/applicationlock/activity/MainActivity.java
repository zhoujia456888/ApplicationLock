package cn.zhoujia.applicationlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhoujia.applicationlock.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_creat_gesturepwd)
    Button btnCreatGesturepwd;
    @Bind(R.id.btn_clean_gesturepwd)
    Button btnCleanGesturepwd;
    @Bind(R.id.btn_change_gesturepwd)
    Button btnChangeGesturepwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_creat_gesturepwd, R.id.btn_clean_gesturepwd, R.id.btn_change_gesturepwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_creat_gesturepwd:
                Intent intent = new Intent(MainActivity.this, CreateGesturePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_clean_gesturepwd:
                Intent intent2 = new Intent(MainActivity.this, CleanGesturePwdActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_change_gesturepwd:
                Intent intent3 = new Intent(MainActivity.this, ChangeGesturePwdActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
