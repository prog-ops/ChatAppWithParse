package com.chat.prs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapterRv extends RecyclerView.Adapter<ChatAdapterRv.VH> {
    private ArrayList<Conversation> conversations;

    class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;// = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    @Override
    public long getItemId(int position) {
        //* LONG TO INT
        long l = position;
        return l;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
