/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontendCliente;

import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorBusqueda;
import ManejoDeInformacion.ManejadorDB;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class PantallaPrincipal extends javax.swing.JFrame {
    ManejadorBusqueda buscador = new ManejadorBusqueda();
    ManejadorDB manejadorDB = new ManejadorDB();
    IngresoPedido ingresoCodigo = new IngresoPedido(new javax.swing.JFrame(), true);
    int numeroPedido;

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
        manejadorDB.conectarConDB();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup_TipoBusqueda = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_listadoProductos = new javax.swing.JList<>();
        txt_ingreso = new javax.swing.JTextField();
        rbtn_porNombreProducto = new javax.swing.JRadioButton();
        rbtn_porFabricante = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        lbl_Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lst_listadoProductos.setFont(new java.awt.Font("Sawasdee", 0, 17)); // NOI18N
        jScrollPane1.setViewportView(lst_listadoProductos);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(40, 170, 670, 480);

        txt_ingreso.setFont(new java.awt.Font("Sawasdee", 0, 17)); // NOI18N
        txt_ingreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ingresoActionPerformed(evt);
            }
        });
        getContentPane().add(txt_ingreso);
        txt_ingreso.setBounds(250, 120, 250, 30);

        btnGroup_TipoBusqueda.add(rbtn_porNombreProducto);
        rbtn_porNombreProducto.setSelected(true);
        rbtn_porNombreProducto.setText("Nombre Producto");
        rbtn_porNombreProducto.setContentAreaFilled(false);
        getContentPane().add(rbtn_porNombreProducto);
        rbtn_porNombreProducto.setBounds(210, 60, 151, 24);

        btnGroup_TipoBusqueda.add(rbtn_porFabricante);
        rbtn_porFabricante.setText("Fabricante");
        rbtn_porFabricante.setContentAreaFilled(false);
        getContentPane().add(rbtn_porFabricante);
        rbtn_porFabricante.setBounds(450, 60, 100, 24);

        jLabel2.setFont(new java.awt.Font("Sawasdee", 1, 25)); // NOI18N
        jLabel2.setText("POR MEDIO DE");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(290, 20, 190, 30);

        jLabel1.setFont(new java.awt.Font("Sawasdee", 1, 19)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buscar.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 40, 110, 130);

        btnGroup_TipoBusqueda.add(jRadioButton1);
        jRadioButton1.setText("Codigo");
        jRadioButton1.setContentAreaFilled(false);
        getContentPane().add(jRadioButton1);
        jRadioButton1.setBounds(330, 90, 75, 24);

        jButton1.setText("RASTREAR PEDIDO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(40, 670, 150, 40);

        lbl_Fondo.setBackground(new java.awt.Color(152, 141, 130));
        lbl_Fondo.setOpaque(true);
        getContentPane().add(lbl_Fondo);
        lbl_Fondo.setBounds(0, 0, 760, 730);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ingresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ingresoActionPerformed
       ListaEnlazada<String> listaBusqueda = new ListaEnlazada();
        
        
        if(rbtn_porNombreProducto.isSelected()){
            listaBusqueda=buscador.buscarProductoPorNombre(txt_ingreso.getText());
        
        }else if(rbtn_porFabricante.isSelected()){
            listaBusqueda=buscador.buscarProductoPorFabricante(txt_ingreso.getText());
        }else{
           
            listaBusqueda=buscador.buscarProductoPorCodigo(txt_ingreso.getText());
        }
        
        accionarDeAcuerdoA(listaBusqueda);
    }//GEN-LAST:event_txt_ingresoActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ingresoCodigo.setLocationRelativeTo(null);
        ingresoCodigo.setVisible(true);
        numeroPedido=ingresoCodigo.codigoPedido;
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void accionarDeAcuerdoA(ListaEnlazada<String> listaBusqueda){
        if(listaBusqueda==null){
            JOptionPane.showMessageDialog(null, "No se encontraron productos\ncon la descripción dada", "", JOptionPane.INFORMATION_MESSAGE);
        }else{                                    
                lst_listadoProductos.setListData(listaBusqueda.convertirAArreglo(listaBusqueda));                        
        }
        
    
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroup_TipoBusqueda;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Fondo;
    private javax.swing.JList<String> lst_listadoProductos;
    private javax.swing.JRadioButton rbtn_porFabricante;
    private javax.swing.JRadioButton rbtn_porNombreProducto;
    private javax.swing.JTextField txt_ingreso;
    // End of variables declaration//GEN-END:variables
}
