/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorBusqueda {//este guarda info en variables qu inmediatamente son recuperadas...        
    
    
    public void buscarProductosListos(long numeroPedido, ListaEnlazada<String> listaPedidoListoParaVender){
        String buscar ="SELECT * FROM Pedido WHERE numeroPedido LIKE ?";        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setDouble(1, numeroPedido);
            
            ResultSet resultadosBusqueda =instrucciones.executeQuery();            
            listaPedidoListoParaVender.establecerNOmbre(String.valueOf(ManejadorEstructuras.ultimoID));            
            
            while(resultadosBusqueda.next()){
                String datosImportantes = resultadosBusqueda.getString("codigoProducto")+","+ resultadosBusqueda.getInt("cantidad")+","+ resultadosBusqueda.getDouble("subtotal");
                
                listaPedidoListoParaVender.anadirAlFinal(datosImportantes);                
            }//por medio de esto es posible establecer el listado que se mostrará en el JLIST y que será útil para registrar la venta                                    
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Se produjo un error al intentar\nobtener los productos listos", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+ SQLe.getMessage());
        }                
    }//este método no tendría que retorna la lista???
    
    
    public ListaEnlazada<String> buscarTiendasExistentes(){
        ListaEnlazada<String> listaTiendas = new ListaEnlazada();
        String buscar = "SELECT nombre FROM Tienda ORDER BY nombre ASC";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            ResultSet resultadoBusqueda = instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){
                listaTiendas.anadirAlFinal(resultadoBusqueda.getString(1));
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al buscar\nlas tiendas existentes", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Busqueda tiendas errada -> "+ SQLe.getMessage());
            
            return null;
        }
        
        return listaTiendas;        
    }
    
    public String[] buscarEmpleado(String codigoEmpleado){//devolverá false cuando no se encuentre al empleado o falle a búsqueda[Bueno, es básicamente lo mismo xD], es decir que true solo será cuando el proceso sea totalmente exitoso
        String buscar = "SELECT nombre, codigo FROM Empleado WHERE codigo = ?";
        String datosEncontrado[] = new String[2];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setString(1, codigoEmpleado);
        
            ResultSet resultadosBusqueda = instrucciones.executeQuery();//recuerda que de todos modos cuando esta var de resultado sea nula, SQL lo toma como una excepción y por ello no exe esto... así que daría igual poner el return null en el cathc porque de todos modos devolvería un null...
            
            while(resultadosBusqueda.next()){
                datosEncontrado[0]=resultadosBusqueda.getString("codigo");
                datosEncontrado[1]=resultadosBusqueda.getString("nombre");                
            }//De todos modos solo se exe 1 vez porque su código es único :v xD jajaja
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error en la búsqueda\nde información de ingreso", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la búsqueda de datos para loggearse-> "+ SQLe.getMessage());            
            return null;
        }
    
        return datosEncontrado;
    }
    
    
    
    
}
