package com.example.android.restukuatno_1202150015_modul6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 01-Apr-18.
 */

public class adapter1 extends RecyclerView.Adapter<adapter1.CommentViewHolder> {
    Context context;
    List<database1> list;

    //konstruktor dari adapterc
    public adapter1(Context context, List<database1> list){
        this.context=context;
        this.list=list;
    }

    //return viewholder dari recyclerview
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.coment, parent, false));
    }

    //mengikat nilai dari list dengan view
    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        database1 crnt = list.get(position);
        //set textview commenter dengan nilai yang didapatatkan
        holder.commenter.setText(crnt.getCommenters());
        //set textview comments dengan nilai yang didapatkan
        holder.comments.setText(crnt.getComments());
    }


    //mendapatkan jumlah item pada recyclernya
    @Override
    public int getItemCount() {
        return list.size();
    }


    //class yang akan dipanggil dan dijalankan sebagai viewholder
    class CommentViewHolder extends RecyclerView.ViewHolder{
        //deklarasi variable yang digunakan
        TextView commenter, comments;
        public CommentViewHolder(View itemView){
            super(itemView);
            //mengakses id textview pada layout
            commenter = itemView.findViewById(R.id.commenter);
            comments = itemView.findViewById(R.id.comments);
        }
    }
}

