/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendEntidades;

import ManejoDeInformacion.ManejadorEstructuras;

/**
 *
 * @author phily
 */
public class Gerente extends Trabajador{
    ManejadorEstructuras manejadorEstructuras = new ManejadorEstructuras();
    
    public Gerente(){
    
    }
//    
//    @Override
//    public void buscar(int tipoBusqueda, String codigo, String lugarDeBusqueda){
//    
//    }
//    
//    @Override
//    public void buscarProducto(){
//    
//    }
//    
//    @Override
//    public void buscarPedido(){
//    
//    }
//    
    
    
    public void registrarDatosTrabajador(String[] datosTrabajador){//DEBE QUEDAR INTACTO ESTE ARREGLO, puesto que el método con el preparedStatement recibe la ubicación del wildCard correspondiente, lo cual vendría a encajar con el númeor de col de este arreglo, NO LO OLVIDES       
       
       super.registrarDatosUsuario(datosTrabajador, "Empleado");
   }
    
    public void buscarCliente(){
    
    }
    
    public void buscarTienda(){
    
    }
    
    public void buscarTrabajador(){
    
    }
    
    public void registrarTienda(){
    
    }
    
    public void registrarProducto(){
    
    }    
    
    public void registrarTrabajador(){
    
    }    
    
    public void modificarTIenda(){
    
    }
    
    public void modificarProducto(){
    
    }
    
    public void modificarCliente(){
    
    }
    
    public void modificarTrabajador(){
    
    }
    
    
    
    
}
