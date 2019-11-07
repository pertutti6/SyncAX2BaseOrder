package narino;
public final class ArticleAttributeValue extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1529843031L;
	public static ArticleAttributeValue getArticleAttributeValue(int iPos)throws Exception{return (ArticleAttributeValue)new ArticleAttributeValue().getObject(iPos);}
}