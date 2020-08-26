/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTransacciones;

import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorInformacion;
import ManejoDeInformacion.Nodo;

/**
 *
 * @author phily
 */
public class Transaccion {//estos métodos solo se encargan de trabajar con la lista de lo que se VA A ENVIAR a la DB el cajero se encarga de exe esto y de exe los métodos para actualizar los que se había hecho en la DB
    public static String tiendaOrigen;
    public static String tiendaDestino;//por el hecho de ser estáticas, no me afectará en nada que ande creadno o no otro obtjeto de tipo transacción
    public ListaEnlazada<String> listaTransaccionUnitaria = new ListaEnlazada();//0-> código, 1-> cantidad, 2-> subtotal                 
    //NO DEBE SER ESTÁTICA, PORQUE PUEDE SER MANEJADA POR 2 TRANSACCIONES A LA VEZ!!! [por si acaso se te ocurre convertirla xD]
    //CUANDO SEA EMPLEADO PARA producto vendido tendrá 0-> codigo, 1-> cantidad y solo xD
    
    /**
     * Este será el método de transacción para hacer la eliminación, se recibe
     * la lista enlazada, puesto que en pedido se requiere hacer la misma op
     * para la lista especificada y para la lista que contine los datos generales
     * entonces allá en las hijas se mandará a llamar este métod cuando requiera
     * que suceda esta acción con la lista que sabe deber afectar
     * @param listaAModificar
     * @param productoELiminar
     * @return
     */
    public String[] eliminarProductoAgregado(ListaEnlazada<String> listaAModificar, int productoELiminar){//debo retornar la cantidad porque esto no lo "sabe" directamente el cajero por ello luego de obtenerlo de la lista se lo debo pasar al método para la información
        listaAModificar.eliminarNodoEn(productoELiminar);//se emplea el método para eliminar, donde se recorre hasta llegar al número de nodo -1        
        
        return listaAModificar.retornarContenidoEliminado().split(",");
    }
    

