/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeInformacion;

/**
 *
 * @author phily
 */
public class ListaEnlazada<E> {
    private Nodo<E> primerNodo;//posee el primero objeto puesto que así se sabe de donde partir
    private Nodo<E> ultimoNodo;//obtiene el último elemento, el cual de forma directa ayuda a saber si tiene o no elementos
    private String nombreLista;//podría tener nombre,, solo debes pensar como se lo asignarás
    private int tamanioLista;
    private int tamanioFinal;//Esta var será útil para las propiedades, pues esta contiene el número de elementos totales que contiene un grupo, en este caso almacenado en una lista
    E contenidoELiminado;    
    
    public ListaEnlazada(){
        inicializarLista();
    }
    
    public ListaEnlazada(String nombreLista){//Este será empleado para las propiedades del jugador
        this();
        establecerNOmbre(nombreLista);
    }
    
    public void inicializarLista(){
        //si tine nombre aquí deberías indicarlo
        primerNodo=ultimoNodo=null;//porque no tienen ningun elemento
        tamanioLista=0;
        nombreLista="";
    }
    
    public void establecerNOmbre(String nombre){
        nombreLista=nombre;
    }
    
    public void establecerTamanioLimite(int limite){
        tamanioFinal=limite;
    }
    
    public void anadirAlFinal(E elementoInsertar){
        if(estaVacia()){
            insertarAlInicio(elementoInsertar);
            tamanioLista++;
        }        
        else{
             ultimoNodo=ultimoNodo.nodoSiguiente= new Nodo<E>(elementoInsertar);
             tamanioLista++;
        }
        
    }  
    
    public void limpiarLIstaCompletamente(){
        inicializarLista();
    }
    
    public E obtnerContenidoEn(int nodoDeInteres){//su valor inicial es 1
        Nodo<E> nodoAuxiliar = primerNodo;
        
        for (int nodoActual = 1; nodoActual < nodoDeInteres; nodoActual++) {//claro, debes fijarte que ese valor de nodo sea <= al tamaño de la lista, pero eso afuera! xD
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }
        
        return nodoAuxiliar.contenido;
    }
    
    public Nodo<E> obtnerNodoEn(int numeroNodo){
        Nodo<E> nodoAuxiliar = primerNodo;
        
        for (int nodoActual = 1; nodoActual < numeroNodo; nodoActual++) {//claro, debes fijarte que ese valor de nodo sea <= al tamaño de la lista, pero eso afuera! xD
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
        }
        
        return nodoAuxiliar;//es lo mismo pero devuelvo un nodo... la verda sale más eficiente obtner el nodo y luego establecerle el contenido directametne en lugar de terne que estar recorriedno otra vez al listado porque se obtuvo de una vez al contenido...
    }
    
    
    public void eliminarNodoEn(int nodoAELiminar){         
        
        if(nodoAELiminar==1){
            contenidoELiminado=primerNodo.contenido;
            primerNodo=primerNodo.nodoSiguiente;
            
        }else{
            Nodo<E> nodoAuxiliar = primerNodo;
            
            for (int nodoActual = 1; nodoActual < (nodoAELiminar-1); nodoActual++) {
                nodoAuxiliar=nodoAuxiliar.nodoSiguiente;
            }
            
            contenidoELiminado=nodoAuxiliar.nodoSiguiente.contenido;
            
            if(nodoAELiminar==tamanioLista){
                
                nodoAuxiliar.nodoSiguiente=null;
                ultimoNodo=nodoAuxiliar;//Si no estaría apuntando a null...
            }else{
                nodoAuxiliar.nodoSiguiente=nodoAuxiliar.nodoSiguiente.nodoSiguiente;//pues así me salto al nodo que se quiere eliminar
            }                        
        }        
        
    }
    
    public String[] convertirAArreglo(ListaEnlazada<String> listaAConvertir){
        String[] arreglo = new String[tamanioLista];
        Nodo<String>nodoAuxiliar = listaAConvertir.primerNodo; 
        
        for (int elementoActual = 0; elementoActual < tamanioLista; elementoActual++) {
            arreglo[elementoActual]=nodoAuxiliar.contenido;
            
            nodoAuxiliar =nodoAuxiliar.nodoSiguiente;
        }
        
        return arreglo;
        
    }
    
    public E retornarContenidoEliminado(){
        return contenidoELiminado;
    }
    
