package Persistencia;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Clase de base datos (persistencia)
 * @version 2.0
 * @author Pablo Armas Bossé
 */

public class BaseDatos {
    public BaseDatos() throws SQLException {
            inicializarBD();
    }

    public void insertarFecha(String stringFecha) throws SQLException, ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(stringFecha, formatter);
       /* Period anios = Period.between(fecha, LocalDate.now());
        System.out.println(anios.getYears());*/ //obtener los años
        Connection c;
            c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
            PreparedStatement preparedStatement = c.prepareStatement("insert into fechas values(?)");
            preparedStatement.setDate(1, java.sql.Date.valueOf(fecha));
            preparedStatement.execute();

    }
    public void inicializarBD () throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", "");
        Statement stmt = c.createStatement();
        stmt.execute("create database if not exists pruebaFechasProg");
        c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
        stmt = c.createStatement();
        stmt.execute("create table if not exists fechas(fecha date)");
    }

    public ArrayList<String> actualizarDatos() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        Connection c;
            c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
            Statement st = c.createStatement();
            st.executeQuery("select * from fechas");
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                String fecha = String.valueOf(rs.getDate(1));
                arrayList.add(fecha);
            }
        return arrayList;
    }
    public ArrayList<String> ordenarDatos(String campo) throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        Connection c;
        c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebaFechasProg", "root", "");
        Statement st = c.createStatement();
        if (campo.equals("Fecha"))
            st.executeQuery("select * from fechas order by Fecha" );
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            String fecha = String.valueOf(rs.getDate(1));
            System.out.println(fecha);
            arrayList.add(fecha);
        }
        return arrayList;
    }
}
