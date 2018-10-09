package com.eltendawy.anamuslim.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.eltendawy.anamuslim.Model.Hadith;
import com.eltendawy.anamuslim.R;

/**
 * Created by Islam_Assem on 24-09-18.
 */

public class HadithDialogFragment extends DialogFragment {

    View view;
    TextView title,content;
    Hadith hadith;

    public HadithDialogFragment() {
    }

    public Hadith getHadith() {
        return hadith;
    }

    public HadithDialogFragment setHadith(Hadith hadith) {
        this.hadith = hadith;
        return this;
    }
    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        try {

            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception ignored){}
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dialog_fragment_hadith,container,false);
        title=view.findViewById(R.id.hadith_title);
        content=view.findViewById(R.id.hadith_content);
        title.setText(hadith==null?"":hadith.getTitle());
        content.setText(hadith==null?"":hadith.getContent());
        return view;
    }
}

