package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.address.Address;

import java.util.List;

public class AddressAdapter extends ArrayAdapter{

     private Context context;

     private int resource;
     private List<Address> addressList;

     private int selectedPosition  = -1;

     private Address selectedAddress;

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public AddressAdapter(@NonNull Context context, int resource , List<Address> addressList) {
        super(context, resource,addressList);
        this.context=context;
        this.resource=resource;
        this.addressList=addressList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(resource,null);


        ImageView imvChose = convertView.findViewById(R.id.imvChose);
        TextView txtNameReceiver=convertView.findViewById(R.id.txtNameUserItem);
        TextView txtPhoneReceiver=convertView.findViewById(R.id.txtPhoneItem);
        TextView txtLocation=convertView.findViewById(R.id.txtLocationItem);

        Address address = addressList.get(position);

        txtNameReceiver.setText(address.getNameReceiver());
        txtLocation.setText(address.getLocation());
        txtPhoneReceiver.setText(address.getPhoneReceiver());

        if(selectedAddress!=null) {
            if (address.getId().equals(selectedAddress.getId())) {
                imvChose.setVisibility(View.VISIBLE);
            } else {
                imvChose.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }
}
