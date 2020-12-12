package com.vad.generatetemplace;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView label;
    private TextView packageName;
    private TextView versionCode;
    private TextView xmlFile;
    private File path;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        label = (TextView) findViewById(R.id.label);
        packageName = (TextView) findViewById(R.id.packagename);
        versionCode = (TextView) findViewById(R.id.versioncode);
        xmlFile = (TextView) findViewById(R.id.xmlfile);

        path = getExternalFilesDir(Environment.getExternalStorageState());
        file = new File(path,"app-debug.apk");

        label.setText(file.toString());


    }

    @SuppressLint("SetTextI18n")
    private void returnCharacterAPK(String filePath){
        try (ApkFile apkFile = new ApkFile(new File(filePath))) {
            ApkMeta apkMeta = null;
            apkMeta = apkFile.getApkMeta();
            //System.out.println(apkMeta.getLabel());
            packageName.setText(apkMeta.getPackageName());
            versionCode.setText(apkMeta.getVersionCode().toString());

//            for (UseFeature feature : apkMeta.getUsesFeatures()) {
//                System.out.println(feature.getName());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnXML(String filePath){
        try (ApkFile apkFile = new ApkFile(new File(filePath))) {
            String manifestXml = apkFile.getManifestXml();
            String xml = apkFile.transBinaryXml("res/menu/main.xml");

            xmlFile.setText(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}