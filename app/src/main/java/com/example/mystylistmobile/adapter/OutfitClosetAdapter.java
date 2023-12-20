package com.example.mystylistmobile.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mystylistmobile.R;
import com.example.mystylistmobile.model.User;
import com.example.mystylistmobile.model.UserItem;
import com.example.mystylistmobile.model.UserOutfit;

import java.util.List;

public class OutfitClosetAdapter extends BaseAdapter {
    private Context context;
    private List<UserOutfit> userOutfits;


    public OutfitClosetAdapter(Context context, List<UserOutfit> userOutfits) {
        this.userOutfits = userOutfits;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userOutfits.size();
    }

    @Override
    public Object getItem(int i) {
        return userOutfits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.outfit_item, parent, false);
        }

        UserOutfit userOutfit = (UserOutfit) getItem(position);
        List<UserItem> userItems = userOutfit.getUserItems();
        ImageView imageView1 = listItemView.findViewById(R.id.imageView1);
        ImageView imageView2 = listItemView.findViewById(R.id.imageView2);
        ImageView imageView3 = listItemView.findViewById(R.id.imageView3);
        ImageView imageView4 = listItemView.findViewById(R.id.imageView4);

        for(int i = 0; i < userItems.size(); i++){
            switch(i){
                case 0:
                    Glide.with(context)
                            .load(userItems.get(0).getImage())
                            .error(R.drawable.icon_app)
                            .into(imageView1);
                    break;
                case 1:
                    Glide.with(context)
                            .load(userItems.get(1).getImage())
                            .error(R.drawable.icon_app)
                            .into(imageView2);
                    break;
                case 2:
                    Glide.with(context)
                            .load(userItems.get(2).getImage())
                            .error(R.drawable.icon_app)
                            .into(imageView3);
                    break;
                case 3:
                    Glide.with(context)
                            .load(userItems.get(3).getImage())
                            .error(R.drawable.icon_app)
                            .into(imageView4);
                    break;
                default:
                    break;
            }

        }
        return listItemView;
    }
}
