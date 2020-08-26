/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import BackendEntidades.Trabajador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Reporte {
    
    public ListaEnlazada<String> reportar(int tipoReporte, String[] tiendaCodigo, Date[] fechas){//en 0-> la tienda destino, 1-> el código del cliente, 2-> desde, 3-> hasta        
        
        
        
        switch(tipoReporte){//comenzaremos desed 0 puesto que así comienza el cbBx
            case 0:
                return reporte1(Trabajador.tiendaDeTrabajo);                                      
            case 1:
                return reporte2();                                    
            case 2:
                return reporte3(Trabajador.tiendaDeTrabajo, tiendaCodigo[0]);//en este caso es la tienda destino                                              
            case 3:
                return reporte4(Trabajador.tiendaDeTrabajo);                         
            case 4:
                return reporte5(tiendaCodigo[1]);//Aquí es el código cliente                        
            case 5:
                return reporte6(tiendaCodigo[1]);//aquí tb                        
            case 6:                                    
                return reporte7(fechas[0],fechas[1]);//en este reporte se necesita "desde" "hasta" para establecer el intervalo                        
            case 7:
                return reporte8();                        
            case 8:
                return reporte9();                               
        }//default no porque debe ser siempre uno de ellos, puesto que no hay nada más xD
        
        return null;
        
    }
    
    public void subreportar(String codigo, int tipoSubreporte){
        switch(tipoSubreporte){
        
        }
        
    }
    
    /**
     * Busca a los pedidos que llegarán a la tienda
     * @param codigoTiendaActual
     * @return
     */
    public ListaEnlazada<String> reporte1(String codigoTiendaActual){
        ListaEnlazada<String> listaReporte1 = new ListaEnlazada();
        
        String reporte1="SELECT numeroPedido, tiendaOrigen, fechaRealizacion, "
                + "anticipo, total, codigoCliente FROM Pedido WHERE "
                + "tiendaDestino = (?)";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte1)){
            instrucciones.setString(1, codigoTiendaActual);
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                resultadoBusqueda.getString(2)+String.valueOf(resultadoBusqueda.getDate(3))+
                 String.valueOf(resultadoBusqueda.getDouble(4))+ String.valueOf(resultadoBusqueda.getDouble(5))+
                        resultadoBusqueda.getDate(6);                        
                
                listaReporte1.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar buscar\n los pedidos que están por llegar", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar el reporte1-> "+ SQLe.getMessage());
            //podrías limpiar la lista... no es oblogatorio pero al menos así se mantendría algo íntegra la info
            listaReporte1.limpiarLIstaCompletamente();//xD mejor sí xD
        }
        
        return listaReporte1;//Recuerda que si es null deberá mostrar un msje en la lista de NADA PARA MOSTRAR...              
    }                
    
    public ListaEnlazada<String> reporte2(){//si no llegara a funcionar asumiré que es por el now, entonces lo que deberás hacer es recibir la fecha actual y mandársela a la DB
        String reporte2="SELECT numeroPedido, codigoCliente, anticipo, total, estado FROM Pedido WHERE fechaRealizacion = NOW()";
        ListaEnlazada<String> listaReporte2 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte2)){
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                resultadoBusqueda.getString(2)+String.valueOf(resultadoBusqueda.getDouble(3))+
                 String.valueOf(resultadoBusqueda.getDouble(4))+ resultadoBusqueda.getDate(5);            
                                    
                
                listaReporte2.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar buscar\n los pedidos que llegarán hoy", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en pedidos a tiempo-> "+ SQLe.getMessage());
        }
        
        return listaReporte2;        
    }
    
    /**
     * Se encarga de mostrar a todos aquellos reportes que están 
     * atrasados en llegar a su tienda Destino...
     * 
     * @param tiendaOrigen
     * @param tiendaDestino
     * @return
     */
    public ListaEnlazada<String> reporte3(String tiendaOrigen, String tiendaDestino){
         String reporte3="SELECT numeroPedido, codigoCliente, anticipo, total, estado FROM Pedido "
                 + "WHERE (DATE_ADD(fechaRealizacion, INTERVAL(SELECT tiempo FROM Tiempo_Envio WHERE"
                 + " Tiempo_Envio.IDtienda1 = ? AND Tiempo_Envio.IDtienda2 != ?) Day) < NOW())";
        ListaEnlazada<String> listaReporte3 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte3)){
            instrucciones.setString(1, tiendaOrigen);
            instrucciones.setString(2, tiendaDestino);//pues así de esta manera obtnedo a todos los tiempos 
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                resultadoBusqueda.getString(2)+String.valueOf(resultadoBusqueda.getDouble(3))+
                 String.valueOf(resultadoBusqueda.getDouble(4))+ resultadoBusqueda.getDate(5);            
                                    
                
                listaReporte3.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar buscar\n los pedidos que llegarán hoy", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en pedidos atrasados-> "+ SQLe.getMessage());
        }
        
        return listaReporte3;                    
    }
    
    /**
     * Encargado de buscar todos aquellos registros que 
     * correspondan a los pedidos que han salido de la 
     * tienda actual y aún están en tránsito...
     * @param tiendaActual
     * @return
     */
    public ListaEnlazada<String> reporte4(String tiendaActual){
        String reporte4="SELECT numeroPedido, codigoCliente, anticipo, total, estado FROM Pedido "
                 + "WHERE estado!= ? AND tiendaDestino = ?";
        ListaEnlazada<String> listaReporte4 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte4)){
            instrucciones.setString(1, "listo");
            instrucciones.setString(2, tiendaActual);
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                resultadoBusqueda.getString(2)+String.valueOf(resultadoBusqueda.getDouble(3))+
                 String.valueOf(resultadoBusqueda.getDouble(4))+ resultadoBusqueda.getDate(5);                                            
                
                listaReporte4.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar buscar\n los pedidos que partieron de esta\ntienda hacia otras...", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar pedidos en transito desde la actual-> "+ SQLe.getMessage());
        }
        
        return listaReporte4;                            
    }
    
    /**
     * Encargado de mostrar todas las compras de un 
     * cliente...
     * @param codigoCliente
     * @return
     */
    public ListaEnlazada<String> reporte5(String codigoCliente){
        String reporte5="SELECT * FROM Venta WHERE codigoCliente = ?";
        ListaEnlazada<String> listaReporte5 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte5)){
            instrucciones.setString(1, codigoCliente);            
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                resultadoBusqueda.getString(2)+resultadoBusqueda.getString(3)+
                        String.valueOf(resultadoBusqueda.getDate(4))+
                 String.valueOf(resultadoBusqueda.getDouble(5));
                
                listaReporte5.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar\nbuscar todas las compras del cliente", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar todas las compras de un cliente-> "+ SQLe.getMessage());
        }
        
        return listaReporte5;                            
    }
    
    /**
     * Encargado de hallar los pedidos en 
     * curso de un cliente específico...
     * recuerda que el codigo del cliente 
     * será recibido a traves de un txtF o 
     * txt
     * @param codigoCliente
     * @return
     */
    public ListaEnlazada<String> reporte6(String codigoCliente){
        String reporte6="SELECT numeroPedido, anticipo, total, estado FROM Pedido WHERE codigoCliente = ? AND estado != ?";
        ListaEnlazada<String> listaReporte6 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte6)){
            instrucciones.setString(1, codigoCliente);            
            instrucciones.setString(2, "listo");//no vayas a olvidar que los estados se escribirán en MINUS siempre! o al menos es este proyecto xD
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = String.valueOf(resultadoBusqueda.getInt(1))+
                String.valueOf(resultadoBusqueda.getDouble(2))+String.valueOf(resultadoBusqueda.getDouble(3))+
                 resultadoBusqueda.getString(4);
                
                listaReporte6.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar la\nlista de pedidos del cliente", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar listado de pedidos del cliente-> "+ SQLe.getMessage());
        }
        
        return listaReporte6;                                         
    }
    
    /**
     * Busca y encuentra los 10 más vendidos :3
     *
     * @param fechaLimiteInferior
     * @param fechaLimiteSuperior
     * @return
     */
    public ListaEnlazada<String> reporte7(Date fechaLimiteInferior, Date fechaLimiteSuperior){
        String reporte7="SELECT codigo, nombre FROM Producto WHERE codigo=(SELECT codigo FROM Tienda_Producto"
                + "WHERE (fecha, BETWEEN ? AND ?) ORDER BY vecesVendido DSC LIMIT 10))";
        ListaEnlazada<String> listaReporte7 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte7)){
            instrucciones.setDate(1, (java.sql.Date) fechaLimiteInferior);            
            instrucciones.setDate(2, (java.sql.Date) fechaLimiteSuperior);//no vayas a olvidar que los estados se escribirán en MINUS siempre! o al menos es este proyecto xD
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultadoBusqueda.getString(1) + resultadoBusqueda.getString(2);                 
                
                listaReporte7.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los\npedidos nunca vendidos\nhasta el momento", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar productos nunca vendidos-> "+ SQLe.getMessage());
        }
        
        return listaReporte7;                            
        
    }
    
    public ListaEnlazada<String> reporte8(){
        ListaEnlazada<String> listareporte8 = new ListaEnlazada();
        String consulta = "SELECT codigo, nombre FROM Tienda";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(consulta)){
            ResultSet resultados = instrucciones.executeQuery();                        
            
            while(resultados.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultados.getString(1) + resultados.getString(2);                              
                
                listareporte8.anadirAlFinal(datosUnificados);
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar\n el producto vendido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los productos + vendidos por Tiendas"+ SQLe.getMessage());                    
        }
        
        return listareporte8;    
    }
    
    /**
     * Halla a los 10 producto más vendidos por
     * tienda en un intervalo de tiempo dado...
     * 
     * @param fechaLimiteInferior
     * @param fechaLimiteSuperior
     * @param codigoTiendaSeleccionada
     * @return
     */
    public ListaEnlazada<String> subreporte8(Date fechaLimiteInferior, Date fechaLimiteSuperior, String codigoTiendaSeleccionada){
        String subreporte8="SELECT codigo, nombre FROM Producto WHERE codigo=(SELECT codigo FROM Tienda_Producto"
                + "WHERE ((fecha, BETWEEN ? AND ?) AND codigoTienda= ?) ORDER BY vecesVendido DSC LIMIT 10))";
        ListaEnlazada<String> listaReporte8 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(subreporte8)){
            instrucciones.setDate(1, (java.sql.Date) fechaLimiteInferior);            
            instrucciones.setDate(2, (java.sql.Date) fechaLimiteSuperior);//no vayas a olvidar que los estados se escribirán en MINUS siempre! o al menos es este proyecto xD
            instrucciones.setString(3, codigoTiendaSeleccionada);
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultadoBusqueda.getString(1) + resultadoBusqueda.getString(2);//codigo y nombre xD             
                
                listaReporte8.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los\n10 más vendidos de la tienda seleccionada\nen un intervalo de tiempo definido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los 10 más vendidos por tienda en un tiempo dado "+ SQLe.getMessage());
        }
        
        return listaReporte8; 
    }
    
     /**
     * Encargado de la búsque de los productos nunca vendidos
     * en general
     * @return
     */
    public ListaEnlazada<String> reporte9(){
        ListaEnlazada<String> listareporte9 = new ListaEnlazada();
        String consulta = "SELECT codigo, nombre, direccion FROM Tienda";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(consulta)){
            ResultSet resultados = instrucciones.executeQuery();                        
            
            while(resultados.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultados.getString(1) + resultados.getString(2)+resultados.getString(3);                              
                
                listareporte9.anadirAlFinal(datosUnificados);
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar\n productos nunca vendidos", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los productos nunca vendidos "+ SQLe.getMessage());                    
        }
        
        return listareporte9;    
    }
    
    public ListaEnlazada<String> subreporte9(String codigoTienda){
        String reporte9="SELECT codigoTienda, nombre, fabricante, precio FROM Tienda_Producto INNER JOIN Producto ON Tienda_Producto.codigoProducto = Producto.codigo"                
                + "WHERE codigoTienda = ? AND vecesVendido = ?";//este código de la tienda por ser subreporte lo obtiene del elemento seleccionado...
        ListaEnlazada<String> listaReporte9 = new ListaEnlazada();
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(reporte9)){
            instrucciones.setString(1, codigoTienda);
            instrucciones.setInt(2, 0);
            
            ResultSet resultadoBusqueda= instrucciones.executeQuery();
            
            while(resultadoBusqueda.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultadoBusqueda.getString(1) + resultadoBusqueda.getString(2)+
                        resultadoBusqueda.getString(3)+ String.valueOf(resultadoBusqueda.getDouble(4));//codigo y nombre xD             
                
                listaReporte9.anadirAlFinal(datosUnificados);
            }//fin del while que se encarga de devocver la columna de cada uno de los registros almacenados en las tuplas...            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los\n10 más vendidos de la tienda seleccionada\nen un intervalo de tiempo definido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los 10 más vendidos por tienda en un tiempo dado "+ SQLe.getMessage());
        }
        
        return listaReporte9; 
    }
    
    /**
     * Devuelve los resultado de un producto 
     * pedido con el mismo número de código
     * @param numeroPedido
     */
    public ListaEnlazada<String> subreporteCompartidoPP(int numeroPedido){//como hay uno que se repite en casi todas... solo lo nombras según el nombre
        //:v de la tabla consultada
        ListaEnlazada<String> listaReporteCompartido = new ListaEnlazada();
        String consulta = "SELECT codigoProducto, cantidad, subtotal, FROM Producto_Pedido WHERE numeroPedido = ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(consulta)){
            ResultSet resultados = instrucciones.executeQuery();
            
            instrucciones.setInt(1, numeroPedido);
            
            while(resultados.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultados.getString(1) + String.valueOf(resultados.getInt(2))+
                        String.valueOf(resultados.getDouble(3));//codigo y nombre xD             
                
                listaReporteCompartido.anadirAlFinal(datosUnificados);
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al obtner informacion\nde los productos pedidos", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los producto pedidos "+ SQLe.getMessage());                    
        }
        
        return listaReporteCompartido;
    
    }
    
    public ListaEnlazada<String> subreporte5(int codigoVenta){
        ListaEnlazada<String> listaSubreporte5 = new ListaEnlazada();
        String consulta = "SELECT codigoProducto, cantidad FROM Venta_Producto WHERE numeroVenta = ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(consulta)){
            ResultSet resultados = instrucciones.executeQuery();
            
            instrucciones.setInt(1, codigoVenta);
            
            while(resultados.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = String.valueOf(resultados.getInt(1)) + String.valueOf(resultados.getInt(2));                                
                
                listaSubreporte5.anadirAlFinal(datosUnificados);
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar\n el producto vendido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar los producto vendidos "+ SQLe.getMessage());                    
        }
        
        return listaSubreporte5;
    
    }
    
    public ListaEnlazada<String> subreporte6(int codigoPedido){
        ListaEnlazada<String> listaSubreporte6 = new ListaEnlazada();
        String consulta = "SELECT codigoProducto, cantidad, subtotal FROM Producto_Pedido WHERE numeroPedido = ?";
        
        try(PreparedStatement instrucciones = ManejadorDB.conexion.prepareStatement(consulta)){
            ResultSet resultados = instrucciones.executeQuery();
            
            instrucciones.setInt(1, codigoPedido);
            
            while(resultados.next()){//recuerda que el igualar es un decir, sin importar que era o que eres ahora eres ESTO y pum cambia de valor xD
                String datosUnificados = resultados.getString(1) + String.valueOf(resultados.getInt(2))+
                        String.valueOf(resultados.getDouble(3));                                
                
                listaSubreporte6.anadirAlFinal(datosUnificados);
            }
            
        
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar\n el producto vendido", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al buscar productos de\nlos pedidos en curso de un cliente "+ SQLe.getMessage());                    
        }
        
        return listaSubreporte6;    
    }
    
    
    
    
   
    
    
}
