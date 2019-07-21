package test.attech.com.attechtest.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;


import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.attech.com.attechtest.R;
import test.attech.com.attechtest.adapters.RecyclerViewAdapter;
import test.attech.com.attechtest.utils.RecyclerViewScrollListener;
import test.attech.com.attechtest.model.MyPojo;
import test.attech.com.attechtest.model.resultsModel;
import test.attech.com.attechtest.network.RetrofitGlobalInterface;
import test.attech.com.attechtest.testAtTech;


public class MainActvty extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialog;
    private AlertDialog alertPrivacy;
    @Inject
    Lazy<RetrofitGlobalInterface> globalInterface;

    private RecyclerViewAdapter mAdapter;
    private RecyclerViewScrollListener scrollListener;

    private ArrayList<MyPojo> modelList;

    String posterP = "https://image.tmdb.org/t/p/w200/";
    String key;
    private ProgressBar pvEndList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actvty);


        ((testAtTech) getApplication()).getNetComponent().injectMainActivity(MainActvty.this);
        modelList = new ArrayList<>();
        findViews();


        networkFetch();


    }


    private void findViews() {
        key = getResources().getString(R.string.headerKey);
        pvEndList = findViewById(R.id.pv_paging);
        alertDialog = new AlertDialog.Builder(MainActvty.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void networkFetch() {

        Call<resultsModel> userCall;
        userCall = globalInterface.get().createTask(1, key);
        userCall.enqueue(new Callback<resultsModel>() {
            @Override
            public void onResponse(Call<resultsModel> call, Response<resultsModel> response) {
                if (response.body() != null) {
                    modelList = response.body().getResults();
                    try {
                        mAdapter.updateList(modelList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (pvEndList.getVisibility() == View.VISIBLE) {
                    pvEndList.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<resultsModel> call, Throwable t) {

            }
        });
        setAdapter();
    }

    private void networkFetchMore() {

        Call<resultsModel> userCall;
        userCall = globalInterface.get().createTask(2, key);
        userCall.enqueue(new Callback<resultsModel>() {
            @Override
            public void onResponse(Call<resultsModel> call, Response<resultsModel> response) {
                if (response.body() != null) {
                    modelList.addAll(response.body().getResults());

                    try {
                        mAdapter.updateList(modelList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (pvEndList.getVisibility() == View.VISIBLE) {
                    pvEndList.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<resultsModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setAdapter() {


//        if (modelList.size() > 0) {
//            findViewById(R.id.txt_waiting).setVisibility(View.GONE);
//        } else {
//            findViewById(R.id.txt_waiting).setVisibility(View.VISIBLE);
//        }

        mAdapter = new RecyclerViewAdapter(MainActvty.this, modelList);

        recyclerView.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        scrollListener = new RecyclerViewScrollListener() {

            public void onEndOfScrollReached(RecyclerView rv) {
                pvEndList.setVisibility(View.VISIBLE);
                Toast.makeText(MainActvty.this, "End reached,fetching more data", Toast.LENGTH_SHORT).show();
                networkFetchMore();
                scrollListener.disableScrollListener();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, MyPojo model) {


            }
        });

        mAdapter.SetOnLongItemClickListener(new RecyclerViewAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position, MyPojo model) {
                privacyDialog(model);

            }
        });


    }

    private void privacyDialog(MyPojo model) {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        alertDialog.setView(view);
        ImageView imgLarg = view.findViewById(R.id.img_large);

        new Handler(getMainLooper()).post(() -> {
            RequestOptions requestOptions = RequestOptions.placeholderOf(R.drawable.noimage).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop();
            Glide.with(this).applyDefaultRequestOptions(requestOptions).load(posterP + model.getPosterPath()).into(imgLarg);

        });
        try {
            alertPrivacy = alertDialog.create();
            if (alertPrivacy.getWindow() != null) {
                alertPrivacy.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final TextView privacyButt = view.findViewById(R.id.privacyButton);
//        privacyButt.setTypeface(Typeface.createFromAsset(getAssets(), "font/droidsans_bold.ttf"));


        if (alertPrivacy != null && alertPrivacy.isShowing()) {
            alertPrivacy.dismiss();
        } else {
            alertPrivacy.show();
        }

        privacyButt.setOnClickListener(v -> alertPrivacy.dismiss());
    }


}
