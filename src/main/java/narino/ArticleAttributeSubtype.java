package narino;
public final class ArticleAttributeSubtype extends imulik.base.BaseObjectWithChilds
{
	public String sName;
	private static final long serialVersionUID = 1544107869L;
	public static ArticleAttributeSubtype getArticleAttributeSubtype(int iPos)throws Exception{return (ArticleAttributeSubtype)new ArticleAttributeSubtype().getObject(iPos);}
}