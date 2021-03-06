/*
 * Copyright (c) 2017. Darryl Hon
 * Modifications Copyright (c) 2017. Darryl Hon
 *
 * This file is part of VOSP and may be covered under one or more licenses.
 * Refer to the project LICENSE and NOTICE files for details.
 */

package com.meowsbox.vosp.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.RemoteException;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.meowsbox.vosp.DialerApplication;
import com.meowsbox.vosp.IRemoteSipService;
import com.meowsbox.vosp.R;
import com.meowsbox.vosp.common.Logger;


/**
 * Created by dhon on 6/22/2017.
 */

public class DialogPremiumRequiredAltIcons {
    public static final boolean DEBUG = DialerApplication.DEBUG;
    static Logger gLog = DEBUG?new Logger(DialerApplication.LOGGER_VERBOSITY):null;
    public final String TAG = this.getClass().getName();

    private AlertDialog dialog;

    public DialogPremiumRequiredAltIcons build(final Context context, final IRemoteSipService sipService) throws RemoteException {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = li.inflate(R.layout.dialog_pro_req_alticons, null);
        ((TextView) view.findViewById(R.id.tvCap)).setText(sipService.getLocalString("help_premium_feature_alt_icons", "Alternate launcher icons to match your home screen. Upgrade to unlock this and other features."));

        final ContextThemeWrapper contextWrapper = new ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog.Builder builder = new AlertDialog.Builder(contextWrapper);
        builder.setView(view);
        builder.setTitle(sipService.getLocalString("premium_features", "Premium Features"))

                .setPositiveButton(sipService.getLocalString("upgrade", "Upgrade"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (sipService != null)
                            try {
                                new DialogPremiumUpgrade().buildAndShow(context, sipService);
                            } catch (RemoteException e) {
                                if (DEBUG) e.printStackTrace();
                            }
                        else if (gLog != null) gLog.l(TAG,Logger.lvDebug,"sipService NULL");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(sipService.getLocalString("close", "Close"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        return this;
    }

    public DialogPremiumRequiredAltIcons buildAndShow(final Context context, final IRemoteSipService sipService) throws RemoteException {
        build(context, sipService);
        dialog.show();
        return this;
    }

    public void dismiss() {
        if (dialog != null) dialog.dismiss();
    }

    public void show() {
        if (dialog != null) dialog.show();
    }

}
