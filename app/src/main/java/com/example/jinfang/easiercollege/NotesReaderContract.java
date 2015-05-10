package com.example.jinfang.easiercollege;

import android.provider.BaseColumns;

/**
 * Created by jinfang on 4/5/15.
 */

public class NotesReaderContract {

    public NotesReaderContract() {}

    public static abstract class Note implements BaseColumns{
        public static final String TABLE_NAME = "notes";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT_CONTENT = "txt_content";
        public static final String COLUMN_NAME_PIC_CONTENT = "pic_content";
        public static final String COLUMN_NAME_TIME_CREATED = "time_created";
        public static final String COLUMN_NAME_TIME_LAST_SAVED ="time_last_saved";

    }

}
