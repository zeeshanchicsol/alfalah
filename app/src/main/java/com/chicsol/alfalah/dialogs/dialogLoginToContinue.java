package com.chicsol.alfalah.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.widgets.faTextView;


/**
 * Created by zeedr on 24/10/2016.
 */

public class dialogLoginToContinue extends DialogFragment {

    EditText etOtherReason;
   // String userpath, selectdlist, jsarray;
    int abtypeid = -1;
    private onCompleteLoginListener mCompleteLoginListener;

    public static dialogLoginToContinue newInstance() {

        dialogLoginToContinue frag = new dialogLoginToContinue();
/*        Bundle args = new Bundle();
        args.putString("jsArray", jsArray.toString());
        args.putString("userpath", userpath);*/
        //   frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  Bundle mArgs = getArguments();
        jsarray = mArgs.getString("jsArray");
        selectdlist = mArgs.getString("selectdlist");
        userpath = mArgs.getString("userpath");*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mCompleteLoginListener = (onCompleteLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnCompleteListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_login_to_continue, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final TextInputLayout textInputLayout = (TextInputLayout) rootView.findViewById(R.id.EditTextBlockDialgTextInputLayout);

        etOtherReason = (EditText) rootView.findViewById(R.id.EditTextBlockDialgOtherReason);




        Button mOkButton = (Button) rootView.findViewById(R.id.mButtonDialogBlock);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                mCompleteLoginListener.onCompleteLogin("");
                dialogLoginToContinue.this.getDialog().cancel();

                getActivity().finish();


            }
        });

        faTextView cancelButton = (faTextView) rootView.findViewById(R.id.mButtonDismissDialogBlock);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                //Toast.makeText(getActivity().getApplicationContext(), "clcieck", Toast.LENGTH_SHORT).show();
                dialogLoginToContinue.this.getDialog().cancel();
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }



    public static interface onCompleteLoginListener {
        public abstract void onCompleteLogin(String s);
    }

}


