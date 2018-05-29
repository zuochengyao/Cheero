package com.icheero.app.activity.data;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.icheero.app.R;
import com.icheero.app.databinding.ActivityViewModelBinding;
import com.icheero.app.model.User;

public class ViewModelActivity extends AppCompatActivity
{
    /*
    @BindView(R.id.text_user_name)
    TextView tvUserName;
    @BindView(R.id.text_user_age)
    TextView tvUserAge;
    @BindView(R.id.text_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.button_user_update)
    Button btnUserUpdate;
    */
    private User mUser;
    private ActivityViewModelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        mUser = new User();
        mUser.setAge(10);
        mUser.setName("Cheero");
        mUser.setPhoneNumber("18513141213");
        binding.setUser(mUser);
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                {
                    mUser.setAge(20);
                    mUser.setName("Zero");
                    binding.setUser(mUser);
                    break;
                }
            }
        }
    };
}
