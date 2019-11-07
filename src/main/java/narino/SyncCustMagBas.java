package narino;
import java.util.Date;
public final class SyncCustMagBas extends imulik.base.BaseObjectWithChilds
{
	public Date dBaseAdrModify;
	public Date dMagAdrModify;
	public Date dBaseCustModify;
	public Date dMagCustModify;
	public int iBaseAdrId;
	public int iMagAdrId;
	public int iBaseCustId;
	public int iMagCustId;
	private static final long serialVersionUID = 1549804592L;
	public static SyncCustMagBas getSyncCustMagBas(int iPos)throws Exception{return (SyncCustMagBas)new SyncCustMagBas().getObject(iPos);}
}