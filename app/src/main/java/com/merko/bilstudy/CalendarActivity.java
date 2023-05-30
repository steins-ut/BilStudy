package com.merko.bilstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.merko.bilstudy.utils.FileHelper;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView textView;
    Button add;
    EditText item;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendarView);
        textView = findViewById(R.id.textViewCal);
        add = findViewById(R.id.buttonAdd);
        item = findViewById(R.id.editText);
        listView = findViewById(R.id.list);
        itemList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        listView.setAdapter(arrayAdapter);
        backButton = findViewById(R.id.backButtonCalendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                String date = day + "/" + (month+1) + "/" + year;
                textView.setText(date);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profile);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemname = item.getText().toString();
                if(itemname != null && !itemname.equals("")) {
                    itemList.add(itemname);
                    item.setText("");
                    FileHelper.writeData(itemList, getApplicationContext());
                    arrayAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(CalendarActivity.this, "Please add some content!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CalendarActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this event?");
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
                        FileHelper.writeData(itemList,CalendarActivity.this);

                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });


    }
}
