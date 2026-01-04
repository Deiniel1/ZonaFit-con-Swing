package sb.zona_fit.controlador;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
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

}
