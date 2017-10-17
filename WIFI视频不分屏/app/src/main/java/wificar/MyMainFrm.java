package wificar;
/*СR�Ƽ���www.xiao-r.com������
 * �й�FPV-WIFI���������������˴��⹤���ң�www.wifi-robots.com���Դ�Դ��ӵ������Ȩ
 * ��Դ������û�ѧϰ֮�ã��Ͻ�������ҵĲ��
 * �緢����Ȩ��Ϊ��СR�Ƽ������ͨ������;���������ߣ�
 * */
import java.net.URL;
import my.wificar.R;
import wificar.MySurfaceView;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;

public class MyMainFrm extends Activity {

	EditText CameraIP,ControlIP,Port;
	Button Button_go;
	String videoUrl,controlUrl,port;
	public static String CameraIp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * ������ǵ�һ�����棬�ڽ����п���������������ͷ�Ļ�ȡ��Ƶ��������ַ
		 * edIP����Ƶ��ַ�ı���
		 * Button_go ������ť
		 * 
		 * */

		setContentView(R.layout.mymainfrm);	
		
		CameraIP = (EditText) findViewById(R.id.editIP);
		ControlIP = (EditText) findViewById(R.id.ip);
		Port = (EditText) findViewById(R.id.port);
		
		Button_go = (Button) findViewById(R.id.button_go);
		
		videoUrl = CameraIP.getText().toString();
		controlUrl = ControlIP.getText().toString();
		port = Port.getText().toString();
		
		Button_go.requestFocusFromTouch();

        
		Button_go.setOnClickListener(new Button.OnClickListener() 
		{
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			//����һ��Intent����
    			Intent intent = new Intent();
    			//��Intent���������һ����ֵ��
    			intent.putExtra("CameraIp", videoUrl);
    			intent.putExtra("ControlUrl", controlUrl);
    			intent.putExtra("Port", port);
    			
    			intent.putExtra("Is_Scale", true);
    			//����Intent����Ҫ������Activity
    			intent.setClass(MyMainFrm.this, MyVideo.class);
    			//ͨ��Intent������������һ��Activity
    			MyMainFrm.this.startActivity(intent);
    			finish();  
	            System.exit(0);  
    		}
    	});
    
	}
	/*
	 * ����Ϊ��һ�·��ؼ�����ʾ���ٰ�һ���˳�����
	 * */
	private long exitTime = 0;
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   
    {  
                 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)  
                 {  
                           
                         if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()���ۺ�ʱ���ã��϶�����2000   
                         {  
                          Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",Toast.LENGTH_SHORT).show();                                  
                          exitTime = System.currentTimeMillis();  
                         }  
                         else  
                         {  
                             finish();  
                             System.exit(0);  
                         }  
                                   
                         return true;  
                 }  
                 return super.onKeyDown(keyCode, event);  
    }  

        

   

}


