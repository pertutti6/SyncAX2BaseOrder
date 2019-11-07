package narino;
public final class FavoriteOrder extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	public int iCustomerPos;
	public int iBaseId;
	private static final long serialVersionUID = 1537006201L;
	public static FavoriteOrder getFavoriteOrder(int iPos)throws Exception{return (FavoriteOrder)new FavoriteOrder().getObject(iPos);}
}