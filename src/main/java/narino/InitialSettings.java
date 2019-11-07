package narino;

import java.io.File;
import java.util.Date;

import annaik.db.client.IDatabase;
import imulik.base.BaseInitialSettings;

public final class InitialSettings extends imulik.base.BaseObjectWithChilds
{
	public String sPostPassword;
	public String sPostUserId;
	public String sPostLicense;
	public String sPathPostLabel;
	public int iOwnAddressPos;
	public int iSendingDocumentTypePos;
	public int iSingleOrderStockPos;
	public int iDefaultSalesChannelPos;
	public String sMultistoreUrl;
	public Date dateLastSyncWithMultistore;
	public int iPickingDocumentTypePos;
	public int iDefaultAdvertisementGroupPos;
	public long lNextClubNumber;
	public int iReservationDocumentTypePos;
	public int iLostAndFoundDocumentTypePos;
	public int iRelocationDocumentTypePos;
	public int iGoodExitDocumentTypePos;
	public int iGoodEntryDocumentTypePos;
	public int iInventoryDocumentTypePos;
	public int iAxDatabasePos;
	private static final long serialVersionUID = 1529843022L;
	public static InitialSettings getInitialSettings(int iPos)throws Exception{return (InitialSettings)new InitialSettings().getObject(iPos);}
	public static InitialSettings getInitialSettings()throws Exception{return (InitialSettings)BaseInitialSettings.getInitialSettings(InitialSettings.class);}
	
	public static long generateNextClubNumber()throws Exception
	{
		if(isRunningOnServer())
		{
			IDatabase db = IDatabase.Factory.getDatabase(InitialSettings.class);
			synchronized(db.getSynchronizationObject())
			{
				InitialSettings set = getInitialSettings();
				long lNext = set.lNextClubNumber++;
				set.update("lNextClubNumber");
				return lNext;
			}
		}
		else{return ((Long)getDbConn().getRMIClient(InitialSettings.class).call()).longValue();}
	}
	
	public static String getPostLabelFile(String sTrackTrace) throws Exception
	{
		String sPathPostLabel = getInitialSettings().sPathPostLabel;
		return sPathPostLabel+ (sPathPostLabel.endsWith(File.separator)?"":File.separator)+sTrackTrace+".zpl";
	}
}