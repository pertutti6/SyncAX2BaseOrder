package narino;
import java.util.Date;
public final class AX_DELIVERY_PICKED extends imulik.base.BaseObjectWithChilds
{
	public Date PickedDate;
	public boolean NeedsSync2AX;
	public int PickedQty;
	public int SollQty;
	public long RECID;
	private static final long serialVersionUID = 1562597792L;
	public static AX_DELIVERY_PICKED getAX_DELIVERY_PICKED(int iPos)throws Exception{return (AX_DELIVERY_PICKED)new AX_DELIVERY_PICKED().getObject(iPos);}
}