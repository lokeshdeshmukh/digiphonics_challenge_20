/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import db.DBHelper;
import structures.Name_Master;

import static com.example.android.materialdesigncodelab.R.drawable.e;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {

    public static  ArrayList<Name_Master> name_masters1 = new ArrayList<>();
    public static ContentAdapter adapter;
    Context context;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        context = container.getContext();
        DBHelper dbHelper = new DBHelper(context);
        try{
            name_masters1 = dbHelper.get_Data_Name_Master("Select * from Name_Master desc");

        }catch (Exception e){
            Log.e("Digi Name Master: ",e.getMessage());
        }
        adapter = new ContentAdapter(recyclerView.getContext(),name_masters1);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();



        return recyclerView;
    }

    public void setUpAdapter(){
        //needs citation

        adapter.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChatBox.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Name_Master",name_masters1.get(getAdapterPosition()));
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
       // private static final int LENGTH = 18;


        private ArrayList<Name_Master> name_masters = new ArrayList<>();
        public ContentAdapter(Context context, ArrayList<Name_Master> name_masters) {
            this.name_masters = name_masters;
            Resources resources = context.getResources();

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Name_Master name_master = name_masters.get(position);
            if(name_master!=null && name_master.getGENDER()!=null){
                if(name_master.getGENDER().equalsIgnoreCase("Male")){
                    holder.avator.setImageResource(R.drawable.man);
                } else {
                    holder.avator.setImageResource(R.drawable.girl);
                }
            }

            if(name_master.getNAME()!=null){
                holder.name.setText(name_master.getNAME());
            } else {
                holder.name.setText("");
            }

            if(name_master.getMOBILE_NUMBER()!=null){
                holder.description.setText(name_master.getMOBILE_NUMBER());
            } else {
                holder.description.setText("");

            }

        }

        @Override
        public int getItemCount() {
            return name_masters.size();
        }
    }

}
