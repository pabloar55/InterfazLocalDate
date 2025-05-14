package Persistencia;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BaseDatos {
    public BaseDatos (){
        try {
            Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", "");
            Statement stmt = c.createStatement();
            stmt.execute("create database if not exists pruebaFechasProg");
            c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
            stmt= c.createStatement();
            stmt.execute("create table if not exists fechas(fecha date)");

            GregorianCalendar calendario = new GregorianCalendar(2003, Calendar.JANUARY, 23);
            DateFormat d = null;
            d.setCalendar(calendario);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertarFecha(String stringFecha){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(stringFecha, formatter);
        Connection c;
        try {
            c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
            PreparedStatement preparedStatement = c.prepareStatement("insert into fechas values(?)");
            preparedStatement.setDate(1, java.sql.Date.valueOf(fecha));
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> actualizarDatos(){
    ArrayList<String> arrayList= new ArrayList<>();
    return arrayList;
    }
}
