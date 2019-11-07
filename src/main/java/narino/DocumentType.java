package narino;
public final class DocumentType extends imulik.base.BaseObjectWithChilds
{
	public int iPreviousTypePos;
	public String sState;
	public boolean bIsReservationRelevant;
	public boolean bIsStockRelevant;
	public String sColor;
	public boolean bIsClosed;
	public String sName;
	private static final long serialVersionUID = 2687119663L;
	public static DocumentType getDocumentType(int iPos)throws Exception{return (DocumentType)new DocumentType().getObject(iPos);}
}