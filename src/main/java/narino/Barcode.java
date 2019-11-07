package narino;


public final class Barcode extends imulik.base.BaseObjectWithChilds
{
	public int iSizePos;
	public int iArticlePos;
	public String sBarcode;
	private static final long serialVersionUID = 1529576168L;
	public static Barcode getBarcode(int iPos)throws Exception{return (Barcode)new Barcode().getObject(iPos);}

	public static int getBarcodePos(String sBarcode)throws Exception
	{
		narino.Barcode bc = new narino.Barcode();
		bc.sBarcode=sBarcode;
		int[] arPos=bc.search(sBarcode.length()==12?"":"sBarcode");
		if(arPos.length==0){return 0;}
		return arPos[0];
	}

}