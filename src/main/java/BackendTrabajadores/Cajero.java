/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTrabajadores;

import ManejoDeInformacion.ManejadorDB;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Cajero extends Usuario{//en cada una de las hijas lo que se hará será hacer los métodos específicos para obtner la información y además se construirá la consulta, ya que ella sabe lo que quiere :) xD    
    
    public Cajero(){
        super();        
    }        
    
    public String buscar(int tipoBusqueda, String codigo, int lugarDeBusqueda, String codigoTiendaActual){//en este caso el cajero solo puede buscar ya sea productos o pedidos por ello solo se nec esas 2 condis xD
        if(tipoBusqueda==1){//según los rbtn que están abajito en el modoCajeroxD
            return manejadorDB.construirConsulta(super.buscarProducto(lugarDeBusqueda, codigoTiendaActual, codigo));
        }else{
            return manejadorDB.construirConsulta(super.buscarPedidoListos(codigo));
        }                      
    }//como ya se sabrá de antemano el dato de código a ingresar entonces en esta var código se tendrá el que corresponda a lo buscado... xD
 
    public void agregarProducto(String[] codigoTiendas, String codigoProducto, int cantidadAAgregar){                                       
        try {
            ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(manejadorDB.construirConsulta(super.obtenerCantidad(super.definirLugarBusqueda(codigoTiendas), codigoProducto)));//se van a traer las existencias
            
            if(ManejadorDB.resultadosConsulta.getInt(1)!=0){//se revisa si aún quedan productos...                
                disminuirExistencias(ManejadorDB.resultadosConsulta.getInt(1), cantidadAAgregar);                    
                
                //TODO
                super.buscarProducto(1, codigoTienda, productoBuscado);//reucerda, esto dependerá de como es que se abastecen las tablas, creo que seá mejor obtner el dato del txt...  y así crear la lista [arreglo o lista de arr xD  creo que eso si no se puede xD] al momento de presionar el btn para add xD
            }else{
                JOptionPane.showMessageDialog(null, "Las existencias se agotaron antes\n de completar este proceso", "", JOptionPane.ERROR_MESSAGE);
            }                        
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO AGREGAR CORRECTAMENTE EL PRODUCTO A LA TRANSACCIÓN");
        }                        
    }
    
    /**
     *  Se encarga de hacer la disminución permitida a la DB y de dar la cantidad
     *  real de productos que actuaron en la transacción, es decir se retorna el 
     *  dato que se puede visualizar en el Frontend :) xD :v
     * 
     * @param cantidadExistente
     * @param cantidadSolicitada
     * @return
     */
    public int disminuirExistencias(int cantidadExistente, int cantidadSolicitada){                
        int sobrante=cantidadExistente-cantidadSolicitada;
          
        if(sobrante<0){
            //se actualiza la DB con el nuevo dato de existencias... creo que deberás hacer un update, que en este caso sería 0, pues no puede pasarse de ahí xD
            
            JOptionPane.showMessageDialog(null, "Las existencias se agotaron antes\n de completar este proceso", "", JOptionPane.WARNING_MESSAGE);
            return cantidadExistente;//debe ser esta, porque eso es de lo que tiene capacidad la DB para dar
        }
                
        //se actualiza a la DB con el dato SOBRANTE;
        return cantidadSolicitada;        
    }
    
    public void devolverProducto(){
    
    }
    
    public void agregarAVenta(){//El IDtienda lo obtendrá del lbl esto porque si lo obtiene del cbBx y no se cb al código de la tienda actual luego de haber presionado el btn para add, se creará la venta solo que para otra tienda y eso estaría mal xD
        
    }
    
    public void agregarAPedido(){//obvi me refiero a add el producto xD y aqupi el IDtienda2 [Destino] se obtnedrá del cbBx de los códigos, puesto que ahí se encuetra el lugar hacia donde quiere se diriga el encargo xD
    
    }
    
    /**
     * Se exe en el botón para aceptar la transacción y solo ahí en ese proceso
     * donde se exe si el listado de su tabla esta vacío o no
     * 
     */
    public void vender(){
    
    }
    
    public void realizarPedido(){
    
    }
    
    public String[] buscarCantidad(String IDtienda, String IDproducto){
        return super.obtenerCantidad(IDtienda, IDproducto);
    }
    
}
