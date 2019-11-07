package narino;
public final class CustomerAttribute extends imulik.base.BaseObjectWithChilds
{
	public String sValue;
	public int iCustomerAttributeTypePos;
	public int iCustomerPos;
	private static final long serialVersionUID = 1538580324L;
	public static CustomerAttribute getCustomerAttribute(int iPos)throws Exception{return (CustomerAttribute)new CustomerAttribute().getObject(iPos);}
}