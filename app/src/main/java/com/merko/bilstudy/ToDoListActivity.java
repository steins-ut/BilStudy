package com.merko.bilstudy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listView;
    View back;
    ArrayList<String>itemList = new ArrayList<>();
    ArrayAdapter<String>arrayAdapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        back = findViewById(R.id.back_button);
        item = findViewById(R.id.editText);
        add = findViewById(R.id.button4);
        listView = findViewById(R.id.list);
        itemList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,itemList);
        listView.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemname = item.getText().toString();
                itemList.add(itemname);
                item.setText("");
                FileHelper.writeData(itemList,getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ToDoListActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item from your to do list");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList.remove(pos);
                        arrayAdapter.notifyDataSetChanged();
                        FileHelper.writeData(itemList,getApplicationContext());
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, ChooseTemplateActivity.class);
                startActivity(intent);
            }
        });
    }

}
