package sb.zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import sb.zona_fit.gui.ZonaFitForma;

import javax.swing.*;
@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        //modo oscuro
        FlatDarculaLaf.setup();
        //instanciasion de la fabrica de spring
        ConfigurableApplicationContext contextoSpring = new SpringApplicationBuilder(ZonaFitSwing.class)
                //configuracion para hacer saber que no es una aplicativo web
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
        //crear un objeto de swing
        SwingUtilities.invokeLater(()->{
            ZonaFitForma zonaFitForma = contextoSpring.getBean(ZonaFitForma.class);
            zonaFitForma.setVisible(true);
        });
    }
}
