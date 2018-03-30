package qh.thien.storekeeper.persistence;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import qh.thien.storekeeper.AppExecutors;
import qh.thien.storekeeper.product.Product;
import qh.thien.storekeeper.product.ProductDao;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "StoreKeeperDB";
    private static AppDatabase instance;

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public abstract ProductDao productDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors){
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = buildDatabase(context.getApplicationContext(), executors);
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    private static AppDatabase buildDatabase(final Context applicationContext, final AppExecutors executors) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(() -> {
                    addDelay();

                    AppDatabase database = getInstance(applicationContext, executors);
                    database.setDatabaseCreated();
                });
            }

        }).build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    private void updateDatabaseCreated(Context applicationContext) {
        if(applicationContext.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    public MutableLiveData<Boolean> getIsDatabaseCreated() {
        return isDatabaseCreated;
    }
}
