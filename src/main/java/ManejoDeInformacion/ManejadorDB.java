/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author phily
 */
public class ManejadorDB {//será instanciado por primera vez en el HOME y por el hecho de que debe manejarse la var con la que se creó la conexión esta se creará como STATIC en home y será llamada como tal en las demás clases de las hijas entidad donde se le necesite para etablecer las instrucciones tal y como ellas las requiere    
    private ManejadorEstructuras estructura = new ManejadorEstructuras();
    
    public static Connection conexion;//se encarga de conectar o desconectar al usuario actual con la DB para interactuar con las estructuras poseedoras de la información
    public static Statement instrucciones;//se encarga de aquirir las sentencias correctas para cumplir con la solicitud hecha por el cliente a la DB
    public static ResultSet resultadosConsulta;
    public static ResultSetMetaData metadatosResultado;
    
    //Aquí va la llamada al driver... por lo visto sigue manteniendo la misma sintaxis que en el libro...
    static final String URL_BASEDEDATOS="jdbc:mysql://localhost:3306/INTELAF?useSSL=false";//creo que esto debe cambiar, y debe tener además de esto el nomre de
    static final String NOMBRE_USUARIO="Leon";
    static final String CONTRASENIA="userL3on@";    
    
    public static int estaVacia=0;
    
    String[] palabrasParaInsertar ={"INSERT INTO ", "VALUES ", "ON DUPLICATE KEY UPDATE "};
    String[] palabrasParaBorrarTuplas = {"DELETE FROM ", "WHERE "};
    
    
    public void conectarConDB(){
        try {
            ManejadorDB.conexion = DriverManager.getConnection(URL_BASEDEDATOS, NOMBRE_USUARIO, CONTRASENIA);                      
          
        }catch(SQLException SQLe){
            JOptionPane.showMessageDialog(null, "No pudo establecerse la conexion", "", JOptionPane.ERROR_MESSAGE);
        }
        
        //esto debe ingresarse para las instrucciones de la tabla...
    
    }
    
