package narino;

import java.util.Hashtable;

import annaik.db.client.IDatabase;
import annaik.util.VectorOfIntegers;


public final class OrderPosition extends imulik.base.BaseObjectWithChilds
{
	public String sChargeRequest;
	public long lErpOrderlineID;
	public int iLastSync;
	public int iEshopOrderLineId;
	public float fTotalWithTax;
	public int iTaxCodePos;
	public boolean bClosed;
	public float fReductionValue;
	public float fReductionPercent;
	public float fOriginalUnitPrice;
	public float fQuantity;
	public int iArticlePos;
	public int iOrderPos;
	public int iOrderPositionId;
	private static final long serialVersionUID = 1529843026L;
	public static OrderPosition getOrderPosition(int iPos)throws Exception{return (OrderPosition)new OrderPosition().getObject(iPos);}
	
	/*public static int[] getOrderPositionPosToPick(int iOrderPos)throws Exception
	{
		if(isRunningOnServer())
		{
			OrderPositionState ops = new OrderPositionState();
			ops.iDocumentTypePos=InitialSettings.getInitialSettings().iReservationDocumentTypePos;
			int[] arOpsPos = ops.search("iDocumentTypePos");
			if(arOpsPos.length>0)
			{
				int[] arOpPos = (int[])IDatabase.Factory.getDatabase(OrderPositionState.class).getArrayFromPositions("iOrderPositionPos", arOpsPos);
				Hashtable ht = new Hashtable();
				ht.put(".", arOpPos);
				ht.put("iOrderPos", new int[]{iOrderPos});
				ht.put(DatabaseConnection.SEARCH_CRITERIA, "iArticlePos");
				return new OrderPosition().search(ht);
			}
			return new int[0];
		}
		else{return (int[])getRMIClient().call(new Integer(iOrderPos));}
	}*/
	
	public static float[] getQty(int[] arOpPos, int iDocTypePos)throws Exception
	{
		if(arOpPos.length>0)
		{
			if(isRunningOnServer())
			{
				int[] arMinMax = VectorOfIntegers.getMinMax(arOpPos);
				float[] arOpPosToQty=new float[arMinMax[1]-arMinMax[0]+1];
				IDatabase dbOps = IDatabase.Factory.getDatabase(OrderPositionState.class);
				Hashtable ht = new Hashtable();
				ht.put("iOrderPositionPos", arOpPos);
				ht.put("iDocumentTypePos", new int[]{iDocTypePos});
				int[] arOpsPos = dbOps.search(ht);
				float[] arOpsQty = (float[])dbOps.getArrayFromPositions("fQuantity", arOpsPos);
				int[] arOpsOpPos = (int[])dbOps.getArrayFromPositions("iOrderPositionPos", arOpsPos);
				for (int i = 0; i < arOpsPos.length; i++)
				{
					arOpPosToQty[arOpsOpPos[i]-arMinMax[0]]+=arOpsQty[i];
				}
				float[] arToReturn = new float[arOpPos.length];
				for (int i = 0; i < arToReturn.length; i++)
				{
					arToReturn[i]=arOpPosToQty[arOpPos[i]-arMinMax[0]];
				}
				return arToReturn; 
			}
			else{return (float[])getRMIClient().call(arOpPos, new Integer(iDocTypePos));}
		}
		return new float[0];
	}
}