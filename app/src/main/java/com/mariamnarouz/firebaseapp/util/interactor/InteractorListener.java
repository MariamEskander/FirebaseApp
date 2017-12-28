package com.mariamnarouz.firebaseapp.util.interactor;

/**
 * Created by Mariam.Narouz on 12/19/2017.
 */

public interface InteractorListener {

     void onTaskStarted(Interactor interactor);

     void onTaskFinished(Interactor interactor, Result result);

     void onTaskProgress(Interactor interactor, int progress);

     void onTaskCanceled(Interactor interactor);


}