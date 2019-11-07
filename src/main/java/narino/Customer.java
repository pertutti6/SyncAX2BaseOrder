package narino;
import java.util.Date;
public final class Customer extends imulik.base.BaseObjectWithChilds
{
	public String sCompany;
	public int iLastSync;
	public boolean bIsLead;
	public int iAdvertisementGroupPos;
	public String sEshopNumber;
	public Date dateBirth;
	public String sPassword;
	public Date dateDSVGO;
	public float fClubPoints;
	public int iClubStatePos;
	public Date dateClubSince;
	public String sClubNumber;
	public int iDefaultAdressPos;
	public int iSalutationPos;
	public int iBaseId;
	public int iLanguagePos;
	public String sFixnet;
	public String sMobile;
	public String sEmail;
	public String sLastName;
	public String sFirstName;
	private static final long serialVersionUID = 1529576164L;
	public static Customer getCustomer(int iPos)throws Exception{return (Customer)new Customer().getObject(iPos);}
}