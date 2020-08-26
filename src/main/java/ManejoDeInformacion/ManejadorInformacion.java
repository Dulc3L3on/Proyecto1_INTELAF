/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phily
 */
public class ManejadorInformacion{
    public static boolean conversionExitosa=true;
    
    public static String devolverString(String[] arreglo){
        String cadena="";
        
        for (int espacioActual = 0; espacioActual < arreglo.length; espacioActual++) {
            cadena=cadena+arreglo[espacioActual];
            
            if(espacioActual<(arreglo.length-1)){
                cadena=cadena+",";
            }                        
        }        
        
        System.out.println(cadena);
        return cadena;
    }
    
    public static java.sql.Date devolverSQLDate(long fecha){//es long porque al obtner la fecha actual se obtiene en iliseg los cuales son de tipo long [porque son muuuy largos]
        return new java.sql.Date (fecha);               
    }
    
    public static java.util.Date convertirStringAUtilDate(String fecha){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            return formatoFecha.parse(fecha);
        } catch (ParseException ex) {
            conversionExitosa=false;
        }
        
        return null;
        
    }
    
}
