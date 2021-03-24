package com.example.programmingtestapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.programmingtestapplication.model.Product;
import com.example.programmingtestapplication.model.User;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM product_table WHERE productName =:name And unitInMilli=:unit LIMIT 1")
    Product getProductByNameAndUnit(String name ,int unit);

    @Insert
    void Insert(Product product);

    @Query("UPDATE product_table SET quantity=quantity+:newQuantity WHERE id=:id")
    void updateQuantityForAdd(int id,int newQuantity);

    @Query("UPDATE product_table SET quantity=quantity-:newQuantity WHERE id=:id")
    void updateQuantityForSale(int id,int newQuantity);

    @Query("DELETE FROM product_table WHERE id = :id")
     void deleteProductById(int  id);

    @Query("SELECT * FROM user_table WHERE userName =:username And password=:password LIMIT 1")
    User getUserByUserNameAndPassword(String username , String password);

    @Query("SELECT * FROM user_table WHERE userName =:username LIMIT 1")
    User getUserByUserName(String username);

    @Insert
    long insertUser(User user);



}
