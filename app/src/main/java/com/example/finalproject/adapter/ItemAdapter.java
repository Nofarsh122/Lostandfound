package com.example.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    /// list of foods
    /// @see Food
    private final List<Item> itemList;

    public ItemAdapter() {
        itemList = new ArrayList<>();
    }

    public void addItems(@NonNull List<Item> items) {
        this.itemList.clear();
        this.itemList.addAll(items);

    }


    public List<Item> getItems() {
        return this.itemList;
    }

    /// create a view holder for the adapter
    /// @param parent the parent view group
    /// @param viewType the type of the view
    /// @return the view holder
    /// @see ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /// inflate the item_selected_food layout
        /// @see R.layout.item_selected_food
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ViewHolder(view);
    }

    /// bind the view holder with the data
    /// @param holder the view holder
    /// @param position the position of the item in the list
    /// @see ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = this.itemList.get(position);
        if (item == null) return;

        holder.itemNameTextView.setText(item.getName());
        holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(item.getImageBase64()));
        holder.itemQuantityTextView.setText(String.valueOf(ItemCountList.get(position).quantity));
    }

    /// get the number of items in the list
    /// @return the number of items in the list
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    /// View holder for the foods adapter
    /// @see RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView itemNameTextView;
        public final ImageView itemImageView;
        public final TextView itemQuantityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name_text_view);
            itemImageView = itemView.findViewById(R.id.item_image_view);
            itemQuantityTextView = itemView.findViewById(R.id.item_quantity_text_view);
        }
    }
}
