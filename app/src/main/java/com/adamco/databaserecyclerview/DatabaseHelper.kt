package com.adamco.databaserecyclerview

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseConstants.DATABASE_NAME,
    null,
    DatabaseConstants.DATABASE_VERSION
) {
    override fun onCreate(database: SQLiteDatabase) {
        val createTableQuery = """
         CREATE TABLE ${DatabaseConstants.TABLE_NAME} (
         ${DatabaseConstants.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DatabaseConstants.COLUMN_NAME} TEXT,
         ${DatabaseConstants.COLUMN_AGE} INTEGER 
         )
     """.trimIndent()

        database.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade
    }

    fun insertData(user: MyUser): Long {
        val values = ContentValues().apply {
            put(DatabaseConstants.COLUMN_NAME, user.name)
            put(DatabaseConstants.COLUMN_AGE, user.age)
        }

        return writableDatabase.insert(DatabaseConstants.TABLE_NAME, null, values)
    }

    fun readData(): List<MyUser> {
        val userList = mutableListOf<MyUser>()
        val cursor: Cursor = readableDatabase.query(DatabaseConstants.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseConstants.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseConstants.COLUMN_NAME))
                val age = getInt(getColumnIndexOrThrow(DatabaseConstants.COLUMN_AGE))

                userList.add(MyUser(id = id, name = name, age = age))
            }
        }
        return userList
    }

    fun updateUser(user: MyUser): Long {
        val values = ContentValues().apply {
            put(DatabaseConstants.COLUMN_AGE, user.age)
            put(DatabaseConstants.COLUMN_NAME, user.name)
        }

        val selection = "${DatabaseConstants.COLUMN_ID} = ?"
        val selectionArg = arrayOf(user.id.toString())

        return writableDatabase.update(DatabaseConstants.TABLE_NAME, values, selection, selectionArg).toLong()
    }

    fun deleteUser(id: Long): Long {
        val selection = "${DatabaseConstants.COLUMN_ID} = ?"
        val selectionArg = arrayOf(id.toString())

        return writableDatabase.delete(DatabaseConstants.TABLE_NAME, selection, selectionArg).toLong()
    }
}
