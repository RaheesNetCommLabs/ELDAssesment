package com.example.eldassesment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.eldassesment.R;
import com.example.eldassesment.adapter.RecyclerViewAdapter;
import com.example.eldassesment.api.RetrofitClient;
import com.example.eldassesment.helper.Image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Image> imageList = new ArrayList<>();
    boolean isLoading = false;

    int mOffset = 0, mLimit = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        populateData(mOffset, mLimit);
        initScrollListener();
    }

    private void populateData(int offset, int limit) {
        Call<List<Image>> call = RetrofitClient.getInstance().getMyApi().getImage(offset, limit);
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                //In this point we got our Image list
                imageList = response.body();
                initAdapter();
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                //handle error or failure cases here
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new RecyclerViewAdapter(imageList);
            recyclerView.setAdapter(recyclerViewAdapter);
        }else {
            recyclerViewAdapter.updateDataSet(imageList);
        }
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == imageList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        imageList.add(null);
        recyclerViewAdapter.notifyItemInserted(imageList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageList.remove(imageList.size() - 1);
                int scrollPosition = imageList.size();
                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                //populateData(mOffset, mLimit);

                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }
}