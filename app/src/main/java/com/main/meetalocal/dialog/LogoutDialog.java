package com.main.meetalocal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.main.meetalocal.R;
import com.main.meetalocal.user.activity.LoginActivity;
import com.main.meetalocal.database.Authentication;

public class LogoutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.logout_dialog_question)
            .setPositiveButton(R.string.logout_dialog_logout, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Authentication().signOutCurrentUser();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            })
            .setNegativeButton(R.string.logout_dialog_stay, null);

        return dialogBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Set color of both buttons
        if(getActivity() != null) {
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.logout_event_positive_button_color));
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        }
    }
}
