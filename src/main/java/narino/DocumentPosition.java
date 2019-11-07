package narino;

import java.util.Date;
import java.util.Hashtable;

import annaik.db.client.DatabaseConnection;
import annaik.db.client.IDatabase;
import annaik.util.Compiler;
import annaik.util.StackOfObjects;
import annaik.util.VectorOfObjects;

public final class DocumentPosition extends imulik.base.BaseObjectWithChilds
{
	public float fQuantity;
	public int iOrderPositionPos;
	public String sParam;
	public String sTempCharge;
	public int iChargePos;
	public int iStockPos;
	public int iBarcodePos;
	public int iDocumentPos;
	public int iDocumentPositionId;
	private static final long serialVersionUID = 1529591490L;
	public static DocumentPosition getDocumentPosition(int iPos)throws Exception{return (DocumentPosition)new DocumentPosition().getObject(iPos);}
	
	public transient int iTransient;
	
	public static StockQty[] getExpectedStock(int[] arBarcodePos, int[] arStockPos, Date datePer, int[] arChargePos)throws Exception
	{
		Hashtable ht = new Hashtable();
		if(arBarcodePos!=null){ht.put("iBarcodePos", arBarcodePos);}
		if(arChargePos!=null){ht.put("iChargePos", arChargePos);}
		IDatabase dbD = IDatabase.Factory.getDatabase(Document.class);
		if(arStockPos!=null){ht.put("iStockPos", arStockPos);}
		else{ht.put(DatabaseConnection.SEARCH_CRITERIA, "iStockPos!");}
		
		{
			Hashtable htDoc = new Hashtable();
			htDoc.put("iDocumentTypePos", new DocumentType().search("bIsStockRelevant! or bIsReservationRelevant!"));
			htDoc.put(DatabaseConnection.SEARCH_CRITERIA,"bClosed!");
			if(datePer!=null)
			{
				Document d = new Document();
				d.dateCreation=new Date(datePer.getTime()+1);
				htDoc.put("", d);
				DatabaseConnection.addCriteria(htDoc, "dateCreation<", false);
			}
			ht.put("iDocumentPos.", dbD.search(htDoc));
		}
		ht.put("iDocumentPos", new Document());
		
		ht.put(DatabaseConnection.SEARCH_SORT_BY, "iDocumentPos.dateCreation,iChargePos,iStockPos,iBarcodePos");
		IDatabase dbDp = IDatabase.Factory.getDatabase(DocumentPosition.class);
		return getStockQtyFromDp(dbDp.search(ht), false);
	}
	
	public static StockQty[] getStockQtyFromDp(int[] arSortedDpPos, boolean bForInventory)throws Exception
	{
		IDatabase dbDp = IDatabase.Factory.getDatabase(DocumentPosition.class);
		VectorOfObjects vecStock = new VectorOfObjects();
		if(arSortedDpPos.length>0)
		{
			synchronized (dbDp.getSynchronizationObject())
			{
				IDatabase dbD = IDatabase.Factory.getDatabase(Document.class);
				int[] arDpBarcodePos = (int[])dbDp.getArrayFromPositions("iBarcodePos", arSortedDpPos);
				int[] arDpStockPos = (int[])dbDp.getArrayFromPositions("iStockPos", arSortedDpPos);
				int[] arDpChargePos = (int[])dbDp.getArrayFromPositions("iChargePos", arSortedDpPos);
				float[] arDpQty = (float[])dbDp.getArrayFromPositions("fQuantity", arSortedDpPos);
				int[] arDocTypePos = (int[])dbD.getArrayFromPositions("iDocumentTypePos", (int[])dbDp.getArrayFromPositions("iDocumentPos", arSortedDpPos));
				boolean[] arStockRelevant = (boolean[])IDatabase.Factory.getDatabase(DocumentType.class).getArrayFromPositions("bIsStockRelevant", arDocTypePos);
				boolean[] arReservationRelevant = (boolean[])IDatabase.Factory.getDatabase(DocumentType.class).getArrayFromPositions("bIsReservationRelevant", arDocTypePos);
				boolean[] arIsVirtual = (boolean[])IDatabase.Factory.getDatabase(Article.class).getArrayFromPositions("bIsVirtual", (int[])IDatabase.Factory.getDatabase(Barcode.class).getArrayFromPositions("iArticlePos", arDpBarcodePos));
				
				double dQtySum = 0;
				double dReservationSum = 0;
				for (int i = 0; i < arSortedDpPos.length; i++)
				{
					float fCurQty = arDpQty[i];
					if(arReservationRelevant[i])
					{
						dReservationSum+=fCurQty;
					}
					if(bForInventory || arStockRelevant[i])
					{
						dQtySum+=fCurQty;
						if(dQtySum<dReservationSum){dReservationSum=Math.max(0, dQtySum);}
					}
					if((i==arSortedDpPos.length-1 || arDpBarcodePos[i]!=arDpBarcodePos[i+1] || arDpStockPos[i]!=arDpStockPos[i+1] || arDpChargePos[i]!=arDpChargePos[i+1]))
					{
						if(Math.abs(dQtySum)>0.0001 && !arIsVirtual[i])
						{
							StockQty sq = new StockQty();
							sq.fQuantity = (float)Compiler.round(dQtySum,3);
							sq.iBarcodePos=arDpBarcodePos[i];
							sq.iChargePos=arDpChargePos[i];
							sq.iStockPos=arDpStockPos[i];
							sq.fReserved=Math.max(0, Math.min(sq.fQuantity, (float)dReservationSum));
							vecStock.addElement(sq);
						}
						dQtySum=0;
						dReservationSum=0;
					}
				}
			}
		}
		StockQty[] ar = new StockQty[vecStock.size()];
		vecStock.copyTo(ar);
		return ar;
	}
	
