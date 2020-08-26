/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontendTrabajadores;

import BackendEntidades.Trabajador;
import BackendTransacciones.Transaccion;
import ManejoDeInformacion.ManejadorDB;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Listeners {//esto es practicemente forntendn así que no habrá problema el emplear aquí elementos del frontend, porque son compañeros     
   modificadorCantidadAdquirida modificadorCantidad;
    
    /*
    Este método será empleado para aquellas tablas que en su cuerpo se den a notar los registros encontrados de una consulta específica
    de tal manera que cuando se obtnega el registro, se mande a mostrar en el txtF y se imponga el límite de venta
   */    
    public String[] paraMouseClicked(int tipoBusqueda, String codigoTiendaActual, String codigoProducto, int fila){//será empleado para las tablas donde se hagan consultas, donde 
        //siempre siempre xD cuando seleccione una fila de una tabla de búsqueda será para obtener el código del producto y el de la tienda [puesto que será empleado solo para la tabla que muestre productos xD]
        String cadenaDescripcion[]= new String[2];
       
        
       try {
           if(tipoBusqueda==1){
            //para el gerente NUNCA se entrará en esta condición, porque el o busca en la actual o en todas
            if(Trabajador.lugarBusqueda==2){//con estas condiciones es posible establecer el lugar de ORIGEN del producto de manera aedecuada
                   Transaccion.tiendaOrigen=(String)ModoCajero.modeloConsulta.getValueAt(fila, 4);//la tienda destino no le afecta en ningún momento a las ventas porque jamás se toma en cuenta el contenido de esta var
                   Transaccion.tiendaDestino=(String)ModoCajero.comboTiendasDestino.getSelectedItem();//de esta manera cada vez estaré alamacenando la tienda de origen que corresponde a la solicitud del cliente
               }else{
                   Transaccion.tiendaOrigen=codigoTiendaActual;//gracias a esta línea, no habrá probelmas con respecto a que sea null, porque de todos modos siempre se andará seteando el valor que le corresponde momentos antes de ser empleado dicho valor
                   //tengo que hacer esto cada vez sino no se tendrá una información correcta ya que se habrá quedado con el val ant de la tienda origen foránea
               }
            
                //Se busca el producto en la tienda que se le envía por eso se le manda el 1 como parámetro
                ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(Home.manejadorDB.construirConsulta(ModoCajero.cajero.buscarProducto(1, Transaccion.tiendaOrigen, codigoProducto)));//siempre será 1 el lugar de búsqueda el parámetro en este método porque es una búsqueda específica, ya sea que se haya hecho en la tienda actual o en otras la búsqueda general, pero por el hecho de que no en ambas muestro el código de la tienda, es necesario que sepa que tipo de búsqueda hice para saber de donde debo ir a traer dicho código  // por el hecho de que es algo repetido            
                cadenaDescripcion[1]=(ManejadorDB.resultadosConsulta.getString(1));
                //se obtiene su cantidad
                ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(Home.manejadorDB.construirConsulta(ModoCajero.cajero.obtenerCantidad(1, Transaccion.tiendaOrigen, codigoProducto)));//se establece el límite del spinner por medio de la cantidad que en la DB se encuentra           
                cadenaDescripcion[0]=String.valueOf(ManejadorDB.resultadosConsulta.getInt(1));//pues solo tiene una columna xD
                
                return cadenaDescripcion;
           }else{//es decir lo que se busca no son productos, sino pedidos...
               Transaccion.tiendaOrigen=codigoTiendaActual;
               ManejadorDB.resultadosConsulta=ManejadorDB.instrucciones.executeQuery(Home.manejadorDB.construirConsulta(ModoCajero.cajero.buscarPedidoListos(Long.parseLong(codigoProducto))));//recuerda, esto es par mandarle al txt lo selecciondado...
               cadenaDescripcion[1]=(ManejadorDB.resultadosConsulta.getString(1));//Aquí se obtiene la descripción del pedido hallado               
           }
           
            
       } catch (SQLException ex) {
            System.out.println("\nNO SE PUDO OBTENER LA DESCRIPCIÓN BUSCADA\n");
            cadenaDescripcion[1]=null;
       }                        
       
       cadenaDescripcion[0]="0";
       return cadenaDescripcion;//si hay problemas entonces no pasará nada... porque el txt recibirá nada y el spinner se quedará con su límite 0
    }//recuerda que este aún no tiene incluida la parte para lospedidos listos, todo lo que aquí ves, es para realizar una transacción
    
   
    public void paraMousePressed(int tipoAccion, int transaccionAfectada, int nodoAfectado){//el listado DE VENTAS o PEDIDOS según la lista en la que se dió el clic y el nodo es el número de fila +1
        if(tipoAccion==1){//quiere decir que se desea eliminar            
            ModoCajero.cajero.eliminar(transaccionAfectada, nodoAfectado);//completamente listo xD.. bueno de parte de cajero y de ventas.. de pedido no tienes nada... :|
            ModoCajero.modeloLista[transaccionAfectada-1].remove(nodoAfectado);
        }else{//Es decir que se desea modificar...
            //se manda a llamar al diálogo para mostrar el spinner donde se escoje cuanto se reudicrá
            //Se llamará el método para obtner dicha cantidad en la parte del parámetro que requiere 
            //De esta xD
            
            modificadorCantidad= new modificadorCantidadAdquirida(new javax.swing.JFrame(), true);//pues necesito que vuelva a su estado original...
            String lineaDescriptiva=(String)ModoCajero.modeloLista[transaccionAfectada-1].getElementAt(nodoAfectado);
            String[] datos=lineaDescriptiva.split("   ");
            String cantidad =datos[0].substring(1, datos[0].length());//obtengo la cantidad del producto involucrada
                                           
            modificadorCantidad.establecerMaximo(Integer.parseInt(cantidad)-1);//pues no debo dejar que haga una eliminación...
            modificadorCantidad.setVisible(true);
            
            if(modificadorCantidad.devolverSeleccion()){//sino ni maiz xD
               ModoCajero.cajero.reducir(transaccionAfectada, nodoAfectado, modificadorCantidad.devolverCantidadReducida());
               int cantidadRestante=(Integer.parseInt(cantidad)-modificadorCantidad.devolverCantidadReducida());
               lineaDescriptiva="["+String.valueOf(cantidadRestante)+"]"+"   "+datos[1];//pues en la primera posición se tiene a la cantidad 
               
               ModoCajero.modeloLista[transaccionAfectada-1].setElementAt(lineaDescriptiva, nodoAfectado);//y listo :3 se actualizan de primero las listasEnlazadas [por el hecho de ser un pedido] luego la DB y de último lo visual
            }                                                                                    
        }
        
        //Mejor mandaré a llamar desde aquí a los modelos, en lgar de llamarlos en el menucito porque aquí obtnego los datos de manera directa, a diferencia de allá
        //pues si lo hago en él tendría que hacer que este devolviera algún valor, pero no me será del todo útil, puesto que para eliminar allá en el menucito ya tengo
        //el dato para realizar la eliminación... así que solo me sería útil para la modificación, y como que no xD
    }
    //Este será útil para desplegar el menú en las tablas de ventas y pedidos donde se tendrán las opciones de eliminar una transacción hecha ó de cambiarle la cantidad
    //recuerda que en este proceso debes hacer que la cantidad que en la fila clickeada estaba, se le "sume" a la cantidad de la DB [la suma no se hace en la DB sino en Java
    //ella solo juega con los datos que tiene, no opera con ellos...
    
}


