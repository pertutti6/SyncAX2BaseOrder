package narino;
public final class ArticleState extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1540931430L;
	public static ArticleState getArticleState(int iPos)throws Exception{return (ArticleState)new ArticleState().getObject(iPos);}
}