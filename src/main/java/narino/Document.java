package narino;
import imulik.base.BaseObjectWithChilds;

import java.util.Date;
import java.util.Hashtable;

import annaik.as.client.IConnectionToServer;
import annaik.db.client.DatabaseConnection;
import annaik.db.client.IDatabase;
import annaik.db.server.Database;
import annaik.rmi.client.RMIClient;
import annaik.util.ThreadsPool;
import annaik.util.VectorOfObjects;
public final class Document extends imulik.base.BaseObjectWithChilds
{
	public int iRelatedDocumentPos;
	public boolean bClosed;
	public String sParam;
	public int iDocumentTypePos;
	public Date dateCreation;
	public int iDocumentId;
	private static final long serialVersionUID = 1529591489L;
	public static Document getDocument(int iPos)throws Exception{return (Document)new Document().getObject(iPos);}
	
	public static void receiveMdeData(Object[] arDoc, Object[][] arDocPositions)throws Exception
	{
		if(arDoc.length>0)
		{
			IDatabase dbHead = IDatabase.Factory.getDatabase(Document.class);
			synchronized(dbHead.getSynchronizationObject())
			{
				int[] arHeadPos = dbHead.add(arDoc);
				try
				{
					IDatabase dbDp=IDatabase.Factory.getDatabase(DocumentPosition.class);;
					VectorOfObjects vecPositions = new VectorOfObjects();
					for (int i = 0; i < arDocPositions.length; i++)
					{
						if(arDocPositions[i].length>0)
						{
							int[] arCurHeadPos = new int[arDocPositions[i].length];
							for (int j = 0; j < arCurHeadPos.length; j++){arCurHeadPos[j]=arHeadPos[i];}
							dbDp.getColumn("iDocumentPos").writeValuesToObjects(arCurHeadPos, arDocPositions[i]);
							
							
							Object[] arDocPos = arDocPositions[i];
							boolean bSkipAddPositions=false;
							for (int j = 0; j < arDocPos.length; j++)
							{
								if(((DocumentPosition)arDocPositions[i][j]).iBarcodePos==0)
								{
									bSkipAddPositions=true;
									break;
								}
							}
							if(!bSkipAddPositions)
							{
								vecPositions.addElements(arDocPositions[i]);
							}
						}
					}
					if(vecPositions.size()>0){dbDp.add(vecPositions.popArray());}
				}
				catch(Exception ex)
				{
					dbHead.delete(arHeadPos);
					throw ex;
				}
			}
		}
		
	}
	
	public static void sendMdeData(IConnectionToServer conn)throws Exception
	{
		IDatabase dbD = IDatabase.Factory.getDatabase(Document.class);
		IDatabase dbDp = IDatabase.Factory.getDatabase(DocumentPosition.class);

		{//deleting old records
            Document d = new Document();
            d.iCreationDate = ThreadsPool.currentTimeSeconds()-7*24*3600;
            int[] arOldHeadPos = d.search(BaseObjectWithChilds.CREATION_DATE_FIELD_NAME+"<");
            if(arOldHeadPos.length>0)
			{
				Hashtable ht = new Hashtable();
				ht.put("iDocumentPos",arOldHeadPos);
				dbDp.delete(dbDp.search(ht));
				dbD.delete(arOldHeadPos);
			}
        }
		

		int[] arHeadPos = dbD.search(new Document(), "bClosed!", ".");
		if(arHeadPos.length>0)
		{
			Hashtable ht = new Hashtable();
			ht.put("iDocumentPos",arHeadPos);
	        ht.put(DatabaseConnection.SEARCH_SORT_BY, "iDocumentPos");
	        int[] arPositionsPos = dbDp.search(ht);
	        int[] arPositionsHeadPos = (int[])dbDp.getArrayFromPositions("iDocumentPos", arPositionsPos);
	        Object[] arDoc = dbD.getObjects(arHeadPos);
	        Object[] arPositions = dbDp.getObjects(arPositionsPos);
	        Object[][] arDocPositions = new Object[arDoc.length][];
	        int iPosCounter = 0;
	        for (int i = 0; i < arDoc.length; i++)
			{
				VectorOfObjects vecCurPositions = new VectorOfObjects();
				while(iPosCounter<arPositions.length && arPositionsHeadPos[iPosCounter]==arHeadPos[i])
				{
					vecCurPositions.addElement(arPositions[iPosCounter++]);
				}
				arDocPositions[i]=vecCurPositions.popArray();
			}
	        RMIClient.getRmiClient(conn, Document.class.getName()).callSynchron("receiveMdeData", arDoc, arDocPositions);
	        dbDp.delete(arPositionsPos);
	        dbD.delete(arHeadPos);
	        Database.writeDatabasesToFiles();
		}		
	}
	
	public static int[] getDocumentPositionPos(int iDocumentPos, boolean bPositiveOnly)throws Exception
	{
		if(iDocumentPos>0)
		{
			DocumentPosition dp = new DocumentPosition();
			dp.iDocumentPos=iDocumentPos;
			Hashtable ht = new Hashtable();
			ht.put("", dp);
			ht.put("iOrderPositionPos", new OrderPosition());
			ht.put(DatabaseConnection.SEARCH_SORT_BY, "iOrderPositionPos.iEshopOrderLineId");
			if(bPositiveOnly){ht.put(DatabaseConnection.SEARCH_CRITERIA, "fQuantity>");}
			return dp.search(ht);
		}
		return new int[0];
	}
}