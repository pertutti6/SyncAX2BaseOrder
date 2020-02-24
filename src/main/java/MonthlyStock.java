import Model.BaseConnection;
import Model.BaseCustomer;
import Model.SendEmailOffice365;
import Model.Stammdaten;
import annaik.db.server.Database;
import narino.Article;
import narino.Barcode;
import narino.Stock;
import narino.StockQty;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthlyStock {

    static Stammdaten stammdaten;
    static List<ArticleStock> articleStocks;
    static List<ExcelLine> excelLines;


    public static void main(String[] args) {
        // Connect to Base
        BaseConnection base = new BaseConnection();
        base.setConnection("live");

        String filename = "";

        System.out.println("***************************************************");
        System.out.println("** Start Monthly Report about StockQty V1.01  ***");
        System.out.println("***************************************************");

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = simpleDateFormat.format(new Date());
            filename = date + "_stock.csv";

            articleStocks = new ArrayList<>();
            StockQty stockQty = new StockQty();
            int[] arrSQ = stockQty.search();
            System.out.println("read all stockqty's from base");
            for (int i = 0; i < arrSQ.length; i++) {
                stockQty = StockQty.getStockQty(arrSQ[i]);
                int iArticlePos = Barcode.getBarcode(stockQty.iBarcodePos).iArticlePos;
                Article article = Article.getArticle(iArticlePos);
                // write to list
                ArticleStock articlestock = new ArticleStock();
                articlestock.iArticlePos = iArticlePos;
                articlestock.iSingleArticlePos = article.iSingleArticlePos;
                articlestock.sArticleNr = article.sNumber;
                articlestock.sArticleName = article.sName;
                articlestock.iVEQty = article.iQuantity;
                articlestock.fStockQty = stockQty.fQuantity;
                articlestock.fVE1StockQty = articlestock.fStockQty * articlestock.iVEQty;
                articleStocks.add(articlestock);
            }
            System.out.println("summaries packages to an article");
            // summary of single articles
            excelLines = new ArrayList<>();
            for (int i = 0; i < articleStocks.size(); i++) {
                if (articleStocks.get(i).iArticlePos == articleStocks.get(i).iSingleArticlePos)
                {
                    // check if alredy read by another charge
                    if (existExcelLine(articleStocks.get(i).iSingleArticlePos))
                    {

                    }
                    else {
                        ExcelLine line = new ExcelLine();
                        line.iSingleArticlePos = articleStocks.get(i).iSingleArticlePos;
                        line.Tag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
                        line.Monat = Calendar.getInstance().get(Calendar.MONTH) + "";
                        line.Jahr = Calendar.getInstance().get(Calendar.YEAR) + "";
                        line.Menge = getQtySum(articleStocks.get(i).iSingleArticlePos) + "";
                        line.ArticleNr = articleStocks.get(i).sArticleNr;
                        line.ArticleName = articleStocks.get(i).sArticleName;
                        excelLines.add(line);
                    }
                }
            }
            System.out.println("write results to teh file : " + filename);
            //write result to file
            FileWriter csvWriter = new FileWriter(filename);
            csvWriter.append("Tag");
            csvWriter.append(",");
            csvWriter.append("Monat");
            csvWriter.append(",");
            csvWriter.append("Jahr");
            csvWriter.append(",");
            csvWriter.append("Menge");
            csvWriter.append(",");
            csvWriter.append("Artikelnr.");
            csvWriter.append(",");
            csvWriter.append("Artikelname");
            csvWriter.append("\n");

            for (int i = 0; i < excelLines.size(); i++) {
                csvWriter.append(excelLines.get(i).Tag);
                csvWriter.append(",");
                csvWriter.append(excelLines.get(i).Monat);
                csvWriter.append(",");
                csvWriter.append(excelLines.get(i).Jahr);
                csvWriter.append(",");
                csvWriter.append(excelLines.get(i).Menge);
                csvWriter.append(",");
                csvWriter.append(excelLines.get(i).ArticleNr);
                csvWriter.append(",");
                csvWriter.append(excelLines.get(i).ArticleName);
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("send eMail with attachment");
            // sent mail with this attachment
            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            date = simpleDateFormat.format(new Date());
            SendEmailOffice365 mail = new SendEmailOffice365();
            String sText = "Hallo\n";
            sText = sText + "\n";
            sText = sText +"Im Anhang findet Ihr den aktuellen Lagerbestand.\n";
            sText = sText + "\n";
            sText = sText +"Freundliche Grüsse\n";
            sText = sText + "\n";
            sText = sText + "Base\n";

            mail.sendEmail("base@sirocco.ch","sbaiz@sirocco.ch","","Lagerbestand " + date,sText,filename);
            System.out.println("***************************************************");
            System.out.println("***                   Fertig                    ***");
            System.out.println("***************************************************");


        } catch (Exception e) {
            e.printStackTrace();
        }
        String sErr;
    }

    private static boolean existExcelLine(int iSingleArticlePos) {
        for (int i = 0; i < excelLines.size(); i++) {
            if (excelLines.get(i).iSingleArticlePos == iSingleArticlePos)
            {
                return true;
            }
        }
        return false;
    }

    private static float getQtySum(int iSingleArticlePos) {
        float fSumme = 0;
        for (int j = 0; j < articleStocks.size(); j++) {
            if (articleStocks.get(j).iSingleArticlePos == iSingleArticlePos)
            {
                fSumme = fSumme + articleStocks.get(j).fVE1StockQty;
            }
        }
        return fSumme;
    }

    static class ExcelLine
    {
        int iSingleArticlePos;
        String Tag;
        String Monat;
        String Jahr;
        String Menge;
        String ArticleNr;
        String ArticleName;
    }

    static class ArticleStock
    {
        int iArticlePos;
        int iSingleArticlePos;
        String sArticleNr;
        String sArticleName;
        int iVEQty;
        float fStockQty;
        float fVE1StockQty;
    }
}
