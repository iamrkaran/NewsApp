package com.clutchx.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Exampleviewholder> {
    public List<newsdata>mExamplelist;
    public Context mContext;
    public static class Exampleviewholder extends RecyclerView.ViewHolder{
        public ImageView newsimage;
        public TextView tv_headline;
        public TextView tv_date;
        public TextView tv_time;
        public LinearLayout itemclick;

        public Exampleviewholder(@NonNull View itemView) {
            super(itemView);
            newsimage=itemView.findViewById(R.id.news_list_img);
            tv_headline=itemView.findViewById(R.id.news_head_tv);
            tv_date=itemView.findViewById(R.id.news_date_tv);
            tv_time=itemView.findViewById(R.id.news_time_tv);
            itemclick=itemView.findViewById(R.id.nextactivity);

        }
    }
    public NewsAdapter(Context context,List<newsdata> examplelist){
        mContext=context;
        mExamplelist=examplelist;
    }
    @NonNull
    @Override
    public Exampleviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_new_list,viewGroup,false);
        Exampleviewholder exampleviewholder= new Exampleviewholder(view);
        return exampleviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Exampleviewholder exampleviewholder, int i) {
        final newsdata currentitem=mExamplelist.get(i);
        exampleviewholder.tv_headline.setText(currentitem.getHeadline());
        exampleviewholder.tv_time.setText(currentitem.getTime());
        exampleviewholder.tv_date.setText(currentitem.getDate());
        Picasso.get().load(currentitem.getImgurl()).into(exampleviewholder.newsimage);
        exampleviewholder.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, FullScreenNews.class);
                i.putExtra("head",currentitem.getHeadline());
                i.putExtra("desc",currentitem.getDescription());
                i.putExtra("time",currentitem.getTime());
                i.putExtra("date",currentitem.getDate());
                i.putExtra("img",currentitem.getImgurl());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mExamplelist.size();
    }
}
