package com.icheero.app.activity.data;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.app.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewModelActivity extends AppCompatActivity
{
    @BindView(R.id.text_user_name)
    TextView tvUserName;
    @BindView(R.id.text_user_age)
    TextView tvUserAge;
    @BindView(R.id.text_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.button_user_update)
    Button btnUserUpdate;

    private User mUser;
    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        ButterKnife.bind(this);
        mUser = new User();
        mUser.setAge(10);
        mUser.setName("Cheero");
        mUser.setPhoneNumber("18513141213");
    }

    @OnClick(R.id.button_user_update)
    void updateUser()
    {
        mUser.setAge(11);
        mUser.setName("Zero");
        mUser.setPhoneNumber("18513141213=====");
    }

}
