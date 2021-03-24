package com.example.programmingtestapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import com.example.programmingtestapplication.ProductDao;
import com.example.programmingtestapplication.ProductDatabase;
import com.example.programmingtestapplication.SingleLiveEvent;
import com.example.programmingtestapplication.model.LoginInfo;
import com.example.programmingtestapplication.model.User;

public class UserRepository {
    private ProductDao productDao;
    private SingleLiveEvent<String> loginProcessLiveData=new SingleLiveEvent<>();
    private SingleLiveEvent<String>signUpProcessLiveData=new SingleLiveEvent<>();

    public SingleLiveEvent<String> getLoginProcessLiveData() {
        return loginProcessLiveData;
    }

    public SingleLiveEvent<String> getSignUpProcessLiveData() {
        return signUpProcessLiveData;
    }

    public UserRepository(Application application) {
        ProductDatabase database=ProductDatabase.getInstance(application);
        productDao=database.productDao();

    }
    public  void login(LoginInfo info){
        new LoginAsyncTask(productDao,loginProcessLiveData).execute(info);
    }

    public void signUp(User user){
        new SignUpAsyncTask(productDao,signUpProcessLiveData).execute(user);
    }



    private static class LoginAsyncTask extends AsyncTask<LoginInfo, Void, Void> {
        private ProductDao productDao;
        SingleLiveEvent<String> listener;
        public LoginAsyncTask(ProductDao productDao,SingleLiveEvent<String> listener) {
            this.productDao = productDao;
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(LoginInfo... loginInfos) {
            User user=productDao.getUserByUserNameAndPassword(loginInfos[0].getUserName(),loginInfos[0].getPassword());
            if(user!=null){
                listener.postValue("success");
                Log.e("info","Success from login");
            }else{
                listener.postValue("failed");
                Log.e("info","Failed from login");
            }


            return null;
        }
    }

    private static class SignUpAsyncTask extends AsyncTask<User, Void, Void> {
        private ProductDao productDao;
        SingleLiveEvent<String> listener;
        public SignUpAsyncTask(ProductDao productDao,SingleLiveEvent<String> listener) {
            this.productDao = productDao;
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            User user=productDao.getUserByUserName(users[0].getUserName());
            if(user==null){
                long rowId=productDao.insertUser(users[0]);
                if(rowId>=0){
                    listener.postValue("success");
                    Log.e("info","Success from sign Up");
                }
            } else{
                listener.postValue("failed");
            }




            return null;
        }
    }


}
