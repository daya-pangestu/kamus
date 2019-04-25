package com.example.daya.kamus.db;

import android.provider.BaseColumns;

class DatabaseContract {

    static final String TABLE_ENGLISH = "table_kamus_english";
    static final String TABLE_INDONESIA = "table_kamus_indonesia";

    static final class KamusEnglish implements BaseColumns {
        static final String WORD = "word";
        static final String TRANSLATE = "translate";
    }

    static final class KamusIndonesia implements BaseColumns {
        static final String KATA = "kata";
        static final String TERJEMAHAN = "terjemahan";
    }

}
