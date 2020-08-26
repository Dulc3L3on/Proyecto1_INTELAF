/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendEntidades;

import java.util.Date;
import BackendTransacciones.Pedido;
import BackendTransacciones.Transaccion;
import BackendTransacciones.Venta;
import FrontendTrabajadores.Home;
import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorBusqueda;
import ManejoDeInformacion.ManejadorDB;
import ManejoDeInformacion.ManejadorEstructuras;
import ManejoDeInformacion.ManejadorInformacion;
import ManejoDeInformacion.Nodo;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Cajero extends Trabajador{//en cada una de las hijas lo que se hará será hacer los métodos específicos para obtner la información y además se construirá la consulta, ya que ella sabe lo que quiere :) xD        
    Venta venta = new Venta();
    Pedido pedido = new Pedido();    
    ManejadorEstructuras estructuras = new ManejadorEstructuras();
    ManejadorBusqueda buscador = new ManejadorBusqueda();//los manejadores [creo que aplicac para todos, lo que ahora diré] no necesitan ser el mismo en las demás clases, esto es porque no guardan info, y si lo hacen, es en variables estáticas...
    
    public Cajero(){
    
    }
    
    public Cajero(String tiendaOrigen){
        super();        
        Transaccion.tiendaOrigen=tiendaOrigen;
    }        
    
    
    //Este método debe desaparecer ya que al buscar un pedido se debe encontrar tras la primera vuelta el número del pedido solicitado, si no es asi es porque no existe...
    /*Terminado...? lo pongo por lo de arriba, ya lo comprobaremos*/
    public String buscar(int tipoBusqueda, String codigo, int lugarDeBusqueda){//en este caso el cajero solo puede buscar ya sea productos o pedidos por ello solo se nec esas 2 condis xD
        if(tipoBusqueda==1){//según los rbtn que están abajito en el modoCajeroxD
            return Home.manejadorDB.construirConsulta(super.buscarProducto(lugarDeBusqueda, Transaccion.tiendaOrigen, codigo));
        }else{
            return Home.manejadorDB.construirConsulta(super.buscarPedidoListos(Long.parseLong(codigo)));
        }                      
    }//como ya se sabrá de antemano el dato de código a ingresar entonces en esta var código se tendrá el que corresponda a lo buscado... xD

    
    /**
     *  Se encarga de hacer la disminución permitida a la DB y de dar la cantidad
     *  real de productos que actuaron en la transacción, es decir se retorna el 
     *  dato que se puede visualizar en el Frontend :) xD :v
     * 
     * @param cantidadExistente
     * @param cantidadSolicitada
     * @param codigoProducto
     * @param codigoTienda
     * @return
     */
    /*Terminado, pero será reemplazado?*/
    public int disminuirExistencias(int cantidadExistente, int cantidadSolicitada, String codigoProducto, String codigoTienda){//debido al tiempo se hará con el método empleado en eliminar y modificar, pues se tomará en cuenta que solo una persona está trabajando
        int unidadesRestantes=cantidadExistente-cantidadSolicitada;
          
        try {    
            if(unidadesRestantes<0){            
                //se actualiza la DB con el nuevo dato SOBRANTE... creo que deberás hacer un update, que en este caso sería 0, pues no puede pasarse de ahí xD            
                ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(0, 0, 1, codigoProducto, codigoTienda)));                                    
                JOptionPane.showMessageDialog(null, "Las existencias disminuyeron antes\n de completar este proceso\nno fue posible transferir la cantidad de productos solicitada", "", JOptionPane.WARNING_MESSAGE);
                return cantidadExistente;//debe ser esta, porque eso es de lo que tiene capacidad la DB para dar
            }                              
            
            ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(unidadesRestantes, 0, 1, codigoProducto, codigoTienda)));                
            
       } catch (SQLException ex) {
       
       
       }
        //IGUAL AQUÍ XD, lo del código xD paa cb la cdad a pediod o a venta xD, RECUERDA es un solo método que dependiendo del tipo, lo cual lo revisará dentro de él revisará una u otra col.. aunque creo que estbas pensando rellenar eso manualmente, pero creo que no es lo mejor porque se llenaría mucho los paráms de anadir producto
        //se actualiza a la DB con el dato SOBRANTE;
        return cantidadSolicitada;    //devulevo este dato porque esta es la cantidad que debe sumársele a las listas de las ventas y pedidos hechos hasta el momento    
    }
    
    
    public String agregarProducto(String codigoProducto, int cantidad, String descripcionSeleccion, double precio){//la descripción la recibo del txt
     int cantidadDisminuida=0;
        
        String descripcionTransaccion="";//contendrá en su primer espacio la fila donde debe agregar el contenido el JLIST y en el segundo la debida descripción... ya sea nueva o actualizada        
        double subtotal=0;
        
        try {
        ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(Home.manejadorDB.construirConsulta(super.obtenerCantidad(1, Transaccion.tiendaOrigen, codigoProducto)));//se van a traer las existencias                 
        
            if(ManejadorDB.resultadosConsulta.getInt(1)>0){//entonces habían existencias    
                cantidadDisminuida=disminuirExistencias(ManejadorDB.resultadosConsulta.getInt(1), cantidad,codigoProducto, Transaccion.tiendaOrigen);//ya se actualizó la cantidad en la DB tanto en el pedido como en la venta, según la situación sucedida
                subtotal=cantidadDisminuida*precio;
                
                 if(lugarBusqueda==1){//Es porque la tienda de origen del producto es la actual y por ello necesito una venta
                     ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(cantidadDisminuida, 1, 0, codigoProducto, Transaccion.tiendaOrigen)));
                     venta.agregarProductoVendido(codigoProducto, cantidadDisminuida, subtotal);//se manejará como int, porque creo que en los reportes me será útil este tipo
                }else if(lugarBusqueda==2){
                    ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(cantidadDisminuida, 2, 0, codigoProducto, Transaccion.tiendaOrigen)));
                    pedido.agregarProductoPedido(codigoProducto, cantidadDisminuida, Transaccion.tiendaOrigen, Transaccion.tiendaDestino, subtotal);//debe ser cantidadDisminuida porque ese es el dato que realamente resto de la DB y por ello es la cantidad que se agregó a la transacción
                }
            }else{
                JOptionPane.showMessageDialog(null, "Se agotaron las existencias antes\n de procesar esta solicitud", "", JOptionPane.INFORMATION_MESSAGE);
            }                    
            
            descripcionTransaccion ="["+String.valueOf(cantidadDisminuida)+"]"+ "   "+ descripcionSeleccion+ " -> "+String.valueOf(subtotal);//recuerda que en el forntend se maneja el hecho de que al no haber nada porque pues se agotaron luego de haber revisado y antes de efectuar la transacción que no  agergue nada...
            //por el hecho de que no haya entrado ya que no habían productos, no habrá problemas con el JLIST puesto que el tamño de lo que se le enviará no sobreparará a 3 por ello no agregará... esto porque el valor default para los int es 0 y por ello la condi que habpia puesto app tb para esta situación...
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SURGIÓ UN ERROR AL INTENTAR OBTENER LAS EXISTENCIAS DEL PRODUCTO", "", JOptionPane.ERROR_MESSAGE);
        }        
       
       return descripcionTransaccion;
    }      
    
    public ListaEnlazada<String> agregarProductoPedidoVendido(long codigoPedido, double totalPorElPedido, double anticipoPagado){
        //se tiene que busacar al producto según el número de pedido general ingresado
        //se debe almacenar en una lista enlazada la cantidad y el código del producto en cuestión
        //el subtotal no es de interés por el hecho de que eso solo sería útil al menos en este punto
        //para hallar el total de la transacción, pero eso ya lo tienes en PEDIDO
        pedido.establecerDatosPedidoListo(totalPorElPedido, anticipoPagado);
        buscador.buscarProductosListos(codigoPedido, venta.listaTransaccionUnitaria);
        return venta.listaTransaccionUnitaria;//esto para que pueda ser usado sin cb el método para registrar venta...
    }
    
    
    /*Terminado*///aquí se manejan lo relacionado a la DB
    public void eliminar(int transaccionAfectada, int registroAEliminar){//el primer parámetro es para saber si a ventas o pedidos se refería la acción realizada
        String tienda=tiendaDeTrabajo;
        String[] datosProductoEliminado;
        
        try {
            if(transaccionAfectada==1){
                datosProductoEliminado=venta.eliminarProducto(registroAEliminar);//aquí obtengo los datos ya separados, es decir el código del producto y su respectiva cantidad            
                ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(Integer.parseInt(datosProductoEliminado[1]), 1, 1, datosProductoEliminado[0], tienda)));//para disminuir la cantidad de veces vendido, no tomo en cuenta el subtotal porque ese dato lo mando hasta el final
            }else{
                //se manda a llamar el método de la hija PEDIDO correspondiente
                datosProductoEliminado=pedido.eliminarProducto(registroAEliminar);
                //aquí se obtiene el código de la tienda donde se encuentra ducho producto
                tienda=datosProductoEliminado[2];
                ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(Integer.parseInt(datosProductoEliminado[1]), 2, 1, datosProductoEliminado[0], tienda)));//para disminuir la cantidad de veces vendido, por el hecho de agregar el número de nodo al que hace referencia por estar ahí la pareja de tiendas que le corresponde, los ínidces se corren 1
            }                         
        
            //método UPDATE 
            ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(Integer.parseInt(datosProductoEliminado[1]), 0, 0, datosProductoEliminado[0], tienda)));//para aumnetar la cantidad existente en TP
                  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "OCURRIÓ UN ERROR AL INTENTAR ACTUALIZAR \nEXISTENCIAS EN LA BASE DE DATOS", " ", JOptionPane.ERROR_MESSAGE);
        }
        
    }//no necesito devolver nada porque la consulta la hago de una vez aquí, además el modelo de la lista recibe los datos de otras partes del bloque del código... de manera más directa
    
    /**
     * Método encargado de restar las unidades de la transacción
     * en la lista enlazada y de aumentar las existencias en la
     * DB debido a las devoluciones
     * 
     * @param transaccionAfectada
     * @param productoAReducir
     * @param cantidadAReducir
     */
    /*Terminado completamente*///se maneja lo relacionado a la DB
    public void reducir(int transaccionAfectada, int productoAReducir, int cantidadAReducir){
        String tienda=tiendaDeTrabajo;//Esto es por el hehcho de que con productos me hes necesario obtner dicho valor del arreglo devuelto por el método de la transacción y por elo para genrealizar, siempre enviaré esa var que tendrá el valor correspondietne según el tipo de transacción afectada
        String[] datosProductoModificado;        
        
        try {
        
            if(transaccionAfectada==1){
                //se llama directamente a los métodos de las hijas de transacción...
                datosProductoModificado = venta.reducirVentaProducto(productoAReducir, cantidadAReducir);//método ya completado 
                ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(cantidadAReducir, 1, 1, datosProductoModificado[0], tienda)));//para disminuir la cantidad de veces vendido
            }else{
                //se llama directamente a los métodos de las hijas de transacción...
                datosProductoModificado = pedido.reducirProductoPedido(productoAReducir, cantidadAReducir);                
                //aquí se obtiene el código de la tienda donde se encuentra ducho producto
                tienda=datosProductoModificado[2];
                ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(cantidadAReducir, 1, 1, datosProductoModificado[0], tienda)));//para disminuir la cantidad de veces vendido y de igual manera que al eliminar los datos del producto son 0-> tiendaOrigen, 1-> codigo, 2-> cantidad en este caso es la misma que la del spinner, a difernecia de las agegaciones de producto, puesto que aquí lo que miran no cb a menos que el mismísimo cajero lo haga, con algun proced como este xD
                
            }                  
        
            ManejadorDB.instrucciones.executeUpdate(Home.manejadorDB.construirActualizacion(modificarCantidad(cantidadAReducir, 0, 0, datosProductoModificado[0], tienda)));//para sumarle la cantidad devuelta...
            
            //Solo eso falta u¿y terminar de implementar los métodos de las hijas :3 xD        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL INTENTAR actualizar la\ncantidad del producto en el inventario", "", lugarBusqueda);
        }
    }                    
    
    /**
     * Método empleado para crear a un cliente 
     * que aún no está registrado en la DB para
     * que pueda generarse su transacción
     * 
     * @param datosCliente
     */
    public void registrarDatosCliente(String[] datosCliente){                
        super.registrarDatosUsuario(datosCliente, "Cliente");//Aquí es donde se recorre el arreglo de las columnas para saber a cuales escoger seún el tam dek arreglo    
    }
    
    /**
     * Por medio de este método será posible hacer una venta
     * normal, no podrá emplearse para la venta de un pedido
     * por el hecho de que la manera de ejecutarse, mas que 
     * todo en el orden de los pasos hay variación
     * 
     * @param tipoVenta
     * @param codigoCliente
     * @param total
     */
    public void vender(int tipoVenta,String codigoCliente, double total){//puesto que hay venta pura y venta de pedido
       
       estructuras.insertarEnVenta(codigoCliente, tiendaDeTrabajo, total);
       
       long IDtransaccionActual= ManejadorEstructuras.ultimoID;//Se obtiene el ID de la transacción de la que dependerá...
       
        Nodo<String> nodoAuxiliar = venta.darListadoDetallado().obtnerPrimerNodo();
        for (int ventaIndividualActual = 1; ventaIndividualActual <= venta.darListadoDetallado().darTamanio(); ventaIndividualActual++) {//debe ser así porque el listado que se revisará debe ser el que ya pasó por el agrupamiento
            String[] contenido = nodoAuxiliar.contenido.split(",");//recuerda 0-> codigo del arti [y pareja de tiendas en el caso de pedidos...], 1-> cantidad adquirida, 2-> subtotal por adqusición
            
            estructuras.insertarEnProductoVendido(ManejadorEstructuras.ultimoID, contenido[0], Integer.parseInt(contenido[1]));//recuerda que en tienda de trabajo, desde que se inició al forntend, se le ha establecido el código de la tienda desde la que está empleando la app xD            
        }//esto hacia arriba app para ambos tipos de ventas sin problema alguno...           
       
       if(tipoVenta==2){
           //se llama a los métodos para eliminar el registro del pedido en PEDIDO que tiene el mismo número de 
           //pedido que el transaccionado xD el cual será el nombre de la lista si es que es un pedidoVendido...
           estructuras.eliminarProductoPedido(Long.parseLong(venta.darListadoDetallado().obtenerNombre()));//y listo xD ya se elimina un pedido registrado xD :3 uwu xD
           //se hace el cambio de las unidades pedidas a vendidas
           convertirAVenta();
       }
       
       // y solo xD ya solo falta imple el hecho de eliminar pedidos de la tabla dep como indep y app el cargar pedido a pedido :v, debe ser similar, luego de esto ya podrás colocarlos en la
       //parte corresp en la pseudofaturita [en el botón] luego puedes proceder con pedidos xD
    }
    
    public void convertirAVenta(){
        Nodo<String> nodoAuxiliar= venta.listaTransaccionUnitaria.obtnerPrimerNodo(); 
        
        for (int pedidoAConvertir = 0; pedidoAConvertir < venta.listaTransaccionUnitaria.darTamanio(); pedidoAConvertir++) {
            String contenido[] = nodoAuxiliar.contenido.split(",");            
            
            modificarCantidad(Integer.parseInt(contenido[1]), 1, 0, contenido[0], tiendaDeTrabajo);
            modificarCantidad(Integer.parseInt(contenido[1]), 2, 1, contenido[0], tiendaDeTrabajo);            
            
            nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
        }
    }
    
    
    public void solicitarPedido(String codigoCliente, double[] total, double[] anticipo){
        
        Nodo<String> nodoAuxiliar = pedido.darListadoGeneralPedidos().obtnerPrimerNodo();
        
        for (int tiendas = 1; tiendas <= pedido.darListadoGeneralPedidos().darTamanio(); tiendas++) {
            String[] parejaTiendas = nodoAuxiliar.contenido.split(",");
            parejaTiendas = parejaTiendas[0].split(" ");
            Date date = new Date();
            
            estructuras.insertarEnPedido(parejaTiendas[0], parejaTiendas[1], codigoCliente, ManejadorInformacion.devolverSQLDate(date.getTime()),total[tiendas], anticipo[tiendas]); //aquí se le manda de una vez la fecha actual...                                   
            
            Nodo<String> nodoPedidosUnitarios=pedido.listaTransaccionUnitaria.obtnerPrimerNodo();
            for (int pedidoActual = 1; pedidoActual <= pedido.listaTransaccionUnitaria.darTamanio(); pedidoActual++) {
                String[] contenidoPedido = nodoPedidosUnitarios.contenido.split(",");
                String[] nodoYCodigo = contenidoPedido[0].split(" ");
                
                
                if(Integer.parseInt(nodoYCodigo[0])==tiendas){//pues vamos a ir en orden xD ADEMÁS NO TENGO LOS IDS DE LOS DEMÁS PEDIDOS :V XD
                    estructuras.insertarEnProductoPedido(ManejadorEstructuras.ultimoID, nodoYCodigo[1], Integer.parseInt(contenidoPedido[1]), Double.parseDouble(contenidoPedido[2]));
                    //listo :3 ahora ya tienen los datos la tabla dep de pedidos uwu xD
                }
            }//Fin del for donde se revisa que el número de nodo sea correspondiente al número de la pareja que se está revisando            
            
        }//fin del for que se encarga de recorrer el listado genreal para hacer la inserción en PEDIDO por medio de los datos del listado general
    
    }
    
    public Venta darVenta(){
        return venta;
    }
    
    public Pedido darPedido(){
        return pedido;
    }  
        
   public String[] obtenerDatosCliente(String nit){                     
       
       return estructuras.obtnerDatosDeCLiente(nit);//yo asumo que si no encuentra nada entonces el arreglo se enviará como null
   }

    
    //lo único que se me ocurre para no tener que estar concatenando cad vez la info para luego pasarla como parám aquí es, mandar cada dato que no conozco por separado [lo que conozco son la fecha  y solo xd JAJAJJA
    public void registrarVenta(){
    
    }
    
    
    public void registrarPedido(){
    
    }
    
    /**
     * En este método se encontrarán los totales por cada grupo
     * de transacción general distinto, esto lo digo por el caso 
     * de pedidos, ya que el requiere generar un pedido por cada
     * diferente pareja...
     */
  
    //ya solo hace falta vender ufff xD Y realizar el pedido que practicamente hace lo mismo que vender solo que con lo extra de estar buscando cuales tienen el mismo numero de nodo que el actual en revisión
    //te recuerdas que debes empleat ya sea lo de last_index o lo que dijo el auxi para así obtner el último índice 
    //recuerda que debes implementar el método para cuando el rbtn de pedidos listos esté activado, creo que la búsqueda ya está REVISA y luego en el listener tendrías que agregar que se vaya 
    //A traer todo lo que esté en PP con el mismo número que el código ingresado y lo pase al listado de VENTAS y como ahí no se permite el eli y modif entonces no hay problema, ya solo es de presionar vender xD
    
}
