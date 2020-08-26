/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorEstructuras {//estos métodos serán útiles para las acciones con las estructuras que no tengan que ver con los reportes!  
    //Aún no se ha discutido si es mejor que aquí se haga de una vez la llamada al manejador o es mejor desde el usuario que esté interactuando en ese momento...
    public static int ultimoID;                                           //LO QUE SE HARÁ PARA AGREGAR E MANERA ADECUADA LOS ERRORES AL LISTADO SERÁ LLAMAR A DICHO OBJ DESDE LA ENTIDAD QUE GENERÓ LA INFORMACIÓN QUE NO FUE ACEPTADA... CREO QUE LA MEJOR MANERA PARA TRABAJAR CON DICHOS ERRORES SERÍA POR MEDIO DEL LANZAMIENTO, DE TLA MANERA QUE DEL OTRO LADO, DE LO QUE MÁS AFUERA ESTÉ TB SE ENTERE DE LO SUCEDIDO... USA THROWS!!!
                                                             //COMO HABRÁ UN MÉTODO DE TRAAJADOR PARA HACER LA INSERCIÓN DE MANERA MANUAL Y DESDE EL ARCHIVO...  POR TL MOTIVO NO TENDRÉ QUE RECIBIR UNA VAR PARA SABER CUANDO REALAMENTE ME INSTERRESA HACER ESE LISTADO...
    

    
    public void eliminarProductoPedido(long numeroPedido){
        String eliminar ="DELETE FROM Pedido WHERE numeroPedido = ?";
        
        try(PreparedStatement instruccionesEliminacion = ManejadorDB.conexion.prepareStatement(eliminar)){
            
            instruccionesEliminacion.setLong(1, numeroPedido);            
            
            instruccionesEliminacion.execute();
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgio un error al intentar \nvender el pedido listo", "", JOptionPane.ERROR_MESSAGE);//digo esto para no ser tan explícita en que se elimina el pedido...
            System.out.println("\n"+SQLe.getMessage());
        }
        
        JOptionPane.showMessageDialog(null, "Se ha vendido el pedido correctamente", "", JOptionPane.INFORMATION_MESSAGE);
    
    }//haciendo esto se eliminan automaticamente los registros que se encuentren en cualquier otra tabla que dependa de esta y tenga ON_DELETE CASCADE xD :)
    
    //util para la creación...
    public boolean insertarDatosCompletosEnCliente(String[] datosCliente){
        String seleccionar ="INSERT INTO Cliente VALUES (?,?,?,?,?,?,?)";        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(seleccionar)){
            instrucciones.setString(1, datosCliente[0]);
            instrucciones.setString(2, datosCliente[1]);
            instrucciones.setString(3, datosCliente[2]);
            instrucciones.setString(4, datosCliente[3]);
            instrucciones.setString(5, datosCliente[4]);
            instrucciones.setString(6, datosCliente[5]);                                               
            instrucciones.setString(7, datosCliente[6]); 
            
            instrucciones.executeUpdate();                                                                        
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar los datos del cliente", "", JOptionPane.ERROR_MESSAGE);            
            System.out.println("\n-> "+SQLex.getMessage());            
            return false;
        }
        
        return true;
    }
    
        //util para la creación...
        public boolean insertarDatosCompletosEnEmpleado(String[] datosEmpleado){//creo que lo que se hubiera podido hacer es insertar el nombre de la tabla, pero ya no así que se quede...
        String seleccionar ="INSERT INTO Cliente VALUES (?,?,?,?,?,?,?)";        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(seleccionar)){
            instrucciones.setString(1, datosEmpleado[0]);
            instrucciones.setString(2, datosEmpleado[1]);
            instrucciones.setString(3, datosEmpleado[2]);
            instrucciones.setString(4, datosEmpleado[3]);
            instrucciones.setString(5, datosEmpleado[4]);
            instrucciones.setString(6, datosEmpleado[5]);                                               
            instrucciones.setString(7, datosEmpleado[6]); 
            
            instrucciones.executeUpdate();                                                                        
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar los datos del empleado", "", JOptionPane.ERROR_MESSAGE);            
            System.out.println("\n-> "+SQLex.getMessage());            
            return false;
        }
        
        return true;
    }
        
    public boolean insertarDatosCompletosEnTienda(String[] datosTienda){        
        String insertar ="INSERT INTO Tienda VALUES (?,?,?,?,?,?,?)";        
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            instrucciones.setString(1, datosTienda[0]);
            instrucciones.setString(2, datosTienda[1]);
            instrucciones.setString(3, datosTienda[2]);
            instrucciones.setString(4, datosTienda[3]);
            instrucciones.setString(5, datosTienda[4]);
            instrucciones.setString(6, datosTienda[5]);                                               
            instrucciones.setString(7, datosTienda[6]); 
            
            instrucciones.executeUpdate();                                                                        
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar los datos del empleado", "", JOptionPane.ERROR_MESSAGE);            
            System.out.println("\n-> "+SQLex.getMessage());            
            return false;
        }
        
        return true;               
    }
    
    public boolean insertarDatosCompletosEnProducto(String[] datosProducto){
        String insertar="INSERT INTO Producto VALUES (?,?,?,?,?)";
        String datosTienda[] = new String[5];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            instrucciones.setString(1, datosTienda[0]);//codigo
            instrucciones.setString(2, datosTienda[1]);//nombre
            instrucciones.setString(3, datosTienda[2]);//fab
            instrucciones.setString(4, datosTienda[3]);//desc
            instrucciones.setString(5, datosTienda[4]);//gar  
            
            instrucciones.executeUpdate();                                                                        
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar los datos del empleado", "", JOptionPane.ERROR_MESSAGE);            
            System.out.println("\n-> "+SQLex.getMessage());            
            return false;
        }
        
        return true;               
    }
    
    public String[] obtnerDatosDeCLiente(String nit){
        String seleccionar ="SELECT nombre, direccion, credito, FROM Cliente";
        String[] resultados = new String[3];
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(seleccionar)){
            instrucciones.setString(1, nit);//mando el dato que está representando el comodín
            ResultSet resultadosSeleccion=instrucciones.executeQuery();            
            
            while(resultadosSeleccion.next()){//Aunque la verdad esto no es necesario, porque si app este método es porque sé que no hay más registros que cumplan con lo anterior
                for (int columnaActual = 0; columnaActual < 3; columnaActual++) {
                    resultados[columnaActual]=resultadosSeleccion.getString(columnaActual+1);                                
                }//fin del for por medio del cual obtnego los datos de las columnas...                
            }
            
            instrucciones.executeQuery();   
            
            
        }catch(SQLException SQLex){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nobtner los datos del cliente", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n-> "+SQLex.getMessage());            
        }
        
        return resultados;
        
    }
  
    
    public boolean insertarEnCliente(String nombre, String nit, String telefono, String credito){
        
        String insertar="INSERT INTO Cliente (nombre, nit, telefono, credito) VALUES (?,?,?,?)";
        
        try(PreparedStatement instrucciones  = ManejadorDB.conexion.prepareStatement(insertar)){
        
            instrucciones.setString(1, nombre);
            instrucciones.setString(2, nit);
            instrucciones.setString(3, telefono);
            instrucciones.setString(4, credito);
            
            instrucciones.executeUpdate();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar el cliente", "", JOptionPane.ERROR_MESSAGE);
           System.out.println("\n"+ex.getMessage());
            return false;
        }
    
            return true;
    }
    
    public boolean insertarEnEmpleado(String nombre, String codigo, String telefono, String dpi){
        
        String insertar="INSERT INTO Empleado (nombre, codigo, telefono, dpi) VALUES (?,?,?,?)";
        
        try(PreparedStatement instrucciones  = ManejadorDB.conexion.prepareStatement(insertar)){
        
            instrucciones.setString(1, nombre);
            instrucciones.setString(2, codigo);
            instrucciones.setString(3, telefono);
            instrucciones.setString(4, dpi);
            
            instrucciones.executeUpdate();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar el empleado", "", JOptionPane.ERROR_MESSAGE);
           System.out.println("\n"+ex.getMessage());
            return false;
        }
    
            return true;
    }
    
    
     public boolean insertarEnTienda(String nombre, String direccion, String codigo, String telefono){
        
        String insertar="INSERT INTO Tienda (nombre, direccion, codigo, telefono1) VALUES (?,?,?,?)";
        
        try(PreparedStatement instrucciones  = ManejadorDB.conexion.prepareStatement(insertar)){
        
            instrucciones.setString(1, nombre);
            instrucciones.setString(2, direccion);
            instrucciones.setString(3, codigo);
            instrucciones.setString(4, telefono);
            
            instrucciones.executeUpdate();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar datos de Tienda", "", JOptionPane.ERROR_MESSAGE);
           System.out.println("\n"+ex.getMessage());
            return false;
        }
    
            return true;
    }
     
    /*UTIL PARA LA CREACIÓN DE UN PRODUCTO*/
    public boolean insertarTiendaProducto(String codigoTienda, String codigoProducto, int unidades, double precio, int vecesVendido, int vecesReservado){//Debe hacerse junto con la creación del prod
        String insertar = "INSERT INTO Tienda_Producto (codigoTienda, codigoProducto, unidades, precio, vecesVendido, cantidadReservada)VALUES (?,?,?,?,?,?)";
        
       try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            
            instrucciones.setString(1, codigoTienda);
            instrucciones.setString(2, codigoProducto);
            instrucciones.setInt(3, unidades);
            instrucciones.setDouble(4, precio);            
            instrucciones.setInt(5, vecesVendido); 
            instrucciones.setInt(6, vecesReservado); 
            
            instrucciones.executeUpdate();           
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nagregar producto al inventario", insertar, JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+ SQLe.getMessage());
            return false;
        }
        
        return true;        
    }      
     
    /**
     * UTIL PARA CREACIÓN
     * @param tienda1
     * @param tienda2
     * @param tiempo
     * @return
     */
    public boolean insertarEnTiempo_Envio(String tienda1, String tienda2, int tiempo){//util para la creacion...
         String insetar = "INSERT INTO Tiempo_Envio VALUES (?,?,?)";
         
         try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insetar)){
             
             instrucciones.setString(1, tienda1);
             instrucciones.setString(2, tienda2);
             instrucciones.setInt(3, tiempo);

             instrucciones.executeUpdate();
         
         }catch(SQLException SQLe){
             JOptionPane.showMessageDialog(null, "Se ha producido un error al intentar\nregistrar la información de tiempo de envío", "", JOptionPane.ERROR_MESSAGE);
             System.out.println("\n"+SQLe.getMessage());
             return false;
         }
         
         return true;
     }
     
     public boolean insertarEnProducto(String nombre, String fabricante, String codigo){
        
        String insertar="INSERT INTO Producto (nombre, fabricante, codigo) VALUES (?,?,?)";
        
        try(PreparedStatement instrucciones  = ManejadorDB.conexion.prepareStatement(insertar)){
        
            instrucciones.setString(1, nombre);
            instrucciones.setString(2, fabricante);
            instrucciones.setString(3, codigo);            
            
            instrucciones.executeUpdate();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nregistrar el producto", "", JOptionPane.ERROR_MESSAGE);
           System.out.println("\n"+ex.getMessage());
            return false;
        }
    
            return true;
    }            

    public boolean insertarEnVenta(String codigoCliente, String codigoTienda, double total){//el lugar de llamada es para que así cuando se generen errores al leer el archivo, no se muetren cada vez los JOP... pero esto aún se encuentra en discusión...
        String insertar ="INSERT INTO Venta (codigoCliente, codigoTienda, fecha, total) VALUES (?,?,?,?)" ;
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)){
            Date fechayHora = new Date();
            java.sql.Date fecha = new java.sql.Date(fechayHora.getTime());//solo se almacena la fecha... la hora se deja perdida xD
            
            instrucciones.setString(1, codigoCliente);
            instrucciones.setString(2, codigoTienda);
            instrucciones.setDate(3, fecha);
            instrucciones.setDouble(4, total);            
        
            instrucciones.executeUpdate();
            
            ResultSet resultado= instrucciones.getGeneratedKeys();
            
            ultimoID=resultado.getInt(1);
            
        }catch(SQLException SQLe){//recuerda que debes poner una condi para que se sepa si se debe mostrar o no el JOP, puesto que si hubieran muchos errores en el arch a leer, sería nada bonito xD                        
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nregistrar la venta", "", JOptionPane.ERROR_MESSAGE);                     
            System.out.println("\n"+ SQLe.getMessage());
            return false;
        }                        
        
        return true;
    }
   
    /**
     * Este método es el empleado para insertar pero
     * cuando no se tiene nada nadita en la dataBase xD
     * @param numeroPedido
     * @param idTiendaOrigen
     * @param idTiendaDestino
     * @param codigoCliente
     * @param fecha
     * @param total
     * @param anticipo
     * @return
     */
    public boolean insertarEnPedidoDesdeArchivo(int numeroPedido, String idTiendaOrigen, String idTiendaDestino, String codigoCliente, java.sql.Date fecha, double total , double anticipo){//el estado no lo mando porque media vez se crea un pedido en camino irá...
        String insertar = "INSERT INTO Pedido (numeroPedido, tiendaOrigen, tiendaDestino, codigoCliente, fechaRealizacion, total, anticipo) VALUES(?,?,?,?,?,?,?)";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)){            
            
            instrucciones.setInt(1, numeroPedido);//talvez tenga que cambiar el tipo de columna en sql...
            instrucciones.setString(2, idTiendaOrigen);
            instrucciones.setString(3, idTiendaDestino);
            instrucciones.setString(4, codigoCliente);            
            instrucciones.setDate(5, fecha);//listo xD ya tiene la fecha de realización xD            
            instrucciones.setDouble(6, total);
            instrucciones.setDouble(7, anticipo);                        
            
            instrucciones.executeUpdate();//aquí no debo buscar al último id por el hecho de que hasta este momento la columna recibe valores, mas no los genera...         
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nregistrar el pedido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+SQLe.getMessage());
            return false;
        }
        
        return true;
    }//recuerda que debes manejar errores de casteo... ya se maneja xD allá en el manejador DB esto por el hecho de la inserción desde un archivo
    
    
    public boolean insertarEnPedido(String idTiendaOrigen, String idTiendaDestino, String codigoCliente, java.sql.Date fecha, double total , double anticipo){//el estado no lo mando porque media vez se crea un pedido en camino irá...
        String insertar = "INSERT INTO Pedido (tiendaOrigen, tiendaDestino, codigoCliente, fechaRealizacion, total, anticipo) VALUES(?,?,?,?,?,?)";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)){
            //Date fechayHora = new Date();
            //java.sql.Date fecha = new java.sql.Date(fechayHora.getTime());//ahora lo que se hará es que por cada pedido generado se mandará la fecha, esto por el hecho que en el archivo se encuentra la fecha y para no andar haciendo otro método
            
            instrucciones.setString(1, idTiendaOrigen);
            instrucciones.setString(2, idTiendaDestino);
            instrucciones.setString(3, codigoCliente);            
            instrucciones.setDate(4, fecha);//listo xD ya tiene la fecha de realización xD            
            instrucciones.setDouble(5, total);
            instrucciones.setDouble(6, anticipo);                        
            
            instrucciones.executeUpdate();
            
            ResultSet resultado = instrucciones.getGeneratedKeys();
            
            ultimoID = resultado.getInt(1);
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nregistrar el pedido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+SQLe.getMessage());
            return false;
        }
        
        return true;
    }//recuerda que debes manejar errores de casteo... ya se maneja xD allá en el manejador DB esto por el hecho de la inserción desde un archivo
    
    
    public boolean insertarEnProductoPedido(int idReciente, String codigoProducto, int cantidadPedida, double subtotal){
        String insertar = "INSERT INTO Producto_Pedido VALUES (?,?,?,?)";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            
            instrucciones.setInt(1, idReciente);//Aunque podría mandar de una vez al ID que aquí ya está registrado
            instrucciones.setString(2, codigoProducto);
            instrucciones.setInt(3, cantidadPedida);
            instrucciones.setDouble(4, subtotal);
            
            instrucciones.executeUpdate();            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nregistrar el producto pedido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+SQLe.getMessage());
            return false;
        }
        
        return true;
    }
    
    
      public boolean insertarEnProductoVendido(int idReciente, String codigoProducto, int cantidadVendida){
        String insertar = "INSERT INTO Venta_Producto VALUES (?,?,?,?)";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            
            instrucciones.setInt(1, ultimoID);//si se puede hacer esto ya que el valor está en esta misma clase...
            instrucciones.setString(2, codigoProducto);
            instrucciones.setInt(3, cantidadVendida);            
            
            instrucciones.executeUpdate();            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\nregistrar el producto pedido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+SQLe.getMessage());
            return false;
        }
        
        return true;
    }    

    /**
     * Método empleado para hacer el incremento de la cantidad
     * que se ha reservado un producto al momento de leer desde
     * el archivo...
     * 
     * @param cantidadReservada
     * @param codigoTienda
     * @param codigoProducto
     * @return
     */
    public boolean actualizarCantidadReservada(int cantidadReservada, String codigoTienda, String codigoProducto){
        String insertar = "UPDATE Tienda_Producto SET cantidadReservada = cantidadReservada + ? WHERE codigoTienda = ? AND codigoProducto = ?";//si no funciona le quitas esos atribs de las tablas y ya... eso implicaría que no deberás tener la funcionalidad de elimniar y modificar cantidad en las transacciones...
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(insertar)){
            
            instrucciones.setInt(1, cantidadReservada);                     
            instrucciones.setString(2, codigoTienda);
            instrucciones.setString(3, codigoProducto);
            
            instrucciones.executeUpdate();           
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\ncambiar las unidades del producto", insertar, JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+ SQLe.getMessage());
            return false;
        }
        
        return true;        
    }      
    
    public boolean eliminarRegistro(int tipoTabla,String condicion){
        String[] nombresTablas={"Pedido", "Producto"};//puesto que estas tienen dependencia y que dichas dependientes emplean CASCADE, problama no habrá xD
        
        
        String eliminar ="DELETE FROM "+nombresTablas[tipoTabla-1] +" WHERE numeroPedido = ?";
        
        try(PreparedStatement instruccionesEliminacion = ManejadorDB.conexion.prepareStatement(eliminar)){
            
            if(tipoTabla==1){
                instruccionesEliminacion.setLong(1, Long.parseLong(condicion));
            }else{
                instruccionesEliminacion.setString(1, condicion);
            }         
            
            instruccionesEliminacion.executeUpdate();
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgio un error al intentar \nvender el pedido listo", "", JOptionPane.ERROR_MESSAGE);//digo esto para no ser tan explícita en que se elimina el pedido...
            System.out.println("\n"+ SQLe.getMessage());
            return false;
        }
    
        return true;
    }
    
    /**
     * Este método será útil para que se aplique
     * después de haber leído el archivo, de tal 
     * manera que cuando se comience a trabajar 
     * de manera manual ya esté el atributo con 
     * dicha caracterísitica auto_INCREMENT XD
     */
    public void alterarTabla(){
        String alteracion="ALTER TABLE Pedido MODIFY numeroPedido INTEGER NOT NULL AUTO_INCREMENT";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(alteracion)){
            
            instrucciones.execute();
            
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "ocurrió un error al intentar\nalterar la tabla", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n"+ SQLe.getMessage());
        }
    }     
 

    
    
    
}
