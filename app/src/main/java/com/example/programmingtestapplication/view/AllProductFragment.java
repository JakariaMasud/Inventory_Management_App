package com.example.programmingtestapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.programmingtestapplication.R;
import com.example.programmingtestapplication.adapter.ProductAdapter;
import com.example.programmingtestapplication.databinding.FragmentAllProductBinding;
import com.example.programmingtestapplication.model.Product;
import com.example.programmingtestapplication.viewModel.ProductViewModel;

import java.util.List;


public class AllProductFragment extends Fragment {
    FragmentAllProductBinding binding;
    RecyclerView.LayoutManager layoutManager;
    ProductViewModel productViewModel;
    ProductAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAllProductBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        layoutManager= new LinearLayoutManager(getActivity());
        binding.productListRV.setHasFixedSize(true);
        binding.productListRV.setLayoutManager(layoutManager);
        productViewModel.getGetAllProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if(products.size()<=0){
                    binding.noItemTV.setVisibility(View.VISIBLE);
                } else {
                    binding.noItemTV.setVisibility(View.GONE);
                    adapter=new ProductAdapter(products);
                    binding.productListRV.setAdapter(adapter);
                }

            }
        });


    }

}