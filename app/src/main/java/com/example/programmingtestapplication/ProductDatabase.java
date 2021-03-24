package com.example.programmingtestapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.programmingtestapplication.Converter.DateConverter;
import com.example.programmingtestapplication.model.Product;
import com.example.programmingtestapplication.model.User;

@Database(entities = {Product.class, User.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance;
    public abstract ProductDao productDao();
    public static synchronized ProductDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
