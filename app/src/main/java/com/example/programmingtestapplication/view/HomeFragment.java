package com.example.programmingtestapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.programmingtestapplication.R;
import com.example.programmingtestapplication.databinding.FragmentAddTransactionBinding;
import com.example.programmingtestapplication.databinding.FragmentHomeBinding;
import com.example.programmingtestapplication.viewModel.UserViewModel;


public class HomeFragment extends Fragment implements View.OnClickListener {
    FragmentHomeBinding binding;
    NavController navController;
    UserViewModel userViewModel;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.addCard.setOnClickListener(this::onClick);
        binding.timelineCard.setOnClickListener(this::onClick);
        binding.saleCard.setOnClickListener(this::onClick);
        binding.locationCard.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_card){
            navController.navigate(R.id.action_homeFragment_to_addTransactionFragment);
        }
        else if(v.getId()==R.id.timeline_card){
            navController.navigate(R.id.action_homeFragment_to_allProductFragment);
        }
        else if(v.getId()==R.id.sale_card){
            navController.navigate(R.id.action_homeFragment_to_saleTransactionFragment);
        }
        else if(v.getId()==R.id.location_card){
            navController.navigate(R.id.action_homeFragment_to_locationTrackingFragment);
        }
    }


}