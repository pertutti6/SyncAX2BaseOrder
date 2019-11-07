package narino;
import java.util.Date;
public final class AX_SalesLine extends imulik.base.BaseObjectWithChilds
{
	public Date MODIFIEDDATETIME;
	public long RECID;
	public long PARTITION;
	public String RECVERSION;
	public String DATAAREAID;
	public String CREATEDBY;
	public String CREATEDDATETIME;
	public String MODIFIEDBY;
	public String SALESUNIT;
	public String EXTITEMIDPARENT;
	public String EXTITEMID;
	public String HASERROR;
	public String ITEMNAME;
	public double TAXPERCENT;
	public double LINEDISCMST;
	public double LINEAMOUNTMST;
	public double TAXAMOUNT;
	public double LINEPERCENT;
	public double LINEDISC;
	public double TAXAMOUNTMST;
	public double LINEAMOUNT;
	public double SALESPRICEMST;
	public double SALESPRICE;
	public String TAXITEMGROUP;
	public double QTYORDERED;
	public String ITEMID;
	public double LINENUM;
	public String SALESIDEXT;
	private static final long serialVersionUID = 1543239319L;
	public static AX_SalesLine getAX_SalesLine(int iPos)throws Exception{return (AX_SalesLine)new AX_SalesLine().getObject(iPos);}
}