    /**
     * Se encrga de recibir línea por línea los datos a ingresar 
     * verificando la cabecera para así conocer donde es que se 
     * requiere la presencia de esa info, tiene controladas las
     * excepciones de casteo y cualquiera que pueda ser lanzada 
     * por mysql, de tal manera que al finalizar el intento de
     * inserción proceda a agregar dicha línea fallada al listado
     * que se encarga de mostrar las fallas... no requiere devolver
     * un dato en específico porque aquí mismo se encarga de los 
     * errores provocados...
     * 
     * @param lineaARegistrar
     * @param numeroLinea
     */
    public String[] llenarBaseDeDatos(String[] lineaARegistrar, int numeroLinea){//si surgen problemas ya tengo almacenada la línea que los provocó, por tal razón podré alamacenarla como erronea
        boolean procesoExitoso=false, fueExitosoElsiguiente=true;
        String columnasInsercion;
        String[] informacionOperacion ={"1",""};
        
        try{//ahorita intentaremos sin tener en cuenta el tamaño de la cadena, luego si da error que no se haya controlado, entonces haces la implementación de la solución
        switch(lineaARegistrar[0]){//todos llevan aún el encabezado!!!    
                case "TIENDA"://no requiere de casteos...                                                        
                    procesoExitoso=estructura.insertarEnTienda(lineaARegistrar[1].trim(), lineaARegistrar[2].trim(), lineaARegistrar[3].trim(), lineaARegistrar[3].trim());                                                
                 break;
             
                case "TIEMPO"://Requiere de casteo a tipo int por el tiempo... aunque no debería...
                    procesoExitoso=estructura.insertarEnTiempo_Envio(lineaARegistrar[1].trim(), lineaARegistrar[2].trim(), Integer.parseInt(lineaARegistrar[3].trim()));//listo xD
                    
                 break;
             
                case "PRODUCTO"://no requiere de casteos...para PRODUCTO EN SI, para su dependiente SI es necesario
                    String codigoProducto=lineaARegistrar[3];
                    lineaARegistrar[3]=lineaARegistrar[1];//Ahora el espacio de nombre lo ocupa el código...
                    lineaARegistrar[1]=codigoProducto;//y ahora si ya tiene el primer espacio luego del encabezado el código, esto par aconcordar con el método que se encaga de armar la súperLIsta xD que permitirá hacer los análisis debidos...
                    
                    informacionOperacion[0]="3";//y así se podrá analizar posteriormente...                    
                 break;
           
                case "EMPLEADO"://no requiere de casteos...
                    columnasInsercion=" (nombre, codigo, telefono, dpi) ";
                    procesoExitoso=estructura.insertarEnEmpleado(lineaARegistrar[1].trim(), lineaARegistrar[2].trim(), lineaARegistrar[3].trim(), lineaARegistrar[4].trim());                
                 break;
                          
                case "CLIENTE"://no requiere de casteos... el crédito lo dejé como string                    
                    procesoExitoso=estructura.insertarEnCliente(lineaARegistrar[1].trim(), lineaARegistrar[2].trim(), lineaARegistrar[3].trim(), lineaARegistrar[4].trim());                    
                 break;
             
                case "PEDIDO":                                
                    //en este es donde debo hacer las respectivas revisiones para saber si lo ingresado es lógico, pues si no
                    //cuando quiera trabajar con un pedido con el tipo de error en los totales, no podre, por culpa de esos errores :| xD...
                    informacionOperacion[0]="2";//para que sepa que lo enviado será para que lo almacene en el listado bajo la mira                                                               
            }                
        
        }catch(ClassCastException | NumberFormatException e){//si es necesario se buscará la clase que corresponda a una excepción por haberse pasado de la cantidad total admitida, pero esto deberá ser en el manejador de estructuras, porque allá es donde está el preparedStatement
        //no se mostrará nada sino hasta el final...la lista de errores horrores xD
        
        }finally{
            if(procesoExitoso && fueExitosoElsiguiente){//debe ser así porque al tener la segunda var por defecto <true> se asegura que cuando el segundo falle, se sepa que todo el proceso falló... pero recuerda que solo cuando falle el 2do proceso en pedido, será cuando se elimine por completo el pedido, porque es todo o nada, ya que cad uno de los pedidos individuales forman en conjunto al pedido general...
                informacionOperacion[0]="0";//para que sepa que no debe hacer nada                
           }else{
                informacionOperacion[1]=ManejadorInformacion.devolverString(lineaARegistrar);
                System.out.println(informacionOperacion[1]);
            }//si llegara a haber fallo, puede que sea porque confundiste las condiciones... 
        }        
        
        return informacionOperacion;        
    }
    
