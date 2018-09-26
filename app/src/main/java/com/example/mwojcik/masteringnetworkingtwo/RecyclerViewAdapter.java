package com.example.mwojcik.masteringnetworkingtwo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<SimpleItem> dataList;
    private Context mContext;

    //customowy listener
    private OnItemClickListener mListener;

    //tworzymy customowego listenera
    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
    //Zebysmy mogli go ustawic sobie w MainACtivity
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public RecyclerViewAdapter(List<SimpleItem> dataList, Context context) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view1 = layoutInflater.inflate(R.layout.row_item, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /***
     * NIGDY NIE USTAWIAC TUTAJ ON CLICK LISTENERA WBUDOWANEGO! NIGDY! ROBI SIE TO W VIEW HOLDERZE
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleItem simpleItem = dataList.get(position);

        //Jedna z mozliwosci:
        holder.itemView.setTag(simpleItem);

        holder.authorTextView.setText(simpleItem.getmAuthor());
        holder.likesTextView.setText(String.valueOf(simpleItem.getmLikes()));

        Picasso.with(mContext).load(simpleItem.getmImageUrl())
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView authorTextView;
        TextView likesTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_view);
            authorTextView = itemView.findViewById(R.id.item_text_view_author);
            likesTextView = itemView.findViewById(R.id.item_text_view_likes);
            itemView.setTag(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleItem item = (SimpleItem) view.getTag();
                    if (mListener != null){
                        if (getAdapterPosition() != RecyclerView.NO_POSITION){
                            Log.d("RecyclerViewAdapter", "item clicked: " + item.getmAuthor());
                            mListener.onItemClicked(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

}
