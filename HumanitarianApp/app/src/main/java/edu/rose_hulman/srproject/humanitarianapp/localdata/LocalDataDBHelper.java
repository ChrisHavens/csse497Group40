package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bollivga on 10/20/2015.
 */
public class LocalDataDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Humanitarian.db";
    private static final String SQL_LOCATION = "CREATE TABLE [Location](" +
            "[ID] [int] NOT NULL,"+
    "[Name] [nchar](50) NOT NULL,"+"[Lat] [int] NOT NULL,"+

    "[Lon] [int] NOT NULL,"+
    "[NameDirty] [boolean] NOT NULL,"+
    "[LatDirty] [boolean] NOT NULL,"+
    "[LonDirty] [boolean] NOT NULL,"+
    "CONSTRAINT [PK_Location] PRIMARY KEY CLUSTERED ( [ID] ASC))";
    private static final String SQL_PEOPLE = "CREATE TABLE [People]("+
            "[ID] [int] NOT NULL,"+
    "[Name] [nchar](50) NOT NULL,"+
    "[NameDirty] [boolean] NOT NULL,"+
    "CONSTRAINT [PK_People] PRIMARY KEY CLUSTERED"+
            "[ID] ASC))";
    private static final String SQL_CHECKLIST = "CREATE TABLE [Checklist](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Name] [nchar](50) NOT NULL,\n" +
            "\t[NameDirty] [boolean] NOT NULL,\n" +
            "\t[SuperID] [int] NOT NULL,\n" +
            "\t[SuperIDDirty] [boolean] NOT NULL,\n" +
            " CONSTRAINT [PK_Checklist] PRIMARY KEY CLUSTERED ([ID] ASC ))";
    private static final String SQL_PROJECT = "CREATE TABLE [Project]("+
    "[ID] [int] NOT NULL,"+
    "[Name] [nchar](50) NOT NULL,"+
    "[NameDirty] [boolean] NOT NULL,"+
    "CONSTRAINT [PK_Project] PRIMARY KEY CLUSTERED ( [ID] ASC ))";
    private static final String SQL_GROUP = "CREATE TABLE [Group](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Name] [nchar](50) NOT NULL,\n" +
            "\t[NameDirty] [boolean] NOT NULL,\n" +
            "\t[SuperID] [int] NOT NULL,\n" +
            "\t[SuperIDDirty] [boolean] NOT NULL,\n" +
            " CONSTRAINT [PK_Group] PRIMARY KEY CLUSTERED \n" +
            "(\n" +
            "\t[ID] ASC\n" +
            "))";
    private static final String SQL_EMAIL = "CREATE TABLE [Email](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Email] [nchar](30) NOT NULL,\n" +
            "\t[EmailDirty] [boolean] NOT NULL\n" +
            ")";
    private static final String SQL_PHONE = "CREATE TABLE [Phone](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Phone#] [nchar](20) NOT NULL,\n" +
            "\t[PhoneDirty] [boolean] NOT NULL\n" +
            ")";
    private static final String SQL_SHIPMENT = "CREATE TABLE [Shipment](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Name] [nchar](50) NOT NULL,\n" +
            "\t[NameDirty] [boolean] NOT NULL,\n" +
            "\t[Status] [nchar](20) NOT NULL,\n" +
            "\t[StatusDirty] [boolean] NOT NULL,\n" +
            "\t[ToLoc] [int] NOT NULL,\n" +
            "\t[ToLocDirty] [boolean] NOT NULL,\n" +
            "\t[LastLoc] [int] NOT NULL,\n" +
            "\t[LastLocDirty] [boolean] NOT NULL,\n" +
            "\t[FromLoc] [int] NOT NULL,\n" +
            "\t[FromLocDirty] [boolean] NOT NULL,\n" +
            "\t[Description] [nchar](140) NOT NULL,\n" +
            "\t[DescriptionDirty] [boolean] NOT NULL,\n" +
            " CONSTRAINT [PK_Shipment] PRIMARY KEY CLUSTERED \n" +
            "(\n" +
            "\t[ID] ASC\n" +
            "))";
    private static final String SQL_NOTE = "CREATE TABLE [Note](\n" +
            "\t[ID] [int] NOT NULL,\n" +
            "\t[Name] [nchar](50) NOT NULL,\n" +
            "\t[NameDirty] [boolean] NOT NULL,\n" +
            "\t[Contents] [nchar](140) NOT NULL,\n" +
            "\t[ContentsDirty] [boolean] NOT NULL,\n" +
            "\t[OwnerID] [int] NOT NULL,\n" +
            "\t[OwnerIDDirty] [boolean] NOT NULL,\n" +
            " CONSTRAINT [PK_Notes] PRIMARY KEY CLUSTERED \n" +
            "(\n" +
            "\t[ID] ASC\n" +
            "))";
    private static final String SQL_CHECKLIST_ITEM = "CREATE TABLE [ChecklistItem](\n" +
            "\t[ID] [nchar](10) NOT NULL,\n" +
            "\t[List] [int] NOT NULL,\n" +
            "\t[Person?] [int] NULL,\n" +
            "\t[PersonDirty] [boolean] NOT NULL,\n" +
            "\t[Done] [boolean] NOT NULL,\n" +
            "\t[DoneDirty] [boolean] NOT NULL,\n" +
            "\t[Info] [nchar](140) NOT NULL,\n" +
            "\t[InfoDirty] [boolean] NOT NULL,\n" +
            " CONSTRAINT [PK_ChecklistItem] PRIMARY KEY CLUSTERED \n" +
            "(\n" +
            "\t[ID] ASC\n" +
            "))";
    private static final String SQL_GROUP_LOC_REL = "CREATE TABLE [GroupLocRel](\n" +
            "\t[GroupID] [int] NOT NULL,\n" +
            "\t[LocID] [int] NOT NULL\n" +
            ")";
    private static final String SQL_PERSON_GROUP_REL = "CREATE TABLE [PersonGroupRel](\n" +
            "\t[PersonID] [int] NULL,\n" +
            "\t[GroupID] [int] NULL\n" +
            ")";
    private static final String SQL_PERSON_PROJ_REL = "CREATE TABLE [PersonProjRel](\n" +
            "\t[PersonID] [int] NOT NULL,\n" +
            "\t[ProjID] [int] NOT NULL\n" +
            ")";
    private static final String SQL_PERSON_LOC_REL = "CREATE TABLE [PersonLocRel](\n" +
            "\t[PersonID] [int] NOT NULL,\n" +
            "\t[LocID] [int] NOT NULL\n" +
            ")";
    private static final String SQL_SHIPMENT_GROUP_REL = "CREATE TABLE [ShipmentGroupRel](\n" +
            "\t[ShipmentID] [int] NOT NULL,\n" +
            "\t[SuperID] [int] NOT NULL\n" +
            ")";
    public LocalDataDBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_LOCATION);
        db.execSQL(SQL_PEOPLE);
        db.execSQL(SQL_PROJECT);
        db.execSQL(SQL_GROUP);
        db.execSQL(SQL_EMAIL);
        db.execSQL(SQL_PHONE);
        db.execSQL(SQL_CHECKLIST);
        db.execSQL(SQL_SHIPMENT);
        db.execSQL(SQL_NOTE);
        db.execSQL(SQL_CHECKLIST_ITEM);
        db.execSQL(SQL_GROUP_LOC_REL);
        db.execSQL(SQL_PERSON_GROUP_REL);
        db.execSQL(SQL_PERSON_LOC_REL);
        db.execSQL(SQL_PERSON_PROJ_REL);
        db.execSQL(SQL_SHIPMENT_GROUP_REL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }
}
