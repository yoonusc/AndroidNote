package com.studio.tranquil.notee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.infobreez.materialreceipt.model.PurchaseOrderModel;
import com.studio.tranquil.notee.Contact;

import java.util.ArrayList;

public class PoDatabaseHandler extends SQLiteOpenHelper {
   private static final String DATABASE_NAME = "materialReceipt";
   private static final int DATABASE_VERSION = 1;


   public PoDatabaseHandler(Context var1) {
      super(var1,DATABASE_NAME, (CursorFactory)null, DATABASE_VERSION);
   }

   public void importPo(PurchaseOrderModel purchaseOrderModel) {
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("subj", var1.get_sub());
      contentValues.put("note", var1.get_note());
      contentValues.put("date", var1.get_date());
      sqLiteDatabase.insert("note", (String)null, var3);
      var2.close();
   }

   public void Delete_Contact(int var1) {
      SQLiteDatabase var2 = this.getWritableDatabase();
      String[] var3 = new String[]{String.valueOf(var1)};
      var2.delete("note", "id = ?", var3);
      var2.close();
   }

   Contact Get_Contact(int var1) {
      SQLiteDatabase var2 = this.getReadableDatabase();
      String[] var3 = new String[]{"id", "subj", "note", "date"};
      String[] var4 = new String[]{String.valueOf(var1)};
      Cursor var5 = var2.query("note", var3, "id=?", var4, (String)null, (String)null, (String)null, (String)null);
      if(var5 != null) {
         var5.moveToFirst();
      }

      Contact var6 = new Contact(Integer.parseInt(var5.getString(0)), var5.getString(1), var5.getString(2), var5.getString(3));
      var5.close();
      var2.close();
      return var6;
   }

   public ArrayList Get_Contacts() {
      try {
         this.contact_list.clear();
         SQLiteDatabase var3 = this.getWritableDatabase();
         Cursor var4 = var3.rawQuery("SELECT  * FROM note", (String[])null);
         if(var4.moveToFirst()) {
            do {
               Contact var5 = new Contact();
               var5.setID(Integer.parseInt(var4.getString(0)));
               var5.set_sub(var4.getString(1));
               var5.set_note(var4.getString(2));
               var5.set_date(var4.getString(3));
               this.contact_list.add(var5);
            } while(var4.moveToNext());
         }

         var4.close();
         var3.close();
         ArrayList var7 = this.contact_list;
         return var7;
      } catch (Exception var8) {
         Log.e("all_contact", "" + var8);
         return this.contact_list;
      }
   }

   public int Get_Total_Contacts() {
      Cursor var1 = this.getReadableDatabase().rawQuery("SELECT  * FROM note", (String[])null);
      var1.close();
      return var1.getCount();
   }

   public ArrayList Search_Contacts(String param1) {

       return new ArrayList();
   }

   public int Update_Contact(Contact var1) {
      SQLiteDatabase var2 = this.getWritableDatabase();
      ContentValues var3 = new ContentValues();
      var3.put("subj", var1.get_sub());
      var3.put("note", var1.get_note());
      var3.put("date", var1.get_date());
      String[] var4 = new String[]{String.valueOf(var1.getID())};
      return var2.update("note", var3, "id = ?", var4);
   }

   public void onCreate(SQLiteDatabase var1) {
      var1.execSQL("CREATE TABLE note(id INTEGER PRIMARY KEY,subj TEXT,note TEXT,date TEXT)");
   }

   public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
      var1.execSQL("DROP TABLE IF EXISTS note");
      this.onCreate(var1);
   }
}
