package narino;

import annaik.db.client.IDatabase;
public final class Charge extends imulik.base.BaseObjectWithChilds
{
	public String sDescription;
	public String sCode;
	private static final long serialVersionUID = 1529843024L;
	public static Charge getCharge(int iPos)throws Exception{return (Charge)new Charge().getObject(iPos);}
	
	public static int getChargePos(String sCharge)throws Exception
	{
		if(isRunningOnServer())
		{
			int iPos = IDatabase.Factory.getDatabase(Charge.class).getColumn("sCode").getPositionFromString(sCharge);
			if(iPos<=0)
			{
				Charge ch = new Charge();
				ch.sCode=sCharge;
				iPos=ch.add();
			}
			return iPos;
		}
		else{return ((Integer)getRMIClient().call(sCharge)).intValue();}
	}
}