package com.example.programmingtestapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmingtestapplication.R;
import com.example.programmingtestapplication.databinding.FragmentLoginBinding;
import com.example.programmingtestapplication.model.LoginInfo;
import com.example.programmingtestapplication.viewModel.ProductViewModel;
import com.example.programmingtestapplication.viewModel.UserViewModel;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    NavController navController;
    UserViewModel userViewModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        binding.loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=binding.usernameET.getText().toString().trim();
                String password=binding.passwordET.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    binding.usernameET.setError("UserName Can not be empty");
                    return;
                }else {
                    if(TextUtils.isEmpty(password)){
                        binding.passwordET.setError("Password Field can not be empty");
                        return;
                    }
                    else{
                        userViewModel.login(new LoginInfo(userName,password));
                    }
                }
            }
        });

        binding.registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        userViewModel.loginProcessLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("success")){
                    navController.navigate(R.id.action_loginFragment_to_homeFragment);
                }
                else if(s.equals("failed")){
                    Toast.makeText(getContext(), "User name or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}