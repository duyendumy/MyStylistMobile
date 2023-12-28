package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;

import java.util.List;

public class ScrollOutfitClosetAdapter extends RecyclerView.Adapter<ScrollOutfitClosetHolder>{

    private Context context;
    private List<UserOutfit> userOutfits;

    public ScrollOutfitClosetAdapter(Context context, List<UserOutfit> userOutfits) {
        this.context = context;
        this.userOutfits = userOutfits;
    }


    @NonNull
    @Override
    public ScrollOutfitClosetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scroll_outfit_item, parent, false);
        return new ScrollOutfitClosetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollOutfitClosetHolder holder, int position) {
        UserOutfit userOutfit = userOutfits.get(position);
        List<UserItem> userItems = userOutfit.getUserItems();
        for(int i = 0; i < userItems.size(); i++){
            switch(i){
                case 0:
                    Glide.with(context)
                            .load(userItems.get(0).getImage())
                            .error(R.drawable.white)
                            .into(holder.imageView1);
                    break;
                case 1:
                    Glide.with(context)
                            .load(userItems.get(1).getImage())
                            .error(R.drawable.white)
                            .into(holder.imageView2);
                    break;
                case 2:
                    Glide.with(context)
                            .load(userItems.get(2).getImage())
                            .error(R.drawable.white)
                            .into(holder.imageView3);
                    break;
                case 3:
                    Glide.with(context)
                            .load(userItems.get(3).getImage())
                            .error(R.drawable.white)
                            .into(holder.imageView4);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return userOutfits.size();
    }
}
