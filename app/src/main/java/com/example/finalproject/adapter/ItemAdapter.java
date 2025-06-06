package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.screens.ItemProfile;
import com.example.finalproject.utils.ImageUtil;
import com.google.firebase.database.annotations.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static class ItemCount {
        Item item;
        int quantity;
    }


    public interface ItemClick {
        public void updateDB(Item item);
    }


    private final List<ItemCount> ItemCountList, filterItemCountList;

    Context context;
    ItemClick itemClick;

    public ItemAdapter(Context context, ItemClick itemClick) {
        ItemCountList = new ArrayList<>();
        filterItemCountList = new ArrayList<>();
        this.context = context;
        this.itemClick = itemClick;
    }

    public void setItems(@NonNull List<Item> items) {
        this.ItemCountList.clear();
        this.filterItemCountList.clear();
        for (Item item : items) {
            addItem(item);
        }
        filterItemCountList.addAll(this.ItemCountList);
        this.notifyDataSetChanged();
    }

    public void filterItems(String text) {
        filterItemCountList.clear();
        filterItemCountList.addAll(this.ItemCountList);
        if (!text.isEmpty())
            filterItemCountList.removeIf(itemCount -> !itemCount.item.getDesc().contains(text));
        this.notifyDataSetChanged();
    }

    private void addItem(@Nullable Item f) {
        if (f == null) return;
        for (int i = 0; i < ItemCountList.size(); i++) {
            ItemCount itemCount = ItemCountList.get(i);
            if (itemCount.item.getId().equals(f.getId())) {
                itemCount.quantity++;
                notifyItemChanged(i);
                return;
            }
        }
        ItemCount itemCount = new ItemCount() {{
            this.item = new Item(f);
            this.quantity = 1;
        }};
        ItemCountList.add(itemCount);
        /// notify the adapter that the data has changed
        /// this specifies that the item at selectedFoods.size() - 1 has been inserted
        /// and the adapter should update the view
        /// @see FoodsAdapter#notifyItemInserted(int)
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
        Item item = filterItemCountList.get(position).item;
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

        if (Objects.equals(item.getStatus(), "Found")) {
            holder.itemRG.check(R.id.rb_item_item_found);
        } else {
            holder.itemRG.check(R.id.rb_item_item_notfound);
        }
        holder.itemRG.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_item_item_found) {
                item.setStatus("Found");
            } else {
                item.setStatus("Not Found");
            }
            itemClick.updateDB(item);
        });

    }

    /// get the number of items in the list
    /// @return the number of items in the list
    @Override
    public int getItemCount() {
        return filterItemCountList.size();
    }


    public void sortNewToOld() {
        this.filterItemCountList.sort(new Comparator<ItemCount>() {
            @Override
            public int compare(ItemCount o1, ItemCount o2) {
                Date date1 = parseDateSafely(o1.item.getDate());
                Date date2 = parseDateSafely(o2.item.getDate());

                if (date1 == null && date2 == null) return 0;
                if (date1 == null) return 1;
                if (date2 == null) return -1;

                return date2.compareTo(date1); // הכי חדש קודם
            }
        });
        this.notifyDataSetChanged();
    }
    private @Nullable Date parseDateSafely(String dateStr) {
        if (dateStr == null) return null;

        String[] parts = dateStr.split("/");
        if (parts.length != 3) return null;

        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // חודש ב־Date מתחיל מ־0
            int year = Integer.parseInt(parts[2]) - 1900; // שנה ב־Date מתחילה מ־1900
            return new Date(year, month, day);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void sortOldToNew() {
        this.filterItemCountList.sort(new Comparator<ItemCount>() {
            @Override
            public int compare(ItemCount o1, ItemCount o2) {
                Date date1 = parseDateSafely(o1.item.getDate());
                Date date2 = parseDateSafely(o2.item.getDate());

                if (date1 == null && date2 == null) return 0;
                if (date1 == null) return 1;
                if (date2 == null) return -1;

                return date1.compareTo(date2); // הכי ישן קודם
            }
        });
        this.notifyDataSetChanged();
    }
    


    public void sortByType() {
        this.filterItemCountList.sort(new Comparator<ItemCount>() {
            @Override
            public int compare(ItemCount o1, ItemCount o2) {
                return o1.item.getType().compareTo(o2.item.getType());
            }
        });
        this.notifyDataSetChanged();
    }

    /// View holder for the foods adapter
    /// @see RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView itemTypeTextView;
        public final TextView itemDateTextView;
        public final ImageView itemImageView;
        public final RadioButton itemFound;
        public final RadioButton itemNotFound;
        public final RadioGroup itemRG;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTypeTextView = itemView.findViewById(R.id.item_item_type_text_view);
            itemDateTextView = itemView.findViewById(R.id.item_item_date_text_view);
            itemImageView = itemView.findViewById(R.id.item_item_image_view);
            itemFound = itemView.findViewById(R.id.rb_item_item_found);
            itemNotFound = itemView.findViewById(R.id.rb_item_item_notfound);
            itemRG = itemView.findViewById(R.id.rg_item_item);

        }
    }
}
