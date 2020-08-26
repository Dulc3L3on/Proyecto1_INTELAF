/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

/**
 *
 * @author phily
 */
public class Registro {
    ManejadorBusqueda buscador = new ManejadorBusqueda();    
    
    public boolean registroEmpleado(String contrasenaIngresada, String nombreUsuarioIngresado){
        String[] datosHallados=buscador.buscarEmpleado(contrasenaIngresada);
        
        if(datosHallados!=null){
            if(datosHallados[0].equals(contrasenaIngresada) && datosHallados[1].equals(nombreUsuarioIngresado)){
              return true;
            }
        }
        
        return false;
    }
    
}
