package narino;
public final class Size extends imulik.base.BaseObjectWithChilds
{
	public String sCode;
	private static final long serialVersionUID = 1529591492L;
	public static Size getSize(int iPos)throws Exception{return (Size)new Size().getObject(iPos);}
}