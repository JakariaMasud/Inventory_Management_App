package com.example.programmingtestapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programmingtestapplication.model.LoginInfo;
import com.example.programmingtestapplication.model.User;
import com.example.programmingtestapplication.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    UserRepository repository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository=new UserRepository(application);
    }

    public void login(LoginInfo info){
        repository.login(info);
    }
    public void signUp(User user){
        repository.signUp(user);

    }
    public LiveData<String> loginProcessLiveData(){
        return repository.getLoginProcessLiveData();
    }
    public LiveData<String> signUpProcessLiveData(){
        return repository.getSignUpProcessLiveData();
    }
}