    /**
     * Empleado al: editar una casilla que ya se encontraba definida con anterioridad, ya sea en crear o al editarla
     *                            [espaecíficamente empleado por el mapa, al menos por el momento] 
     * Encargado de dejar sin ninguna referencia hacia o del objeto en cuestión para reemplazarlo
     * por el objeto encontrado en el indice correspondiente al arreglo de objetos del panel, 
     * numero que corresponderá con el "índice" en el que se ha colocado el nodo en la lista, 
     * @param indiceObjetoELiminar
     */
    public void sustiruirEn(int indiceObjetoELiminar, E elementoSustitucion){//Es decir que la lista se crea de la misma manera en que el panel asigna los indices a sus componentes
        Nodo<E> nodoActual=primerNodo;
        int indiceActual=0;
        
        while(indiceActual<indiceObjetoELiminar || indiceObjetoELiminar==0){
            if(indiceObjetoELiminar==0){//este podría convertirse en el método insertarAlInicio
                insertarAlInicio(elementoSustitucion);
            }
            
            if(indiceActual==indiceObjetoELiminar-1 && nodoActual.obtenerSiguiente().obtenerSiguiente()==null) {//este podría volverse un método insertarAlFinal
                ultimoNodo=nodoActual.nodoSiguiente= new Nodo<E>(elementoSustitucion);
            } else if(indiceActual==indiceObjetoELiminar-1){//Esto podría convertirse en un método insertarEn... pues siempre será así
                Nodo<E> cadenaNodosPreservar;
                cadenaNodosPreservar=nodoActual.obtenerSiguiente().obtenerSiguiente();
                nodoActual.nodoSiguiente=new Nodo<E>(elementoSustitucion,cadenaNodosPreservar);//presrvo la cadena al tomarla a partir de donde quiero manternerla y asignarsela al nodo siguiente del sustituidor
            }
            
            //pregunta: en este caso no es necesario cambiar la referencia del último nodo?... yo diría que no pues es una ref al objeto no al "lugar" que opcupa el objeto en la lista... pues realmente no existe un lugar físico como tal
            indiceActual++;
        }               
    }//Este es creado puesto que en realidad al modificar la casilla, esta se reemplazará y no actualizará por la nueva, esto para evitar estar revisando que modifico y que no
    
    /**
     * Este método será eficiente para obtner la lista de los datos de
     * los nodos especificados de la versión anterior de la lista, siempre
     * y cuando se le manden en orden...
     * 
     * @param listadoIndices     
     * @return      
     */
    public ListaEnlazada<E> obtnerListadoFiltrado(ListaEnlazada<Integer> listadoIndices){
        ListaEnlazada<E> listaFiltrada = new ListaEnlazada();
        Nodo<Integer> nodoActual = listadoIndices.obtnerPrimerNodo();
        Nodo<E> nodoAuxiliar = primerNodo;
        
        for (int numeroNodo = 0; numeroNodo < (listadoIndices.obtenerUltimoNodo().contenido+1); numeroNodo++) {//puesto que hasta el valor que en la última posición esté es hasta donde se quiere obtener
            if(numeroNodo==nodoActual.contenido){
                listaFiltrada.anadirAlFinal(nodoAuxiliar.contenido);
                nodoActual=nodoActual.nodoSiguiente;//para así seguir con el número que corresponde
            }
                        
            nodoAuxiliar=nodoAuxiliar.nodoSiguiente;//para ubicarse en el siguiente contenido que quizá sea el solicitado...
        }
        
        return listaFiltrada;
    }
    
    
    public void eliminarUltimo(){
         Nodo<E> nodoAuxiliar;
         nodoAuxiliar = primerNodo; 
         
         if(primerNodo==ultimoNodo){
             primerNodo=ultimoNodo=null;
         }else{
             while(nodoAuxiliar.obtenerSiguiente()!=ultimoNodo){//si no sale bien es porque no se está comprando de forma correcta
                nodoAuxiliar=nodoAuxiliar.obtenerSiguiente();
            }
             
             ultimoNodo=nodoAuxiliar;
             nodoAuxiliar.nodoSiguiente=null;
         }
        
         tamanioLista--;
        
    }
    
    public E retornarUltimoELemento(){
        return ultimoNodo.obtenerObjectcEnCasilla();
    }
    
    public void insertarAlInicio(E elementoInsertar){
        primerNodo=ultimoNodo= new Nodo<E>(elementoInsertar);
    }        
        
    public boolean estaVacia(){
        return primerNodo==null;
    }
    
    public Nodo<E> obtnerPrimerNodo (){
        return primerNodo;
    }
    
    public Nodo<E> obtenerUltimoNodo(){
        return ultimoNodo;
    }   
    
    public int darTamanio(){
        return tamanioLista;
    }
    
    public int darTamanioLimite(){// "límite" para el caso de los grupos de las proiedades
        return tamanioFinal;
    }
    
    public String obtenerNombre(){
        return nombreLista;
    }   
    
}
