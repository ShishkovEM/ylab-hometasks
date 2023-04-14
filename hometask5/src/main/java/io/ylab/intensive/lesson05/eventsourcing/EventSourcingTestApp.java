package io.ylab.intensive.lesson05.eventsourcing;

import io.ylab.intensive.lesson05.eventsourcing.api.ApiApp;
import io.ylab.intensive.lesson05.eventsourcing.db.DbApp;

public class EventSourcingTestApp {
    public static void main(String[] args) {
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
