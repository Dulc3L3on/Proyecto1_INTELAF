/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTransacciones;

import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorInformacion;
import ManejoDeInformacion.Nodo;
import java.util.Arrays;

/**
 *
 * @author phily
 */
public class Pedido extends Transaccion{    
    ListaEnlazada<String> listaGeneralPedidos = new ListaEnlazada();//tendrá en cada nodo, concatenado el id de la pareja de tiendas, la cantidad Total de artículos y se crea un nodo más por cada pareja distinta xD
    //datos en la listaGeneral: "tiendaOrigen tiendaDestino", #deArticulosDiferentes del pedido en esa pareja de tiendas en específico...       
    //datos en listaEspecifica: "#nodo codigoArticulo", cantidad, subtotal        
    double[] datosPedidosListos = new double[2];
    double[] anticipos;
    
    
    public void agregarProductoPedido(String codigoArticulo, int cantidad, String tiendaOrigen, String tiendaDestino, double subtotal){
        String datosUnificados=tiendaOrigen+" "+tiendaDestino;
        int numeroParejaPerteneciente=0;
         
        if(listaGeneralPedidos.estaVacia()){
            datosUnificados+=","+String.valueOf(cantidad);
            listaGeneralPedidos.anadirAlFinal(datosUnificados);//solo requiere la pareja de tiendas y la cantidad de productos que a ella solicitaron         
        }else{
            Nodo<String> nodoAuxiliar=listaGeneralPedidos.obtnerPrimerNodo();
            
            for (int parejaTiendasActual = 1; parejaTiendasActual <= listaGeneralPedidos.darTamanio(); parejaTiendasActual++) {
                String[] datosGenerales=nodoAuxiliar.contenido.split(",");//pues así agrupo a las tiendas en 0                
                
                if(datosGenerales[0].equalsIgnoreCase(datosUnificados)){
                    datosGenerales[1]=String.valueOf(Integer.parseInt(datosGenerales[1])+cantidad);//actualizo el número de productos totales si importar el tipo, que estan siendo solicitados a la pareja actual
                    nodoAuxiliar.reestablecerContenido(ManejadorInformacion.devolverString(datosGenerales));                                       
                }
                if(parejaTiendasActual==listaGeneralPedidos.darTamanio()){
                    datosUnificados+=","+String.valueOf(cantidad);
                    listaGeneralPedidos.anadirAlFinal(datosUnificados);                    
                }
                
                numeroParejaPerteneciente=parejaTiendasActual;//pues de todos modos tendrá que obtener algún valor por los que recorre el for xD
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
            }//FIN DEL FOR CON EL CUAL se agrupan a los productos por pareja de tiendas...            
        }
        
        String datosProducto=numeroParejaPerteneciente+" "+codigoArticulo+","+ String.valueOf(cantidad)+","+ String.valueOf(subtotal);//ahi te recuerdas que el número de nodos comienzan a guardarse desde 1
        listaTransaccionUnitaria.anadirAlFinal(datosProducto);
    }               
    
    
    
    
    /*TERMINADO COMPLETAMENTE XD*/
    public String[] eliminarProducto(int productoELiminar) {
        //Aquí hago el cambio en la lista general, es decir 
        //le resto la cantidad de artículos a la lista general
        //si resultan ser iguales entonces elimino el nodo...
        //Es decir que aplico el mismo método de la lista especifica
        //para MODIFICAR el artículo, porque puede que sea 0 el resultado
        //y con ello deba eliminar o puede que no sea así y con ello deba 
        //modificar, eso se traduce a que el método debe recibir una lista enlazada
        //para que pueda ser genreal, entonces lo que se hará será que en lugar de 
        //Tener en el método a la lista específica, se le mandará la lista específica
        //Desde aquí o desde ventas, de tal manera que pueda procesar ese método de 
        //Transacción
        
        
        String[] contenidoPedidoELiminado=eliminarProductoAgregado(listaTransaccionUnitaria, productoELiminar);
        String[] nodoYCodigo=contenidoPedidoELiminado[0].split(" ");//se separ el número de nodo que reperesentaba la pareja de tiendas por medio de las cuales era posible para este producto, tener una vía de comunicación
        String[] datosGeneralesPedido=reducirCantidadProducto(listaGeneralPedidos, Integer.parseInt(nodoYCodigo[0]), Integer.parseInt(contenidoPedidoELiminado[1]));//se hace la debida actualización a la listaque contiene los datos generales que serán útiles para insertar la nueva info en la tabla pedidos                
        
        String[] codigosTiendas=datosGeneralesPedido[0].split(" ");//se separan los códigos de las tiendas.. que se encuentran en el listado general
        
        //recuerda 0-> codigoProducto; 1-> cantidad; 2-> tiendaOrigen, debo hacer esto porque con estos datos ya sé a que tupla debo ir a hacer las modificaciones
        String[] datosPedidoELiminado=new String[3];                
        datosPedidoELiminado[0]=nodoYCodigo[1];//obtengo el código del producto...
        datosPedidoELiminado[1]=contenidoPedidoELiminado[1];//la cantidad del producto eliminado...
        datosPedidoELiminado[2]=codigosTiendas[0];//puesto que en esa posición se encuentra la tienda de origen
        return datosPedidoELiminado;//de esta manera puedo seguir trabajando en cajero de la misma manera que antes, es decir con un arreglo de 3 espacios, 0-> tienda, 1-> codigoProducto, 2-> cantidad
    }    
    
