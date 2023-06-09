package data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.dicoding.submissionjetpackcompose.data.FavoriteDataBase;
import com.dicoding.submissionjetpackcompose.data.FavoriteProductDao;

import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"uncheked", "deprecation"})
public final class FavoriteDatabase_Impl extends FavoriteDataBase {
  private volatile FavoriteProductDao _favoriteProductDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper( DatabaseConfiguration databaseConfiguration) {
    final SupportSQLiteOpenHelper.Callback _openCallBack = new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(1) {

      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `clothes` (`id` INTEGER NOT NULL, `image` INTEGER NOT NULL, `titleProd` TEXT NOT NULL, `description` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY, identitiy_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES(42, 'dde9b90cc398129fddc0bd4351bca65c')");
      }
      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size= mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `clothes`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public  RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsClothes = new HashMap<String, TableInfo.Column>(5);
        _columnsClothes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClothes.put("image", new TableInfo.Column("image", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClothes.put("title", new TableInfo.Column("titleProd", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClothes.put("description", new TableInfo.Column("descriptionProd", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClothes.put("detail", new TableInfo.Column("detailProd", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeyClothes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClothes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClothes = new TableInfo("clothes", _columnsClothes, _foreignKeyClothes, _indicesClothes);
        final TableInfo _existingClothes = TableInfo.read(_db, "clothes");
        if (! _infoClothes.equals(_existingClothes)) {
          return new RoomOpenHelper.ValidationResult(false, "clothes(com.dicoding.submissionjatcpackcompose.model.Clothes).\n"
                  + " Expected:\n" + _infoClothes + "\n"
                  + " Found:\n" + _existingClothes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "dde9b90cc398129fddc0bd4351bca65c", "9ec54532ade70c233013bf2ecb2712b6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context)
            .name(databaseConfiguration.name)
            .callback(_openCallBack)
            .build();
    final SupportSQLiteOpenHelper _helper = databaseConfiguration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "clothes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `clothes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoriteProductDao.class, FavoriteProductDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
          @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public FavoriteProductDao favoriteProductDao() {
    if (_favoriteProductDao != null) {
      return _favoriteProductDao;
    } else {
      synchronized(this) {
        if(_favoriteProductDao == null) {
          _favoriteProductDao = new FavoriteProductDao_Impl(this);
        }
        return _favoriteProductDao;
      }
    }
  }
}

