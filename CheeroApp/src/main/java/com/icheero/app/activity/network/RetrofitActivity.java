package com.icheero.app.activity.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.icheero.app.R;
import com.icheero.app.model.Course;
import com.icheero.app.model.Student;
import com.icheero.sdk.core.network.retrofit.RetrofitManager;
import com.icheero.sdk.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
public class RetrofitActivity extends Activity
{
    private static final Class TAG = RetrofitActivity.class;

    @BindView(R.id.image_retrofit)
    ImageView imageRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        RetrofitManager.getInstance().callGetResult();
    }

    // region Retrofit demo

    private void testRetrofit()
    {
        RetrofitManager.getInstance().callGetResult();
    }

    @SuppressLint("CheckResult")
    private void testFlatMap()
    {
        Student[] students = new Student[2];
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("数学"));
        courses.add(new Course("语文"));
        courses.add(new Course("英语"));
        students[0] = new Student("Zero", courses);
        students[1] = new Student("Cheero", courses);
        Observable.fromArray(students)
        .flatMap((Function<Student, ObservableSource<Course>>) student -> Observable.fromIterable(student.getCourses()).delay(1, TimeUnit.SECONDS))
        .subscribe(course -> Log.d(TAG, "Course: " + course.getName()));
    }

    private void testMap()
    {
        Observable.just(R.drawable.icon_telo).subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.io()).map(resourceId -> {
            Log.d(TAG, "map in, Thread-ID: " + Thread.currentThread().getId());
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return getTheme().getDrawable(resourceId);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Drawable>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {
                Log.d(TAG, "onSubscribe in, Thread-ID: " + Thread.currentThread().getId());
            }

            @Override
            public void onNext(Drawable drawable)
            {
                Log.d(TAG, "onNext in, Thread-ID: " + Thread.currentThread().getId());
                imageRetrofit.setImageDrawable(drawable);
            }

            @Override
            public void onError(Throwable e)
            {
                Log.d(TAG, "onError in, Thread-ID: " + Thread.currentThread().getId());
                Toast.makeText(RetrofitActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete()
            {
                Log.d(TAG, "onComplete in, Thread-ID: " + Thread.currentThread().getId());
                Toast.makeText(RetrofitActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testCreate()
    {
        int resourceId = R.drawable.me;
        Observable.create((ObservableOnSubscribe<Drawable>) emitter -> {
            Log.d(TAG, "create in, Thread-ID: " + Thread.currentThread().getId());
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            Drawable drawable = getTheme().getDrawable(resourceId);
            emitter.onNext(drawable);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()) /* 事件产生线程：Observable.OnSubscribe 被激活时所处的线程 */.observeOn(AndroidSchedulers.mainThread()) /* 事件消费线程：指定 Subscriber 所运行在的线程 */.subscribe(new Observer<Drawable>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {
                Log.d(TAG, "onSubscribe in, Thread-ID: " + Thread.currentThread().getId());
            }

            @Override
            public void onNext(Drawable drawable)
            {
                Log.d(TAG, "onNext in, Thread-ID: " + Thread.currentThread().getId());
                imageRetrofit.setImageDrawable(drawable);
            }

            @Override
            public void onError(Throwable e)
            {
                Log.d(TAG, "onError in, Thread-ID: " + Thread.currentThread().getId());
                Toast.makeText(RetrofitActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete()
            {
                Log.d(TAG, "onComplete in, Thread-ID: " + Thread.currentThread().getId());
                Toast.makeText(RetrofitActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testFromArray()
    {
        String[] names = {"Zero", "Cheero", "Yao"};
        Observable.fromArray(names).subscribe(observer);
    }

    // region Retrofit Class

    private Observer<String> observer = new Observer<String>()
    {
        @Override
        public void onSubscribe(Disposable d)
        {
            Log.d(TAG, "observer: onSubscribe in");
        }

        @Override
        public void onNext(String s)
        {
            Log.d(TAG, "observer: onNext in. name = " + s);
        }

        @Override
        public void onError(Throwable e)
        {
            Log.d(TAG, "observer: onError in");
        }

        @Override
        public void onComplete()
        {
            Log.d(TAG, "observer: onComplete in");
        }
    };

    private Subscriber<String> subscriber = new Subscriber<String>()
    {
        @Override
        public void onSubscribe(Subscription s)
        {
            Log.d(TAG, "Subscriber: onSubscribe in");
        }

        @Override
        public void onNext(String s)
        {
            Log.d(TAG, "Subscriber: onNext in");
        }

        @Override
        public void onError(Throwable t)
        {
            Log.d(TAG, "Subscriber: onError in");
        }

        @Override
        public void onComplete()
        {
            Log.d(TAG, "Subscriber: onComplete in");
        }
    };

    Observable observable = Observable.create(emitter -> {
        emitter.onNext("Hello");
        emitter.onNext("RxJava");
        emitter.onNext("RxAndroid");
        emitter.onComplete();
    });

    // 相当于上面的observable对象方法
    Observable just = Observable.just("Hello", "RxJava", "RxAndroid");

    // endregion
}
