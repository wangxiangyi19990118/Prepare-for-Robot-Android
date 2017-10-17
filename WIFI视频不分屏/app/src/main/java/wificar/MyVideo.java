package wificar;
/*СR�Ƽ���www.xiao-r.com������
 * �й�FPV-WIFI���������������˴��⹤���ң�www.wifi-robots.com���Դ�Դ��ӵ������Ȩ
 * ��Դ������û�ѧϰ֮�ã��Ͻ�������ҵĲ��
 * �緢����Ȩ��Ϊ��СR�Ƽ������ͨ������;���������ߣ�
 * */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import my.wificar.R;

public class MyVideo extends Activity {
    private Button TakePhotos;
    private Button ViewPhotos;
    private Button BtnForward, BtnBackward, BtnLeft, BtnRight, BtnStop;
    URL videoUrl;
    public static String CameraIp;
    public static String CtrlIp;
    public static String CtrlPort;
    MySurfaceView r;
    private Socket socket;
    OutputStream socketWriter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//��ȥ���⣨Ӧ�õ����ֱ���Ҫд��setContentView֮ǰ����������쳣��
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.myvideo);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        r = (MySurfaceView) findViewById(R.id.mySurfaceViewVideo);
        TakePhotos = (Button) findViewById(R.id.TakePhoto);
        ViewPhotos = (Button) findViewById(R.id.ViewPhoto);

        BtnForward = (Button) findViewById(R.id.button_forward);
        BtnBackward = (Button) findViewById(R.id.button_backward);
        BtnLeft = (Button) findViewById(R.id.button_left);
        BtnRight = (Button) findViewById(R.id.button_right);
        BtnStop = (Button) findViewById(R.id.button_stop);

        Intent intent = getIntent();
        //��Intent���и���keyȡ��value
        CameraIp = intent.getStringExtra("CameraIp");
        CtrlIp = intent.getStringExtra("ControlUrl");
        CtrlPort = intent.getStringExtra("Port");
        Log.d("wifirobot", "control is :++++" + CtrlIp);
        Log.d("wifirobot", "CtrlPort is :++++" + CtrlPort);
        r.GetCameraIP(CameraIp);
        InitSocket();
        BtnForward.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        BtnBackward.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        BtnRight.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        });
        BtnLeft.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        BtnStop.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        });
        TakePhotos.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (null != Constant.handler) {
                    Message message = new Message();
                    message.what = 1;
                    Constant.handler.sendMessage(message);
                }
            }

        });

        ViewPhotos.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MyVideo.this, BgPictureShowActivity.class);
                //ͨ��Intent������������һ��Activity
                MyVideo.this.startActivity(intent);

            }

        });

    }

    public void InitSocket() {

        try {
            socket = new Socket(InetAddress.getByName(CtrlIp), Integer.parseInt(CtrlPort));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            socketWriter = socket.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Toast.makeText(this,"��ʼ������ʧ�ܣ�"+e.getMessage(),Toast.LENGTH_LONG).show();
    }

    public void onDestroy() {
        super.onDestroy();

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2500)  //System.currentTimeMillis()���ۺ�ʱ���ã��϶�����2500
            {
                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


