package qh.thien.storekeeper.product.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import qh.thien.storekeeper.product.Product;
import qh.thien.storekeeper.product.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

    private final LiveData<Product> observableProduct;
    private ObservableField<Product> product;
    private final int PRODUCT_ID;

    public ProductViewModel(@NonNull Application application, ProductRepository repository, final int productId) {
        super(application);
        PRODUCT_ID = productId;

        observableProduct = repository.findProduct(productId);
    }

    public LiveData<Product> getProduct() {
        return observableProduct;
    }

    public void setProduct(Product product){
        this.product.set(product);
    }
}
