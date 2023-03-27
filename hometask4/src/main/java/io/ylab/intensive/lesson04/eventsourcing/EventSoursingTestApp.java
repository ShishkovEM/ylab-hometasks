package io.ylab.intensive.lesson04.eventsourcing;

import io.ylab.intensive.lesson04.eventsourcing.api.ApiApp;
import io.ylab.intensive.lesson04.eventsourcing.db.DbApp;

public class EventSoursingTestApp {
    public static void main(String[] args) throws Exception {
        String[] arg = new String[0];

        new Thread(() -> {
            try {
                DbApp.main(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                ApiApp.main(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
