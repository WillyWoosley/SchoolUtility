package com.gamecodeschool.schoolutility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by wdwoo on 3/30/2017.
 */

public class DialogAddArticle extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.article_assign, null);

        Button buttonOk = (Button) dialogView.findViewById(R.id.articleAssignOk);
        Button buttonCancel = (Button) dialogView.findViewById(R.id.articleAssignCancel);

        builder.setView(dialogView).setMessage("Add Article");

        buttonOk.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dismiss();
                    }
                }
        );

        buttonOk.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
