package com.mustafa.clinicappointmentsystem.PatientBottomFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.databinding.FragmentGetAppointmentBinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetAppointmentFragment extends Fragment {

    private FragmentGetAppointmentBinding binding;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapterItems;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;

    private String DoctorName = "",Status = "",DoctorID = "",currentDoctorName="";
    private DatePickerDialog datePickerDialog;
    private String date = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetAppointmentBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        adapterItems = new ArrayAdapter<String>(binding.getRoot().getContext(),R.layout.list_item,arrayList);
        binding.getAppointmentAutoText.setAdapter(adapterItems);

        getDoctorName();
        getAppointmentDate();
        initDatePicker();

        binding.datePickerBtn.setText(getTodayDate());

        binding.getAppointmentAutoText.setOnItemClickListener((parent, view, position, id) ->
                DoctorName = parent.getItemAtPosition(position).toString());

        requestAppointment();

        return binding.getRoot();
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        month = month +1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeStringDate(day,month,year);
    }

    private void getDoctorName(){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                while (resultSet.next()){

                    currentDoctorName = resultSet.getString("doctor_name");
                    String doctorSurname = resultSet.getString("doctor_surname");

                    arrayList.add(currentDoctorName + " " +doctorSurname);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void getAppointmentDate(){
        binding.datePickerBtn.setOnClickListener(item -> {
            datePickerDialog.show();
        });
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                date = makeStringDate(year,month,day);
                binding.datePickerBtn.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(),style,dateSetListener,year,month,day);

    }

    private String makeStringDate(int year,int month,int day){
        return year + "-" + month + "-" + day;

    }
    private String getDoctorID(){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        String parts[] = DoctorName.split(" ", 2);

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                while (resultSet.next()){

                    if(parts[0].equals(resultSet.getString("doctor_name"))){
                        DoctorID = resultSet.getString("doctor_id");
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return DoctorID;
    }

    private void requestAppointment(){
        binding.getAppointmentClicked.setOnClickListener(item -> {
            String appointment_id = binding.getAppointmentIDEditText.getText().toString().trim();
            String health_issue = binding.getAppointmentHealthEditText.getText().toString().trim();

            AppointmentInfo appointment = new AppointmentInfo();
            appointment.requestAppointment(binding.getRoot().getContext(),appointment_id,getDoctorID(),health_issue,date);

            clearTextView();
        });
    }

    private void clearTextView(){
        binding.getAppointmentHealthEditText.setText("");
        binding.getAppointmentIDEditText.setText("");
    }
}