    /**
     * Este método es el empleado para llenar la tabla de pedidos
     * a partir de la información del archivo a leer...
     * 
     * @param informacionPedidos
     * @param totalPorPedido
     * @return
     */
    public ListaEnlazada<String> llenarPedidos(ListaEnlazada<String> informacionPedidos, double totalPorPedido){//sip, debe recibir el dato por el hecho de que ya se hizo allá la sumatoria, lo que en este momento se necesita...
        boolean procesoExitoso=false;        
        boolean fueExitosoElsiguiente=false;
                
        //todavía lleva el encabezado!!!
        Nodo<String> nodoAuxiliar = informacionPedidos.obtnerPrimerNodo();
        String[] lineaARegistrar= nodoAuxiliar.contenido.split(",");//la posición de donde se halen los datos genreales, es arbitraria...
        Date date;
        
        date=ManejadorInformacion.convertirStringAUtilDate(lineaARegistrar[4]);//se convierte el string a DATE
        procesoExitoso=ManejadorInformacion.conversionExitosa;//Esto lo coloco por el hecho de que puede devolver una excepción si es que no contiene los caracteres que necesita para la conversión...            
        
        if(procesoExitoso){//me permito hacer esto por el hecho de que toda la info recibida en conjunto porman un todo, por ello, o estan todas o no lo están...
            procesoExitoso=estructura.insertarEnPedidoDesdeArchivo(Integer.parseInt(informacionPedidos.obtenerNombre().trim()), lineaARegistrar[2].trim(), lineaARegistrar[3].trim(), lineaARegistrar[5].trim(),ManejadorInformacion.devolverSQLDate(date.getTime()), totalPorPedido, Double.parseDouble(lineaARegistrar[9].trim()));//este totalPorPedido es el total que se le debe informar a una tienda dada                      
            System.out.println("EL error ocurrió en la inserción del pedido general");
            
            if(procesoExitoso){
                for (int i = 0; i < informacionPedidos.darTamanio(); i++) {//pues porque aquí se encuentra el total de productos individuales pedidos...
                    fueExitosoElsiguiente=estructura.insertarEnProductoPedido(Integer.parseInt(informacionPedidos.obtenerNombre().trim()), lineaARegistrar[6].trim(), Integer.parseInt(lineaARegistrar[7].trim()), Double.parseDouble(lineaARegistrar[8].trim()));
                     estructura.actualizarCantidadReservada(Integer.parseInt(lineaARegistrar[7].trim()), lineaARegistrar[2].trim(), lineaARegistrar[6].trim());//es en la posición 2 primero porque aún está el encabezado y segundo porque esa es la tienda de origen...
                    
                    if(!fueExitosoElsiguiente){
                        break;
                    }
                
                    nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
                    if(nodoAuxiliar!=null){
                        lineaARegistrar= nodoAuxiliar.contenido.split(",");//así recorro a todos los pedidos individuales que en el listado existan...                                
                    }
                    
                }//recuerda que el número del pedido lo tienes tanto en el nombre como en el espacio 1 del arreglo [por la presencia de los encabezados...] y aquí no puedes emplear el método de estructura para obtner el ID puesto que no es generada por la DB!!!
            }                                    
        }
        
        if(!procesoExitoso || !fueExitosoElsiguiente){
            estructura.eliminarProductoPedido(Long.parseLong(informacionPedidos.obtenerNombre()));//solo borro el del pedido general, porque si surgió algo malo con un pedido individual luego de haber creado a uno entonces al borrar de pedido también esos se eliminarán...
            return informacionPedidos;
        }//pues con cualquiera de los dos, debería eliminar en PEDIDO
        
        return null;
    }
    
    public ListaEnlazada<String> llenarInventario(ListaEnlazada<String> listaProductos){//xD es decir llenar la tabla de producto y tienda_producto, recuerda que aquí la lista que recibes es del MISMO tipo de producto!!!
        boolean procesoExitoso=false;        
        boolean fueExitosoElsiguiente=false;
        ListaEnlazada<String> listadoINdividualErrado = new ListaEnlazada();
                
        //todavía lleva el encabezado!!!
        Nodo<String> nodoAuxiliar = listaProductos.obtnerPrimerNodo();
        String[] lineaARegistrar= nodoAuxiliar.contenido.split(",");//la posición de donde se halen los datos genreales, es arbitraria...                        
        
        //no es necesario revisar si la cantidad es 0, porque de todos modos cuando se quiera busacar al producto, no se mostrará porque ya se tiene esa restricción impuesta...
        if(Integer.parseInt(lineaARegistrar[4])>0){
            procesoExitoso=estructura.insertarEnProducto(lineaARegistrar[3].trim(), lineaARegistrar[2].trim(), lineaARegistrar[1].trim());                       
        }//por si acaso hicieron haberías en la DB anterior...            
            if(procesoExitoso){
                for (int i = 0; i < listaProductos.darTamanio(); i++) {//pues porque aquí se encuentra el total de productos individuales pedidos...
                   fueExitosoElsiguiente=estructura.insertarTiendaProducto(lineaARegistrar[6].trim(), lineaARegistrar[1].trim(), Integer.parseInt(lineaARegistrar[4].trim()), Double.parseDouble(lineaARegistrar[5].trim()), 0, 0);//ya que no quiere dejarse poner el default...                   
                    
                    if(!fueExitosoElsiguiente){
                        listadoINdividualErrado.anadirAlFinal(ManejadorInformacion.devolverString(lineaARegistrar));
                    }
                
                    nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//al final este nodo sigueinte será null, pero no provocará ningún daño por el hehco de que no se le pedirá que haga algo...
                    if(nodoAuxiliar!=null){
                        lineaARegistrar= nodoAuxiliar.contenido.split(",");//así recorro a todos los productos individuales que en el listado existan...            
                    }
                    
                }//recuerda que el número del pedido lo tienes tanto en el nombre como en el espacio 1 del arreglo [por la presencia de los encabezados...] y aquí no puedes emplear el método de estructura para obtner el ID puesto que no es generada por la DB!!!
            }                                           
        
        if(!procesoExitoso){            
            return listaProductos;//pues ninguno de los que sean de ese tipo estrarán buenos...porque no existirá su padre...
        }else if(!fueExitosoElsiguiente){
            return listadoINdividualErrado;                
        }//pues quiere decir que solo hubieron errores que no afectan de manera global sino solamente individual, y eso es lo que retornaré para almacenar en los errores generales
        
        return null;
    }
    
    
    public String[] eliminarEncabezados(String[] datosCompletos){
        String[] datosUtiles= new String[datosCompletos.length-1];//Pues tendrá el tamaño menos el encabezado
        
        for (int datos = 0; datos < datosUtiles.length; datos++) {
            datosUtiles[datos]=datosCompletos[datos+1];
        }
    
        return datosUtiles;
    }
    
