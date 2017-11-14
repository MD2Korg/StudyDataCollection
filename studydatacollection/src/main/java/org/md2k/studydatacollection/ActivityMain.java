package org.md2k.studydatacollection;

import android.os.Bundle;
import android.util.Log;

import org.md2k.datakitapi.time.DateTime;
import org.md2k.mcerebrum.system.update.Update;
import org.md2k.studydatacollection.menu.MyMenu;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ActivityMain extends AbstractActivityMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDataCollection();
        if (getIntent().getBooleanExtra("background", false))
            finish();
    }

    public void onDestroy() {
        super.onDestroy();
    }

}
