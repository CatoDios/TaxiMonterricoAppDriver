package tmasociados.com.taximonterrico.utils;

import android.app.ProgressDialog;
import android.content.Context;


import tmasociados.com.taximonterrico.R;


/**
 * Created by junior on 26/11/16.
 */

public class ProgressDialogCustom extends ProgressDialog {


    public ProgressDialogCustom(Context context, String text) {
        super(context);
        setIndeterminate(true);
        setMessage(text);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setCancelable(false);
        setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.circle_progress));
    }


}
