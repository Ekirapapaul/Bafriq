package com.buildafrique.pn.app.Utils;

/**
 * Created by ekirapa on 9/29/17.
 *
 * @author ekirapa
 */

public class DataNotifier {
    public interface StateChangeListener {

        void notifyData(int number, int id);
    }

    private static DataNotifier mInstance;
    private StateChangeListener mListener;
    private int tag;

    private DataNotifier() {
    }

    public static DataNotifier getInstance() {
        if (mInstance == null) mInstance = new DataNotifier();
        return mInstance;
    }

    public void setListener(StateChangeListener listener) {
        mListener = listener;
    }

    public void dataChanged(int number, int id) {
        if (mListener != null) {
            mListener.notifyData(number, id);
        }
    }
}
