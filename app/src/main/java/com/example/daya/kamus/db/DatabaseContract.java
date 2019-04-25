package com.example.daya.kamus.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_ENGLISH = "table_kamus_english";
    static String TABLE_INDONESIA = "table_kamus_indonesia";

    static final class KamusEnglish implements BaseColumns {
        static String WORD = "word";
        static String TRANSLATE = "translate";
    }

    static final class KamusIndonesia implements BaseColumns {
        static String KATA = "kata";
        static String TERJEMAHAN = "terjemahan";
    }

}
