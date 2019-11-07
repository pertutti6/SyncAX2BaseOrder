package narino;
public final class PostalLocation extends imulik.base.BaseObjectWithChilds
{
	public int iBaseId;
	public String sCity;
	public String sZipCode;
	public int iCountryPos;
	private static final long serialVersionUID = 1529591496L;
	public static PostalLocation getPostalLocation(int iPos)throws Exception{return (PostalLocation)new PostalLocation().getObject(iPos);}
}