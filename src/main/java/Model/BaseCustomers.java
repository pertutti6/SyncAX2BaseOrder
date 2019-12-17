package Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaseCustomers extends Databases{
    List<BaseCustomer> customers;

    public BaseCustomers() {
        customers = new ArrayList<BaseCustomer>();
        setEnvironments("test","test","live", "test");




    }

    public void getCustFromMag(String sWhich)
    {
        String sSQL = "";

        if (openMagDB()) {
            if (sWhich.equalsIgnoreCase("open")) {
                sSQL = "SELECT * FROM customer_entity cus where last_base_sync != updated_at or last_base_sync IS NULL order by updated_at ";
            }
            ResultSet results = executeMagQuery(sSQL);
            while((results!=null) && (results.next()))
            {
                System.out.print(rec.getString("CustomerID"));
            }
        }
    }
}
