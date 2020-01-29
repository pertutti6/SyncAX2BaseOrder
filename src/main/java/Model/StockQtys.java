package Model;

import narino.Document;
import narino.DocumentPosition;
import narino.Stock;
import narino.StockQty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockQtys {
    List<Entity> lstSoll = new ArrayList<Entity>();
    List<Entity> lstIst = new ArrayList<Entity>();
    int iDocumentPos = 0;

    public void readFileOrgStockQty() {
        BufferedReader csvReader = null;
        Entity entity;
        try {
            csvReader = new BufferedReader(new FileReader("C:\\Temp\\ausfall\\original.csv"));
            String row;
            boolean bFirst = true;
            while ((row = csvReader.readLine()) != null) {
                System.out.println(row);
                if (bFirst)
                {
                    bFirst = false;
                }
                else {
                    String[] data = row.split(";");
                    entity = new Entity();
                    entity.iID = Integer.parseInt(data[0].toString());
                    entity.iBarcodePos = Integer.parseInt(data[1].toString());
                    entity.iStockPos = Integer.parseInt(data[2].toString());
                    entity.iChargePos = Integer.parseInt(data[3].toString());
                    entity.fQuantity = Integer.parseInt(data[4].toString());
                    entity.fReserve = Integer.parseInt(data[5].toString());
                    // do something with the data
                    lstSoll.add(entity);
                }
            }
            csvReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createDocument() {
        try {
            Document docu = new Document();
            docu.dateCreation = new Date();
            docu.iDocumentTypePos = 1003;
            docu.sParam = "Ausfall 22.1.2020";
            iDocumentPos = docu.add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findIstEntry(Entity entityIst) {
        float fQty = 0;
        for (int i = 0; i < lstSoll.size(); i++) {
            Entity entity = lstSoll.get(i);
            if (!entity.bDeleted) {
                if (entity.iStockPos == entityIst.iStockPos &&
                        entity.iBarcodePos == entityIst.iBarcodePos &&
                        entity.iChargePos == entityIst.iChargePos)
                {
                    fQty = entity.fQuantity;
                    entity.bDeleted = true;
                    break;
                }
            }
        }
        entityIst.fQuantity = fQty;
    }

    public float findAndDelEntry(StockQty stockQtyIst) {
        float fQty = 0;
        for (int i = 0; i < lstSoll.size(); i++) {
            Entity entity = lstSoll.get(i);
            if (!entity.bDeleted) {
                if (entity.iStockPos == stockQtyIst.iStockPos &&
                        entity.iBarcodePos == stockQtyIst.iBarcodePos &&
                        entity.iChargePos == stockQtyIst.iChargePos) {
                    fQty = stockQtyIst.fQuantity;
                    entity.bDeleted = true;
                    break;
                }
            }
        }
        return fQty;
    }

    public void createDocuPosition(int iBarcodePos,int iStockPos, int iChargePos, float fQty)
    {
        try {
            DocumentPosition position = new DocumentPosition();
            position.iBarcodePos = iBarcodePos;
            position.iStockPos = iStockPos;
            position.iChargePos = iChargePos;
            position.fQuantity = fQty;
            position.iDocumentPos = iDocumentPos;
            int pos = position.add();
            System.out.println("created position " + pos + "; Docu " + iDocumentPos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fillRestOfEntity() {
        for (int i = 0; i < lstSoll.size(); i++) {

            try {
                Entity entity = lstSoll.get(i);
                if (!entity.bDeleted) {
                    DocumentPosition position = new DocumentPosition();
                    position.iBarcodePos = entity.iBarcodePos;
                    position.iStockPos = entity.iStockPos;
                    position.iChargePos = entity.iChargePos;
                    position.fQuantity = (float) entity.fQuantity;
                    position.iDocumentPos = iDocumentPos;
                    int pos = position.add();
                    System.out.println("created position " + pos + "; Docu " + iDocumentPos);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void readStockQtyIst() {
        Entity entity;
        try {
            StockQty stockQtyIst = new StockQty();
            int[] arrSQty = stockQtyIst.search();
            for (int i = 0; i < arrSQty.length; i++) {
                stockQtyIst = StockQty.getStockQty(arrSQty[i]);
                System.out.println("Read base StockQty " + i);
                entity = new Entity();
                entity.iID = stockQtyIst.getId();
                entity.iBarcodePos = stockQtyIst.iBarcodePos;
                entity.iStockPos = stockQtyIst.iStockPos;
                entity.iChargePos = stockQtyIst.iChargePos;
                entity.fQuantity = stockQtyIst.fQuantity;
                entity.fReserve = stockQtyIst.fReserved;
                // do something with the data
                lstIst.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Entity> searchStQty(int iStockPos) {
        List<Entity> list = new ArrayList<Entity>();
        for (int i = 0; i < lstIst.size(); i++) {
            if (lstIst.get(i).iStockPos == iStockPos)
            {
                list.add(lstIst.get(i));
            }
        }
        return list;
    }

    public void checkStock() {
        try {
            createDocument();
            for (int i = 0; i < lstSoll.size(); i++) {
                System.out.println("Position " + i);
                Entity entity = lstSoll.get(i);
                StockQty stockqty = new StockQty();
                stockqty.iStockPos = entity.iStockPos;
                stockqty.iBarcodePos = entity.iBarcodePos;
                stockqty.iChargePos = entity.iChargePos;
                stockqty.fQuantity = entity.fQuantity;
                int[] intArr = stockqty.search();
                if (intArr.length == 0) {
                    String error = "fehlt";
                    createDocuPosition(entity.iBarcodePos, entity.iStockPos, entity.iChargePos,entity.fQuantity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class Entity
    {
        public int iID;
        int iBarcodePos;
        int iStockPos;
        int iChargePos;
        float fQuantity;
        float fReserve;
        boolean bDeleted = false;

        public Entity() {
        }

        public Entity(Entity entity) {
            this.iID = entity.iID;
            this.iBarcodePos = entity.iBarcodePos;
            this.iStockPos = entity.iStockPos;
            this.iChargePos = entity.iChargePos;
            this.fQuantity = entity.fQuantity;
            this.fReserve = entity.fReserve;
            this.bDeleted = entity.bDeleted;
        }
    }
}
