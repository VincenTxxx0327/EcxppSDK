package com.ecxppsdk.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ecxppsdk.R;


/**
 * Author: VincenT
 * Date: 2017/3/1 11:22
 * Contact:qq 328551489
 * Purpose:自定义加载框
 */
public class DialogLoading extends DialogFragment {


    private AlertDialog mAlertDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_loading_dialog, null);
        builder.setView(view);
        setCancelable(false);
        mAlertDialog = builder.create();
        return mAlertDialog;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}
