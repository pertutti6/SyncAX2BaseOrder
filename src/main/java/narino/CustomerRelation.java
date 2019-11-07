package narino;
public final class CustomerRelation extends imulik.base.BaseObjectWithChilds
{
	public int iOfCustomerPos;
	public int iIsTypePos;
	public int iCustomerPos;
	private static final long serialVersionUID = 1529576166L;
	public static CustomerRelation getCustomerRelation(int iPos)throws Exception{return (CustomerRelation)new CustomerRelation().getObject(iPos);}
}