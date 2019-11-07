package narino;
public final class SalesChannel extends imulik.base.BaseObjectWithChilds
{
	public byte[] sPackingImageBytes;
	public int sPackingImageNumber;
	public String sPackingImage;
	public boolean bPrintFreeReturnLabel;
	public boolean bSkipReturnLabel;
	public String sName;
	private static final long serialVersionUID = 1546193197L;
	public static SalesChannel getSalesChannel(int iPos)throws Exception{return (SalesChannel)new SalesChannel().getObject(iPos);}
}