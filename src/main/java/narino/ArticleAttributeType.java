package narino;
public final class ArticleAttributeType extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1529843030L;
	public static ArticleAttributeType getArticleAttributeType(int iPos)throws Exception{return (ArticleAttributeType)new ArticleAttributeType().getObject(iPos);}
}