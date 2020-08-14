/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontendTrabajadores;

import ManejoDeInformacion.ManejadorDB;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author phily
 */
public class ResultSetTableModel extends AbstractTableModel{        

    @Override//recuerda que estos métodos son empleados por la tabla al momento de informarle que su modelo ha cambiado, lo cual se hace en el método para mostrar la respectiva consulta xD
    public int getRowCount() {
        try {
            ManejadorDB.resultadosConsulta.last();
            return ManejadorDB.resultadosConsulta.getRow();
            //En este caso no puede obtenerse el total de filas con los metadatos ya que una fila
            //en sí es un registro que es información para java, entonces por ello debes adquirir 
            //esto de la información misma, así como cuando quieres obtener información de la información
            //acudes a los metadatos porque ellos eso es lo que conocen, es decir tú acudes a aquel que sabe sobre el tema
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se han logrado obtener los datos\n de la base de datos", "Error al consultar", JOptionPane.ERROR_MESSAGE);
            System.out.println("Problema al obtener número de filas\n");
            ex.printStackTrace();
        }
        
        return 0;//por si acaso ocurre algo...
    }

    @Override
    public int getColumnCount() {
        try {
            return ManejadorDB.metadatosResultado.getColumnCount();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se han logrado obtener los datos\n de la base de datos", "Error al consultar", JOptionPane.ERROR_MESSAGE);
            System.out.println("Problema al obtener número de columnas\n");
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public Object getValueAt(int fila, int columna) {        
        
        try {
            ManejadorDB.resultadosConsulta.absolute(fila);
            ManejadorDB.resultadosConsulta.absolute(fila+1);//ouesto que lo que debe ver con la DB inicia el conteo en 1 y Java en 0
            return ManejadorDB.resultadosConsulta.getObject(columna+1);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se han logrado obtener los datos\n de la base de datos", "Error al consultar", JOptionPane.ERROR_MESSAGE);
            System.out.println("Problema al obtener el \"valor en\"\n");
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public void mostrarConsulta(String consulta){        
        try {
        //Recuerda que este string de consulta final será armado por el método general consulta del MDB
        ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(consulta);
        
        ManejadorDB.metadatosResultado=ManejadorDB.resultadosConsulta.getMetaData();
        
        fireTableStructureChanged();//Este método es el que se encarga de actualizar la tabla :)
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se ha logrado consultar correctamente\n a la base de datos", "Error al consultar", JOptionPane.ERROR_MESSAGE);
        }                
    }
    
}
