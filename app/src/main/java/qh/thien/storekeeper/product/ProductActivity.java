package qh.thien.storekeeper.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import qh.thien.storekeeper.R;
import qh.thien.storekeeper.StoreKeeperApp;

public class ProductActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initSaveProductButton();
    }

    private String getTextFromEditText(int id){
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private void initSaveProductButton() {
        Button saveProductBtn = (Button) findViewById(R.id.act_product_btn_save);
        saveProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product productTobeSaved = new Product();
                productTobeSaved.setName(getTextFromEditText(R.id.act_product_et_product_name));
                productTobeSaved.setSellingPrice(Double.parseDouble(getTextFromEditText(R.id.act_product_et_product_selling_price)));
                productTobeSaved.setStockPrice(Double.parseDouble(getTextFromEditText(R.id.act_product_et_product_stock_price)));

                ((StoreKeeperApp)getApplication()).getAppExecutors().diskIO().execute(() -> {
                    ((StoreKeeperApp)getApplication()).getProductRepository().insert(productTobeSaved);
                });
            }
        });
    }
}
