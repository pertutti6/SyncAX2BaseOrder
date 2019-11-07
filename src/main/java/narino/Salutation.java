package narino;
public final class Salutation extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1529843028L;
	public static Salutation getSalutation(int iPos)throws Exception{return (Salutation)new Salutation().getObject(iPos);}
}