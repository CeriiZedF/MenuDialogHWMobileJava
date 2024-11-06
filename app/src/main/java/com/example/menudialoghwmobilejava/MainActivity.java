package com.example.menudialoghwmobilejava;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView editText;
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    Button editTextButton;
    Button selectTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentDateTime = findViewById(R.id.currentDateTime);
        setInitialDateTime();
        editText = findViewById(R.id.edit_text);
        ImageButton dialogButton = findViewById(R.id.button1);
        ImageButton pushButton = findViewById(R.id.button2);
        editTextButton = findViewById(R.id.editTextButton);
        selectTextButton = findViewById(R.id.selectText);



        selectTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckboxDialog();
            }
        });
        editTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditTextDialog();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditTextDialog();
            }
        });

        MyNotificationManager.createNotificationChannel(this);
        pushButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyNotificationManager.showNotification(MainActivity.this,"HELLO", editText.getText().toString());
            }
        });
    }

    public void onCallNotificationButtonClick(View view) {
        String messageText = editText.getText().toString().trim();

        if (!messageText.isEmpty()) {
            String currentTime = android.text.format.DateFormat.format("HH:mm:ss", new java.util.Date()).toString();
            MyNotificationManager.showNotification(this, "Нове сповіщення", messageText + " - " + currentTime);
        } else {
            Toast.makeText(this, "Текстове поле порожнє", Toast.LENGTH_SHORT).show();
        }
    }

    public void setDate(View v) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Local Time")
                .setMessage("Встановити поточний час?")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setInitialDateTime();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void setInitialDateTime() {
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t=(TimePicker view, int hourOfDay, int minute) ->{
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };
    DatePickerDialog.OnDateSetListener d=(DatePicker view, int year, int monthOfYear, int dayOfMonth)-> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    private void showEditTextDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog, null);

        final EditText editText = dialogView.findViewById(R.id.editTextDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredText = editText.getText().toString();
                ((TextView) findViewById(R.id.edit_text)).setText(enteredText);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setTitle("Введите текст");

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showCheckboxDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_with_checkbox, null);

        CheckBox checkBox1 = dialogView.findViewById(R.id.checkBox1);
        CheckBox checkBox2 = dialogView.findViewById(R.id.checkBox2);
        CheckBox checkBox3 = dialogView.findViewById(R.id.checkBox3);

        checkBox1.setText(R.string.checkbox1);
        checkBox2.setText(R.string.checkbox2);
        checkBox3.setText(R.string.checkbox3);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder selectedTexts = new StringBuilder();

                if (checkBox1.isChecked()) {
                    selectedTexts.append(checkBox1.getText()).append(" ");
                }
                if (checkBox2.isChecked()) {
                    selectedTexts.append(checkBox2.getText()).append(" ");
                }
                if (checkBox3.isChecked()) {
                    selectedTexts.append(checkBox3.getText()).append(" ");
                }

                ((TextView) findViewById(R.id.edit_text)).setText(selectedTexts.toString().trim());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setTitle("Выберите текст");
        builder.create().show();
    }



}

