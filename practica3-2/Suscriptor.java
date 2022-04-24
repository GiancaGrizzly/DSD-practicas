// Clase que representa a un suscriptor

public class Suscriptor
{
    private String identificador;
    private boolean ha_donado;

    public Suscriptor (String identificador)
    {
        this.identificador = identificador;
        ha_donado = false;
    }

    public String GetId ()
    {
        return identificador;
    }

    public boolean Ha_donado ()
    {
        return ha_donado;
    }

    public void Set_donado ()
    {
        ha_donado = true;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this) return true;

        if (!(o instanceof Suscriptor)) return false;


        Suscriptor susc = (Suscriptor) o;
        return this.identificador.equals(susc.identificador);
    }
}
