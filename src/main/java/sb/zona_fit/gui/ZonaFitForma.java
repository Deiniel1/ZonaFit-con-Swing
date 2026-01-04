package sb.zona_fit.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.zona_fit.modelo.Cliente;
import sb.zona_fit.servicio.ClienteServicio;
import sb.zona_fit.servicio.IClienteServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//@component
public class ZonaFitForma extends JFrame{
    private JPanel PanelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    //La anotacion no va aqui porque se estaria inyectando despues de la creacion del servicio
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

@Autowired //Se hace la anotacion aqui antes del contructor
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
    clientesTabla.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            cargarClienteSeleccionado();
        }
    });
    guardarButton.addActionListener(e -> {
        guardarCliente();

    });
    eliminarButton.addActionListener(e -> {
        eliminarRegistro();

    });
    limpiarButton.addActionListener(e -> {
        limpiarForms();
    });
}
    public void iniciarForma(){
    setContentPane(PanelPrincipal);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//cierra el programa al cerrar la ventana
    setSize(900,700);//da dimensiones en la ventana
    setLocationRelativeTo(null);//centra la ventana
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0,4){//evita la edicion de valores en la celda de la tabla
            @Override
            public boolean isCellEditable (int row, int column){
                return false;
            }
        };//instanciamos el objeto con las dimensiones de nuestra tabla
        String[] cabeceros = {"Id","Nombre","Apellido","Membresia"};//El arreglo actuara como contenedor de cabeceros para las columnas
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);//Coge cada elemento del arreglo y lo posiciona como una columna
        this.clientesTabla = new JTable(tablaModeloClientes);//Crea una nueva tabla con las configuraciones ya planteadas
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//restringir seleccion a seleccion singular en la tabla
        //cargar lista de clientes
        listarClientes();
    }
    private void listarClientes(){
    this.tablaModeloClientes.setRowCount(0);
    var clientes = this.clienteServicio.listarClientes();
    clientes.forEach(cliente->{
        Object [] renglonCliente = {
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getMembresia()
        };
        this.tablaModeloClientes.addRow(renglonCliente);
    });
    }
    public void guardarCliente(){
    if(nombreTexto.getText().equals("")){
        mostrarMensaje("Proporciona un nombre");
        nombreTexto.requestFocusInWindow();
        return;
    }
    if (membresiaTexto.getText().equals("")){
        mostrarMensaje("Proporciona una membresia");
        membresiaTexto.requestFocusInWindow();
        return;
    }
    //Recuperar los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());

        Cliente cliente = new Cliente(this.idCliente,nombre,apellido,membresia);
        clienteServicio.guardarCliente(cliente);//insetar/modificar
        if(this.idCliente==null){
            mostrarMensaje("Cliente agregado con exito!");
        }else {
            mostrarMensaje("El cliente fue actualizado!");
        }
        limpiarForms();
        listarClientes();


    }
    private void eliminarRegistro(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon!=-1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            clienteServicio.eliminarCliente(idCliente);
            mostrarMensaje("Cliente con id "+this.idCliente.toString()+" eliminado");
            limpiarForms();
            listarClientes();

       }
       else {
           mostrarMensaje("Debe seleccionar un registro a eliminar!");
        }


    }
    private void cargarClienteSeleccionado(){
    var renglon = clientesTabla.getSelectedRow();
    var id = clientesTabla.getModel().getValueAt(renglon,0).toString();//se llama el renglon y columna
    this.idCliente = Integer.parseInt(id); //almacena el id en una variable, y asi no se muestra en formulario de interfaz
    var nombre = clientesTabla.getModel().getValueAt(renglon,1).toString();
    this.nombreTexto.setText(nombre);
    var apellido = clientesTabla.getModel().getValueAt(renglon,2).toString();
    this.apellidoTexto.setText(apellido);
    var membresia = clientesTabla.getModel().getValueAt(renglon,3).toString();
    this.membresiaTexto.setText(membresia);

    }
    private void limpiarForms(){
    nombreTexto.setText("");
    apellidoTexto.setText("");
    membresiaTexto.setText("");
    //limpiar id seleccionado
    this.idCliente = null;
    //deseleccionar la seleccion en tabla
    this.clientesTabla.getSelectionModel().clearSelection();
    }
    private void mostrarMensaje(String mensaje){
    JOptionPane.showMessageDialog(this,mensaje);

    }
}
