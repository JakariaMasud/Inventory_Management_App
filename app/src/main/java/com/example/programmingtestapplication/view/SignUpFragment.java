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
import com.example.programmingtestapplication.databinding.FragmentSignUpBinding;
import com.example.programmingtestapplication.model.User;
import com.example.programmingtestapplication.viewModel.UserViewModel;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    NavController navController;
    UserViewModel userViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentSignUpBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.signUpProcessLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("success")){
                    Toast.makeText(getContext(), "Succesfully Account created", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_signUpFragment_to_loginFragment);
                }
                else if(s.equals("failed")){
                    Toast.makeText(getContext(), "UserName is not Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=binding.userNameET.getText().toString().trim();
                String password=binding.passwordET.getText().toString().trim();
                String firstName=binding.firstNameET.getText().toString().trim();
                String lastName=binding.lastNameET.getText().toString().trim();
                if(TextUtils.isEmpty(firstName)){
                    binding.firstNameET.setError("First Name Must be given");
                }else{
                    if(TextUtils.isEmpty(lastName)){
                        binding.lastNameET.setError("Last Name Field Can not be empty");
                    }else{
                        if(TextUtils.isEmpty(userName)){
                            binding.userNameET.setError("User Name Can not be empty");
                        }else{
                            if(TextUtils.isEmpty(password)){
                                binding.passwordET.setError("password must be given");
                            }else{
                                //every field is valid
                                User user=new User(userName,firstName,lastName,password);
                                userViewModel.signUp(user);
                            }
                        }
                    }
                }
            }
        });


    }
}