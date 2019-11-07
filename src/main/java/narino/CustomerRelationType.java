package narino;
public final class CustomerRelationType extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1529843027L;
	public static CustomerRelationType getCustomerRelationType(int iPos)throws Exception{return (CustomerRelationType)new CustomerRelationType().getObject(iPos);}
}