package narino;
public final class ArticleAttribute extends imulik.base.BaseObjectWithChilds
{
	public int iArticleAttributeSubtypePos;
	public String sVariableValue;
	public int iArticleAttributeValuePos;
	public int iArticleAttributeTypePos;
	public int iArticlePos;
	private static final long serialVersionUID = 1529591493L;
	public static ArticleAttribute getArticleAttribute(int iPos)throws Exception{return (ArticleAttribute)new ArticleAttribute().getObject(iPos);}
}