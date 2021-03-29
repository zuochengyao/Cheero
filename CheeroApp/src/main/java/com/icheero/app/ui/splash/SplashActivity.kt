package com.icheero.app.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import com.icheero.app.BR
import com.icheero.app.R
import com.icheero.app.activity.MainActivity
import com.icheero.app.databinding.ActivitySplashBinding
import com.icheero.sdk.base.ui.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun variableId(): Int = BR.app_splash

    override fun init() {
        super.init()
        timer = object : CountDownTimer(3400, 1000) {
            override fun onFinish() {
                openActivity(MainActivity::class.java)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                mBinding.splashSkip.text = getString(R.string.cheero_app_splash_skip).replace("%s", (millisUntilFinished / 1000).toString())
            }
        }
        timer.start()
    }
}