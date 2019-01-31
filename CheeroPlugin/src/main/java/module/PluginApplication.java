package module;

import com.icheero.plugin.framework.andfix.AndFixPatchManager;
import com.icheero.sdk.base.BaseApplication;

public class PluginApplication extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        AndFixPatchManager.getInstance().init(this);
    }
}
