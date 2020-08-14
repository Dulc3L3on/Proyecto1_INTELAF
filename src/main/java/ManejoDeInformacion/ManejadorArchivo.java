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
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorArchivo {
    
    public ListaEnlazada<String> leerArchivoAbastecimiento(String direccion){//Esta dirección podrá ser obtenida al momento de llamar al JFileCHooser ya que este tiene un método para devolver
        
        ListaEnlazada<String> listaDeLineas = new ListaEnlazada();
        
        if(direccion!=null){
            try(BufferedReader buffer = new BufferedReader(new FileReader(direccion))){
                
                String linea;
                while((linea=buffer.readLine())!=null){
                    listaDeLineas.anadirAlFinal(linea);
                }                            
            
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No existe el archivo", "No se ha encontrado el archivo", JOptionPane.WARNING_MESSAGE);//aunque sería ilógico mostrar esto ya que si lo está seleccionando desde el file chooser, esto no sucederá
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar leer el archivo", "Error de lectura", JOptionPane.ERROR);
            }
        }                                
        
        return listaDeLineas;
    }    
    
    public void armarListadoErrores(){
    
    }
    
}


/*notas: LO que se hará aquó será obtener todos las lineas del archivo para almacenarlos en una lista enlazada
  de tal manera que sea más sencillo el manejp de ellas en procesos posteriores
*/

    
