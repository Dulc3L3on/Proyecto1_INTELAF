/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

import FrontendTrabajadores.listadoLineasErradas;

/**
 *
 * @author phily
 */
public class ManejoDeErrores <T>{
    ManejadorDB manejadorDB = new ManejadorDB();    
    ManejadorEstructuras manejadorEstructuras = new ManejadorEstructuras();
    
    public static ListaEnlazada<String> listaDeErrores = new ListaEnlazada();//esta tendrá su nombre establecido según la query general donde se haya generado la excepción.. pero no es obligatorio...
    ListaEnlazada<ListaEnlazada<String>> listaPedidosConPosiblesErrores = new ListaEnlazada();    
    ListaEnlazada<ListaEnlazada<String>> listaProductosPosiblementeErrada = new ListaEnlazada();
    listadoLineasErradas erradas = new listadoLineasErradas(new javax.swing.JFrame(), true);    

    /**
     * Se encarga de hacer la acción sergún la instrucción
     * recibida por el número del primer espacio del arr
     * @param informacion
     */
    public void agregarAListado(String[] informacion){//0 -> no hacer nada [nisiquiera lo reviso, pero bueno xD, 1-> agegar a listado de eerores, 2-> agregarAlListadoWarning [cuarentena xD]
        
        if(Integer.parseInt(informacion[0])==1){//en 0 se encuentran las instrucciones de la acción que deberá ejecutar según lo que le hayan enviado
            listaDeErrores.anadirAlFinal(informacion[1]);                
        }
        else if(Integer.parseInt(informacion[0])==2){
            anadirAEvaluacion(informacion[1], listaPedidosConPosiblesErrores);
        }else if(Integer.parseInt(informacion[0])==3){
            anadirAEvaluacion(informacion[1], listaProductosPosiblementeErrada);
        }   
    }//para el caso de los métodos de inserción donde se reciban los parámetro "regaditos" habrá que emplear String.format para pasarlos a un arreglo [esto cuando no sea del arch, porque ahí ya tienes el arreglo solo es de usar Arrays.toString
    
   
    /**
     * Encargado de crear el super listado donde se tendrán distribuidos 
     * a los pedidos/prodcutos según el número de pedido que tenga... esto por el 
     * hecho de no saber si se van a presentar en orden o no los pedidos
     * @param datosAEvaluar
     */
    private void anadirAEvaluacion(String datosAEvaluar, ListaEnlazada<ListaEnlazada<String>> listaConPosiblesErrores){//conviene más utilizar un super arreglo para ir creando si es que difiere del nombre del nodo esto para después ir sumando de manera más sencilla
        ListaEnlazada<String> listaPorNumeroPedido = new ListaEnlazada();
        String[] datosPedido=datosAEvaluar.split(",");//aún lleva la cabecera
        
        if(listaConPosiblesErrores.estaVacia()){
            listaPorNumeroPedido.anadirAlFinal(datosAEvaluar);
            listaPorNumeroPedido.establecerNOmbre(datosPedido[1].trim());//Aquí le establezco por nombre el número del pedido
            listaConPosiblesErrores.anadirAlFinal(listaPorNumeroPedido);                        
        }else{
            Nodo<ListaEnlazada<String>> nodoAuxiliar = listaConPosiblesErrores.obtnerPrimerNodo();
            
            for (int numeroPedidoActual = 1; numeroPedidoActual <= listaConPosiblesErrores.darTamanio(); numeroPedidoActual++) {//aquí recorreré a los nodos con diferente número de pedido                                
                if(nodoAuxiliar.contenido.obtenerNombre().equalsIgnoreCase(datosPedido[1])){//aquí verifico si el número de pedido del entrante es igual al que tiene por nombre la lista actual
                    nodoAuxiliar.contenido.anadirAlFinal(datosAEvaluar);
                    break;//funciona como un return...
                }
                if(numeroPedidoActual==listaConPosiblesErrores.darTamanio()){
                    listaPorNumeroPedido.anadirAlFinal(datosAEvaluar);
                    listaPorNumeroPedido.establecerNOmbre(datosPedido[1].trim());//Aquí le establezco por nombre el número del pedido
                    listaConPosiblesErrores.anadirAlFinal(listaPorNumeroPedido);
                    break;//debo parar porque el tamaño de la lista crecerá y como luego de esto me ubico en el siguiente nodo, provicaría un loop infinito xD
                }//es decir que creo una nueva lista para recibir a todos los demás pedidos que puedan venir...            
                
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//para avanzar a la siguiente lista que vendría a ser el nodo de la super lista la cual es la que se debe recorrer para comparar los nombres...
            }            
            
        }                   
        
    }
    
