package com.vad.generatetemplace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;


import com.jaredrummler.apkparser.ApkParser;
import com.jaredrummler.apkparser.model.ApkMeta;
import com.jaredrummler.apkparser.model.UseFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView label;
    private TextView packageName;
    private TextView versionCode;
    private TextView xmlFile;
    private File path;
    private File file;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        label = (TextView) findViewById(R.id.label);
        packageName = (TextView) findViewById(R.id.packagename);
        versionCode = (TextView) findViewById(R.id.versioncode);
        xmlFile = (TextView) findViewById(R.id.xmlfile);

        returnCharacterAPK("com.vad.templaceweb");
        returnXML("com.vad.templaceweb");


    }

    @SuppressLint("SetTextI18n")
    private void returnCharacterAPK(String packagename){
        PackageManager pm = getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packagename, 0);
            ApkParser apkParser = ApkParser.create(appInfo);
            ApkMeta meta = apkParser.getApkMeta();
            String packageName = meta.packageName;
            long versionCode = meta.versionCode;

            label.setText(packageName+" "+versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void returnXML(String packagename){
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(packagename, 0);
            ApkParser apkParser = ApkParser.create(appInfo);
            String readableAndroidManifest = apkParser.getManifestXml();
            String xml = apkParser.transBinaryXml("res/layout/activity_main.xml");
            packageName.setText(xml);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}