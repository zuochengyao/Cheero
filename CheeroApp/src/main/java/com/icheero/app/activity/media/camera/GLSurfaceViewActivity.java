package com.icheero.app.activity.media.camera;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GLSurfaceViewActivity extends BaseActivity
{
    @BindView(R.id.gl_surface_view)
    GLSurfaceView mGLSurfaceView; // 用于渲染OpenGl

    private GLSurfaceRenderer mGLSurfaceRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glsurface_view);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        mGLSurfaceRenderer = new GLSurfaceRenderer();
        // Create an OpenGL ES 2.0 context
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(mGLSurfaceRenderer);
    }

    /**
     * GLSurfaceView.Renderer
     *
     * 该接口定义了用于绘制在图形所需的方法GLSurfaceView
     * 必须提供这个接口作为一个单独的类的实现，并将其赋值给setRenderer
     *
     */
    private class GLSurfaceRenderer implements GLSurfaceView.Renderer
    {
        /**
         * 系统调用一次该方法，用于创建GLSurfaceView
         * 使用此方法来执行只需要发生一次的操作，比如设置OpenGL的环境参数或初始化的OpenGL图形对象
         */
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            // Set the bg frame color
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }

        /**
         * 在GLSurfaceView发生改变时，系统回调方法
         */
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            GLES20.glViewport(0, 0, width, height);
        }

        /**
         * 系统调用该方法用于绘制或重新绘制图形。
         */
        @Override
        public void onDrawFrame(GL10 gl)
        {
            // Redraw bg color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }
    }
}
