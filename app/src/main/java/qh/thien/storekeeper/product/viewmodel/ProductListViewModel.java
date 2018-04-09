package qh.thien.storekeeper.product.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import qh.thien.storekeeper.StoreKeeperApp;
import qh.thien.storekeeper.product.Product;

public class ProductListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Product>> observableProducts;

    public ProductListViewModel(Application application){
        super(application);

        observableProducts = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableProducts.setValue(null);

        LiveData<List<Product>> products = ((StoreKeeperApp) application).getProductRepository().getProducts();

        // observe the changes of the products from the database and forward them
        observableProducts.addSource(products, observableProducts::setValue);
    }

    public LiveData<List<Product>> getProducts(){
        return observableProducts;
    }
}
