package com.gamexwin.aether;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    private LinearLayout root, apps;
    private EditText search;
    private final int bg=Color.rgb(12,16,37), accent=Color.rgb(130,112,255);
    private GradientDrawable rounded(int color,int radius) {
        GradientDrawable d=new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d;
    }
    private TextView text(String value,int size,boolean bold) {
        TextView v=new TextView(this); v.setText(value);v.setTextColor(Color.WHITE);v.setTextSize(size);
        if(bold)v.setTypeface(null,Typeface.BOLD);
        v.setPadding(18,14,18,14);return v;
    }
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(bg);
        root.setPadding(24,24,24,16);setContentView(root);
        TextView title=text("GX   GameXWIN OS",28,true);title.setTextColor(0xffc6b9ff);root.addView(title);
        root.addView(text("Aether Edition  •  Android Desktop",14,false));
        LinearLayout quick=new LinearLayout(this);quick.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(quick);
        addShortcut(quick,"Wi-Fi",android.provider.Settings.ACTION_WIFI_SETTINGS);
        addShortcut(quick,"Bluetooth",android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        addShortcut(quick,"Settings",android.provider.Settings.ACTION_SETTINGS);
        search=new EditText(this);search.setSingleLine(true);search.setHint("Search apps...");
        search.setTextColor(Color.WHITE);search.setHintTextColor(0xffaaaac0);
        search.setBackground(rounded(0xff25294a,24));search.setPadding(20,14,20,14);
        root.addView(search);
        ScrollView scroll=new ScrollView(this);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        apps=new LinearLayout(this);apps.setOrientation(LinearLayout.VERTICAL);scroll.addView(apps);
        search.addTextChangedListener(new android.text.TextWatcher(){
            public void beforeTextChanged(CharSequence s,int a,int c,int f){}
            public void onTextChanged(CharSequence s,int start,int before,int count){populate(s.toString());}
            public void afterTextChanged(android.text.Editable e){}
        });
        populate("");
        TextView footer=text("GameXWIN  •  Installed Android apps",12,false);
        footer.setGravity(Gravity.CENTER);root.addView(footer);
    }
    private void addShortcut(LinearLayout bar,String label,String action){
        TextView b=text(label,13,true);b.setBackground(rounded(accent,18));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,-2,1);p.setMargins(3,8,3,14);
        bar.addView(b,p);
        b.setOnClickListener(v->{try{startActivity(new Intent(action));}catch(Exception e){Toast.makeText(this,"Unavailable",Toast.LENGTH_SHORT).show();}});
    }
    private void populate(String filter){
        apps.removeAllViews();
        Intent intent=new Intent(Intent.ACTION_MAIN);intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm=getPackageManager();
        List<ResolveInfo> list=pm.queryIntentActivities(intent,0);
        Collections.sort(list,new ResolveInfo.DisplayNameComparator(pm));
        int shown=0;
        for(ResolveInfo info:list){
            String label=info.loadLabel(pm).toString();
            if(!label.toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT)))continue;
            TextView row=text("◈   "+label,18,false);
            row.setBackground(rounded(0xff202641,16));
            LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,6,0,6);apps.addView(row,p);
            row.setOnClickListener(v->{
                Intent launch=pm.getLaunchIntentForPackage(info.activityInfo.packageName);
                if(launch!=null){try{startActivity(launch);}catch(Exception e){Toast.makeText(this,"App failed to launch",Toast.LENGTH_SHORT).show();}}
            });shown++;
        }
        if(shown==0)apps.addView(text("No matching apps",16,false));
    }
}
