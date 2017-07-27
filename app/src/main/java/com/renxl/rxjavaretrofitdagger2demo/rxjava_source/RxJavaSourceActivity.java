package com.renxl.rxjavaretrofitdagger2demo.rxjava_source;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.renxl.rxjavaretrofitdagger2demo.R;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Observable;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.OnSubscrible;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Subscrible;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.transform.Func;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/7/11 14:19.
 */

public class RxJavaSourceActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaSourceActivity";
    @BindView(R.id.btn_do)
    Button mBtnDo;
    @BindView(R.id.btn_transform)
    Button mBtnTransform;
    @BindView(R.id.img)
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_source);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_do)
    public void onViewClicked() {

        Observable.create(new OnSubscrible<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                subscrible.onNext("看电影");
            }
        }).subscribe(new Subscrible<String>() {
            @Override
            public void onNext(String s) {
                Log.i("renxl", "问题:" + s);
                Log.i("renxl", "回答：好");
            }
        });

    }

    @OnClick(R.id.btn_transform)
    public void onTransform() {
        Observable.create(new OnSubscrible<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                subscrible.onNext("来，给哥哥看图片");
            }
        }).map(new Func<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                Log.i(TAG, "被观察者说: " + s);
                return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            }
        }).subscribe(new Subscrible<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                if (bitmap != null)
                    mImg.setImageBitmap(bitmap);

                Log.i(TAG, "转换后的观察者说: 我来给你看图片");
            }
        });
    }

    @OnClick(R.id.btn_scheduler)
    public void onScheduler() {
        Observable.create(new OnSubscrible<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                subscrible.onNext("线程切换");
                Log.i(TAG, "call" + Thread.currentThread().getName());
            }
        }).map(new Func<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                Log.i(TAG, "map" + Thread.currentThread().getName());
                return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            }
        }).subscribleOn()
                .observeOn()
                .subscribe(new Subscrible<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImg.setImageBitmap(bitmap);
                        Log.i(TAG, "onNext" + Thread.currentThread().getName());
                    }
                });
    }
}
