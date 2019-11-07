package narino;
public final class PaymentMethod extends imulik.base.BaseObjectWithChilds
{
	public short shPayableDays;
	public String sName;
	private static final long serialVersionUID = 1539349349L;
	public static PaymentMethod getPaymentMethod(int iPos)throws Exception{return (PaymentMethod)new PaymentMethod().getObject(iPos);}
}