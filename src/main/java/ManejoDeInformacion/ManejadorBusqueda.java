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

    public String[] determinarExistenciaProducto(String codigo){
        String buscarExistencia ="SELECT * FROM Producto WHERE codigo = ?";
        String[] informacionHallada = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscarExistencia)){
            instrucciones.setString(1, codigo);
            
            ResultSet resultados=instrucciones.executeQuery();
            
            while(resultados.next()){//realmente debería haber solo una existencia así que...este while está de más...
                informacionHallada[0]=resultados.getString(1);
                informacionHallada[1]=resultados.getString(2);
                informacionHallada[2]=resultados.getString(3);
                informacionHallada[3]=resultados.getString(4);
                informacionHallada[4]=resultados.getString(5);                                
            }//Reucerda, si ya está en la DB no tendría porque tener excepciones que eviten la conversion...
            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al\ndeterminar la existencia del\nproducto", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al crear un producto -> "+ SQLe.getMessage());
            return null;
        }
        
        return informacionHallada;//Allá en el frontend cuando recibas un valor!= null mostrarás un msje diciendo que ya existía y por ello marcarás una var para que cuando acepte, se haga el update    
    }
    
    public String[] determinarExistenciaEmpleado(String codigo){
        String buscarExistencia ="SELECT * FROM Empleado WHERE codigo = ?";        
        String[] informacionHallada = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscarExistencia)){
            ResultSet resultados=instrucciones.executeQuery();
            
            while(resultados.next()){//realmente debería haber solo una existencia así que...este while está de más...
                informacionHallada[0]=resultados.getString(1);
                informacionHallada[1]=resultados.getString(2);
                informacionHallada[2]=resultados.getString(3);
                informacionHallada[3]=resultados.getString(4);
                informacionHallada[4]=resultados.getString(5);                                
                informacionHallada[5]=resultados.getString(6);                                
                informacionHallada[6]=resultados.getString(7);                                                
            }//Reucerda, si ya está en la DB no tendría porque tener excepciones que eviten la conversion...
            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al\ndeterminar la existencia del\nempleado", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al crear un empleado -> "+ SQLe.getMessage());
            return null;
        }
        
        return informacionHallada;//Allá en el frontend cuando recibas un valor!= null mostrarás un msje diciendo que ya existía y por ello marcarás una var para que cuando acepte, se haga el update                
    }
    
    public String[] determinarExistenciaTienda(String codigo){
        String buscarExistencia ="SELECT * FROM Tienda WHERE codigo = ?";        
        String[] informacionHallada = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscarExistencia)){
            ResultSet resultados=instrucciones.executeQuery();
            
            while(resultados.next()){//realmente debería haber solo una existencia así que...este while está de más...
                informacionHallada[0]=resultados.getString(1);
                informacionHallada[1]=resultados.getString(2);
                informacionHallada[2]=resultados.getString(3);
                informacionHallada[3]=resultados.getString(4);
                informacionHallada[4]=resultados.getString(5);                                
                informacionHallada[5]=resultados.getString(6);                                
                informacionHallada[6]=resultados.getString(7);                                                
            }//Reucerda, si ya está en la DB no tendría porque tener excepciones que eviten la conversion...
            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al\ndeterminar la existencia de\nla tienda", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al crear una tienda -> "+ SQLe.getMessage());
            return null;
        }
        
        return informacionHallada;//Allá en el frontend cuando recibas un valor!= null mostrarás un msje diciendo que ya existía y por ello marcarás una var para que cuando acepte, se haga el update                
    }
    
    public String[] determinarExistenciaCliente(String nit){
        String buscarExistencia ="SELECT * FROM Empleado WHERE codigo = ?";        
        String[] informacionHallada = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscarExistencia)){
            ResultSet resultados=instrucciones.executeQuery();
            
            while(resultados.next()){//realmente debería haber solo una existencia así que...este while está de más...
                informacionHallada[0]=resultados.getString(1);
                informacionHallada[1]=resultados.getString(2);
                informacionHallada[2]=resultados.getString(3);
                informacionHallada[3]=resultados.getString(4);
                informacionHallada[4]=resultados.getString(5);                                
                informacionHallada[5]=resultados.getString(6);                                
                informacionHallada[6]=resultados.getString(7);                                                
            }//Reucerda, si ya está en la DB no tendría porque tener excepciones que eviten la conversion...            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al\ndeterminar la existencia del\ncliente", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al crear un cliente -> "+ SQLe.getMessage());
            return null;
        }
        
        return informacionHallada;//Allá en el frontend cuando recibas un valor!= null mostrarás un msje diciendo que ya existía y por ello marcarás una var para que cuando acepte, se haga el update                    
    }                
    
    
    public String[] buscarProductoEnEstaTienda(String codigo, String codigoTienda){//UTIL PARA LA MODIFICACIÓN DEL PRODUCTO...
        String buscar ="SELECT*FROM Tienda_Producto WHERE codigoProducto = ? AND codigoTienda = ?";
        String[] informacionHallada = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            instrucciones.setString(1, codigo);
            instrucciones.setString(2, codigoTienda);
            
            ResultSet resultados=instrucciones.executeQuery();
            
            while(resultados.next()){//realmente debería haber solo una existencia así que...este while está de más...
                informacionHallada[0]=resultados.getString(1);//codigoTienda
                informacionHallada[1]=resultados.getString(2);//codigoProducto
                informacionHallada[2]=String.valueOf(resultados.getInt(3));//unicades
                informacionHallada[3]=String.valueOf(resultados.getDouble(4));//precio
                informacionHallada[4]=resultados.getString(5);//ceves vendido                                
                informacionHallada[5]=resultados.getString(6);//cdadReservada                 
                                                       
            }//Reucerda, si ya está en la DB no tendría porque tener excepciones que eviten la conversion...            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al\ndeterminar la existencia del\ncliente", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al crear un cliente -> "+ SQLe.getMessage());
            return null;
        }
        
        return informacionHallada;
        
    }
    
    /**
     * Este y el método de abajo son útiles para ser empleados
     * en la búsque de los productos hecha en el MC, donde al
     * obtener de una vez el resultado, solo es necesario que 
     * devuelvan al Result para que la tabla pueda adquirirlos
     * en su variable goba y así app los respectivos cambios..
     * 
     * @param codigoProducto
     * @param codigoTienda
     * @return
     */
    
    public ResultSet buscarProductoEnTIendaActual(String codigoProducto, String codigoTienda){//Esto es para la interfaz modoCajero
        String buscar ="SELECT codigo, nombre, fabricante, cantidadReservada,"
                + " precio, codigoTienda FROM Tienda_Producto INNER JOIN "
                + "Producto ON Tienda_Producto.codigoProducto = Producto.codigo "
                + "AND unidades>0 AND Tienda_Producto.codigoProducto= ? AND "
                + "Tienda_Producto.codigoTienda= ?";        
        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setString(1, codigoProducto);
            instrucciones.setString(2, codigoTienda);
            
            ResultSet resultados = instrucciones.executeQuery();
        
            return resultados;
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ha surgido un error en la búsqueda\ndel producto en la tienda actual", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la búsqueda del producto en tienda actual -> "+ SQLe.getMessage());            
        }
        
        return null;//Aunque no debería ser así... pero bueno tb se vale xD
    }
    
    public ResultSet buscarProductoEnDemasTiendas(String codigoProducto, String codigoTienda){//Esto es para la interfaz modoCajero
        String buscar ="SELECT codigo, nombre, fabricante, cantidadReservada,"
                + " precio, codigoTienda FROM Tienda_Producto INNER JOIN "
                + "Producto ON Tienda_Producto.codigoProducto = Producto.codigo "
                + "AND unidades>0 AND Tienda_Producto.codigoProducto= ? AND "
                + "Tienda_Producto.codigoTienda != ?";        
        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setString(1, codigoProducto);
            instrucciones.setString(2, codigoTienda);
            
            ResultSet resultados = instrucciones.executeQuery();
        
            return resultados;
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ha surgido un error en la búsqueda\ndel producto en la tienda actual", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la búsqueda del producto en tienda actual -> "+ SQLe.getMessage());            
        }
        
        return null;//Aunque no debería ser así... pero bueno tb se vale xD
    }            
    
    public ListaEnlazada<String> buscarProductoPorNombre(String nombre){
        ListaEnlazada<String> listaProductosHallados = new ListaEnlazada();
        String buscar ="SELECT codigo, nombre, fabricante, precio, vecesVendido FROM Producto INNER JOIN Tienda_Producto WHERE"
                + " Producto.codigo = Tienda_Producto.codigoProducto AND nombre LIKE ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            instrucciones.setString(1, "%"+nombre+"%");
            
            ResultSet resultado = instrucciones.executeQuery();
            
            while(resultado.next()){
                String definicionProducto = resultado.getString(1)+"  "+resultado.getString(2)+"  "+
                        resultado.getString(3)+"  "+String.valueOf(resultado.getDouble(4))+"  "+ 
                        String.valueOf(resultado.getInt(5));
                
                listaProductosHallados.anadirAlFinal(definicionProducto);                            
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nbuscar coincidencias con\nla parte especificada", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar coincidencias por nombre -> "+ SQLe.getMessage());
            return null;
        }
        
        return listaProductosHallados;
    }
    
    public ListaEnlazada<String> buscarProductoPorFabricante(String fabricante){
     ListaEnlazada<String> listaProductosHallados = new ListaEnlazada();
        String buscar ="SELECT codigo, nombre, fabricante, precio, vecesVendido FROM Producto INNER JOIN Tienda_Producto WHERE"
                + " Producto.codigo = Tienda_Producto.codigoProducto AND fabricante LIKE ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            instrucciones.setString(1, "%"+fabricante+"%");
            
            ResultSet resultado = instrucciones.executeQuery();
            
            while(resultado.next()){
                String definicionProducto = resultado.getString(1)+"  "+resultado.getString(2)+"  "+
                        resultado.getString(3)+"  "+String.valueOf(resultado.getDouble(4))+"  "+ 
                        String.valueOf(resultado.getInt(5));
                
                listaProductosHallados.anadirAlFinal(definicionProducto);                            
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nbuscar coincidencias con\nla parte especificada", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar coincidencias por fabricante -> "+ SQLe.getMessage());
            return null;
        }
        
        return listaProductosHallados;
    }
    
    public ListaEnlazada<String> buscarProductoPorCodigo(String codigo){
     ListaEnlazada<String> listaProductosHallados = new ListaEnlazada();
        String buscar ="SELECT codigo, nombre, fabricante, precio, vecesVendido FROM Producto INNER JOIN Tienda_Producto WHERE"
                + " Producto.codigo = Tienda_Producto.codigoProducto AND codigo LIKE ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            instrucciones.setString(1, codigo);//como no dijeron que debía ser una parte...
            
            ResultSet resultado = instrucciones.executeQuery();
            
            while(resultado.next()){
                String definicionProducto = resultado.getString(1)+"  "+resultado.getString(2)+"  "+
                        resultado.getString(3)+"  "+String.valueOf(resultado.getDouble(4))+"  "+ 
                        String.valueOf(resultado.getInt(5));
                
                listaProductosHallados.anadirAlFinal(definicionProducto);                            
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nbuscar coincidencias con\nla parte especificada", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar coincidencias por codigo -> "+ SQLe.getMessage());
            return null;
        }
        
        return listaProductosHallados;   
    
    }
    
    
    public void buscarProductosListos(int numeroPedido, ListaEnlazada<String> listaPedidoListoParaVender){//Aún no doy exactamente su función, creo que lo que querías hacer era hallar todos aquellos producto que tuvieran el mismo código y estuvieran listos para ser vendidos...
        String buscar ="SELECT * FROM Producto_Pedido WHERE numeroPedido LIKE ?";//antes tenías aquí a pedido, pero me parece razonable que sea PP... SIP ESTE MÉTODO ES PARA BUSCAR LOS PEDIDOS INDIV Y ASÍ PROCESAR LOS PEDIDOS GENERALES LISTOS...
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setInt(1, numeroPedido);
            
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
    
    public boolean actualizarProducto(String[] nuevosDatos){
     String actualizar="UPDATE Producto SET nombre= ?, fabricante = ?, descripcion = ?, garantia = ?)";        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(actualizar)){            
            instrucciones.setString(2, nuevosDatos[1]);//nombre
            instrucciones.setString(3, nuevosDatos[2]);//fab
            instrucciones.setString(4, nuevosDatos[3]);//desc
            instrucciones.setString(5, nuevosDatos[4]);//gar  
            
            instrucciones.executeUpdate();                                                                        
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar los datos del empleado", "", JOptionPane.ERROR_MESSAGE);            
            System.out.println("\n-> "+SQLex.getMessage());            
            return false;
        }
        
        return true;    
    
    }
    
    public boolean actualizarTiendaProducto(String[] datosActualizados){
       String actualizar = "UPDATE Tienda_Producto SET unidades =?, precio = ?";
        
       try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(actualizar)){            
            instrucciones.setString(1, datosActualizados[6]);
            instrucciones.setString(2, datosActualizados[5]);
            
            instrucciones.executeUpdate();           
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nagregar producto al inventario", actualizar, JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+ SQLe.getMessage());
            return false;
        }
        
        return true;    
    }
    
    public boolean actualizarEmpleado(String[] datosActualizados){
        String buscar = "UPDATE Empleado SET nombre = ?, telefono = ?, nit = ?, dpi =?, correo =?, direccion = ? ";
        String datosEncontrado[] = new String[2];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(buscar)){
            
            instrucciones.setString(1, datosActualizados[0]);//nombre
            instrucciones.setString(2, datosActualizados[2]);//telefono
            instrucciones.setString(3, datosActualizados[4]);//nit
            instrucciones.setString(4, datosActualizados[3]);//dpi
            instrucciones.setString(5, datosActualizados[5]);
            instrucciones.setString(6, datosActualizados[6]);                        
        
            instrucciones.executeUpdate();//recuerda que de todos modos cuando esta var de resultado sea nula, SQL lo toma como una excepción y por ello no exe esto... así que daría igual poner el return null en el cathc porque de todos modos devolvería un null...            
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error en la búsqueda\nde información de ingreso", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la búsqueda de datos para loggearse-> "+ SQLe.getMessage());            
            return false;
        }
    
        return true;
    }
    
    public boolean actualizarCliente(String[] datosActualizados){//Reucerda que estos booleans, los tenías para saber si cerraba la ventana o la dejabas abierta... [esto para todos los de creación y modif]
      
        String actualizar="UPDATE Cliente SET nombre = ?, telefono = ?, dpi = ?, correo = ?, direccion = ?";
        
        try(PreparedStatement instrucciones  = ManejadorDB.conexion.prepareStatement(actualizar)){
        
            instrucciones.setString(1, datosActualizados[0]);
            instrucciones.setString(2, datosActualizados[2]);
            instrucciones.setString(3, datosActualizados[4]);
            instrucciones.setString(4, datosActualizados[5]);
            instrucciones.setString(4, datosActualizados[6]);
            
            instrucciones.executeUpdate();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar el cliente", "", JOptionPane.ERROR_MESSAGE);
           System.out.println("\n"+ex.getMessage());
            return false;
        }
    
            return true;
        
    }
    
    
    
    
    
    
}
