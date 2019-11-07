package narino;
public final class CustomerAttributeType extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1538580323L;
	public static CustomerAttributeType getCustomerAttributeType(int iPos)throws Exception{return (CustomerAttributeType)new CustomerAttributeType().getObject(iPos);}
}