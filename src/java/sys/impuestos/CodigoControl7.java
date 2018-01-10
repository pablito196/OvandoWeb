/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.impuestos;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author JUAN PABLO
 */
public class CodigoControl7
{
    private String numeroAutorizacion;
    private long numeroFactura;
    private String nitci;
    private Date fechaTransaccion;
    private String fechaTransaccionStr;
    private int monto;
    private String llaveDosificacion;

    public long getNumeroFactura()
    {
        return this.numeroFactura;
    }

    public void setNumeroFactura(long numeroFactura)
    {
        this.numeroFactura = numeroFactura;
    }

    public String getNitci()
    {
        return this.nitci;
    }

    public void setNitci(String nitci)
    {
        this.nitci = nitci;
    }

    public Date getFechaTransaccion()
    {
        return this.fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion)
    {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getFechaTransaccionStr()
    {
        Date fecha = getFechaTransaccion();
        String formato = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);

        String resul = sdf.format(fecha);
        return resul;
    }

    public int getMonto()
    {
        return this.monto;
    }

    public void setMonto(int monto)
    {
        this.monto = monto;
    }

    public String getLlaveDosificacion()
    {
        return this.llaveDosificacion;
    }

    public void setLlaveDosificacion(String llaveDosificacion)
    {
        this.llaveDosificacion = llaveDosificacion;
    }

    public String obtener()
    {
        String numAutorizacion = getNumeroAutorizacion();
        String numFactura = String.valueOf(getNumeroFactura());
        String numNitci = getNitci();
        String numFechaTransaccion = getFechaTransaccionStr();
        String numMonto = String.valueOf(getMonto());
        String numLlaveDosificacion = getLlaveDosificacion();

        Verhoeff ver = new Verhoeff();

        numFactura = numFactura + ver.obtener(numFactura);
        numNitci = numNitci + ver.obtener(numNitci);
        numFechaTransaccion = numFechaTransaccion + ver.obtener(numFechaTransaccion);
        numMonto = numMonto + ver.obtener(numMonto);

        numFactura = numFactura + ver.obtener(numFactura);
        numNitci = numNitci + ver.obtener(numNitci);
        numFechaTransaccion = numFechaTransaccion + ver.obtener(numFechaTransaccion);
        numMonto = numMonto + ver.obtener(numMonto);

        long sumatoria = Long.parseLong(numFactura) + Long.parseLong(numNitci) + Long.parseLong(numFechaTransaccion) + Long.parseLong(numMonto);
        String sumaStr = String.valueOf(sumatoria);

        sumaStr = sumaStr + ver.obtener(sumaStr);
        sumaStr = sumaStr + ver.obtener(sumaStr);
        sumaStr = sumaStr + ver.obtener(sumaStr);
        sumaStr = sumaStr + ver.obtener(sumaStr);
        sumaStr = sumaStr + ver.obtener(sumaStr);

        String digitos = sumaStr.substring(sumaStr.length() - 5, sumaStr.length());

        int uno = Integer.parseInt(digitos.substring(0, 1)) + 1;
        int dos = Integer.parseInt(digitos.substring(1, 2)) + 1;
        int tres = Integer.parseInt(digitos.substring(2, 3)) + 1;
        int cuatro = Integer.parseInt(digitos.substring(3, 4)) + 1;
        int cinco = Integer.parseInt(digitos.substring(4, 5)) + 1;

        String cuno = getLlaveDosificacion().substring(0, uno);
        String cdos = getLlaveDosificacion().substring(uno, uno + dos);
        String ctres = getLlaveDosificacion().substring(uno + dos, uno + dos + tres);
        String ccuatro = getLlaveDosificacion().substring(uno + dos + tres, uno + dos + tres + cuatro);
        String ccinco = getLlaveDosificacion().substring(uno + dos + tres + cuatro, uno + dos + tres + cuatro + cinco);

        cuno = getNumeroAutorizacion() + cuno;
        cdos = numFactura + cdos;
        ctres = numNitci + ctres;
        ccuatro = numFechaTransaccion + ccuatro;
        ccinco = numMonto + ccinco;

        String mensaje = cuno + cdos + ctres + ccuatro + ccinco;
        String llave = getLlaveDosificacion() + digitos;
        AllegedRC4 ged = new AllegedRC4();
        String cifrado = ged.encriptaSinguion(mensaje, llave);

        int salto = 5;
        int numero0 = 0;
        int numero1 = 0;
        int numero2 = 0;
        int numero3 = 0;
        int numero4 = 0;
        int num = 0;
        for (int u = 0; u < cifrado.length(); u++) 
        {
            if (num == 0) 
            {
                numero0 = obtieneAscii(cifrado.toCharArray()[u]) + numero0;
            }
            if (num == 1) 
            {
                numero1 = obtieneAscii(cifrado.toCharArray()[u]) + numero1;
            }
            if (num == 2) 
            {
                numero2 = obtieneAscii(cifrado.toCharArray()[u]) + numero2;
            }
            if (num == 3) 
            {
                numero3 = obtieneAscii(cifrado.toCharArray()[u]) + numero3;
            }
            if (num == 4) 
            {
                numero4 = obtieneAscii(cifrado.toCharArray()[u]) + numero4;
            }
            num++;
            if (num == 5) 
            {
                num = 0;
            }
        }
        int total = numero0 + numero1 + numero2 + numero3 + numero4;

        numero0 = total * numero0 / uno;
        numero1 = total * numero1 / dos;
        numero2 = total * numero2 / tres;
        numero3 = total * numero3 / cuatro;
        numero4 = total * numero4 / cinco;

        int nuevoTotal = numero0 + numero1 + numero2 + numero3 + numero4;
        Base64 b = new Base64(nuevoTotal);
        String enbase = b.getResultado();

        String nuevaLlave = getLlaveDosificacion() + digitos;
        String nuevoMensaje = enbase;
        AllegedRC4 ge = new AllegedRC4();
        String codigoControl = ge.encripta(nuevoMensaje, nuevaLlave);
        return codigoControl;
    }

    public String getNumeroAutorizacion()
    {
        return this.numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion)
    {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public int obtieneAscii(char valor) 
    {
        return valor;
    }
}
