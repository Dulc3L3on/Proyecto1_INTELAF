/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendTrabajadores;

import ManejoDeInformacion.ListaEnlazada;
import ManejoDeInformacion.ManejadorDB;

/**
 *
 * @author phily
 */
public class Usuario {//en este padre lo que se hará será construir todos aquello métodos que compartan las demás clases en donde se obtiene la información para armar la consulta, que la hija en específic desea, lo cual concuerda con lo que sucede en la realidad xD
    public ManejadorDB manejadorDB = new ManejadorDB();
    //este arreglo me simplifica el proceso al momento de add el producto, pues después de hacer la revisión con respecto a la cantidad si pasa la prueba ya solamente es de pasar este arreglo [ya que tiene la info tal y como debe recibirla el método para construir la consulta] y asó hacer la agregación a la tabla correspondiente [venta/epdido] luego de haber hecho el descuento correspondiente
    public String paraObtencion[] = new String[6];//se emplearán arreglos por el hecho de que los tipos de consulta pueden variar y por lo mismo tendría que agrandarse el bloque para que se pueda especificar en que ídice del ciclo se debe add y en cuales solo crear el nodo[esto por el hecho de que revisará el método general cuales están nulos y cuelesno para formar la declaración final    
    public String palabrasParaObtenerValor[][]= new String[3][4];
    
    public int lugarBusqueda;//debe guardarse ya que por medio de este valor en el listener se sabrá de donde obtnerl el código de la tienda
        
    public Usuario(){//Estas son las palabras pertenecientes a los metadatos de la tabla de donde se extraerá el dato
        palabrasParaObtenerValor[0][0]="unidades";
        palabrasParaObtenerValor[0][1]="Tienda_Producto";
        palabrasParaObtenerValor[0][2]="IDtienda";
        palabrasParaObtenerValor[0][3]="IDproducto";
    }
    
    
    
    /**
     * Este es para cuando el rbtn de producto esté seleccionado
     * sin importar que al final de cuentas termine convirtiendose
     * la operación en una venta o en un pedido, puesto se busca un 
     * producto, ya sea para vender o para comprar [o en el caso de
     * que no seleccione, solo para hallar xD]
     * [BUSCA EN T_P Y P]
     *
     * @param lugarDeBusqueda     
     * @param codigoTienda    
     * @param productoBuscado
     * @return
     */
    public String[] buscarProducto(int lugarDeBusqueda, String codigoTienda, String productoBuscado){//puesto que puede buscarse en más de 1 lugar, entonces se empleará un for, donde el índice en el que vaya, será el número que corresponda al lugar de búsqueda
        //lugarDeBUsqueda==1 en lugar conocido, 2 en los deconocidos, 3 solo busca en ambos xD
        //donde para decir eso se usará un no sera igual a... [ahí buscas como, por que no es un número para usar el <>
            lugarBusqueda=lugarDeBusqueda;        
        
            paraObtencion[1]="Tienda_Producto";
            paraObtencion[2]="Producto";
            paraObtencion[3]="Tienda_Producto.IDproducto = Producto.codigo";//puesto que una sola vez aparece ese código en producto, por ser su tabla
        
        if(lugarDeBusqueda==1){
            //Se forma llena el arreglo con los signos que corresponden, tanto en este como en los otros métodos específico s del cajero que tienen la misma función, la de abastecer con la info que se requiere para formar la declaración final :)                           
            paraObtencion[0]="IDproducto, nombre, fabricante, cantidad_reservada, precio";        
            paraObtencion[4]="IDtienda = "+ codigoTienda +" AND "+ "IDproducto LIKE "+productoBuscado+"%";//asumo que esto se app a la tabla del FROM...
            
        }if(lugarDeBusqueda==2){//ya que el número es proporcionado por un ciclo entonces no habrá error de recibir un número no esperado, si se llegara a necesitar este método en un lugar donde no esté encerrado por un ciclo, esto porque o es una u otra opción, ó porque se add más [aunque dudo esto último, entonces habría que add las otrs opciones, o un switch si se sabe que los números recibidos podrían diferir de los esperados            
            paraObtencion[0]="IDproducto, nombre, fabricante, cantidad_reservada, precio, IDtienda";            
            paraObtencion[4]="IDtienda != "+ codigoTienda+" AND "+ "IDproducto LIKE "+productoBuscado+"%";//pues puede que presione el ENTER antes de terminar de ingresar el código completo, entonces con ello debería buscar el cajero entre las coincidencias mostradas
        }else{//pues al buscar a un producto en general solamente debo serciorarme de que el código sea el del busca
            paraObtencion[0]="IDproducto, nombre, fabricante, cantidad_reservada, precio, IDtienda";//esta opción la add por el hecho de que los genrentes miran a los pedidos en general         
        }
    
        return paraObtencion;
    }//no olvides que el código de la tienda se obtendrá del cbBx por lo cual no importa que tienda origen tenga el pedido, porque al final de cuentas a ella se le avisará de que algo que no ha solicitado le llegará xD
    
