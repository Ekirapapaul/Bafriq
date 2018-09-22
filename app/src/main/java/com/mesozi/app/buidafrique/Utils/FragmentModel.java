package com.mesozi.app.buidafrique.Utils;

/**
 * Created by ekirapa on 9/29/17.
 *
 * @author ekirapa
 */

public class FragmentModel {
    public interface FragStateChangeListener {

        void nextFrag(int number);
        void nextFrag(String name, String email, String number, String password);
    }

    private static FragmentModel mInstance;
    private FragStateChangeListener mListener;
    private int tag;

    private FragmentModel() {
    }

    public static FragmentModel getInstance() {
        if (mInstance == null) mInstance = new FragmentModel();
        return mInstance;
    }

    public void setListener(FragStateChangeListener listener) {
        mListener = listener;
    }

    public void addNewFrag(int number) {
        if (mListener != null) {
            mListener.nextFrag(number);
        }
    }
    public void addNewFrag(String name, String email, String number, String password) {
        if (mListener != null) {
            mListener.nextFrag(name, email, number, password);
        }
    }
}
