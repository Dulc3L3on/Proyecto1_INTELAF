/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendEntidades;

import ManejoDeInformacion.ManejadorEstructuras;


/**
 *
 * @author phily
 */
public class Trabajador extends Usuario{
    public String tiendaDeTrabajo;
    public static int lugarBusqueda;//es decir en la tienda actual, en las demás ó en todas, por medio de este valor en el listener se sabrá de donde obtnerl el código de la tienda
    
    public String palabrasParaObtenerValor[][]= new String[3][4];
    public String paraActualizacion[]= new String[5];
    
    ManejadorEstructuras manejadorEstructuras = new ManejadorEstructuras();
    
    public Trabajador(){
        palabrasParaObtenerValor[0][0]="unidades";
        palabrasParaObtenerValor[0][1]="Tienda_Producto";
        palabrasParaObtenerValor[0][2]="IDtienda";
        palabrasParaObtenerValor[0][3]="IDproducto";//Reucerda que al final de cuentas este método solo será para obtner la cantidad del producto ya que pra obtener el numero de venta y pedido se empleará select last_insert_id()... :) XD        
    }
    
    
    /*
        En este método se crear la estructura con la 
        cual se construirá la consulta para hacer la
        add de los datos en las tablas compuestas 
        T_P, P_P, V_P
    */
//  public String[] insertarProductoEnDependientes(){
    
//        return //se retornará el arreglo general para inserción.. solo que aún no lo coloco porque hay que definir cuantos comando mysql podrían necesitarse para hacer cualquier inserción aquí requerida;
//}

