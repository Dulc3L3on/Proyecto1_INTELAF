/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTrabajadores;

import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorDB;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Cajero extends Usuario{//en cada una de las hijas lo que se hará será hacer los métodos específicos para obtner la información y además se construirá la consulta, ya que ella sabe lo que quiere :) xD    
    String tiendaTrabajo;    
    ListaEnlazada<String> ventaPorProducto;
    ListaEnlazada<String> pedidoPorProducto;
    ListaEnlazada<ListaEnlazada<String>> ventaCompleta= new ListaEnlazada();
    ListaEnlazada<ListaEnlazada<String>> pedidoCompleto= new ListaEnlazada();
    
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
            ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(manejadorDB.construirConsulta(super.obtenerCantidad(1, super.definirLugarBusqueda(codigoTiendas), codigoProducto)));//se van a traer las existencias
            
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
    }//deprecated
    
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
        int unidadesRestantes=cantidadExistente-cantidadSolicitada;
          
        if(unidadesRestantes<0){
            //se actualiza la DB con el nuevo dato SOBRANTE... creo que deberás hacer un update, que en este caso sería 0, pues no puede pasarse de ahí xD
            
            //Aquí debes mandarle el código de la tienda, por medio del método de usuario que debes modif, el cual da dependiendo del lugar de búsqueda
            JOptionPane.showMessageDialog(null, "Las existencias se agotaron antes\n de completar este proceso", "", JOptionPane.WARNING_MESSAGE);
            return cantidadExistente;//debe ser esta, porque eso es de lo que tiene capacidad la DB para dar
        }
            
        //IGUAL AQUÍ XD, lo del código xD paa cb la cdad a pediod o a venta xD, RECUERDA es un solo método que dependiendo del tipo, lo cual lo revisará dentro de él revisará una u otra col.. aunque creo que estbas pensando rellenar eso manualmente, pero creo que no es lo mejor porque se llenaría mucho los paráms de anadir producto
        //se actualiza a la DB con el dato SOBRANTE;
        return cantidadSolicitada;        
    }
    
    
    //Este método será empleado para actualizar la cantidad del producto, agregar al JList y a la la lista que se leerá para construir la consulta 
    public String anadirProducto(String[] codigoTiendas, String codigoProducto, int cantidad, String descripcionSeleccion){//si la transacción fue una venta entonces este arreglo será nulo, sino se mandarán los datos de las tiendas... este arreglo se armará en el frontend        
        int cantidadDisminuida=0;//recuerda que aquí el código de TIendas es solo para pedido, por ello debes modif el método para determinar que tienda, porque no es eficiente... debes hacer que devuela a la tienda actual lo cual solo la posee el trabajdor o la tienda de origen dada por el value at
        //entonces si es pedido, ya solo deberías recibir a la tienda de destino, porque como pensaste el permitir reservar para otra tienda, entonces es lo único que variraría, por ello teinda de origen no se recibe como parám aquí
        //ahí revisas como quedaría usuario luego de crear a trabajador como hijo de usuario y que las entidades de trabajdor hereden de ella, reivisas lo de los métodos y las vars...
        //entonces aquí lo que ya tienes listo es el add a la lista gen como específica de ambas transacciones, el determinar la cantidad permitida para vender y el retornar la descrip completa
        //que la lista debe mostrar, recuerda encuerrar eso en un if, para que cuando devuelva null, por el hecho de haber surgido un error con la obtneción de la cdad en la DB, no suceda nada malo con la lista
        //Recuerda que debes hacer append... luego de eso debes imple el método para insertar en las tablas converg, tal y como se escribió en el cuad y en el doc, puesto que son simi no se requerirá un arreglo como la 
        //consulta de cdad, creo xD ahí lees xD luego debes llamarlos en la parte de disminución de la cdad, recuerda que según el lugar a se irá a la col corresp del peddido o de la venta
        
        String descripcionTransaccion="";
        
        try {
        ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(manejadorDB.construirConsulta(super.obtenerCantidad(1, super.definirLugarBusqueda(codigoTiendas), codigoProducto)));//se van a traer las existencias        
        
            if(ManejadorDB.resultadosConsulta.getInt(1)>0){
                cantidadDisminuida=disminuirExistencias(ManejadorDB.resultadosConsulta.getInt(1), cantidad);//ya se actualizó la cantidad en la DB tanto en el pedido como en la venta, según la situación sucedida
            
                 if(lugarBusqueda==1){//Es porque la tienda de origen del producto es la actual y por ello necesito una venta
                    ventaCompleta.anadirAlFinal(aVenta(codigoProducto, cantidad));//se manejará como int, porque creo que en los reportes me será útil este tipo
                }else if(lugarBusqueda==2){
                    pedidoCompleto.anadirAlFinal(aPedido(codigoProducto, cantidad));
                }
            }else{
                JOptionPane.showMessageDialog(null, "Se agotaron las existencias antes\n de procesar esta solicitud", "", JOptionPane.INFORMATION_MESSAGE);
            }                    
            
            descripcionTransaccion ="["+cantidadDisminuida+"]"+ "  "+ descripcionSeleccion;//Esto será lo que se append en la lista... xD
        } catch (SQLException ex) {
            
        }
        
       return descripcionTransaccion;//Esto lo recibirá el JList para appendizarlo xD add una condi allá en el forntend, donde si esto se encuentra vacío que no add xD
    }
    
    
    
    public ListaEnlazada<String> aVenta(String codigoArticulo, int cantidad){//El IDtienda lo obtendrá del lbl esto porque si lo obtiene del cbBx y no se cb al código de la tienda actual luego de haber presionado el btn para add, se creará la venta solo que para otra tienda y eso estaría mal xD
        ventaPorProducto = new ListaEnlazada();
        
        ventaPorProducto.anadirAlFinal(codigoArticulo);
        ventaPorProducto.anadirAlFinal(String.valueOf(cantidad));
        
        return ventaPorProducto;
    }
    
    public ListaEnlazada<String> aPedido(String codigoArticulo, int cantidad){//obvi me refiero a add el producto xD y aqupi el IDtienda2 [Destino] se obtnedrá del cbBx de los códigos, puesto que ahí se encuetra el lugar hacia donde quiere se diriga el encargo xD
        pedidoPorProducto = new ListaEnlazada();
        
        pedidoPorProducto = new ListaEnlazada();
        
        pedidoPorProducto.anadirAlFinal(codigoArticulo);
        pedidoPorProducto.anadirAlFinal(String.valueOf(cantidad));
        
        return pedidoPorProducto;
    }
    
    public void devolverProducto(){
        
        
    }
    
    /**
     * Se exe en el botón para aceptar la transacción y solo ahí en ese proceso
     * donde se exe si el listado de su tabla esta vacío o no
     * 
     */
    public void vender(){//creo que se convertirán en un solo método por el hecho de que solo el nombre de las col, cb y que en una se envía a una col más... así que haz algo simi a lo que se hzo para lo de las cantidades
        //Aquí es donde se genera la consulta para crear la vent en Venta y la venta individual en Venta_Producto
        
    }
    
    public void realizarPedido(){
        //y aquí se hace le proceso para crear el pedido general y los específicos
    }
    
    public void establecerTiendaDeTrabajo(String codigoTienda){
        tiendaTrabajo=codigoTienda;
    }
    
    
    public String darLugarDeTrabajo(){
        return tiendaTrabajo;
    }
    

    
}
