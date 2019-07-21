package test.attech.com.attechtest.di.module;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.attech.com.attechtest.network.RetrofitGlobalInterface;

import static android.content.Context.CONNECTIVITY_SERVICE;

@Module(includes = {ContextModule.class, NetModule.class})
public class GlobalModule {

    private String base_Url;

    public GlobalModule(String base_Url) {
        this.base_Url = base_Url;
    }

    @Provides
    public RetrofitGlobalInterface global_interface(Retrofit retrofitBuilder) {
        return retrofitBuilder.create(RetrofitGlobalInterface.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(base_Url)
                .build();
    }

    @Provides
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo hello = null;
        if (cm != null) {
            hello = cm.getActiveNetworkInfo();
        }
        return hello != null && hello.isConnectedOrConnecting();
    }
}