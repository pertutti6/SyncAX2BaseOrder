package narino;
public final class TaxCode extends imulik.base.BaseObjectWithChilds
{
	public float fPercent;
	public String sName;
	private static final long serialVersionUID = 1580128329L;
	public static TaxCode getTaxCode(int iPos)throws Exception{return (TaxCode)new TaxCode().getObject(iPos);}
}