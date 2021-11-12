package com.Ahmadmsh.logger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    File str_SdPath;
    File logFile;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str_SdPath = Environment.getExternalStorageDirectory();
        logFile = new File(str_SdPath + File.separator +"logger"+File.separator + "log.txt");
        textView=findViewById(R.id.textViewLog);
        editText=findViewById(R.id.editText);





    }

    public void click(View view) {

        switch (view.getId()){
            case R.id.fileCreator:
                try {
                    logFile.mkdirs();
                    if (logFile.exists()){
                        logFile.delete();
                    }
                    logFile.createNewFile();
                    final String test = "Hello Android";
                    OutputStream out = new FileOutputStream(logFile);
                    OutputStreamWriter osw = new OutputStreamWriter(out);

                    osw.write(test);

                    osw.flush();
                    osw.close();

                    textView.setText(textView.getText()+"\n"+"file creation was successful !");



                } catch (IOException e) {

                    e.printStackTrace();

                    textView.setText(textView.getText()+"\n"+"file creation was failed !");

                }
                break;

            case R.id.sender:
                if (logFile.exists()) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    Uri logUri = Uri.parse(logFile.toString());
                    sharingIntent.setType("*/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, logUri);
                    sharingIntent.putExtra(Intent.EXTRA_TITLE, "logUri");
                    startActivity(Intent.createChooser(sharingIntent, "Share logfile using :"));
                    textView.setText(textView.getText()+"\n"+"file was shared!");
                }
                break;

            case R.id.adder:

                if (!logFile.exists()){
                    textView.setText(textView.getText()+"\n"+"file is't exist firstly create it ");

                }else {

                    try {
                        String et_text=editText.getText().toString();


                        OutputStream out = new FileOutputStream(logFile,true);
                        OutputStreamWriter osw = new OutputStreamWriter(out);

                        osw.append("\n"+et_text);
                        textView.setText(textView.getText()+"\n"+"Appended new string! ");
                        editText.setText("");

                        osw.flush();
                        osw.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                break;

        }

    }
}
