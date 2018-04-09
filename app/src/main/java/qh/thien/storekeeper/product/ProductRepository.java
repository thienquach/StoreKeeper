package qh.thien.storekeeper.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import qh.thien.storekeeper.persistence.AppDatabase;

public class ProductRepository {

    private static ProductRepository instance;
    private final AppDatabase appDatabase;
    private MediatorLiveData<List<Product>> observableProducts;

    private ProductRepository(final AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        observableProducts = new MediatorLiveData<>();

        observableProducts.addSource(appDatabase.productDao().getAll(), products ->{
            if(this.appDatabase.getIsDatabaseCreated().getValue() != null){
                observableProducts.postValue(products);
            }
        });
    }

    public static ProductRepository getInstance (final AppDatabase appDatabase){
        if(instance == null){
            synchronized (ProductRepository.class){
                if(instance == null){
                    instance = new ProductRepository(appDatabase);
                }
            }

        }
        return instance;
    }

    public LiveData<List<Product>> getProducts(){
        return observableProducts;
    }

    public LiveData<Product> findProduct(final int productId){
        return appDatabase.productDao().getById(productId);
    }
}
