package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.screens.ItemProfile;
import com.example.finalproject.utils.ImageUtil;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        private static class ItemCount {
           Item item;
            int quantity;
        }

        private final List<ItemCount> ItemCountList;

        Context context;

        public ItemAdapter(Context context) {
            ItemCountList = new ArrayList<>();
            this.context = context;
        }

        public void setItems(@NonNull List<Item> items) {
            this.ItemCountList.clear();
            this.addItems(items);
        }

        public void addItems(@NonNull List<Item> items) {
            for (Item item : items) {
                addItem(item);
            }
        }

        public void addItem(@Nullable Item f) {
            if (f == null) return;
            for (int i = 0; i < ItemCountList.size(); i++) {
                ItemCount itemCount = ItemCountList.get(i);
                if (itemCount.item.getId().equals(f.getId())) {
                    itemCount.quantity++;
                    notifyItemChanged(i);
                    return;
                }
            }
            ItemCountList.add(new ItemCount() {{
                this.item = new Item(f);
                this.quantity = 1;
            }});
            /// notify the adapter that the data has changed
            /// this specifies that the item at selectedFoods.size() - 1 has been inserted
            /// and the adapter should update the view
            /// @see FoodsAdapter#notifyItemInserted(int)
            notifyItemInserted(ItemCountList.size() - 1);
        }

        public List<Item> getitems() {
            List<Item> items = new ArrayList<>();
            for (ItemCount itemCount : ItemCountList) {
                for (int i = 0; i < itemCount.quantity; i++) {
                    items.add(itemCount.item);
                }
            }
            return items;
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
            Item item = ItemCountList.get(position).item;
            if (item == null) return;

            holder.itemTypeTextView.setText("סוג הפריט:" + item.getType());
            holder.itemDateTextView.setText("תאריך בו נמצאה האבדה :" + item.getDate());
            holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(item.getImageBase64()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemProfile.class);
                    intent.putExtra("ITEM_ID", item.getId());
                    context.startActivity(intent);
                }
            });

        }

        /// get the number of items in the list
        /// @return the number of items in the list
        @Override
        public int getItemCount() {
            return ItemCountList.size();
        }

        /// View holder for the foods adapter
        /// @see RecyclerView.ViewHolder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView itemTypeTextView;
            public final TextView itemDateTextView;
            public final ImageView itemImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                itemTypeTextView = itemView.findViewById(R.id.item_item_type_text_view);
                itemDateTextView = itemView.findViewById(R.id.item_item_date_text_view);
                itemImageView = itemView.findViewById(R.id.item_item_image_view);

            }
        }
    }
