package com.icheero.app.activity.framework;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableDefer;

@SuppressLint("CheckResult")
public class RxJavaActivity extends BaseActivity
{
    @BindView(R.id.rx_create_subscribe)
    Button rxCreateSubscribe;
    @BindView(R.id.rx_create_just)
    Button rxCreateJust;
    @BindView(R.id.rx_create_from_array)
    Button rxCreateFromArray;
    @BindView(R.id.rx_create_empty_and_error)
    Button rxCreateEmpty;
    @BindView(R.id.rx_create_from_iterable)
    Button rxCreateFromIterable;
    @BindView(R.id.rx_create_timer)
    Button rxCreateTimer;
    @BindView(R.id.rx_create_interval)
    Button rxCreateInterval;
    @BindView(R.id.rx_create_interval_range)
    Button rxCreateIntervalRange;
    @BindView(R.id.rx_create_range_and_long)
    Button rxCreateRangeAndLong;
    @BindView(R.id.rx_create_defer)
    Button rxCreateDefer;

    @BindView(R.id.rx_filter)
    Button rxFilter;
    @BindView(R.id.rx_filter_take)
    Button rxFilterTake;
    @BindView(R.id.rx_first_and_last)
    Button rxFilterFirstAndLast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
    }

    // region 创建操作符

    /**
     * 测试RxJava中各种 创建操作符
     *
     * 使用Observable:
     *      不超过1000个元素、随着时间流逝基本不会出现OOM
     *      GUI事件或者1000Hz频率以下的元素
     *      平台不支持Java Steam(Java8新特性)
     *      Observable开销比Flowable低
     *
     * 使用Flowable:
     *      超过10k+的元素(可以知道上限)
     *      读取硬盘操作（可以指定读取多少行
     *      通过JDBC读取数据库
     *      网络（流）IO操作
     *
     * @param v view
     */

    @OnClick({
            R.id.rx_create_subscribe, R.id.rx_create_just, R.id.rx_create_from_array, R.id.rx_create_empty_and_error, R.id.rx_create_from_iterable,
            R.id.rx_create_timer, R.id.rx_create_interval, R.id.rx_create_interval_range, R.id.rx_create_range_and_long, R.id.rx_create_defer
    })
    public void onRxCreateEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.rx_create_subscribe:
            {
                // create: 创建一个被观察者
                Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onComplete();
                }).subscribe(new Observer<Integer>() // 创建观察者
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        Log.i(TAG, "Observer onSubscribe");
                    }

                    @Override
                    public void onNext(Integer i)
                    {
                        Log.i(TAG, "Observer onNext:" + i);
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        Log.i(TAG, "Observer onError");
                    }

                    @Override
                    public void onComplete()
                    {
                        Log.i(TAG, "Observer onComplete");
                    }
                });
                break;
            }
            case R.id.rx_create_just:
            {
                // just 最多只能10个参数，并且超过两个会调用 fromArray
                Observable.just("1", 2, 3, 4, 5, 6, 7, 8, 9, 10).subscribe(str -> Log.i(TAG, str.toString()));

                break;
            }
            case R.id.rx_create_from_array:
            {
                Observable.fromArray("1", 2, 3, 4, 5, 6, 7, 8, 9, 10, 11).subscribe(str -> Log.i(TAG, str.toString()));
                break;
            }
            case R.id.rx_create_empty_and_error:
            {
                // empty: 只会回调 complete()
                Observable.empty().subscribe(obj -> Log.i(TAG, "next" + obj.toString()), e -> Log.i(TAG, "error"), () -> Log.i(TAG, "complete"));
                // error: 只会回调 error()
                Observable.error(new RuntimeException("test")).subscribe(obj -> Log.i(TAG, "next" + obj.toString()), e -> Log.i(TAG, "error"), () -> Log.i(TAG, "complete"));
                break;
            }
            case R.id.rx_create_from_iterable:
            {
                List<String> list = new ArrayList<>();
                list.add("Cheero");
                list.add("Flow");
                Observable.fromIterable(list).subscribe(str -> Log.i(TAG, str));
                break;
            }
            case R.id.rx_create_timer:
            {
                Observable.timer(1, TimeUnit.SECONDS).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_create_interval:
            {
                // interval: 无法停止，需要take操作
                // take: 控制观察者接收的事件的数量
                Observable.interval(1, 1, TimeUnit.SECONDS)
                        .filter(l -> l >= 2)
                        .take(10)
                        .subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_create_interval_range:
            {
                Observable.intervalRange(0, 10, 1, 1, TimeUnit.SECONDS).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_create_range_and_long:
            {
                Observable.range(0, 5).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                Observable.rangeLong(Integer.MAX_VALUE, 5L).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_create_defer:
            {
                Observable<Object> observable = Observable.defer(() -> ObservableDefer.just("1", "2", 3));
                //订阅第一个观察者
                observable.subscribe(obj -> Log.i(TAG, String.valueOf(obj)));
                //订阅第二个观察者
                observable.subscribe(obj -> Log.i(TAG, String.valueOf(obj)));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    // endregion

    // region 过滤操作符

    @OnClick({R.id.rx_filter, R.id.rx_filter_take, R.id.rx_first_and_last})
    public void onRxFilterEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.rx_filter:
            {
                Observable.just(1, 2, 3).filter(integer -> integer >= 2).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_filter_take:
            {
                // 过滤5个元素
                Observable.interval(1, TimeUnit.SECONDS).take(5).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                Observable.interval(1, TimeUnit.SECONDS).takeLast(5).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                // 过滤5秒内的元素
                Observable.interval(1, TimeUnit.SECONDS).take(5, TimeUnit.SECONDS).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                Observable.interval(1, TimeUnit.SECONDS).takeLast(5, TimeUnit.SECONDS).subscribe(i -> Log.i(TAG, String.valueOf(i)));
                break;
            }
            case R.id.rx_first_and_last:
            {
                // 选取第一个元素(允许为空)
                Observable.just(1, 2, 3).firstElement().subscribe(element -> Log.i(TAG, String.valueOf(element)));
                // 选取最后一个元素(允许为空)
                Observable.just(1, 2, 3).lastElement().subscribe(element -> Log.i(TAG, String.valueOf(element)));

                Observable.empty().first(2) // 这里的2是默认元素，非第二个元素
                          .subscribe(ele -> Log.i(TAG, String.valueOf(ele)));

                Observable.empty().last(3) // 这里的3是默认发射元素，并非第三个元素
                          .subscribe(ele -> Log.i(TAG, String.valueOf(ele)));
                break;
            }
        }
    }

    // endregion
}
