package com.kil.mychatroom.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kil.mychatroom.ModelView.UserListModel;
import com.kil.mychatroom.R;
import com.kil.mychatroom.databinding.UserListBinding;

import java.util.List;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.myViewHolder> {
    List<UserListModel> userListModels;
    LayoutInflater layoutInflater;
    String[] emails;
    public AdapterUserList(List<UserListModel> userListModels){
        this.userListModels=userListModels;
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        private UserListBinding newsBinding;
        public myViewHolder(UserListBinding newsBinding) {
            super(newsBinding.getRoot());
            this.newsBinding=newsBinding;
        }

        public void bind(UserListModel userListModel){
            this.newsBinding.setListUser(userListModel);
        }
        public UserListBinding getUserBinding(){
                return newsBinding;
        }
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_userlist,parent,false);
//
//        return new myViewHolder(view);
        if (layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        UserListBinding newsBinding=UserListBinding.inflate(layoutInflater,parent,false);
        return new myViewHolder(newsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      //  holder.textView.setText(emails[position]);
        UserListModel userListModel=userListModels.get(position);
        holder.bind(userListModel);
    }

    @Override
    public int getItemCount() {
        return userListModels.size();
    }


    public UserListModel getUser(int position) {
        return userListModels.get(position);
    }

}
