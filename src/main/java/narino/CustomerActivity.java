package narino;
public final class CustomerActivity extends imulik.base.BaseObjectWithChilds
{
	public String sFunction;
	public String sPage;
	public String sChannel;
	public int iBarcodePos;
	public int iCustomerPos;
	private static final long serialVersionUID = 1529591494L;
	public static CustomerActivity getCustomerActivity(int iPos)throws Exception{return (CustomerActivity)new CustomerActivity().getObject(iPos);}
}