    /**
     * Busca en 
     * @param codigoPedido
     * @return
     */
    public String[] buscarPedidoListos(String codigoPedido){//en este caso bastará con tener el código del pedido, porque una teinda solo puede entregar pedidos que a ella le hallan llegado, y pues si no lo encuentra es porque o aún hace falta que llegue ó es porque el cliente se confudió de tienda
    //puede ser de dos tipos ,por la agregación que hiciste, 
    //es decir el buscar pedido teniendo como destino la tienda 
    //actaul ó teniendo como destino alguna otra tienda
        paraObtencion[0]="codigo, IDcliente, nombre, estado";
        paraObtencion[1]="Pedido";
        paraObtencion[2]="Cliente";
        paraObtencion[3]="Pedido.IDcliente=Cliente.nit";
        paraObtencion[4]="codigo lIKE "+Integer.parseInt(codigoPedido)+"%";//por si acaso no ingresara todo el codiguito xD
    
        return paraObtencion;    
    }            
    
    
    
    /**
     * Empleado en el método "para el mouse clicked" ya que 
     * por medio de este es posible establecer el límite de
     * venta de un producto en específico...xD
     *
     * @param tipoDato
     * @param requisito1
     * @param requisito2
     
     * @return
     */
    public String[] obtenerCantidad(int tipoDato, String requisito1, String requisito2){//1.> cantidadProducto, 2.> # de venta, 3.> codigoPedido        
        paraObtencion[0]=palabrasParaObtenerValor[tipoDato][0];//la columna a traer
        paraObtencion[1]=palabrasParaObtenerValor[tipoDato][1];//tabla de obtneción            
        
        if(tipoDato==3){//es decir si el dato a traer será de pedido            
            paraObtencion[4]=palabrasParaObtenerValor[tipoDato][2]+" = "+requisito1;
        }else{            
            paraObtencion[4]=palabrasParaObtenerValor[tipoDato][2]+" = "+requisito1+ " AND "+ palabrasParaObtenerValor[tipoDato][3]+" = "+ requisito2;            
        }        
        
        return paraObtencion;
    }
    
    public String[] insertarEnTablasCompuestas(){
    
        return //se retornará el arreglo general para inserción.. solo que aún no lo coloco porque hay que definir cuantos comando mysql podrían necesitarse para hacer cualquier inserción aquí requerida;
    }
    
    public String definirLugarBusqueda(String[] codigoTiendas){
        if(lugarBusqueda==1){
            return codigoTiendas[0];//tienda actual
        }
        
        return codigoTiendas[1];//no creo que hay problemas por la opción 4 debida a que el genrente mirará a todos los productos...
    }
        
    public void registrarse(){    
        
    }        
    
    public int darLugarBusqueda(){
        return lugarBusqueda;
    }
    
}
