package narino;
public final class OrderPositionState extends imulik.base.BaseObjectWithChilds
{
	public int iDocumentTypePos;
	public float fQuantity;
	public int iOrderPositionPos;
	private static final long serialVersionUID = 1531333635L;
	public static OrderPositionState getOrderPositionState(int iPos)throws Exception{return (OrderPositionState)new OrderPositionState().getObject(iPos);}
}