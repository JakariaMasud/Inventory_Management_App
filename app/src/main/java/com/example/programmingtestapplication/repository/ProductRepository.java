package com.example.programmingtestapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.programmingtestapplication.ProductDao;
import com.example.programmingtestapplication.ProductDatabase;
import com.example.programmingtestapplication.SingleLiveEvent;
import com.example.programmingtestapplication.model.Product;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private SingleLiveEvent<String> addProcessLiveData=new SingleLiveEvent<>();
    private SingleLiveEvent<String>saleProcessLiveData=new SingleLiveEvent<>();


    public LiveData<String> getAddProcessLiveData() {
        return addProcessLiveData;
    }

    public LiveData<String> getSaleProcessLiveData() {
        return saleProcessLiveData;
    }

    public ProductRepository(Application application) {

        ProductDatabase database=ProductDatabase.getInstance(application);
        productDao=database.productDao();
        allProducts=productDao.getAllProducts();


    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

        public void insertOrUpdateForAdd(Product product){

          new InsertUpdateForAddAsyncTask(productDao,addProcessLiveData).execute(product);


        }

    public void UpdateForSale(Product product){
        new UpdateForSaleAsyncTask(productDao,saleProcessLiveData).execute(product);

    }

    private static class InsertUpdateForAddAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        SingleLiveEvent<String> listener;
        public InsertUpdateForAddAsyncTask(ProductDao productDao,SingleLiveEvent<String> listener) {
            this.productDao = productDao;
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(Product... products) {
           Product product= productDao.getProductByNameAndUnit(products[0].getProductName(),products[0].getUnitInMilli());
           if(product==null){
               productDao.Insert(products[0]);
               listener.postValue("Successfully Inserted");
               Log.e("info","inserted");

           }else {
               productDao.updateQuantityForAdd(product.getId(),products[0].getQuantity());
               listener.postValue("Successfully Updated");
               Log.e("info","updated");

           }

            return null;
        }
    }
    private static class UpdateForSaleAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        SingleLiveEvent<String> listener;
        public UpdateForSaleAsyncTask(ProductDao productDao,SingleLiveEvent<String>listener) {
            this.productDao = productDao;
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(Product... products) {
            Product product= productDao.getProductByNameAndUnit(products[0].getProductName(),products[0].getUnitInMilli());
            if(product!=null){
                if(product.getQuantity()>products[0].getQuantity()){
                    productDao.updateQuantityForSale(product.getId(),products[0].getQuantity());
                    listener.postValue("Success");
                }
                else if(product.getQuantity()==products[0].getQuantity()){
                    productDao.deleteProductById(product.getId());
                    listener.postValue("Success");
                }
                else{
                    listener.postValue("not enough Product In Inventory");
                    Log.e("info","not enough Product In Inventory");


                }
            }
            else{
                listener.postValue("this Product is not available in Inventory");
                Log.e("info","this Product is not available in Shop");
               
            }



            return null;
        }
    }


}
