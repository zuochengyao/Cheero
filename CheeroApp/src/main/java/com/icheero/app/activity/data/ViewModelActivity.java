package com.icheero.app.activity.data;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;

import com.icheero.app.R;
import com.icheero.app.databinding.ActivityViewModelBinding;
import com.icheero.app.model.User;

import java.lang.ref.WeakReference;

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
    private WeakHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        mHandler = new WeakHandler(this);
        mUser = new User();
        mUser.setAge(10);
        mUser.setName("Cheero");
        mUser.setPhoneNumber("18513141213");
        binding.setUser(mUser);
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class WeakHandler extends Handler
    {
        private final WeakReference<ViewModelActivity> mActivity;

        private WeakHandler(ViewModelActivity activity)
        {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                {
                    mActivity.get().mUser.setAge(20);
                    mActivity.get().mUser.setName("Zero");
                    mActivity.get().binding.setUser(mActivity.get().mUser);
                    break;
                }
            }
        }
    }
}
