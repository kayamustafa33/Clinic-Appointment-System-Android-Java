package com.mustafa.clinicappointmentsystem.Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToServer {

    Connection connection = null;

    private final String host = "10.0.2.2";

    private final String database = "ClinicSystemDB";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "15749206880";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public ConnectToServer() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
    }

    private void connect()
    {
        Thread thread = new Thread(() -> {
            try
            {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, pass);
                status = true;
                System.out.println("connected:" + status);
            }
            catch (Exception e)
            {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
