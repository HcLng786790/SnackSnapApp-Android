package com.huuduc.giuaky.activity.fragmentStatus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huuduc.giuaky.activity.OrdersDetailsActivity;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.adapter.OrdersAdapter;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.interfaces.OrdersClickListener;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CookFragment extends Fragment {

    private RecyclerView recyclerView;
    private View mView;

    private OrdersAdapter ordersAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CookFragment newInstance(String param1, String param2) {
        CookFragment fragment = new CookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cook, container, false);

        recyclerView = mView.findViewById(R.id.rcv_orders);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadOrders();

        return mView;
    }

    private void loadOrders() {

        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getAllByStatus(Orders.COOKING, MainActivity.user.getId())
                .enqueue(new Callback<List<Orders>>() {
                    @Override
                    public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Orders>> call, Throwable t) {
                        Toast.makeText(getContext(), "Save error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Orders> body) {
        ordersAdapter = new OrdersAdapter(getContext(), body, new OrdersClickListener() {
            @Override
            public void onClickOrders(Orders orders) {
                onClickToDetail(orders);
            }
        });
        recyclerView.setAdapter(ordersAdapter);
    }

    private void onClickToDetail(Orders orders) {
        Intent intent = new Intent(getContext(), OrdersDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_orders", orders);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}