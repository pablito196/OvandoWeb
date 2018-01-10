/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.impuestos;

/**
 *
 * @author JUAN PABLO
 */
public class Base64
{
    private String resultado;
    private long va;

    public Base64(long valor)
    {
        this.va = valor;
    }

    public String getResultado()
    {
        long cociente = 1L;
        long resto = 0L;
        String resul = "";
        while (cociente > 0L) 
        {
            cociente = this.va / 64L;
            resto = this.va % 64L;
            Diccionario dic = new Diccionario();
            //Diccionario dic = new Diccionario();
            resul = dic.getValor((int)resto) + resul;
            this.va = cociente;
        }
        this.resultado = resul;
        return this.resultado;
    }
}
