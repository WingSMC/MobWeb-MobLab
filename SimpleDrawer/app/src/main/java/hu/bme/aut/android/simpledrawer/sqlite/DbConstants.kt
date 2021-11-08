package hu.bme.aut.android.simpledrawer.sqlite

import android.database.sqlite.SQLiteDatabase
import android.util.Log

object DbConstants {
    const val DATABASE_NAME = "simpledrawer.db"
    const val DATABASE_VERSION = 4 // Növelt verziószám color miatt

    object Points {
        enum class Columns {
            ID, COORD_X, COORD_Y, COLOR
        }

        const val DATABASE_TABLE = "points"
        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"
        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key autoincrement,
            ${Columns.COORD_X.name} real not null,
            ${Columns.COORD_Y.name} real not null,
            ${Columns.COLOR.name} integer not null
        );"""

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Points::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }

    object Lines {
        enum class Columns {
            ID, START_X, START_Y, END_X, END_Y, COLOR
        }

        const val DATABASE_TABLE = "lines"
        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"
        private val DATABASE_CREATE ="""create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key autoincrement,
            ${Columns.START_X.name} real not null,
            ${Columns.START_Y.name} real not null,
            ${Columns.END_X.name} real not null,
            ${Columns.END_Y.name} real not null,
            ${Columns.COLOR.name} integer not null
        );"""

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Lines::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }
}
