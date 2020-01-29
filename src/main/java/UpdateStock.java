import Model.BaseConnection;
import Model.StockQtys;
import narino.Stock;
import narino.StockQty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateStock {

    public static void main(String[] args) {
   /*     StockQtys stockqtySoll = new StockQtys();
        List<StockQtys.Entity> inventory = new ArrayList<StockQtys.Entity>();

        stockqtySoll.readFileOrgStockQty();

        BaseConnection base = new BaseConnection();
        base.setConnection("live");

        try {
    //        stockqtySoll.createDocument();

            Stock stock = new Stock();
            int[] arrStock = stock.search();
            for (int st = 0; st < arrStock.length; st++) {
                int iStockPos = arrStock[st];
                StockQty stockqty = new StockQty();
                stockqty.iStockPos = iStockPos;
                int[] arrStoQty = stockqty.search();

                // List<StockQtys.Entity>  lstStoQty = stockqtySoll.searchStQty(iStockPos);

                for (int i = 0; i < arrStoQty.length; i++) {
                    float fQty;
                    StockQty stockQtyIst = StockQty.getStockQty(arrStoQty[i]);
                    // check if stockqty exist in soll
                    fQty =  stockqtySoll.findAndDelEntry(stockQtyIst);

                //    float fQty = 0;
                //    stockqtySoll.findIstEntry(lstStoQty.get(i));
                //    inventory.add( new StockQtys.Entity(lstStoQty.get(i)));
                    System.out.println(st + "," + i + "; Qty " + fQty);
                    if (fQty != stockQtyIst.fQuantity) {
                        stockqtySoll.createDocuPosition(stockQtyIst.iBarcodePos, stockQtyIst.iStockPos, stockQtyIst.iChargePos, fQty);
                    }
                }

            }
            // alle noch nicht erfassten StockQty's
   //         stockqtySoll.fillRestOfEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

    private class RunnableImpl implements Runnable {

        public void run()
        {
            System.out.println(Thread.currentThread().getName()
                    + ", executing run() method!");
        }
    }
}


