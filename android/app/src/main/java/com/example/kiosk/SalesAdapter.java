package com.example.kiosk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    private List<Sales> salesList;
    private Context context;

    public SalesAdapter(Context context, List<Sales> salesList) {
        this.context = context;
        this.salesList = salesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sales, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sales sales = salesList.get(position);
        holder.salesDate.setText(sales.getDate());
        holder.salesItemName.setText(sales.getItemName());
        holder.salesQuantity.setText(String.valueOf(sales.getQuantity()));
        holder.salesTotalPrice.setText(String.format("%,d원", sales.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView salesDate;
        public TextView salesItemName;
        public TextView salesQuantity;
        public TextView salesTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salesDate = itemView.findViewById(R.id.sales_date);
            salesItemName = itemView.findViewById(R.id.sales_item_name);
            salesQuantity = itemView.findViewById(R.id.sales_quantity);
            salesTotalPrice = itemView.findViewById(R.id.sales_total_price);
        }
    }
}
