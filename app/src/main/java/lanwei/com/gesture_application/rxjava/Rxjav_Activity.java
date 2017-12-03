package lanwei.com.gesture_application.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/12/3.
 */

public class Rxjav_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {


            }
        });

        Action1 action1=new Action1() {
            @Override
            public void call(Object o) {

            }
        };
    }




}
