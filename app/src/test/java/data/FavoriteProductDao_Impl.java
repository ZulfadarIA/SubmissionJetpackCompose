package data;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.dicoding.submissionjetpackcompose.data.FavoriteProductDao;
import com.dicoding.submissionjetpackcompose.model.Clothes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteProductDao_Impl implements FavoriteProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Clothes> __insertionAdapterOfAudio;

  private final EntityDeletionOrUpdateAdapter<Clothes> __deletionAdapterOfAudio;

  public FavoriteProductDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAudio = new EntityInsertionAdapter<Clothes>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `clothes` (`id`,`image`,`titleProd`,`descriptionProd`,`detailProd`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Clothes value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getImage());
        if (value.getTitleProd() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitleProd());
        }
        if (value.getDescriptionProd() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescriptionProd());
        }
        if (value.getDetailProd() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDetailProd());
        }
      }
    };
    this.__deletionAdapterOfAudio = new EntityDeletionOrUpdateAdapter<Clothes>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Clothes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Clothes value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Object insert(final Clothes favorite, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAudio.insert(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final Clothes favorite, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAudio.handle(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Clothes>> getAllFavoriteClothes() {
    final String _sql = "SELECT * FROM Clothes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"Clothes"}, new Callable<List<Clothes>>() {
      @Override
      public List<Clothes> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "titleProd");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "descriptionProd");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detailProd");
          final List<Clothes> _result = new ArrayList<Clothes>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Clothes _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpImage;
            _tmpImage = _cursor.getInt(_cursorIndexOfImage);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            _item = new Clothes(_tmpId,_tmpImage,_tmpTitle,_tmpDescription,_tmpDetail);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Boolean> isFavorited(final long id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Clothes WHERE id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"Clothes"}, new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if(_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

}
