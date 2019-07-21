package test.attech.com.attechtest.di.component;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Component;
import test.attech.com.attechtest.di.module.GlobalModule;
import test.attech.com.attechtest.di.module.NetModule;
import test.attech.com.attechtest.network.RetrofitGlobalInterface;
import test.attech.com.attechtest.ui.MainActvty;

@Singleton
@Component(modules = {GlobalModule.class, NetModule.class})
public interface NetComponent {

    void injectMainActivity(MainActvty mobilogxMainActivity);


    Context context();

    RetrofitGlobalInterface getGlobalInterface();

}