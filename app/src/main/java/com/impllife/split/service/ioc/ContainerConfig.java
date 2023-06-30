package com.impllife.split.service.ioc;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.impllife.split.data.jpa.provide.AppDatabase;
import com.impllife.split.data.jpa.provide.PeopleDao;
import com.impllife.split.data.jpa.provide.TransactionDao;
import com.impllife.split.ui.MainActivity;

import static com.impllife.split.service.ioc.Container.inject;
import static com.impllife.split.service.ioc.Container.putBean;

public class ContainerConfig {
    public static void init() {
        putBean(() -> {
            RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(inject(MainActivity.class), AppDatabase.class, AppDatabase.DB_NAME);

            AppDatabase db = builder.build();
            putBean(TransactionDao.class, db.getTransactionDao());
            putBean(PeopleDao.class, db.getPeopleDao());
            return db;
        });
    }
}
