package com.chicsol.alfalah.dialogs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.activities.MatchAidActivity;
import com.chicsol.alfalah.widgets.faTextView;

import org.json.JSONArray;


/**
 * Created by zeedr on 24/10/2016.
 */

public class dialogMatchAidUnderProcess extends DialogFragment {


    String userpath, jsarray;


    public static dialogMatchAidUnderProcess newInstance(JSONArray jsArray, String userpath) {

        dialogMatchAidUnderProcess frag = new dialogMatchAidUnderProcess();
        Bundle args = new Bundle();
        args.putString("jsArray", jsArray.toString());
        args.putString("userpath", userpath);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mArgs = getArguments();

        jsarray = mArgs.getString("jsArray");
        userpath = mArgs.getString("userpath");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_match_aid_underprocess, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        Button mOkButton = (Button) rootView.findViewById(R.id.mButtonDialogMatchAidUPViewProgress);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                dialogMatchAidUnderProcess.this.getDialog().cancel();
                Intent intent = new Intent(getActivity(), MatchAidActivity.class);
                startActivity(intent);

            }
        });


        faTextView cancelButton = (faTextView) rootView.findViewById(R.id.dismissBtnMatchAidUPCC);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                dialogMatchAidUnderProcess.this.getDialog().cancel();
            }
        });

        AppCompatButton cancelButton1     = (AppCompatButton) rootView.findViewById(R.id.mButtonDialogMatchAidUPCancel);
        cancelButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                dialogMatchAidUnderProcess.this.getDialog().cancel();
            }
        });


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


}


