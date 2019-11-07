package narino;
public final class ArticleGroup extends imulik.base.BaseObjectWithChilds
{
	public String sDescription;
	public String sCode;
	private static final long serialVersionUID = 1529843029L;
	public static ArticleGroup getArticleGroup(int iPos)throws Exception{return (ArticleGroup)new ArticleGroup().getObject(iPos);}
}