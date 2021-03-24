package com.example.programmingtestapplication.view;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmingtestapplication.Constants;
import com.example.programmingtestapplication.R;
import com.example.programmingtestapplication.databinding.FragmentLocationTrackingBinding;
import com.example.programmingtestapplication.services.LocationService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class LocationTrackingFragment extends Fragment implements View.OnClickListener {
    FragmentLocationTrackingBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLocationTrackingBinding.inflate(inflater,container,false);
        View v=binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.startLocationBTN.setOnClickListener(this::onClick);
        binding.stopLocationBTN.setOnClickListener(this::onClick);

    }

    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.start_locationBTN){
            if (!checkPermissions()) {
                requestPermissions();
            } else {

                startLocationService();
            }


        }
        else if(v.getId()==R.id.stop_locationBTN){
            if(!serviceIsRunningInForeground(getContext())){
                Intent intent=new Intent(getContext(), LocationService.class);
                intent.setAction(Constants.STOP_LOCATION_SERVICE);
                getActivity().startService(intent);
                Toast.makeText(getContext(), "Location Service stopped", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
               shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {

            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("App needs the permission for its service ")
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            Constants.REQUEST_LOCATION_PERMISSION_CODE);
                                }
                            }
                    ).show();
        } else {
            Log.e("info", "Requesting permission");

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.REQUEST_LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == Constants.REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length <= 0) {


            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                startLocationService();
            } else {
                // Permission denied.

            }
        }
    }
    public void startLocationService(){
        if(!serviceIsRunningInForeground(getContext())){
            Intent intent=new Intent(getContext(), LocationService.class);
            intent.setAction(Constants.START_LOCATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getActivity().startForegroundService(intent);
            } else {
                getActivity().startService(intent);
            }
            Toast.makeText(getContext(), "Location Service Started ", Toast.LENGTH_SHORT).show();
        }
    }
}