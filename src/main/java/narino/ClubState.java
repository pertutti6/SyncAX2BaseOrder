package narino;
public final class ClubState extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1536849827L;
	public static ClubState getClubState(int iPos)throws Exception{return (ClubState)new ClubState().getObject(iPos);}
}