    /**
     * Método llamado luego de haber terminado de leer el archivo
     * puesto que aún no se ha trabajado nada con respecto a los 
     * pedidos, entonces, este se encarga de revisar en primer 
     * lugar si el valor de anticipo < al total, si no es así
     * entonces de un vez manda a agregar al listado de erroneas
     * sino pues llama al método que intentará agregar a la DB el 
     * registro si no se puede, elimina y retorna la info para add
     * al listado de erradas...
     * 
     */
    public void determinarError(){
       buscarErroresPosiblesEnListadoProductos();
       buscarErroresEnLIstaPedidos();       
    }//pues debe analizar a ambos...
    
    public void buscarErroresEnLIstaPedidos(){
        double anticipo=0, totalPedido=0;
        long numeroPedido;
        
        if(!listaPedidosConPosiblesErrores.estaVacia()){//por si las moscas xD
            Nodo<ListaEnlazada<String>> nodoAuxiliarNodoLista = listaPedidosConPosiblesErrores.obtnerPrimerNodo();
            ListaEnlazada<String> nodoAuxiliarLista = nodoAuxiliarNodoLista.obtenerObjectcEnCasilla();
        
            for (int listaActual = 1; listaActual <=listaPedidosConPosiblesErrores.darTamanio(); listaActual++) {//for por medio del cual se navega entre todas las listas
                numeroPedido=Long.parseLong(nodoAuxiliarLista.obtenerNombre());//obtengo el númeoro de pedido que corresponde a los elementos a revisar actualemente   TALVEZ EN LA AUSENCIA DEL USO DE ESTA VAR ESTÁ EL ERROR...
                        
                Nodo<String> nodoAuxiliar = nodoAuxiliarLista.obtnerPrimerNodo();//obtnengo el ndo que contiene a los pedidos a estudiar                       
                for (int nodoActual = 1; nodoActual <= nodoAuxiliarLista.darTamanio(); nodoActual++) {
                    String[] contenidoPedidoActual = nodoAuxiliar.contenido.split(",");
                
                    if(nodoActual==1){
                        anticipo=Double.parseDouble(contenidoPedidoActual[9]);//adquiero el total del anticipo...
                    }                
               
                    totalPedido+=Double.parseDouble(contenidoPedidoActual[8]);               
                
                    nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
                }//fin del for que recorre los nodos del listado de un pedido en particular para hallar el total de él
            
                if(anticipo>totalPedido){//quiere decir que debe mandarse a la lista de erradas, porque eso no puede ni debería suceder...
                    agregarContenidoTotalAErradas(nodoAuxiliarLista);
                }else{
                    agregarContenidoTotalAErradas(manejadorDB.llenarPedidos(nodoAuxiliarLista, totalPedido));//agegrgo a la DB si es que no existen errores, sino pues agergo al listado de erradas y luego borro los registros con el número de pedido dado                
                }//es apta para ir a insertarse a la DB
            
            
                nodoAuxiliarNodoLista=nodoAuxiliarNodoLista.nodoSiguiente;//me paso al sigueinte nodo de la superLista
                if(listaActual<listaPedidosConPosiblesErrores.darTamanio()){//pues si no dará null pointer...
                    nodoAuxiliarLista = nodoAuxiliarNodoLista.obtenerObjectcEnCasilla();//me posiciono en la lista de número de pedido diferente siguiente...
                }
            
            }//fin del for por medio del cual se procesan todos los pedidos                   
        }        
        
        //Ingependientemente de que exista o no un listado de pedidos, debo hacer esa columna autoincrementable, pues me servirá en procesosposteriores...
        manejadorEstructuras.alterarTabla();//par aplicar el autonIncrement que debe comenzar a funcionar luego de haber leido el arch independientemente de que salga bien
    }
    
