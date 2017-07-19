package com.ecxppsdk.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ecxppsdk.R;


/**
 * Author: VincenT
 * Date: 2017/3/1 11:22
 * Contact:qq 328551489
 * Purpose:自定义加载框
 */
public class DialogLoading extends DialogFragment {

    TextView tvMainText;

    private AlertDialog mAlertDialog;
    private String mText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_loading_dialog, null);
        builder.setView(view);
        setCancelable(false);
        tvMainText = (TextView) view.findViewById(R.id.tv_dialog_mainText);
        tvMainText.setText(TextUtils.isEmpty(mText) ? getString(R.string.text_loading) : mText);
        mAlertDialog = builder.create();
        return mAlertDialog;
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}
