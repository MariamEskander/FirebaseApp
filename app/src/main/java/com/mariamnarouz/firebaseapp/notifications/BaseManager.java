package com.mariamnarouz.firebaseapp.notifications;

/**
 * Created by Mariam.Narouz on 12/28/2017.
 */

import com.mariamnarouz.firebaseapp.util.interactor.Result;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;


abstract class BaseManager extends Observable {

    void updateObservers(int event) {
        updateObservers(event, null);
    }

    void updateObservers(int event, Object data) {
        setChanged();

        Result result = new Result(event);
        if (data != null) {
            result = new Result(event, data);
        }
        notifyObservers(result);
    }

    public void register(Observer observer) {
        addObserver(observer);
    }

    public void unregister(Observer observer) {
        deleteObserver(observer);
    }

}