	public static DocumentPosition[][] getValidDpFromOp(int[] arSortedOpPos, int iAllowedDocTypePos)throws Exception
	{
		DocumentPosition[][] arValidDp = new DocumentPosition[arSortedOpPos.length][0];
		if(arSortedOpPos.length>0)
		{
			IDatabase dbD = IDatabase.Factory.getDatabase(Document.class);
			IDatabase dbDp = IDatabase.Factory.getDatabase(DocumentPosition.class);
			Hashtable ht = new Hashtable();
			ht.put("iOrderPositionPos", arSortedOpPos);
			ht.put("iDocumentPos", new Document());
			{
				Hashtable htDoc = new Hashtable();
				htDoc.put("bClosed", new boolean[]{true});
				ht.put("iDocumentPos.", dbD.search(htDoc));
			}
			ht.put(DatabaseConnection.SEARCH_SORT_BY, "fQuantity,iDocumentPos.dateCreation,iOrderPositionPos");
			int[] arDpPos = dbDp.search(ht);
			int[] arDpDocTypePos = (int[])dbD.getArrayFromPositions("iDocumentTypePos", (int[])dbDp.getArrayFromPositions("iDocumentPos", arDpPos));
			int[] arDpPreviousTypePos = (int[])IDatabase.Factory.getDatabase(DocumentType.class).getArrayFromPositions("iPreviousTypePos", arDpDocTypePos);
			DocumentPosition[] arDp = new DocumentPosition[arDpPos.length];
			System.arraycopy(dbDp.getObjects(arDpPos), 0, arDp, 0, arDp.length);
			for (int i = 0; i < arDp.length; i++)
			{
				arDp[i].iTransient=arDpDocTypePos[i];
				float fRestQty=arDp[i].fQuantity;
				int iCurIndex = i-1;
				while(iCurIndex>-1 && arDp[iCurIndex].iOrderPositionPos==arDp[i].iOrderPositionPos && fRestQty!=0 && (arDp[iCurIndex].iChargePos==arDp[i].iChargePos || arDp[iCurIndex].iChargePos==0 || arDp[i].iChargePos==0))
				{
					if(arDp[iCurIndex].fQuantity*arDp[i].fQuantity<0)
					{
						if(arDp[iCurIndex].iStockPos==arDp[i].iStockPos || arDp[iCurIndex].iDocumentPos==arDp[i].iDocumentPos || (arDpPreviousTypePos[i]>0 && arDpDocTypePos[iCurIndex]==arDpPreviousTypePos[i]))
						{
							float fQtyToReduce = Math.signum(arDp[i].fQuantity)*Math.min(Math.abs(fRestQty), Math.abs(arDp[iCurIndex].fQuantity));
							arDp[iCurIndex].fQuantity+=fQtyToReduce;
							fRestQty-=fQtyToReduce;
						}
					}
					if(arDp[iCurIndex].iStockPos==arDp[i].iStockPos && arDpDocTypePos[iCurIndex]==arDpDocTypePos[i] && arDp[iCurIndex].fQuantity!=0 && arDp[iCurIndex].iChargePos==arDp[i].iChargePos)
					{
						arDp[iCurIndex].fQuantity+= arDp[i].fQuantity;
						arDp[i].fQuantity=0;
					}
					iCurIndex--;
				}
			}
			int iDpIndex=arDp.length-1;
			float[] arOpQty = (float[])IDatabase.Factory.getDatabase(OrderPosition.class).getArrayFromPositions("fQuantity", arSortedOpPos);
			StackOfObjects st = new StackOfObjects(arDp.length);
			
			for (int i = arSortedOpPos.length-1; i>-1 ; i--)
			{
				st.clear();
				float fRestQty = arOpQty[i];
				while(iDpIndex>-1 && arDp[iDpIndex].iOrderPositionPos==arSortedOpPos[i])
				{
					if(arDp[iDpIndex].fQuantity!=0 && fRestQty!=0)
					{
						if(iAllowedDocTypePos==0 || iAllowedDocTypePos == arDpDocTypePos[iDpIndex]){st.put(arDp[iDpIndex]);}
						if(Math.abs(fRestQty)<Math.abs(arDp[iDpIndex].fQuantity))
						{
							arDp[iDpIndex].fQuantity = Math.signum(arDp[iDpIndex].fQuantity)*Math.abs(fRestQty);
						}
						fRestQty=Math.signum(fRestQty)*(Math.abs(fRestQty)-Math.abs(arDp[iDpIndex].fQuantity));
					}
					iDpIndex--;
				}
				arValidDp[i]=new DocumentPosition[st.getFilledSize()];
				System.arraycopy(st.getArray(), 0, arValidDp[i], 0, arValidDp[i].length);
			}
		}
		return arValidDp;
	}
	
}