    /**
     * Este método me será útil para las devoluciones de los productos 
     * al efectuarse la transacción actual, también será útil tanto para 
     * incrementar como para decrementar el número de veces que ha sido
     * vendido o solicitado un producto en cuestión... no emplearé esto 
     * para hacer la disminución de los productos porque no se como ponerle
     * una restricción tal que si llega a obtner un valor menor a 0 entonces 
     * que inserte otro tipo de valor ... aunque podría usar este método 
     * luego de haber encontrado la situación respectiva, y sería más directo
     * pero...puede que hayan errores con respecto a que en otra caja se esté
     * realizando otro proceso con el mismo producto, más que todo en las ventas
     * porque si se está en otra op pero se le suma, no afecta en nada...
     *
     * @param cantidadAumentar
     * @param tipoDatoAAumentar
     * @param tipoOperacion
     * @param codigoProducto
     * @param codigoTienda
     * @return
     */
    /*Terminado*/
    public String[] modificarCantidad(int cantidadAumentar,int tipoDatoAAumentar,int tipoOperacion, String codigoProducto, String codigoTienda){//útil tanto para eliminar como para la devolución de un producto
        String[] columnaAumento={"unidades ", "vecesVendido ", "cantidadReservada "};
        String[] operandos={" + ", " - "};
        
        paraActualizacion[0]="Tienda_Producto";//creo que habrá un problema con el hecho de que la cantidad a aumentar sea double, porque allá en la tabla las unidades son de tipo int        
        paraActualizacion[1]=columnaAumento[tipoDatoAAumentar]+" = " + columnaAumento[tipoDatoAAumentar]+" "+operandos[tipoOperacion]+" "+String.valueOf(cantidadAumentar);
        paraActualizacion[2]="IDproducto = "+ "'"+codigoProducto+"'" + " AND "+ "IDtienda = "+"'"+codigoTienda+"'";
        
        return paraActualizacion;//Deplano que se tendrá que quedar así, para cuando se esté procesando con normalidad y usar el modificard de la cantidad con el presparedStatement oara la lectura del arch, sino  funcionara este el del statement, entonces sí procedería a cambiar el método...
    }//serrá llamado por cada columna que vaya a ser afectada
    //recuerda que si te da tiempo deberás nodificar estos métodos porque deberás estar seteando cada vez las comillas
    
    
    public void establecerDeTrabajo(String codigoTienda){
        tiendaDeTrabajo=codigoTienda;
    }
    
       
    /**
     * Empleado en el método "para el mouse clicked" ya que 
     * por medio de este es posible establecer el límite de
     * venta de un producto en específico...xD y en general
     * ir a traer el dato importante del producto de interés
     *
     * @param tipoDato
     * @param requisito1
     * @param requisito2
     
     * @return
     */    
    /*Terminado, pero será reeemplazado?*/
    public String[] obtenerCantidad(int tipoDato, String requisito1, String requisito2){//1.> cantidadProducto, 2.> # de venta, 3.> codigoPedido... estos son los valores que se obtnedrán        
        paraObtencion[0]=palabrasParaObtenerValor[tipoDato][0];//la columna a traer
        paraObtencion[1]=palabrasParaObtenerValor[tipoDato][1];//tabla de obtneción            
        
        if(tipoDato==3 || tipoDato==2){//es decir si el dato a traer será de pedido o de venta
            paraObtencion[4]=palabrasParaObtenerValor[tipoDato][2]+" = "+ "'"+requisito1+"'";
        }else{            
            paraObtencion[4]=palabrasParaObtenerValor[tipoDato][2]+" = "+"'"+requisito1+"'"+ " AND "+ palabrasParaObtenerValor[tipoDato][3]+" = "+ "'"+requisito2+"'";            
        }        
        
        return paraObtencion;
    }//este método cambiará o desaparecerá, puesto que para obtner el id de la venta o del pedido, se empleará select last_insert_id() ya que estas son autoincrementables, pordrá operar de forma correcta :)
    //entonces si cambia, lo más probable sería que se tenga a un arreglo en cada método que requiere de este, en donde tenga los metadatos correspondientes y los string de los datos a tratar
    //ó que se vuelva un método esecífico, como en el caso de buscar pedido...
    
    
    /**
     * Método encargado de mandar la info a insertar al método de las
     * inserciones, esta sintaxis será útil tanto para el método 
     * de inserción, como para el de actualización [ellos basicamente 
     * tienen la misma forma de trabajar     
     * @param nombreTabla
     * @param datosUsuario
     */
    public void registrarDatosUsuario(String[] datosUsuario, String nombreTabla){                            
       
       if(nombreTabla.equalsIgnoreCase("Cliente")){//ya que solo existen dos tipos de usuario...
            manejadorEstructuras.insertarDatosCompletosEnCliente(datosUsuario);//sería bueno que con ese boolean que da el manejador, se mantenga abierta la ventanita y se vuelvan a solicitar los datos
       }else{//ya que solo existen dos tipos de usuario...
           manejadorEstructuras.insertarDatosCompletosEnEmpleado(datosUsuario);
       }
       
       
       //aquí se manda a llamar el método de las estructuras para hacer una insercionGeneral... recuerda que debe llevar un where, en este caso sabes que es el nit... eso ya lo tienes       
   
   }//un método similar se hará para registrar a un trabajador
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    

    //BUENO... TE QUEDASTE EN EL HECHO DE QUE NO TE PARECE EL MÉTODO PARA ACTUALIZAR LA TABLA, EL DE UPDATE SI, Y EL DE GERENTE TB PERO EL DE LA TABL NO, PORQUE ES UN DISPARATE
    //Tienes que revisar esto porque según lo que llevas debes obtener nuevamente la cantidad del producto para sumarle la cantidad que se quito... si lleva mucho trabajo quitarás esto
    //porque DEBES terminar lo que en el enunciao está y nisiquiera tienes al método para leer el arch se RESPONSABLEEEE!
    
    //AHORA SI, VE LA SINTAXIS GENREAL DE LOS INSERT!, DE LOS UPDATE!, Porque requieres hacer eso en la parte donde eliminas o modificas... pues eso ya está controlado, solo debes llenar la info
    //y construir las consultas con ellas para que en el listener puedan exe con la var de manejadorDB y así se pueda actualizar el dato... tanto en la DB como en la lista Visual.. quien cb su
    //Dato al obtnerlo y luego reemplazar el val entre[] por el dado por el método de la transacción
    

}