    /**
     * Como para el caso de los productos la lista súperEnlazada agrupará
     * según el código del producto sin importar lo demás, aquí debe revi
     * sarse ese sobrante para hallar posibles repeticiones y así obviarlas
     * aquí será un error si el código del producto y el código e la tienda
     * es exactamente el mismo, en ese caso se procederá a eliminar a la 2da
     * ocurrencia y se preservará a la primera... Aquí a diferencia de los 
     * pedidos, se tomará como errada toda la fila SI al insertr en la tabla 
     * de productos surge un error, de lo contrario solo se mandará al nodo
     * en cuestión...
     */
    public void buscarErroresPosiblesEnListadoProductos(){//recuerda aquí el nombre de la lista es el código del producto...
        if(!listaProductosPosiblementeErrada.estaVacia()){
            Nodo<ListaEnlazada<String>> nodoAuxiliarNodoLista = listaProductosPosiblementeErrada.obtnerPrimerNodo();
            ListaEnlazada<String> nodoAuxiliarLista = nodoAuxiliarNodoLista.obtenerObjectcEnCasilla();
        
            for (int listaActual = 1; listaActual <=listaProductosPosiblementeErrada.darTamanio(); listaActual++) {//for por medio del cual se navega entre todas las listas                                                    
                agregarContenidoTotalAErradas(manejadorDB.llenarInventario(nodoAuxiliarLista));           
            
                nodoAuxiliarNodoLista=nodoAuxiliarNodoLista.nodoSiguiente;//me paso al sigueinte nodo de la superLista            
                if(listaActual<listaProductosPosiblementeErrada.darTamanio()){//pues si no dará null pointer...
                    nodoAuxiliarLista = nodoAuxiliarNodoLista.obtenerObjectcEnCasilla();//me posiciono en la lista de número de pedido diferente siguiente...
                }            
            }//fin del for que se encarga de pasar a analizar el listado de productos con el mismo código        
        }                
    }
    
    
    public void agregarContenidoTotalAErradas(ListaEnlazada<String> listaErradaCompletamente){        
        if(listaErradaCompletamente!=null){
            Nodo<String> nodoAuxiliar= listaErradaCompletamente.obtnerPrimerNodo();
        
            for (int nodoActual = 1; nodoActual <= listaErradaCompletamente.darTamanio(); nodoActual++) {
                String contenidoErrado = nodoAuxiliar.contenido;
            
                listaDeErrores.anadirAlFinal(contenidoErrado);
            }//y así añado al listado de errores al pedido fallido
        }
        
        
    }//este también deberá ser empleado cuando falle un ingreso en la tabla dependiente, esto por el subtotal...
    
     public void mostrarListado(boolean agregarLineas){
        erradas.mostrarDatos(listaDeErrores, agregarLineas);    
        erradas.setLocationRelativeTo(null);
        erradas.setVisible(true);    
        
        listaPedidosConPosiblesErrores.limpiarLIstaCompletamente();//se eliminan por completo los datos... de los errores, claro xD
        listaProductosPosiblementeErrada.limpiarLIstaCompletamente();
    }    
      
    
    //Bien hubieras podido crear un método para eliminar todos aquellos registros que coincidieran en algo, pero
    //tienes cosas que hacer... no estaba difícil, si te da tiempo lo haces xD
}
