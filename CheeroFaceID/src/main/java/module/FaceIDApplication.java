package module;

import com.icheero.faceid.manager.FaceIDManager;
import com.icheero.sdk.base.BaseApplication;

public class FaceIDApplication extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        FaceIDManager.getInstance();
    }
}
