package Presentacion;

import Persistencia.BaseDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class VentanaPrincipal extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton button1;
    private JLabel etiqueta;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JScrollPane scrollTabla;
    private BaseDatos bd;

    public VentanaPrincipal() {
        try {
            bd = new BaseDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se puede concetar a la base de datos", "Error Database", JOptionPane.ERROR_MESSAGE);
            exit(0);
        }
        setContentPane(panel1);
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cabeceras = {"Fecha", "nombre"};
        modelo.setColumnIdentifiers(cabeceras);
        mostrarDatos();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();



        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionAniadirFecha();
            }
        });
        tabla.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String nombreColumna = tabla.getColumnName(tabla.getTableHeader().columnAtPoint(e.getPoint()));
                    if (nombreColumna.equals("Fecha"))
                        mostrarDatosordenados(nombreColumna);
                }
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    accionAniadirFecha();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    VentanaModificar ventanaModificar = new VentanaModificar(VentanaPrincipal.this);
                }
            }
        });
    }

    public void accionAniadirFecha() {
        String texto = textField1.getText();
        try {
            bd.insertarFecha(texto);
            mostrarDatos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se pudo insertar la fecha en la base de datos", "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(VentanaPrincipal.this, "Fecha incorrecta", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
        textField1.setText("");
    }

    public void mostrarDatos() {
        ArrayList<String> arrayFechas = new ArrayList<>();
        try {
            arrayFechas = bd.actualizarDatos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se pudo actualizar los datos de la base de datos", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        modelo.setRowCount(0);
        for (String s : arrayFechas) {
            String[] cadena = {s};
            modelo.addRow(cadena);
        }
        tabla.setModel(modelo);
    }

    public void mostrarDatosordenados(String campo) {
        ArrayList<String> arrayFechas = new ArrayList<>();
        try {
            arrayFechas = bd.ordenarDatos(campo);
            System.out.println(arrayFechas.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se pudo actualizar los datos de la base de datos", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        modelo.setRowCount(0);
        for (String s : arrayFechas) {
            String[] cadena = {s};
            modelo.addRow(cadena);
        }
        tabla.setModel(modelo);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTable getTabla() {
        return tabla;
    }

    public JTextField getTextField1() {
        return textField1;
    }
}