    /**
     *
     * @param productoAfectado
     * @param cantidadAreducir
     * @return
     */
    /*Terminado completamente*/
    public String[] reducirProductoPedido(int productoAfectado, int cantidadAreducir){
        
        String[] contenidoProductoModificado=reducirCantidadProducto(listaTransaccionUnitaria, productoAfectado, cantidadAreducir);
        String[] nodoYCodigo=contenidoProductoModificado[0].split(" ");
        String[] datosGeneralesPedido=reducirCantidadProducto(listaGeneralPedidos, Integer.parseInt(nodoYCodigo[0]), cantidadAreducir);
        
        String[] codigosTiendas=datosGeneralesPedido[0].split(" ");                 
        
        String[] datosPedidoModificado=new String[3];//estos son los datos para que se pueda hacer lo que se requiere tanto en la DB como en JLIST
        datosPedidoModificado[0]=codigosTiendas[0];//puesto que en esa posición se encuentra la tienda de origen
        datosPedidoModificado[1]=nodoYCodigo[1];//obtengo el código del producto...
        datosPedidoModificado[2]=contenidoProductoModificado[1];//la cantidad del producto eliminado...
        
        return datosPedidoModificado;        
    }   
    
    /**
     * Se encarga de mandar el nodo con el cual se compararán a lso 
     * demaś, basandode en el número de nodo[pareja de tiendas distinta]
     * y el código del porducto
     *//*TERMINADO*/
    private void agruparSegunParejaTiendas(){
        Nodo<String> nodoAuxiliar=listaTransaccionUnitaria.obtnerPrimerNodo();//pues este listado es el que debo agrupar, porque el general ya lo está... recuerda, aquí se agrupan prods co mismo código y misma pareja de tiendas...
        
        for (int nodoActual = 1; nodoActual <= listaTransaccionUnitaria.darTamanio(); nodoActual++) {
            formarGruposDeProductos(nodoAuxiliar, listaTransaccionUnitaria);
            
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//no habrá porblemas, pues si llega a cb la lista a tal punto que el sigueinte ahora es null, cuando revise el for se dará cuena de esto... tal y como sucedería sin borones "inesperados" ya o avanzaría y no habría null pointer xD            
        }//Fin del for con el que se sigue con el sigueinte nodo diferente al que se estaba antes...        
        
    }
    
    /**
     * Este método se encarga de hallar el subTotal de cada 
     * uno de los diferentes pedidos, es decir de cada pedido
     * con diferente pareja de tiendas, pues así se obtnedrá
     * la info genreal de dicho pedido tanto para la DB como
     * para visualizarlo
     * @return 
     *//*TERMINADO*/
    public double[] hallarTotalCadaPareja(){
        agruparSegunParejaTiendas();
        
        double[] subTotalesPorPareja = new double[listaGeneralPedidos.darTamanio()];//puesto que esta tiene el tamaño que corresponde a los diferentes pedidos que se vayan a realizar para un mismo cliente...
        Nodo<String> nodoAuxiliar =listaTransaccionUnitaria.obtnerPrimerNodo();//pues este es quien contiene los datos a revisar      
        
        for (int numeroNodoActual = 1; numeroNodoActual <= listaTransaccionUnitaria.darTamanio(); numeroNodoActual++) {
              String[] contenidoCompletoProducto = nodoAuxiliar.contenido.split(",");//0-> numeroNodo codigoArticulo, 1->  cantidad, 2-> subtotal
              String[] nodoYCodigo = contenidoCompletoProducto[0].split(" ");
            
              subTotalesPorPareja[Integer.parseInt(nodoYCodigo[0])]+=Double.parseDouble(contenidoCompletoProducto[2]);                        
        }//fin del for que se encarga de llenar los espacios del arreglo según el número de nodo que tenga el nodo xD actualmente revisado                
        
        return subTotalesPorPareja;
        
    }//SE DEVOLVERÁ DIRECTAMENTE AL la tabla, puesto que esta se alimenta de arreglos, sino habría que hacer doble trabajo, entonces eso quiere decir que esta var debe ser global, porque depués
    //necesito este dato otra vez, solo que para INSERTAR en la DB con el preparedStatement... estos valores nunca cambian, los que varían son los anticipos...
           
