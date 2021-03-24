package com.example.programmingtestapplication.view;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.programmingtestapplication.R;
import com.example.programmingtestapplication.databinding.FragmentAddTransactionBinding;
import com.example.programmingtestapplication.databinding.FragmentSaleTransactionBinding;
import com.example.programmingtestapplication.model.Product;
import com.example.programmingtestapplication.viewModel.ProductViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SaleTransactionFragment extends Fragment {
    FragmentSaleTransactionBinding binding;
    Date selectedDate;
    int quantity,unit,rate;
    String name;
    ProductViewModel productViewModel;
    DatePickerDialog.OnDateSetListener datePicker;
    NavController navController;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentSaleTransactionBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        final Calendar myCalendar = Calendar.getInstance();
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.SaleProcessLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Success")){
                    navController.navigate(R.id.action_saleTransactionFragment_to_allProductFragment);
                }
                else{
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }



            }
        });
        datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DATE, dayOfMonth);
                selectedDate=cal.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString=format1.format(selectedDate);
                binding.saleSelectedTV.setText(dateString);

            }

        };
        binding.saleSelectDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.saleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=binding.saleProductNameET.getText().toString();
                String unitStr=binding.saleProductUnitET.getText().toString();
                String quantityStr=binding.saleProductQuantityET.getText().toString();
                String rateStr=binding.saleProductRateET.getText().toString();
                if(TextUtils.isEmpty(name)){
                    binding.saleProductNameET.setError("Product Name Field Cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(unitStr)){
                    binding.saleProductUnitET.setError("Unit Field Cannot be empty");
                    return;
                }
                else{
                    if(isNumeric(unitStr)){
                        unit=Integer.parseInt(unitStr);
                    }
                    else{
                        binding.saleProductUnitET.setError("This Field Should be number");
                        return;
                    }
                }
                if(TextUtils.isEmpty(quantityStr)){
                    binding.saleProductQuantityET.setError("quantity Field Cannot be empty");
                    return;
                }
                else{
                    if(isNumeric(quantityStr)){
                        quantity=Integer.parseInt(quantityStr);
                    }
                    else{
                        binding.saleProductQuantityET.setError("This Field Should be number");
                        return;
                    }
                }

                if(TextUtils.isEmpty(rateStr)){
                    binding.saleProductRateET.setError("rate Field Cannot be empty");
                    return;
                }
                else{
                    if(isNumeric(rateStr)){
                        rate=Integer.parseInt(rateStr);
                    }
                    else{
                        binding.saleProductRateET.setError("This Field Should be number");
                        return;
                    }
                }
                if(selectedDate==null){
                    binding.saleSelectedTV.setText("No Date Selected");
                    Toast.makeText(getContext(), "Please Select a Date ", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    productViewModel.UpdateForSale(new Product(name,unit,quantity,rate,selectedDate));

                }






            }
        });



    }

    public static boolean isNumeric(String string) {
        int intValue;
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    }
