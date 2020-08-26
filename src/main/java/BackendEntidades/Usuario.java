/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendEntidades;

/**
 * En esta clase no se alamacenará nada, solo se tienen los métodos
 * que las entidades en el contexto comparte, de tal manera que no 
 * importa que cada vez se esté instanciando un nuevo objeto de dicha
 * entidad porque solo realizará procesos a base de los datos de las 
 * var STATIC de sus hijas quienes si deben saber que es lo que ha 
 * sucedido hasta el momento y por ello es nec que no se instancien 
 * cada vez, a menos que deba iniciarse desde 0
 * @author phily
 */
public class Usuario {//en este padre lo que se hará será construir todos aquello métodos que compartan las demás clases en donde se obtiene la información para armar la consulta, que la hija en específic desea, lo cual concuerda con lo que sucede en la realidad xD    
    //este arreglo me simplifica el proceso al momento de add el producto, pues después de hacer la revisión con respecto a la cantidad si pasa la prueba ya solamente es de pasar este arreglo [ya que tiene la info tal y como debe recibirla el método para construir la consulta] y asó hacer la agregación a la tabla correspondiente [venta/epdido] luego de haber hecho el descuento correspondiente
    public String paraObtencion[] = new String[6];//se emplearán arreglos por el hecho de que los tipos de consulta pueden variar y por lo mismo tendría que agrandarse el bloque para que se pueda especificar en que ídice del ciclo se debe add y en cuales solo crear el nodo[esto por el hecho de que revisará el método general cuales están nulos y cuelesno para formar la declaración final        
    
    
        
    public Usuario(){//Estas son las palabras pertenecientes a los metadatos de la tabla de donde se extraerá el dato
        
    }
    
    
    
    /**
     * Es un método general para buscar productos, que cumple con 
     * las necesidades del cliente, gerente [quienes buscan a todos
     * los productos] y a cajero que cuando el rbtn de producto 
     * esté seleccionado sin importar que al final de cuentas termine 
     * convirtiendose la operación en una venta o en un pedido, puesto
     * se busca un producto, ya sea para vender o para comprar [o en 
     * el caso de que no seleccione, solo para hallar xD]
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
            Trabajador.lugarBusqueda=lugarDeBusqueda;//por el hecho dde ser necesario para saber de donde se irá a trar el producto cuando se tenga que añadir
            String comparador[] = {"=", "<>"};
        
            paraObtencion[1]="Tienda_Producto";
            paraObtencion[2]="Producto";//si llegara a lanzar algún error puede que sea porque se está colocando una condición de la otra tabla, aunque, no veo que sea ilógico esto xD
            paraObtencion[3]="Tienda_Producto.IDproducto = Producto.codigo AND Tienda_Producto.unidades > 0";//puesto que una sola vez aparece ese código en producto, por ser su tabla.
        
        if(lugarDeBusqueda!=3){//luegar conocido
            //Se forma llena el arreglo con los signos que corresponden, tanto en este como en los otros métodos específico s del cajero que tienen la misma función, la de abastecer con la info que se requiere para formar la declaración final :)                           
            paraObtencion[0]="IDproducto, nombre, fabricante, cantidad_reservada, IDtienda, precio";        
            paraObtencion[4]="IDtienda "+comparador[lugarDeBusqueda-1]+ "'"+codigoTienda+"'" +" AND "+ "IDproducto LIKE "+"'"+productoBuscado+"%"+"'";//asumo que esto se app a la tabla del FROM...
            
         //en los lugares diferentes alconocido [es decir que no sea en tienda actual]
        }else{//ya que el número es proporcionado por un ciclo entonces no habrá error de recibir un número no esperado, si se llegara a necesitar este método en un lugar donde no esté encerrado por un ciclo, esto porque o es una u otra opción, ó porque se add más [aunque dudo esto último, entonces habría que add las otrs opciones, o un switch si se sabe que los números recibidos podrían diferir de los esperados            
            paraObtencion[0]="IDproducto, nombre, fabricante, cantidad_reservada, precio, IDtienda";//esta opción la add por el hecho de que los genrentes miran a los pedidos en general         
        }//par obtner estas columnas de todos...
    
        return paraObtencion;
    }//no olvides que el código de la tienda se obtendrá del cbBx por lo cual no importa que tienda origen tenga el pedido, porque al final de cuentas a ella se le avisará de que algo que no ha solicitado le llegará xD
    
    /**
     * Busca en 
     * @param codigoPedido
     * @return
     */
    public String[] buscarPedidoListos(long codigoPedido){//en este caso bastará con tener el código del pedido, porque una teinda solo puede entregar pedidos que a ella le hallan llegado, y pues si no lo encuentra es porque o aún hace falta que llegue ó es porque el cliente se confudió de tienda
    //puede ser de dos tipos ,por la agregación que hiciste, 
    //es decir el buscar pedido teniendo como destino la tienda 
    //actaul ó teniendo como destino alguna otra tienda
        paraObtencion[0]="numeroPedido, IDcliente, nombre, fechaRealizacion, estado, anticipo, total";//no te procupes por los tipos de las columnas listadas, de eso se encarga el JTable
        paraObtencion[1]="Pedido";
        paraObtencion[2]="Cliente";
        paraObtencion[3]="Pedido.IDcliente=Cliente.nit";
        paraObtencion[4]="codigo = "+ String.valueOf(codigoPedido);//por si acaso no ingresara todo el codiguito xD
    
        return paraObtencion;    
    }//deberá quedarse así por el hecho de que en la tabla se tiene contemplado esto                  
 
}
