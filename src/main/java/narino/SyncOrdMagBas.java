package narino;
import java.util.Date;
public final class SyncOrdMagBas extends imulik.base.BaseObjectWithChilds
{
	public Date dBaseOrdLineModify;
	public Date dMagOrdLineModify;
	public Date dBaseOrdModify;
	public Date dMagOrdModify;
	public int iBaseOrdLineId;
	public int iMagOrdLineId;
	public int iBaseOrdId;
	public int iMagOrdId;
	private static final long serialVersionUID = 1549804593L;
	public static SyncOrdMagBas getSyncOrdMagBas(int iPos)throws Exception{return (SyncOrdMagBas)new SyncOrdMagBas().getObject(iPos);}
}