    public String construirConsulta(String[] cadenaDeInformacion){
        String[] palabrasParaConsultar ={"SELECT ", "FROM ", "INNER JOIN ", "ON ", "WHERE ", "ORDER BY "};//por el inner JOin debes fijarte si la ubicación para el inner join es nula entonces deberás avanzar 2 pasos en lugar de 1, por el hecho de tener el ON
        String consulta="";//no creo que de problemas con ini a la var así, si sí entonces ahí quitas select del arreglo de palabras clave y lo dejas aquí, pero el ciclo cambiaría un porquito... habría que saltarse la primera vuelta y de ahí si comenzar a incre el índice de las KeyWords xD
        
        for (int grupoDatos = 0; grupoDatos < cadenaDeInformacion.length; grupoDatos++) {
            if(cadenaDeInformacion[grupoDatos]!=null){//pues si es null, quiere decir que para la consulta de ese momento no es necesaria dicha palabra clave xD... y así se generaliza este proceso xD
                consulta+=palabrasParaConsultar[grupoDatos]+cadenaDeInformacion[grupoDatos];
            }            
        }
        
        consulta+=";";
        return consulta;        
    }
    
    public String construirActualizacion(String[] informacionEmpaquetada){
        String[] palabrasParaActualizar = {"UPDATE ", "SET ", "WHERE ", "ORDER BY ", "LIMIT "};
        String modificacion="";
        
        for (int grupoDatos = 0; grupoDatos < informacionEmpaquetada.length; grupoDatos++) {
            if(informacionEmpaquetada[grupoDatos]!=null){//pues si es null, quiere decir que para la consulta de ese momento no es necesaria dicha palabra clave xD... y así se generaliza este proceso xD
                modificacion+=palabrasParaActualizar[grupoDatos]+informacionEmpaquetada[grupoDatos];
            }            
        }
        
        modificacion=";";
        return modificacion;          
    }
    
    
   
    
   
    
    public String construirEliminacion(){
        String eliminacion="";
        
        return eliminacion;        
    }
    
    
    public boolean estaConectada(){
        return true;
    }
    
    public static boolean estaVacia(){
        String consulta ="SELECT COUNT(*) FROM Tienda";
        
        try(Statement instrucciones = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY, estaVacia)){
        
            ResultSet resultadosHallados= instrucciones.executeQuery(consulta);
            
            if(resultadosHallados.next()){
                estaVacia =resultadosHallados.getInt(1);
            }else{
                System.out.println("numero de registros en tienda-> "+ estaVacia);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Surgió un error al intentar\ndeterminar registros en inventario", "", JOptionPane.ERROR_MESSAGE);
            System.out.println("\nerror al corroborar existencia de datos: "+ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void desconectarDeDB(){
    
    }
   
  
}

//Proceso general para interactuar con la DB
/*
    1. Obtengo y clasifico la info, esto según el tipo de consulta que se vaya a realizar con ella
    2. Construyo la consulta a partir de la info recolectada
    3. Mando a ejecutar la consulta con las instrucciones adecuadas
    4. Guardo lo obtenido por la exe de la consulta en una variable para los resultados
    5. Obtengo los resultados de un tipo determinado para procesarlos como deba xD
    ... esto ya depende del uso que se le vaya a dar...
*/