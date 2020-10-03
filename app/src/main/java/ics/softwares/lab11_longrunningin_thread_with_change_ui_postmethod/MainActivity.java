package ics.softwares.lab11_longrunningin_thread_with_change_ui_postmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public boolean nonStop=true;
    TextView mytextView;
    Button btnStartThread;
    Button btnStopThread;
    ImageView imageView;
    Bitmap bitmap;
    String TAG="UIThread";
    Handler handler;
    ArrayList<Integer> imageFiles=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageFiles.add(new Integer(R.drawable.image1));
        imageFiles.add(new Integer(R.drawable.image2));
        imageFiles.add(new Integer(R.drawable.image3));
        imageFiles.add(new Integer(R.drawable.image4));
        mytextView=(TextView)findViewById(R.id.textview);

        this.handler=new Handler();

        btnStartThread=(Button)findViewById(R.id.startThread);
        btnStartThread.setOnClickListener(new View.OnClickListener() {
            int i=0;

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (nonStop) {
                            bitmap = BitmapFactory.decodeResource(getResources(), imageFiles.get(i));
                            try {
                                Thread.sleep(1000);
                                imageView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(bitmap);
                                        i = (i + 1) % 4;
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        nonStop=true;
                    }
                }).start();



            }
        });
        btnStopThread=(Button)findViewById(R.id.stopThread);
        btnStopThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nonStop=false;
                Toast toast = Toast.makeText(MainActivity.this, "I am a message", Toast.LENGTH_LONG);
                toast.show();
                //nonStop=false;
            }
        });
    }
}
