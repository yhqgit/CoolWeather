package com.project.yhq.coolweather.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.project.yhq.coolweather.db.CoolWeatherOpenHelper;

import java.sql.SQLData;

/**
 * Created by yhq on 2016/3/1.
 */
public class DatabaseProvider extends ContentProvider
{
    public static final int PROVINCE_DIR = 0;

    public static final int PROVINCE_ID = 1;

    public static final String AUTHORITY = "com.project.yhq.coolweather.provider";

    private static UriMatcher uriMatcher;

    private CoolWeatherOpenHelper dbHelper;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "province", PROVINCE_DIR);
        uriMatcher.addURI(AUTHORITY, "province/#", PROVINCE_ID);
    }

    @Override
    public boolean onCreate()
    {
        dbHelper = new CoolWeatherOpenHelper(getContext(), "cool_weather", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri))
        {
            case PROVINCE_DIR:
                cursor = db.query("Province", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PROVINCE_ID:
                String provinceId = uri.getPathSegments().get(1);
                cursor = db.query("Province", projection, "id = ?", new String[]{provinceId}, null, null, sortOrder);
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        switch (uriMatcher.match(uri))
        {
            case PROVINCE_DIR:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY +  ".province";
            case PROVINCE_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".province";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri))
        {
            case PROVINCE_DIR:
            case PROVINCE_ID:
                long newProvinceId = db.insert("Province", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY  + "/Province/" + newProvinceId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri))
        {
            case PROVINCE_DIR:
                deletedRows = db.delete("Province", selection, selectionArgs);
                break;
            case PROVINCE_ID:
                String provinceId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Province", "id = ?", new String[]{provinceId});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri))
        {
            case PROVINCE_DIR:
                updatedRows = db.update("Province", values, selection, selectionArgs);
                break;
            case PROVINCE_ID:
                String provinceId = uri.getPathSegments().get(1);
                updatedRows = db.update("Province",values, "id = ?", new String[]{provinceId});
                break;
            default:
                break;
        }
        return updatedRows;
    }
}
