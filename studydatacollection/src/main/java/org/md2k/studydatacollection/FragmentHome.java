package org.md2k.studydatacollection;

import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.md2k.mcerebrum.core.access.appinfo.AppInfo;
import org.md2k.mcerebrum.core.data_format.DATA_QUALITY;
import org.md2k.mcerebrum.system.update.Update;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.content.Context.NOTIFICATION_SERVICE;

public class FragmentHome extends Fragment {

    FancyButton data_collection;
//    FancyButton pause_resume_data_collection;
    AwesomeTextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        tv = (AwesomeTextView) view.findViewById(R.id.textview_status);
    }
    Drawable getDataQualityImage(int value){
        switch(value){
            case -1: return new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_refresh).sizeDp(24).color(Color.GRAY);
            case DATA_QUALITY.GOOD: return new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_check_circle).sizeDp(24).color(Color.GREEN);
            case DATA_QUALITY.BAND_OFF: return new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_times_circle).sizeDp(24).color(Color.RED);
            default: return new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_exclamation_triangle).sizeDp(24).color(Color.YELLOW);
        }
    }
    @Override
    public void onResume(){
        boolean start = AppInfo.isServiceRunning(getActivity(), ServiceStudy.class.getName());

        if(!start) {
            updateStatus("Data collection off", DefaultBootstrapBrand.DANGER, false);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(ServiceStudy.NOTIFY_ID, ServiceStudy.getCompatNotification(getActivity(),"Data Collection - OFF (click to start)"));

        }else{
            updateStatus(null, DefaultBootstrapBrand.SUCCESS, true);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(ServiceStudy.NOTIFY_ID, ServiceStudy.getCompatNotification(getActivity(),"Data Collection - ON"));
        }

        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    void updateStatus(String msg, BootstrapBrand brand, boolean isSuccess){
        tv.setBootstrapBrand(brand);
        if(isSuccess) {
            int uNo=Update.hasUpdate(getActivity());
            if(uNo==0)
            tv.setBootstrapText(new BootstrapText.Builder(getActivity()).addText("Status: ").addFontAwesomeIcon("fa_check_circle").build());
            else {
                tv.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                tv.setBootstrapText(new BootstrapText.Builder(getActivity()).addText("Status: ").addFontAwesomeIcon("fa_check_circle").addText(" (Update Available)").build());
            }
        }
        else
            tv.setBootstrapText(new BootstrapText.Builder(getActivity()).addText("Status: ").addFontAwesomeIcon("fa_times_circle").addText(" ("+msg+")").build());
    }

}
