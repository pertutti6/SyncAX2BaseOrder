package narino;
public final class AdvertisementGroup extends imulik.base.BaseObjectWithChilds
{
	public int iDefaultLanguagePos;
	public int iDefaultSalutationPos;
	public String sName;
	private static final long serialVersionUID = 1540373541L;
	public static AdvertisementGroup getAdvertisementGroup(int iPos)throws Exception{return (AdvertisementGroup)new AdvertisementGroup().getObject(iPos);}
}