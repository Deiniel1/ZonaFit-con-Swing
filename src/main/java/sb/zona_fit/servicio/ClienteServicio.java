package sb.zona_fit.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.zona_fit.modelo.Cliente;
import sb.zona_fit.repositorio.ClienteRepositorio;

import java.util.List;
@Service
public class ClienteServicio implements IClienteServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    @Override
    public Cliente buscarClientePorId(Integer id) {
        Cliente cliente = clienteRepositorio.findById(id).orElse(null);
        return cliente;
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);//si el cliente es null inserta, si existe actualiza

    }

    @Override
    public void eliminarCliente(Integer id) {
        clienteRepositorio.deleteById(id);

    }
}
