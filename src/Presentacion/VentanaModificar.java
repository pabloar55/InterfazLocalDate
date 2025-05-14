package Presentacion;

import javax.swing.*;

public class VentanaModificar extends JFrame{
    private JPanel panel;
    private JButton botonModificar;
    private JTextField modificaLaFechaTextField;
    private VentanaPrincipal ventanaPrincipal;
    public VentanaModificar(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal= ventanaPrincipal;
        setTitle("VentanaModificar");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        int fila = ventanaPrincipal.getTabla().getSelectedRow();
        modificaLaFechaTextField.setText(ventanaPrincipal.getModelo().getValueAt(fila, 0).toString());
        setVisible(true);

    }

}
