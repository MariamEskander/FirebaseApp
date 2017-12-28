package com.mariamnarouz.firebaseapp.util.interactor;

/**
 * Created by Mariam.Narouz on 12/19/2017.
 */


import android.content.Context;
import android.os.AsyncTask;


public abstract class Interactor extends AsyncTask<Void, Integer, Result> {

    private InteractorListener mListener;
    private Context mContext;

    protected abstract Result onTaskWork();

    public Interactor(InteractorListener listener, Context context) {

        this.mListener = listener;
        this.mContext = context;
    }
    public Interactor(InteractorListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (mListener != null)
            mListener.onTaskStarted(this);
    }

    @Override
    protected Result doInBackground(Void... params) {

        return onTaskWork();
    }

    @Override
    protected void onPostExecute(Result result) {

        super.onPostExecute(result);

        if (mListener != null)
            mListener.onTaskFinished(this, result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        if (mListener != null)
            mListener.onTaskProgress(this, values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (mListener != null)
            mListener.onTaskCanceled(this);
    }

    public void updateView(int progress) {

        publishProgress(progress);
    }

    public Result executeSync() {

        return onTaskWork();
    }

}