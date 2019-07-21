package test.attech.com.attechtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skyhope.showmoretextview.ShowMoreTextView;

import test.attech.com.attechtest.R;
import test.attech.com.attechtest.model.MyPojo;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MyPojo> modelList;
    RequestOptions requestOptions;
    private OnItemClickListener mItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    String posterP = "https://image.tmdb.org/t/p/w200/";

    public RecyclerViewAdapter(Context context, ArrayList<MyPojo> modelList) {
        this.mContext = context;
        this.modelList = modelList;
        requestOptions = RequestOptions.placeholderOf(R.drawable.noimage).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop();

    }

    public void updateList(ArrayList<MyPojo> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final MyPojo model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getReleaseDate());
            genericViewHolder.itemTxtSynopsis.setText(model.getOverview());
            Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(posterP+model.getPosterPath()).into(genericViewHolder.imgUser);

        }
    }
    private MyPojo getItem(int position) {
        return modelList.get(position);
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    //Methods to access onClicks

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnLongItemClickListener(final OnLongItemClickListener longItemClickListener) {
        this.onLongItemClickListener = longItemClickListener;
    }




    //In defined click Listener interfaces

    public interface OnItemClickListener {
        void onItemClick(View view, int position, MyPojo model);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, int position, MyPojo model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;
        private ShowMoreTextView itemTxtSynopsis;



        public ViewHolder(final View itemView) {
            super(itemView);

            this.imgUser = itemView.findViewById(R.id.img_user);
            this.itemTxtTitle = itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage = itemView.findViewById(R.id.item_txt_date);
            this.itemTxtSynopsis = itemView.findViewById(R.id.item_txt_synopsis);

            //ShowmoreTExview Library functions
            itemTxtSynopsis.addShowMoreText("Continue");
            itemTxtSynopsis.setShowMoreColor(mContext.getResources().getColor(R.color.green_lght));
            itemTxtSynopsis.setShowLessTextColor(mContext.getResources().getColor(R.color.orange));
            itemTxtSynopsis.addShowLessText("Less");
            itemTxtSynopsis.setShowingLine(2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongItemClickListener.onLongItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                    return false;
                }

            });


        }
    }

}

