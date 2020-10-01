package com.example.eldassesment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eldassesment.R;
import com.example.eldassesment.helper.Image;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    public List<Image> mImageList;
    private Context context;


    public RecyclerViewAdapter(List<Image> itemList) {

        mImageList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return mImageList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void updateDataSet(List<Image> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtSizeItem, txtDateItem, txtTimeItem;
        ImageView imgViewThumbItem, imgViewStatusItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewThumbItem = itemView.findViewById(R.id.imgViewThumbItem);
            imgViewStatusItem = itemView.findViewById(R.id.imgViewStatusItem);
            txtSizeItem = itemView.findViewById(R.id.txtSizeItem);
            txtDateItem = itemView.findViewById(R.id.txtDateItem);
            txtTimeItem = itemView.findViewById(R.id.txtTimeItem);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        int statusIcon;
        Image image = mImageList.get(position);

        //Setting Image thumbnail
        Glide.with(context).load(image.getThumbnail()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgViewThumbItem);

        String status = image.getStatus();
        if (status.equals("STATUS_UPLOADED")) {
            statusIcon = R.drawable.status_uploaded;
        } else if (status.equals("STATUS_NONE")) {
            statusIcon = R.drawable.status_none;
        } else {
            statusIcon = R.drawable.status_downloaded;
        }
        viewHolder.imgViewStatusItem.setImageResource(statusIcon);

        String dataTime = image.getDateTime();
        //Split Data-Time string
        String[] dateTimeSplit = dataTime.split("T");
        viewHolder.txtDateItem.setText(dateTimeSplit[0]);
        viewHolder.txtTimeItem.setText(dateTimeSplit[1]);

        viewHolder.txtSizeItem.setText(image.getFileSize());
    }

}
