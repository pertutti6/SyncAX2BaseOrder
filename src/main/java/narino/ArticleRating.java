package narino;
public final class ArticleRating extends imulik.base.BaseObjectWithChilds
{
	public String sComment;
	public int iRate;
	public int iCustomerPos;
	public int iArticlePos;
	private static final long serialVersionUID = 1539175269L;
	public static ArticleRating getArticleRating(int iPos)throws Exception{return (ArticleRating)new ArticleRating().getObject(iPos);}
}