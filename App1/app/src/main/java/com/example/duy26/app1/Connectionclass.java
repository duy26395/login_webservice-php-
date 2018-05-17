package com.example.duy26.app1;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionclass {
//    String ip = "192.168.1.11:1433";
    String ip = "192.168.1.6:1433";
    String db = "App1_DB";
    String db_user = "ahihi";
    String password = "123";
    String classname = "net.sourceforge.jtds.jdbc.Driver";


    @SuppressLint("NewApi")
    public Connection CONN()
    {
        StrictMode.ThreadPolicy policy  = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String conURL = null;
        try
        {
            Class.forName(classname);
            conURL = "jdbc:jtds:sqlserver://" + ip + ";" + "databaseName=" + db + ";user="
                    + db_user + ";password=" + password + ";";
            connection = DriverManager.getConnection(conURL);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }

}


