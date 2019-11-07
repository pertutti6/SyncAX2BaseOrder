package narino;

import annaik.gui.IGuiContainer;
import annaik.gui.ReportGuiFrame.IReportProducer;
import annaik.util.VectorOfObjects;

public final class ReportTemplate extends imulik.base.BaseObjectWithChilds
{
	public int iSalesChannelPos;
	public String sReportProducerClass;
	public byte byQuantity;
	public String sName;
	private static final long serialVersionUID = 1546193199L;
	public static ReportTemplate getReportTemplate(int iPos)throws Exception{return (ReportTemplate)new ReportTemplate().getObject(iPos);}
	
	public static ReportTemplate[] findTemplates(int iSalesChannelPos)throws Exception
	{
		if(iSalesChannelPos>0)
		{
			ReportTemplate temp = new ReportTemplate();
			temp.iSalesChannelPos=iSalesChannelPos;
			int[] arPos = temp.search("iSalesChannelPos");
			Object[] ar = temp.getObjects(arPos);
			ReportTemplate[] toReturn = new ReportTemplate[ar.length];
			System.arraycopy(ar, 0, toReturn, 0, ar.length);
			return toReturn;
		}
		return new ReportTemplate[0];
	}
	
	public void fillPages(Document docShipment, VectorOfObjects vecPages)throws Exception
	{
		final IReportProducer producer = (IReportProducer)Class.forName(sReportProducerClass).newInstance();
		for (int i = 0; i < byQuantity; i++)
		{
			int iPage = 0;
			while(true)
			{
				IGuiContainer cont = producer.getPage(iPage, new Object[]{docShipment});
				if(cont!=null){vecPages.addElement(cont);}
				else{break;}
				iPage++;
			}
		}
	}
}