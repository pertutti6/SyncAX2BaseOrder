package narino;
public final class ArticleDescription extends imulik.base.BaseObjectWithChilds
{
	public String sText10;
	public String sText09;
	public String sText08;
	public String sText07;
	public String sText06;
	public String sText05;
	public String sText04;
	public String sText03;
	public String sText02;
	public String sText01;
	public int iLanguagePos;
	public int iArticlePos;
	private static final long serialVersionUID = 1540034833L;
	public static ArticleDescription getArticleDescription(int iPos)throws Exception{return (ArticleDescription)new ArticleDescription().getObject(iPos);}
}