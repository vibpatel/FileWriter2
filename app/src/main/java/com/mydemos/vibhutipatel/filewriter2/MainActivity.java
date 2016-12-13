package com.mydemos.vibhutipatel.filewriter2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    //String filename = "TestAndroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTerminate=(Button) this.findViewById(R.id.buttonTerminate);
        Button btnDelete=(Button) this.findViewById(R.id.buttondel);
        Button btnSave = (Button) this.findViewById(R.id.save);
        Button btnLoad = (Button) this.findViewById(R.id.load);
        Button btnRename=(Button) this.findViewById(R.id.bttnrename);
        Button btnCreate=(Button) this.findViewById(R.id.buttonCreate);
        final EditText edtInput = (EditText) this.findViewById(R.id.edtInputText);
        final TextView txtOutput = (TextView) this.findViewById(R.id.textView);

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //final List<ActivityManager.RunningServiceInfo> runningProcess = am.getRunningServices(30);
            final List<ActivityManager.RunningAppProcessInfo> runningProcess=am.getRunningAppProcesses();
        final String filename = "TestAndroid.txt";
        final String filedir="MYAPP";
        final File filed = new File(Environment.getExternalStorageDirectory(), filedir);
        final File file = new File(filed, filename);
        final File newFile = new File(filed,"File Renamed.txt");
        final File createFile = new File(filed,"File Created.txt");
        filed.mkdirs();


        btnTerminate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               Process.killProcess(Process.myPid());
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    createFile.createNewFile();
                    Toast.makeText(getApplicationContext(), "File Created", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnRename.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                file.renameTo(newFile);
                Toast.makeText(getApplicationContext(), "File renamed to:"+newFile, Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                file.delete();
                newFile.delete();
                createFile.delete();
                Toast.makeText(getApplicationContext(), "Files Deleted", Toast.LENGTH_LONG).show();
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //String[] array = new String[runningProcess.size()];

                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(edtInput.getText().toString().getBytes());
                    fo.write("\n".getBytes());
                    Date date=new Date(file.lastModified());
                    String p="Pid is: ";
                    Toast.makeText(getApplicationContext(), "Last Modified date "+date, Toast.LENGTH_LONG).show();
                    if (runningProcess != null && runningProcess.size() > 0) {
                        for(int i=0;i<runningProcess.size();i++) {
                            fo.write(runningProcess.get(i).processName.toString().getBytes());
                            fo.write("\n".getBytes());
                            fo.write(p.getBytes());
                            fo.write(Integer.toString(runningProcess.get(i).pid).getBytes());
                            fo.write("\n".getBytes());
                        }
                        fo.close();
                    } else {
                        Toast.makeText(getApplicationContext(), "No application is running", Toast.LENGTH_LONG).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    int length = (int) file.length();
                    byte[] bytes = new byte[length];
                    FileInputStream fi = new FileInputStream(file);
                    fi.read(bytes);
                    String text = new String(bytes);
                    txtOutput.setText(text);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


