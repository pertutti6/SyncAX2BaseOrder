package narino;
public final class DeliveryType extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1551038637L;
	public static DeliveryType getDeliveryType(int iPos)throws Exception{return (DeliveryType)new DeliveryType().getObject(iPos);}
}