package AppDatabase;

import android.provider.BaseColumns;

public class StoppageContract {

    private StoppageContract() {}



    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "stoppages";
        public static final String COLUMN_NAME_BUS_NAME = "bus_name";
        public static final String COLUMN_NAME_ARRIVAL_TIME = "arrival_time";
        public static final String COLUMN_NAME_ROUTE_NAME = "route_name";
        public static final String COLUMN_NAME_STOPPAGE = "stoppage";
    }

}
