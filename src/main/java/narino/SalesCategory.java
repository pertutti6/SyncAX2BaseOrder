package narino;
public final class SalesCategory extends imulik.base.BaseObjectWithChilds
{
	public String sDescription;
	public String sCode;
	private static final long serialVersionUID = 1536661000L;
	public static SalesCategory getSalesCategory(int iPos)throws Exception{return (SalesCategory)new SalesCategory().getObject(iPos);}
}