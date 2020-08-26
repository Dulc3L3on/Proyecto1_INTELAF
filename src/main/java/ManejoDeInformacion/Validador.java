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
public class Validador {
    ManejadorBusqueda buscador = new ManejadorBusqueda();    
    
    public boolean registroEmpleado(String contrasenaIngresada, String nombreUsuarioIngresado){
        String[] datosHallados=buscador.buscarEmpleado(contrasenaIngresada);
        
        if(datosHallados[0]!=null && datosHallados[1]!=null){
            if(datosHallados[0].equals(contrasenaIngresada) && datosHallados[1].equals(nombreUsuarioIngresado)){
              return true;
            }
        }
        
        return false;
    }
    
    /**
     * Este método se encarga de revisar que los primero campos del
     * arreglo que estpen dentro del rango de los datos obligatorios
     * no esté vacío para proceder a aceptarlos...
     * 
     * @param informacionIngresada
     * @param cantidadObligada
     * @return
     */
    public boolean aceptarRegistro(String[] informacionIngresada, int cantidadObligada){
        for (int datoActual = 0; datoActual < informacionIngresada.length; datoActual++) {
            if(datoActual<=cantidadObligada && informacionIngresada[datoActual]==null){
                return false;
            }//iba a poner un else para el return true, pero tendría que tener 3 así que mejor no... xD
            
        }
    
        return true;
    }
}
