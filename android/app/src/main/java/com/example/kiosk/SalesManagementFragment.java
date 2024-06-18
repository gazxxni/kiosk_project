package com.example.kiosk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private SalesAdapter adapter;
    private List<Sales> salesList;
    private CalendarView calendarView;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_management, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_sales);
        calendarView = view.findViewById(R.id.calendar_view_sales);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        salesList = new ArrayList<>();
        adapter = new SalesAdapter(getContext(), salesList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getContext());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                loadSales(date);
            }
        });

        return view;
    }

    private void loadSales(String date) {
        String url = "http://10.0.2.2/get_sales.php?date=" + date;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        salesList.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String date = jsonObject.getString("date");
                                String itemName = jsonObject.getString("item_name");
                                int quantity = jsonObject.getInt("quantity");
                                int totalPrice = jsonObject.getInt("total_price");

                                Sales sales = new Sales(date, itemName, quantity, totalPrice);
                                salesList.add(sales);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed to parse data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
