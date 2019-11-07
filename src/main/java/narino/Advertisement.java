package narino;
public final class Advertisement extends imulik.base.BaseObjectWithChilds
{
	public byte[] sImageBytes;
	public int sImageNumber;
	public String sImage;
	public String sHref;
	public String sHtml;
	public int iOrder;
	public int iAdvertisementGroupPos;
	private static final long serialVersionUID = 1540373542L;
	public static Advertisement getAdvertisement(int iPos)throws Exception{return (Advertisement)new Advertisement().getObject(iPos);}
}