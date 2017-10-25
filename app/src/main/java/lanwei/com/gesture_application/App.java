package lanwei.com.gesture_application;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/24.
 */

public class App extends Application {


    public ArrayList<Activity> activities;
    @Override
    public void onCreate() {
        super.onCreate();
        activities = new ArrayList();
    }

}
