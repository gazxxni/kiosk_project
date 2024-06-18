// Example: InventoryManagementFragment.java
package com.example.kiosk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class InventoryManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private InventoryItemAdapter adapter;
    private List<InventoryItem> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_management, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_inventory);

        itemList = new ArrayList<>();
        adapter = new InventoryItemAdapter(getContext(), itemList);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        loadItems(); // 데이터를 불러오는 메서드

        return view;
    }

    private void loadItems() {
        String url = "http://10.0.2.2/get_items.php"; // 에뮬레이터에서 로컬 서버에 접근할 때 사용

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            itemList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String category = jsonObject.getString("category");
                                int price = jsonObject.getInt("price");
                                String imageUrl = jsonObject.getString("image_url");
                                int quantity = jsonObject.getInt("quantity");

                                // Ensure image path is relative
                                imageUrl = "http://10.0.2.2/" + imageUrl;

                                InventoryItem item = new InventoryItem(name, category, price, imageUrl, quantity);
                                itemList.add(item);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
