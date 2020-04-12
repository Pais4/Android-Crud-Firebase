package com.example.firebasecrud.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firebasecrud.Models.ContactoModel;
import com.example.firebasecrud.R;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {

    private final Context context;
    private ContactoModel contactoModel;
    private ArrayList<ContactoModel> list;

    public ContactoAdapter(Context context, ArrayList<ContactoModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_contacto, parent, false);
        }

        TextView tv_item_contacto = itemView.findViewById(R.id.tv_item_contacto);
        contactoModel = list.get(position);
        tv_item_contacto.setText(contactoModel.get_nombre());

        return itemView;
    }
}
