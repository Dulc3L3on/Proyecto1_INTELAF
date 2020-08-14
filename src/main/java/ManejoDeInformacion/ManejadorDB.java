/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 *
 * @author phily
 */
public class ManejadorDB {
    public static Connection conexion;//se encarga de conectar o desconectar al usuario actual con la DB para interactuar con las estructuras poseedoras de la información
    public static Statement instrucciones;//se encarga de aquirir las sentencias correctas para cumplir con la solicitud hecha por el cliente a la DB
    public static ResultSet resultadosConsulta;
    public static ResultSetMetaData metadatosResultado;
    
    //Aquí va la llamada al driver... por lo visto sigue manteniendo la misma sintaxis que en el libro...
    static final String URL_BASEDEDATOS="jdbc:mysql://localhost:3306/INTELAF";//creo que esto debe cambiar, y debe tener además de esto el nomre de
    static final String NOMBRE_USUARIO="Leon";
    static final String CONTRASENIA="userL3on@";
    
    String[] palabrasParaConsultar ={"SELECT ", "FROM ", "INNER JOIN ", "ON ", "WHERE ", "ORDER BY "};//por el inner JOin debes fijarte si la ubicación para el inner join es nula entonces deberás avanzar 2 pasos en lugar de 1, por el hecho de tener el ON
    
    
    public void conectarConDB(){
        try {
            conexion = DriverManager.getConnection(URL_BASEDEDATOS, NOMBRE_USUARIO, CONTRASENIA);
            instrucciones = (Statement) conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error de conexión");                        
        }
        
    
    }
        
    public ListaEnlazada<Integer> inicializarDB(ListaEnlazada<String> listaDatosIniciales){
        ListaEnlazada<Integer> lineasErroneas = new ListaEnlazada();
        
        Nodo<String> nodoAuxiliar = listaDatosIniciales.obtnerPrimerNodo();
        
        
        for (int linea = 0; linea < listaDatosIniciales.darTamanio(); linea++) {
            String ArregloCampos[] = nodoAuxiliar.contenido.split(",");                        
            
            switch(ArregloCampos[0].toLowerCase()){
                //se mandan a llamar a los métodos según la palabra clave al inicio (si es que se encuentra)
                
                case "tienda"://esto desaparecerá por el hecho de tener los métodos generales para cada una de las operaciones que se pueden hacer en una DB
                    
                    
                break;
                
                
                case "tiempo":
                    
                break;
                
                case "producto":
                    
                break;
                
                
                case "cliente":
                    
                break;
                
                
                case "pedido":
                    
                break;
                
                default:
                    lineasErroneas.anadirAlFinal(linea);//puesto que se comienzan a enumerar desde 1
                    
                break;
                
            }
            
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }
        
        return lineasErroneas;//entonces esto hará que se ahprre una comparación y un método 
    }
    
    
    //solo será necesario un método por cada una de las peticiones, ya se que se dividen en 4 generales
    //y en dos tipos para cada una de ellas, es decir con o sin restricción...
    
    /**
     * Este método será empleado para la consulta en el modo cajero de tal manera que 
     * la clase de resultSetTable pueda obtner la sintaxis correspondiente a una consulta
     * [es decir que en MC será empleado solo para la tabla de búsqueda de prod ó pedido...]
     * @param especificaciones
     * @return
    */
//    public String insertarEnTabla(String[] especificaciones){//RECUERDA QUE ESTE ARREGLO NO TIENE SU TAMAÑO SEGÚN LAS COSAS QUE POSEA, SINO QUE TIENE EL TAMAÑO QUE DEBERÍA TENER UNA FRASE COMPLETÍSIMA
//        String consulta="";//prueba, si da problemas el haber ini con "" entonces coloca select ya que esta es OBLIGATORIA para una consulta
//        
//        for (int frase = 0; frase < palabrasParaConsultar.length; frase++) {
//            if(especificaciones[frase]!=null){
//                consulta=consulta+palabrasParaConsultar[frase]+especificaciones[frase];
//            }
//        }                
//    
//        return consulta;
//    }
    
    //estos 3 [los de delcarar...] métodos forman las declaraciones con la estructura que corresponde a lo solicitado especificamente :), con la sintaxis adecuada, de tal manera que ya solo sea de informar a mysql lo que debe hacer :)
    
    public String construirConsulta(String[] cadenaDeInformacion){
        String consulta="";//no creo que de problemas con ini a la var así, si sí entonces ahí quitas select del arreglo de palabras clave y lo dejas aquí, pero el ciclo cambiaría un porquito... habría que saltarse la primera vuelta y de ahí si comenzar a incre el índice de las KeyWords xD
        
        for (int grupoDatos = 0; grupoDatos < cadenaDeInformacion.length; grupoDatos++) {
            if(cadenaDeInformacion[grupoDatos]!=null){//pues si es null, quiere decir que para la consulta de ese momento no es necesaria dicha palabra clave xD... y así se generaliza este proceso xD
                consulta+=palabrasParaConsultar[grupoDatos]+cadenaDeInformacion[grupoDatos];
            }            
        }
        
        consulta+=";";
        return consulta;        
    }
    
    public String construirModificacion(){
        String modificacion="";
        
        return modificacion;          
    }
    
    public String construirEliminacion(){
        String eliminacion="";
        
        return eliminacion;        
    }
    
    
    public boolean estaConectada(){
        return true;
    }
    
    public boolean estaVacia(){
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