package com.example.kiosk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private List<Item> itemList;
    private OnItemRemoveListener onItemRemoveListener;
    private OnQuantityChangeListener onQuantityChangeListener;

    public BasketAdapter(List<Item> itemList, OnItemRemoveListener onItemRemoveListener, OnQuantityChangeListener onQuantityChangeListener) {
        this.itemList = itemList;
        this.onItemRemoveListener = onItemRemoveListener;
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item_layout, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Item item = itemList.get(position);

        // 이미지 로딩
        Glide.with(holder.itemImageView.getContext())
                .load(item.getImageUrl())
                .into(holder.itemImageView);

        holder.itemNameTextView.setText(item.getName());
        holder.itemPriceTextView.setText(item.getPrice() + "원");
        holder.itemQuantityTextView.setText(String.valueOf(item.getQuantity()));

        holder.removeItemButton.setOnClickListener(v -> {
            if (onItemRemoveListener != null) {
                onItemRemoveListener.onItemRemove(position);
            }
        });

        holder.decreaseQuantityButton.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (onQuantityChangeListener != null) {
                    onQuantityChangeListener.onQuantityChange(item);
                }
            }
        });

        holder.increaseQuantityButton.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(Item item);
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemQuantityTextView;
        Button removeItemButton;
        Button decreaseQuantityButton;
        Button increaseQuantityButton;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_image);
            itemNameTextView = itemView.findViewById(R.id.item_name);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemQuantityTextView = itemView.findViewById(R.id.item_quantity);
            removeItemButton = itemView.findViewById(R.id.delete_item);
            decreaseQuantityButton = itemView.findViewById(R.id.decrease_quantity);
            increaseQuantityButton = itemView.findViewById(R.id.increase_quantity);
        }
    }
}
