package com.huuduc.giuaky.activity.fragmentMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.ShowDetailActivity;
import com.huuduc.giuaky.adapter.FavoriteAdapter;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerViewFavorite;
    private FavoriteAdapter favoriteAdapter;
    private View mView;

    private List<Product> productList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        //Ánh xạ
        recyclerViewFavorite = mView.findViewById(R.id.rcv_favorite);

//        products = new DSProduct();

//        favoriteAdapter = new FavoriteAdapter(getContext(), products, new ItemClickListener() {
//            @Override
//            public void onClickItem(Product product) {
//                onClickToDetail(product);
//            }
//        });
//        favoriteAdapter = new FavoriteAdapter(getContext(), productList, new ItemClickListener() {
//            @Override
//            public void onClickItem(Product product) {
//                onClickToDetail(product);
//            }
//        });

        //gán layout cho rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewFavorite.setLayoutManager(linearLayoutManager);

//        favoriteAdapter.setData(products.getListByFavorite(products.getProducts()));
//        recyclerViewFavorite.setAdapter(favoriteAdapter);

        //Gọi xử lí api
        loadProductFavorite();

        return mView;
    }


    private void loadProductFavorite() {

        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        productApi.getAllByFavorite()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getContext(), "Save error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Product> products) {
        favoriteAdapter = new FavoriteAdapter(getContext(), products, new ItemClickListener() {
            @Override
            public void onClickItem(Product product) {
                onClickToDetail(product);
            }
        });

        recyclerViewFavorite.setAdapter(favoriteAdapter);
    }


    public void onClickToDetail(Product product) {
        Intent intent = new Intent(getContext(), ShowDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}