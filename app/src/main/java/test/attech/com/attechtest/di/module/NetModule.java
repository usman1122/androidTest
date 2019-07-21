package test.attech.com.attechtest.di.module;

import android.content.Context;


import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;


@Module(includes = {ContextModule.class})
public class NetModule {

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024); //10MB of Network Cache
    }

    @Provides
    public File cacheFile( Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    public OkHttpClient okHttpClient(Cache cache, Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(context))
                .cache(cache)
                .build();
    }

}