/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_intelaf;    

import FrontendCliente.PantallaPrincipal;
import FrontendTrabajadores.Home;
import FrontendTrabajadores.Reportes;
        
/**
 *
 * @author phily
 */
public class PrincipalTrabajadores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Home home = new Home();      
        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal();
        
        home.setLocationRelativeTo(null);
        home.setVisible(true);
        
        /*Reportes reportes = new Reportes();
        reportes.setLocationRelativeTo(null);
        reportes.setVisible(true);*/
            
        }                
}