    public double[] hallarAnticipoDeCadaPareja(double[] subtotales){
        anticipos = new double[subtotales.length];
        
        for (int subtotalActual = 0; subtotalActual < subtotales.length; subtotalActual++) {
            anticipos[subtotalActual]=subtotales[subtotalActual]*0.25;
        }
        
        return anticipos;        
    }
          
    public void actualizarAnticipoDePareja(int numeroPareja, double nuevoValor){
        anticipos[numeroPareja]=nuevoValor;
    }
    
    /**
     * Este método es el encargado de unificar a los datos que JTable recibe
     * es decir convierte en una matriz a la pareja de tiendas, subtotal y 
     * al anticipo, puesto que tienen correspondencia
     * 
     * @param subtotal
     * @param anticipo
     * @return 
     */
    public String[][] agruparDatosPedidoMostrar(double[] subtotal, double[] anticipo){
        String[][] datosPedido = new String[subtotal.length][3];
        
        Nodo<String> nodoAuxiliar = listaGeneralPedidos.obtnerPrimerNodo();
        
        for (int parejaActual = 0; parejaActual < subtotal.length; parejaActual++) {
            String[] datosGenerales =nodoAuxiliar.contenido.split(",");
            
            datosPedido[parejaActual][0]=datosGenerales[0];
            datosPedido[parejaActual][1]=String.valueOf(subtotal[parejaActual]);
            datosPedido[parejaActual][2]=String.valueOf(anticipo[parejaActual]);
            
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;            
        }
        
        return datosPedido;
    }
    
    public void establecerDatosPedidoListo(double total, double anticipoPagado){
        datosPedidosListos[0]=total;
        datosPedidosListos[1]=anticipoPagado;
    }
    
    /**
     * Empleado para hacer los debidos registros en las tablas
     * de pedido y PP, empleado pasos después de haber recibido
     * la cantidad de anticipo pagada, para
     * @param nitCliente
     */
    public void registrarPedidosGenreales(String nitCliente, String totalDebido, String anticipo){//sip, debe estar separado, porque esto lo podré hacer hasta que obtnenga cuanto pago, no como, eso no me interesa aquí, pero sí el cuanto
        
        
        for (int pedidoGeneralActual = 0; pedidoGeneralActual < 10; pedidoGeneralActual++) {
            
        }//no puse la tienda porque eso ya está en la lista y aún no deberás revisarla porque ya se obtuvieron y almacenaron en un arreglo, así que ya solo es de mandar a llamar al método para hacer la insercion con el prStt
        
    }//de primero se exe este para tner listo el listado de los códigos de tal manera que en el registro de los unitarios no existan problemas.... usa otra estructura mejor xD
    
    public void registrarPedidosUnitarios(int[] codigosPedido){//solo es nec este arreglo, porque lo demás ya lo tengo en la lista unitaria...
        
    }    

    public double[] darAnticipos(){
        return anticipos;
    }
    
    public ListaEnlazada<String> darListadoGeneralPedidos(){
        return listaGeneralPedidos;
    }
    
}


//NOTA: Lo que se haŕa con los pedidos será tener una lista general donde 
//se crearán más nodos si la pareja [tienda Orgien y destino] son distintas
//de tal manera que cuando existan COINCIDENCIAS en las TIENDAS se les incremente
//El número de artículos totales, dato que será útil para saber hasta que nodo
//se debe insertar con el mismo número de pedido, esto se hace porque como 
//existen diferentes tiempos de envío, entonces cuando una tienda esté lista
//puede que la otra no y cuando marcara pedido entregado parecería que la otra
//tienda ya entregó su encargo a pesar de no ser así

//ENTONCES: cuando se haya reocrrido la cantidad de artículos totales pedidos
//A una pareja en específico se procederá a crear otro registro en la tabla
//de pedidos para que se puede generar otro código y así no suceda este error 
//lógico
