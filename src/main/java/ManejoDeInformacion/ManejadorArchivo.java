/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorArchivo {
    ManejadorDB manejadorDB = new ManejadorDB();
    ManejoDeErrores manejadorErrores = new ManejoDeErrores();
    
    public void leerArchivoAbastecimiento(String path){
        if(path!=null){
            try(BufferedReader buffer = new BufferedReader(new FileReader(path))){//lo que esto dice es, procesa la info con el uffer a partir del arch leido... o algo así xD
                String lineaAEstudiar;
                int numeroLinea=1;
                
                while((lineaAEstudiar=buffer.readLine())!=null){                    
                    
                    //Se manda a llamar al métod de manejador de estructuras para ingresar la línea donde corresponde
                    //y tb se manda a llamar el método de los errores para que haga lo que debe
                    manejadorErrores.agregarAListado(manejadorDB.llenarBaseDeDatos(lineaAEstudiar.split(","), numeroLinea));
                    numeroLinea++;                                        
                }
                
            }catch(FileNotFoundException exc){                                
                JOptionPane.showMessageDialog(null, "No se encontró el archivo\nen la dirección seleccionada", "", JOptionPane.ERROR_MESSAGE);
                //Aquí no es necesario hacer una acción extra...
                
            } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Ocurrio un error al\nintentar obtner los datos", "", JOptionPane.ERROR_MESSAGE);
               //Aqui debería llamarse a un método que se encargue de truncar a la DB o borrarla completamentte, por la creación de tablas...
            }finally{
                //la tabla se alteró al final, en el bloque para hacer las asignaciones a producto pedido y pedido
                //también se debe mandar a llamar el método para mostrar el listado de los errores
            
                manejadorErrores.determinarError();
                manejadorErrores.mostrarListado(false);//pero en los pedidos diferirá esto un poquito... creo que al final ya no usaste el valor de las líneas allá al registrar la info en las tablas...
            }
            

        }            
    } 
    
}


/*notas: LO que se hará aquó será obtener todos las lineas del archivo para almacenarlos en una lista enlazada
  de tal manera que sea más sencillo el manejp de ellas en procesos posteriores
*/

    
