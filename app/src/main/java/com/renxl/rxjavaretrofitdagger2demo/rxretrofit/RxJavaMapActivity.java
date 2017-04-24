package com.renxl.rxjavaretrofitdagger2demo.rxretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.renxl.rxjavaretrofitdagger2demo.R;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.Advertising;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by renxl
 * On 2017/4/12 14:35.
 */

public class RxJavaMapActivity extends AppCompatActivity {

    @BindView(R.id.btn_rxmap)
    Button btnRxmap;
    @BindView(R.id.tv_rx_map)
    TextView tvRxMap;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjavamap_activity);
        ButterKnife.bind(this);
        disposable = new CompositeDisposable();
    }

    @OnClick(R.id.btn_rxmap)
    public void onViewClicked() {
        RxRetrofitService service = RxRetrofitClient.getRxRetrofitService();
        doFlateMapService(service);
    }

    private void doFlateMapService(final RxRetrofitService service) {
        service.getAdvertisings(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        tvRxMap.setText("请求失败");
                    }
                })
                .doOnNext(new Consumer<List<Advertising>>() {
                    @Override
                    public void accept(List<Advertising> advertisings) throws Exception {
                        if (advertisings == null || advertisings.size() <= 0) return;
                        Log.i("renxl", "doOnNextaccept--" + advertisings.get(0).toString());
                    }
                })
                .map(new Function<List<Advertising>, Integer>() {
                    @Override
                    public Integer apply(List<Advertising> advertisings) throws Exception {
                        Log.i("renxl", "map--" + Thread.currentThread().getName());
                        return 1;
                    }
                })
                .observeOn(Schedulers.io()) // flatMap 时需要将现场执行任务的线程重新设置，要不就会重新按照哪个线程调用哪个线程执行
                .flatMap(new Function<Integer, ObservableSource<List<Advertising>>>() {
                    @Override
                    public ObservableSource<List<Advertising>> apply(Integer i) throws Exception {
                        Log.i("renxl", "concatMap--" + Thread.currentThread().getName());
                        return service.getAdvertising(i);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Advertising>>() {
                    @Override
                    public void accept(List<Advertising> advertisings) throws Exception {
                        Log.i("renxl", "concatMap--" + Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("renxl", "Throwable--" + Thread.currentThread().getName());
                    }
                });

    }

    private void doFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("renxl", "doOnNextaccept--" + integer);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("renxl", "doOnErroraccept" + throwable.getMessage());
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> lists = new ArrayList();
                        for (int i = 0; i < 3; i++) {
                            lists.add(integer + "--String--" + i);
                        }
                        return Observable.fromIterable(lists).delay(10, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        if (!TextUtils.isEmpty(str))
                            Log.i("renxl", str);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void doMap(RxRetrofitService service) {
        service.getAdvertisings(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Advertising>, String>() {
                    @Override
                    public String apply(List<Advertising> advertisings) throws Exception {
                        if (advertisings == null || advertisings.size() == 0) return "数据为空";
                        return advertisings.get(0).toString();
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(String value) {
                tvRxMap.setText(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("renxl", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
