package narino;
public final class FavoriteOrderPosition extends imulik.base.BaseObjectWithChilds
{
	public String sParam;
	public int iArticlePos;
	public int iFavoriteOrderPos;
	public float fQuantity;
	private static final long serialVersionUID = 1537006202L;
	public static FavoriteOrderPosition getFavoriteOrderPosition(int iPos)throws Exception{return (FavoriteOrderPosition)new FavoriteOrderPosition().getObject(iPos);}
}