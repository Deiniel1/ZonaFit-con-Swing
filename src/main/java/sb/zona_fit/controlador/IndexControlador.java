package sb.zona_fit.controlador;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.zona_fit.modelo.Cliente;
import sb.zona_fit.servicio.IClienteServicio;

import java.util.List;

@Component
@Data
@ViewScoped//realiza que la informacion de la clase index controlador se visualice por el tiempo de vida de la vista
//es decir el tiempo en que dure en utilidad visual
public class IndexControlador {
    @Autowired
    IClienteServicio clienteServicio;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();


    }

    public void cargarDatos(){
        this.clientes = this.clienteServicio.listarClientes();
        this.clientes.forEach(cliente->{
            logger.info(cliente.toString());
        });
    }
    public void agregarCliente(){
        this.clienteSeleccionado = new Cliente();
    }

    public void guardarCliente(){
        logger.info("Cliente a guardar: "+this.clienteSeleccionado);
        //Agregar (insert)
        if(this.clienteSeleccionado.getId()==null){
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            this.clientes.add(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("cliente Agregado"));
        }
        //Modificar (update)
        else{
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Cliente Actualizado!"));
        }
        //Ocultar ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        //Actualizar tabla usando ajax
        PrimeFaces.current().ajax().update("forma-clientes:mensajes","forma-clientes:clientes-tabla");
        //Reset del objeto clienteseleccionado
        this.clienteSeleccionado=null;
    }

    public void eliminarCliente(){
        logger.info("Cliente a eliminar: "+this.clienteSeleccionado);
        this.clienteServicio.eliminarCliente(this.clienteSeleccionado.getId());
        //eliminar de la lista de cliente para no hacer refresh en bd
        this.clientes.remove(this.clienteSeleccionado);
        //Reset de cliente seleccionado
        this.clienteSeleccionado=null;
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Cliente Eliminado"));
        PrimeFaces.current().ajax().update("forma-clientes:mensajes","forma-clientes:clientes-tabla");
    }

}
