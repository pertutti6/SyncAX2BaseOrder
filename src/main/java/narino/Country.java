package narino;
public final class Country extends imulik.base.BaseObjectWithChilds
{
	public String sFullName;
	public String sShortName;
	private static final long serialVersionUID = 1529591495L;
	public static Country getCountry(int iPos)throws Exception{return (Country)new Country().getObject(iPos);}
}