package test.attech.com.attechtest;

import android.app.Application;


import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import test.attech.com.attechtest.di.component.DaggerNetComponent;
import test.attech.com.attechtest.di.component.NetComponent;
import test.attech.com.attechtest.di.module.ContextModule;
import test.attech.com.attechtest.di.module.GlobalModule;
import test.attech.com.attechtest.di.module.NetModule;

public class testAtTech extends Application {

    private NetComponent mMobilogxNetComponent;
        String url = "https://api.themoviedb.org/";

        @Override
        public void onCreate() {
            super.onCreate();

            mMobilogxNetComponent = DaggerNetComponent.builder()
                    .GlobalModule(new GlobalModule(url))
                    .NetModule(new NetModule())
                    .ContextModule(new ContextModule(this))
                    .build();

            ViewPump.init(ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setDefaultFontPath("mosterrat_regular.ttf")
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build());

        }
        public NetComponent getNetComponent() {
            return mMobilogxNetComponent;
        }
        }