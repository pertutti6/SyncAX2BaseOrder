package narino;

import annaik.util.Compiler;

public final class Address extends imulik.base.BaseObjectWithChilds
{
	public int iLastSync;
	public int iEshopAddressId;
	public String sName;
	public int iBaseId;
	public int iPostalLocationPos;
	public String sStreet;
	private static final long serialVersionUID = 1529576165L;
	public static Address getAddress(int iPos)throws Exception{return (Address)new Address().getObject(iPos);}
	
	public String getFormated(int iCustomerPos)throws Exception
	{
		String sNameToUse = getName(iCustomerPos);
		PostalLocation loc = PostalLocation.getPostalLocation(iPostalLocationPos);
		Country c = Country.getCountry(loc.iCountryPos);
		return sNameToUse+Compiler.NEW_LINE+sStreet+Compiler.NEW_LINE+loc.sZipCode+" "+loc.sCity+Compiler.NEW_LINE+(Compiler.isSet(c.sFullName)?c.sFullName:"");
	}
	
	public String getName(int iCustomerPos)throws Exception
	{
		String sNameToUse = sName;
		if(!Compiler.isSet(sName))
		{
			Customer c = Customer.getCustomer(iCustomerPos);
			sNameToUse=(Compiler.isSet(c.sFirstName)?c.sFirstName+" ":"")+(Compiler.isSet(c.sLastName)?c.sLastName:"");
		}
		return sNameToUse;
	}
}