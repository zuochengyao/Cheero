package com.icheero.sdk.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.icheero.sdk.core.manager.PermissionManager
import com.icheero.sdk.core.manager.PermissionManager.PermissionListener
import com.icheero.sdk.core.manager.ViewManager
import com.icheero.sdk.util.Log
import com.icheero.sdk.util.RefUtils
import java.util.*

abstract class BaseViewModelActivity<binding : ViewDataBinding, viewModel : BaseViewModel> :
        AppCompatActivity(), PermissionListener {

    @JvmField
    protected var mPermissionManager: PermissionManager? = null
    protected lateinit var TAG: Class<*>
    lateinit var binding: binding
    lateinit var viewModel: viewModel

    // region Activity's Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = javaClass
        Log.i(TAG, "onCreate")
        binding = DataBindingUtil.setContentView(this, layoutId())
        viewModel = viewModel()
        // binding.setVariable()
        mPermissionManager = PermissionManager(this)
        ViewManager.getInstance().addActivity(this)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewManager.getInstance().removeActivity(this)
        Log.i(TAG, "onDestroy")
    }

    // endregion

    // region abstract methods
    abstract fun layoutId():Int

    abstract fun viewModel():viewModel
    // endregion

    // region Extra Methods
    protected fun openActivity(activityClass: Class<out Activity?>?) {
        if (activityClass != null)
            openActivity(activityClass, null)
    }

    protected fun openActivity(activityClass: Class<out Activity?>?, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, activityClass!!)
        if (bundle != null && bundle.size() > 0)
            intent.putExtras(bundle)
        startActivity(intent)
    }

    protected val statusBarHeight: Int
        protected get() {
            val resId: Int
            var startBarHeight = 0
            try {
                val obj = RefUtils.getObjectDeclaredFieldValue("com.android.internal.R\$dimen", "status_bar_height")
                        ?: return 0
                resId = obj.toString().toInt()
                startBarHeight = resources.getDimensionPixelSize(resId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return startBarHeight
        }

    protected val navBarHeight: Int
        protected get() {
            val resId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resId)
        }

    protected val isPortrait: Boolean
        protected get() = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    // endregion

    // region 权限管理相关
    /**
     * 权限申请回调
     * @param permission 权限名称
     * @param isGranted 权限是否申请成功
     */
    override fun onPermissionRequest(isGranted: Boolean, permission: String) {
        Log.i(TAG, String.format("Permission:%s granted %s ", permission, isGranted))
    }

    /**
     * 多权限申请回调
     * @param permissions 权限名称列表
     * @param isGranted 所有权限是否申请成功
     */
    override fun onPermissionRequest(isGranted: Boolean, vararg permissions: String) {
        val permissionsStr = Arrays.asList(*permissions).toString()
        Log.i(TAG, String.format("Permission:%s granted %s ", permissionsStr, isGranted))
    }
    // endregion
}