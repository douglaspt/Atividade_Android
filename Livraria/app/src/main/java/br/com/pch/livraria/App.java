package br.com.pch.livraria;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by douglas.teixeira on 15/03/2017.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
