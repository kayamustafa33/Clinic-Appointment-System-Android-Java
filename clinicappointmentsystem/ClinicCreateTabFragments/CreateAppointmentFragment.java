package com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.databinding.FragmentCreateAppointmentBinding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateAppointmentFragment extends Fragment {

    private FragmentCreateAppointmentBinding binding;
    private ArrayAdapter<String> adapterItems;
    private ArrayList<String> arrayList,statusArraylist;
    private ArrayAdapter<String> statusAdapterItems;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;

    private String DoctorName = "",Status = "",DoctorID = "",currentDoctorName="";
    private DatePickerDialog datePickerDialog;
    private String date = "";
    private Date RDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateAppointmentBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        statusArraylist = new ArrayList<>();
        adapterItems = new ArrayAdapter<String>(binding.getRoot().getContext(),R.layout.list_item,arrayList);
        binding.autoCompleteTextView.setAdapter(adapterItems);

        statusArraylist.add("Active");
        statusArraylist.add("Passive");
        statusAdapterItems = new ArrayAdapter<String>(binding.getRoot().getContext(),R.layout.list_item,statusArraylist);
        binding.setStatusText.setAdapter(statusAdapterItems);

        getDoctorName();
        getAppointmentDate();
        initDatePicker();

        binding.datePickerBtn.setText(getTodayDate());

        binding.autoCompleteTextView.setOnItemClickListener((parent, view, position, id) ->
                DoctorName = parent.getItemAtPosition(position).toString());

        binding.setStatusText.setOnItemClickListener((parent, view, position, id) ->
            Status = parent.getItemAtPosition(position).toString());

        createAppointment();

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


    private void createAppointment(){
        binding.createAppointmentClicked.setOnClickListener(item -> {

            String appointment_id = binding.createAppointmentIDEditText.getText().toString().trim();
            String patient_id = binding.createPatientIdEditText.getText().toString().trim();
            String health_issue = binding.createAppointmentHealthIssueEditText.getText().toString().trim();

            AppointmentInfo appointment = new AppointmentInfo();
            appointment.createAppointment(binding.getRoot().getContext(),appointment_id,getDoctorID(),patient_id,health_issue,Status,date);


        });
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
}