package com.kimyayd.stage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText date , location , time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create);
        date= findViewById(R.id.event_date);
//        location= findViewById(R.id.edit_place);
        time= findViewById(R.id.event_time);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPlacePickerDialog();
//            }
//        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }
    private void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,(view, selectedYear,selectedMonth, selectedDayOfMonth) -> {String selectedDate = selectedDayOfMonth +"/"+(selectedMonth+1)+"/"+selectedYear;
            date.setText(selectedDate);},year,month,dayOfMonth);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(){
    Calendar calendar = Calendar.getInstance(Locale.FRANCE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(HomeActivity.this,(view, hourOfDay, selectedMinute) -> {String selectedTime = hourOfDay +":"+selectedMinute;
        time.setText(selectedTime);},hour,minute,true);
        timePickerDialog.show();
    }
}