    /**
     * Empleado para el momento en el que se da clic derecho para reducir la cantidad de producto solicitada
     * Este tendría que ser el método de transacción de tal manera que las hijas lo invoquen y así envíen a la lista 
     * que les interesa tratar... en el caso de pedido se empleará 2 veces donde la segunda será para la general porque 
     * de lo que suceda con la específica depende el listado de datos generales del pedido
     * 
     * @param listaAModificar
     * @param productoAReducir
     * @param cantidadAReducir
     * @return
     */
    /*TERMINADO completamente, pues ya se contempla lo de los subtotales*///recuerda que este método y el de eliminar estan nice xD
    public String[] reducirCantidadProducto(ListaEnlazada<String> listaAModificar, int productoAReducir, int cantidadAReducir){
        if(cantidadAReducir>0){//nunca sucederá esto por el spn, pero al menos estará preparado para esto el método...
            //se manda a llamar el método para obtner el contenido en...
            Nodo<String> lugarDeReduccion = listaAModificar.obtnerNodoEn(productoAReducir);
            String[] datosProducto=lugarDeReduccion.contenido.split(",");//obtengo el arreglo de datos del tipo de producto vendido            
            
            
            if(cantidadAReducir==Integer.parseInt(datosProducto[1])){//si es igual a lo que había originalemnte, entonces se elimina...
                datosProducto=eliminarProductoAgregado(listaAModificar, productoAReducir);        
                //esto puede suceder para cuando se esté eliminanod un producto solicitado porque ya no hay más productos solicitados a esa pareja en particular y eso está bien, pero que suceda por acción de user no, porque tendría de alguna manera indicar a JLIST que elimine y no modifique contenido... pues la modificación en la lista enlazada y el la DB estarían contemplado pero lo ant noup xD :| xD
            }else{
                //Se efectúa la resta
                datosProducto[1]=String.valueOf(Integer.parseInt(datosProducto[1])-cantidadAReducir);                                
                if(datosProducto.length==3){//puesto que tb se recibe el arreglo de datos generales y alli no tengo ni necesito tener un subtototal, ya que se hallará hasta que se aegure la transacción por medio de la agrupación...
                    //Se obtiene el precio par reestablecer el total
                    double precio=Double.parseDouble(datosProducto[2])/Integer.parseInt(datosProducto[1]);
                    datosProducto[2]=String.valueOf(precio*cantidadAReducir);//se alamacena el nuevo subtotal
                }
                
                //se manda el nuevo dato al contenido del nodo que ya se obtuvo luego de verificar si había que restar o no                
                lugarDeReduccion.reestablecerContenido(ManejadorInformacion.devolverString(datosProducto));//como ya le he establecido el límite de que no sea mayor a la cantidad actual entonces ya no me preocupo porque puedan ahaber cantidades <0                
                
                datosProducto[1]=String.valueOf(cantidadAReducir);//aigno este valor, para reutilizar este arreglo puesto que se tenía lo necesario para hacer la modif en la DB a excepción de la cantidad a sumar, asi que...
            }        
            
            return datosProducto;//me encargo de devolver los datos de ese objeto eliminado, los cuales me ayudarán en el proceso de la devolución del producto...
        }
        
            return null;//pero a esta parte NUNCA se llegará por el hecho de que el spinner tiene configurado el mínimo de 1
    }    
    
    
    /**
     * Métod compartido por ambas transacciones
     * puesto que se encarga de cubrir el proceso
     * en el cual a partir de la info de un grupo 
     * de productos en particular se procede a comparar
     * con los demás para saber si pertenece o no a 
     * la misma clasificación, de tal manera que pueda
     * procederse a fusionar...
     * @param nodoComparador
     * @param listaARevisar
     */
    public void formarGruposDeProductos(Nodo<String> nodoComparador, ListaEnlazada<String> listaARevisar){
        String[] datosAComparar=nodoComparador.contenido.split(",");
        Nodo<String> nodoAuxiliar=listaARevisar.obtnerPrimerNodo();        
        
        for (int nodoActual = 1; nodoActual <= listaARevisar.darTamanio(); nodoActual++) {
            String[] contenidoDelActual=nodoAuxiliar.contenido.split(",");//De esta manera puedo obtner todos los datos, los cuales no solo serán útiles para comparar sino también cuando se encuentrene coincidencias, puesto que aquí se encuentran los datos a sumar, en este caso la cantidad y el subtotal correspondiente
            //0-> [nodo] codigo, 1-> cantidad, 2-> subtotal
            
            if(datosAComparar[0].equalsIgnoreCase(contenidoDelActual[0])){//puesto que en ambas listas aquí contengo sus respectivos distintivos...
                datosAComparar[1]=String.valueOf(Integer.parseInt(datosAComparar[1])+Integer.parseInt(contenidoDelActual[1]));//se hace la fusión de las cantidades
                datosAComparar[2]=String.valueOf(Integer.parseInt(datosAComparar[2])+Integer.parseInt(contenidoDelActual[2]));//se hace la fusión de los subtotales
                nodoAuxiliar.reestablecerContenido(ManejadorInformacion.devolverString(datosAComparar));//reestablezco el contenido en el primer nodo coincidiente... y por estar en este if, el nodo preserva intacto su contenido si así lo amerita su unicidad xD
                
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//no dará null pointer por el hecho de que el for cada vez revisará el tamño de la lista y como este va cambiando conforme ella lo hace, entones cabal xD si sobrepasa dicho valor yo no se exe esto [eso sucedería porque justo en el último nodo encontró coin...]
                listaARevisar.eliminarNodoEn(nodoActual);//de esta manera evito que se creen más grupos de algo que ya se había clasificado                
            }else{
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//debo hacer esto así porque si lo hiciera sin hacer esta comparación después de haber eliminado querría que esa "nada" me llevara hacia otro nodo que poblimente también sería nada... y pues daría null pointe xD
            }
            
        }
        
    }
    
    
    public ListaEnlazada<String> darListadoDetallado(){
        return listaTransaccionUnitaria;
    }            
    
    /**
     * Por medio de este método será posible hallar el 
     * total a pagar por un producto en específico
     * lo cual al ser sumado permite hallar el subtotal
     * de la transacción específica que en conjunto con 
     * los demás subtotales viene a formar el total de 
     * pago
     * 
     * @param codigoProducto
     * @param tienda
     * @param cantidadAdquirida
     * @return
     */
    public int hallarPagosUnitarios(String codigoProducto, String tienda, int cantidadAdquirida){
      //se llama al método que se encarga de obtner el precio desde la DB
        int precio=0;//cuando tengas al método le vuelas este cerito xD
        
        return precio*cantidadAdquirida;
    }
    
}
