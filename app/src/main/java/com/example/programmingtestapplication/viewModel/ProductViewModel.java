package com.example.programmingtestapplication.viewModel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.programmingtestapplication.model.Product;
import com.example.programmingtestapplication.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository=new ProductRepository(application);
    }


    public LiveData<List<Product>> getGetAllProducts() {
        return repository.getAllProducts();
    }

    public void insertOrUpdateForAdd(Product product){

        repository.insertOrUpdateForAdd(product);
    }

    public void UpdateForSale(Product product){
        repository.UpdateForSale(product);
    }
    public LiveData<String> addProcessLiveData(){
        return repository.getAddProcessLiveData();
    }
    public LiveData<String>SaleProcessLiveData(){
        return repository.getSaleProcessLiveData();
    }



}
