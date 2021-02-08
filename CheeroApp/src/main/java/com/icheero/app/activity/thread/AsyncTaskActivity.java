package com.icheero.app.activity.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AsyncTaskActivity extends BaseActivity
{
    @BindView(R.id.task_add)
    Button mTaskAdd;
    @BindView(R.id.task_execute)
    Button mTaskExecute;

    private MyAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);
        mAsyncTask = new MyAsyncTask();
    }

    @OnClick({R.id.task_add, R.id.task_execute})
    public void OnTaskClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.task_add:
                MyAsyncTask.execute(new Task());
                break;
            case R.id.task_execute:
                MyAsyncTask.execute(new Task());
                MyAsyncTask.execute(new Task());
                MyAsyncTask.execute(new Task());
                MyAsyncTask.execute(new Task());
                mAsyncTask.execute();
                break;
        }
    }

    private class Task implements Runnable
    {
        @Override
        public void run()
        {
            Log.i(TAG, "Task name: " + this);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings)
        {
            Log.i(TAG, "doInBackground");
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Log.i(TAG, "onPostExecute");
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
    }
}
