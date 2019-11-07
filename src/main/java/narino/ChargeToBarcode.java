package narino;
import java.util.Date;
public final class ChargeToBarcode extends imulik.base.BaseObjectWithChilds
{
	public int iRawMaterialChargePos;
	public Date dateExpiry;
	public int iChargePos;
	public int iBarcodePos;
	private static final long serialVersionUID = 1535568038L;
	public static ChargeToBarcode getChargeToBarcode(int iPos)throws Exception{return (ChargeToBarcode)new ChargeToBarcode().getObject(iPos);}
}