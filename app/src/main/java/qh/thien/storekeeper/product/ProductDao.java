package qh.thien.storekeeper.product;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();

    @Query("SELECT p.name FROM product p")
    LiveData<List<Product>> getAllNames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product p WHERE p.uid = :productId")
    LiveData<Product> getById(int productId);
}
