/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontendTrabajadores;

import BackendTrabajadores.Cajero;
import BackendTrabajadores.Usuario;
import ManejoDeInformacion.ManejadorDB;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Listeners {//esto es practicemente forntendn así que no habrá problema el emplear aquí elementos del frontend, porque son compañeros
   Usuario usuario;//lo hago de esta manera puesto que para el click derecho me será útil pero el tipo Gerente por el hecho de que el va a poder modificar cliente, tiendas, trabajadores y productos [para este último tendrás que hacer la conversión a USUARIO]
   ManejadorDB manejadorDB = new ManejadorDB();   
   
   /*
    Este método será empleado para aquellas tablas que en su cuerpo se den a notar los registros encontrados de una consulta específica
    de tal manera que cuando se obtnega el registro, se mande a mostrar en el txtF y se imponga el límite de venta
   */    
    public String[] paraMouseClicked(String codigoTiendaActual, String codigoProducto, int fila, int columna){//será empleado para las tablas donde se hagan consultas, donde 
        //siempre siempre xD cuando seleccione una fila de una tabla de búsqueda será para obtener el código del producto y el de la tienda [puesto que será empleado solo para la tabla que muestre productos xD]
        String cadenaDescripcion[]= new String[2];
        usuario = new Cajero();   
        String[] codigoTiendas = new String [2];
        
       try { 
            codigoTiendas[0]=codigoTiendaActual;
            codigoTiendas[1]=(String)ModoCajero.modeloConsulta.getValueAt(fila, columna);                       
            
            ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(manejadorDB.construirConsulta(usuario.buscarProducto(1, usuario.definirLugarBusqueda(codigoTiendas), codigoProducto)));//siempre será 1 el lugar de búsqueda el parámetro en este método porque es una búsqueda específica, ya sea que se haya hecho en la tienda actual o en otras la búsqueda general, pero por el hecho de que no en ambas muestro el código de la tienda, es necesario que sepa que tipo de búsqueda hice para saber de donde debo ir a traer dicho código  // por el hecho de que es algo repetido            
            cadenaDescripcion[1]=(ManejadorDB.resultadosConsulta.getString(1));
            
            ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(manejadorDB.construirConsulta(usuario.obtenerCantidad(1, usuario.definirLugarBusqueda(codigoTiendas), codigoProducto)));//se establece el límite del spinner por medio de la cantidad que en la DB se encuentra           
            cadenaDescripcion[0]=String.valueOf(ManejadorDB.resultadosConsulta.getInt(1));//pues solo tiene una columna xD
           
            return cadenaDescripcion;
            
       } catch (SQLException ex) {
           System.out.println("\nNO SE PUDO OBTENER LA DESCRIPCIÓN DEL PRODUCTO\n");
       }                        
       
       cadenaDescripcion[0]="0";
       return cadenaDescripcion;//si hay problemas entonces no pasará nada... porque el txt recibirá nada y el spinner se quedará con su límite 0
    }       
    
   
    public void paraMousePressed(){
        //Este será útil para desplegar el menú en las tablas de ventas y pedidos donde se tendrán las opciones de eliminar una transacción hecha ó de cambiarle la cantidad
        //recuerda que en este proceso debes hacer que la cantidad que en la fila clickeada estaba, se le "sume" a la cantidad de la DB [la suma no se hace en la DB sino en Java
        //ella solo juega con los datos que tiene, no opera con ellos...
    }
}


