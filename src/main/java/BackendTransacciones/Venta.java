/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTransacciones;

import ManejoDeInformacion.Nodo;

/**
 *
 * @author phily
 */
public class Venta extends Transaccion{//Ahoara si ventas está completamente terminada, pues ya teine todos los métodos y además los productos se almacenan sin repeticiones
        
      /**
     * Esta será la estrucutra general para añadir productos a las transacciones,
     * puesto que no me es útil agrupar a los productos desde un inicio por el 
     * hecho de que debo mostrar cuanto ha invertido por él esto debido a que al
     * momento de llegar al paso final, ya solo se muestran los datos de la 
     * transacción en general, de tal manera que hasta ese instante antes de 
     * mostrar eso, sea necesario hacer las agrupaciones según similitudes...
     *    
     * @param codigoProducto
     * @param cantidad
     * @param subtotal
     */
    public void agregarProductoVendido(String codigoProducto, int cantidad, double subtotal){
        String datosProducto=codigoProducto+","+ String.valueOf(cantidad)+","+ String.valueOf(subtotal);//0-> codigo, 1-> cantidad, 2-> precio y por esto no altera alo demás ya que los espacios de antes siguen tendiendo los mismos valores             
        
        listaTransaccionUnitaria.anadirAlFinal(datosProducto);                            
    }       
    
    /*TERMINADO :v XD*/
    public String[] eliminarProducto(int productoELiminar){
        return super.eliminarProductoAgregado(listaTransaccionUnitaria, productoELiminar);
    }
    
    //por este parámetro de cantidad, será necesario que en el listener se mande a llamar 
    //al diálogo antes de llamar a este métod y así se le devuelva el valor que se 
    //estableció a eliminar en el spinner...
    
    /*TERMINADO: los subtotales se considera en el método de transacción...*/
    public String[] reducirVentaProducto(int productoAfectado, int cantidadAreducir){//El producto es recibido como el número de fila que corresponde al nodo en el que se encuentra ubicado
        return reducirCantidadProducto(listaTransaccionUnitaria,productoAfectado, cantidadAreducir);//Recuerda que sin importar que sea un producto para venta o pedido 0-> codigo [y en pedido tb el nodo], 1-> cantidad, 2-> subtotal                                 
    }
    
    /**
     * Por medio de este método agrupo a los productos con el 
     * mismo código de tal manera que puedan fusionarse la 
     * cantidad de dichos porductos, como el subtotal de los
     * mismos, de tal manera que al ejecutar la query, se requiera
     * utilizarla una menor cantidad de veces 
     */
    private void agruparTiposProductos(){
        
        Nodo<String> nodoAuxiliar= listaTransaccionUnitaria.obtnerPrimerNodo();
        
        for (int numeroNodoActual = 1; numeroNodoActual <= listaTransaccionUnitaria.darTamanio(); numeroNodoActual++) {
            
            formarGruposDeProductos(nodoAuxiliar, listaTransaccionUnitaria);
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }
        
    }
    
    /**
     * Este método se encarga de hacer la suma de los totales parciales 
     * de los productos agrupados por similitud
     * el valor devuelto será recibido por le lbl correspondiente en el 
     * diálogo paso final, luego cuando se quiera registrar en la DB la venta
     * este GENERAL, este dato podrá ser obtenido directamente del txt
     * para no esar exe en espacios incecesarios a este método
     */
    public int totalizarVenta(){
        int totalVenta=0;
        
        agruparTiposProductos();//agrupo de una vez... para que estarlo haciendo por separado si de todos modos requiero hacer esto antes de hallar el total
        
        Nodo<String> nodoAuxiliar =listaTransaccionUnitaria.obtnerPrimerNodo();
        
        for (int tipoProductoActual = 1; tipoProductoActual <= listaTransaccionUnitaria.darTamanio(); tipoProductoActual++) {
            String[] contenidoNodoActual=nodoAuxiliar.contenido.split(",");
            
            totalVenta+=Integer.parseInt(contenidoNodoActual[2]);//incremento el valor del total por medio del dato "subtotal" del producto actual
            
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }
        
        return totalVenta;
    }
  
    public void registrarVenta(){
        
    }

    
}


//muy bien ya se agregó la funcion para que no se repitan productos... ahora solo falta implementar lo extra que pedidos requiere... ya sabes un mpetodo que corrobore las tienda , si son iguales
//obtener el nodo inicial según la suma de las cantidades que los nodos ant tenian y el final como el nodo inicial 'la cantidad que este tiene