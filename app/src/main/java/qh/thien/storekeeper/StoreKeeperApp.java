package qh.thien.storekeeper;

import android.app.Application;

import qh.thien.storekeeper.persistence.AppDatabase;
import qh.thien.storekeeper.product.ProductRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class StoreKeeperApp extends Application{

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();
    }

    public AppDatabase getDataBase(){
        return AppDatabase.getInstance(this, appExecutors);
    }

    public ProductRepository getProductRepository(){
        return ProductRepository.getInstance(getDataBase());
    }
}
