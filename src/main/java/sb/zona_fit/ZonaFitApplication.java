package sb.zona_fit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sb.zona_fit.modelo.Cliente;
import sb.zona_fit.servicio.IClienteServicio;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
//desactivamos esta anotacion, ya que si se deja esta sera la que levantara la aplicacion por consola
//y lo que se requiere es el levantamiento por swing
public class ZonaFitApplication implements CommandLineRunner {
    @Autowired
    private IClienteServicio clienteServicio;

    private static  final Logger logger =
            LoggerFactory.getLogger(ZonaFitApplication.class);
    String nl = System.lineSeparator();

	public static void main(String[] args) {
        logger.info("Iniciando aplicacion...");
        //levanta la fabrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
        logger.info("Aplicacion Finalizada!");
	}

    @Override
    public void run(String... args) throws Exception {
        logger.info(nl+"***ZONA FIT GYM***"+nl);
        menu();

    }
    public void menu (){
        boolean salir = false;
        while (!salir){
            Scanner sc = new Scanner(System.in);
            logger.info(nl+"""
                    1.Listar Clientes
                    2.Buscar por Id
                    3.Insertar Cliente
                    4.Modificar Cliente
                    5.Eliminar Cliente
                    6.salir
                    Elige una de las opciones: """);
            var opcion = Integer.parseInt(sc.nextLine());
            switch (opcion){
                case 1-> {
                    List<Cliente> clientes = clienteServicio.listarClientes();
                    clientes.forEach(cliente -> {
                        logger.info(cliente.toString()+nl);
                    });
                }
                case 2-> {
                    logger.info("***Busqueda por Id***"+nl);
                    logger.info("Ingresa el id del cliente a buscar: ");
                    var idCliente = Integer.parseInt(sc.nextLine());
                    Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
                    if (cliente!=null){
                        logger.info("Cliente encontrado: "+cliente+nl);
                    }else {
                        logger.info("Cliente no encontrado: "+cliente+nl);
                    }
                }
                case 3->{
                    logger.info("***Insecion de Clientes Nuevos***");
                    logger.info("Ingrese el Nombre: ");
                    var nombre = sc.nextLine();
                    logger.info("Ingrese el apellido: ");
                    var apellido = sc.nextLine();
                    logger.info("Ingrese el valor de la membresia: ");
                    var membresia = Integer.parseInt(sc.nextLine());
                    Cliente cliente = new Cliente();
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setMembresia(membresia);
                    clienteServicio.guardarCliente(cliente);
                    logger.info("Cliente agregado: "+cliente+nl);
                }

                case 4->{
                    logger.info("***Modificacion de Clientes***"+nl);
                    logger.info("Ingresa el id del cliente a modificar: ");
                    var idModificar = Integer.parseInt(sc.nextLine());
                    Cliente cliente = clienteServicio.buscarClientePorId(idModificar);
                    logger.info("Cliente sin modificar: "+cliente+nl);
                    if (cliente!=null){
                        logger.info("Nombre: ");
                        var nombre = sc.nextLine();
                        logger.info("Apellido: ");
                        var apellido = sc.nextLine();
                        logger.info("mebresia");
                        var membresia = Integer.parseInt(sc.nextLine());
                        cliente.setNombre(nombre);
                        cliente.setApellido(apellido);
                        cliente.setMembresia(membresia);
                        clienteServicio.guardarCliente(cliente);
                        logger.info("Cliente Modificado: "+cliente+nl);
                    }else {
                        logger.info("Cliente no encontrado "+cliente+nl);
                    }

                }
                case 5->{
                    logger.info("Ingrese el id del cliente: "+nl);
                    var id = Integer.parseInt(sc.nextLine());
                    var idEliminar = clienteServicio.buscarClientePorId(id);
                    if (idEliminar!=null) {
                        clienteServicio.eliminarCliente(id);
                        logger.info("Cliente "+idEliminar+" fue eliminado!"+nl);
                    }else {
                        logger.info("El id proporcionado no existe en la base de datos");
                    }
                }
                case 6->{
                    logger.info("Hasta pronto!");
                    salir = true;
                }default -> logger.info("Opcion no valida, Escoge una de las opciones del menu "+opcion+nl);
            }
        }
    }
}
