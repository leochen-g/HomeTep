package jdl.android.test;
import jdl.android.test.Login;
import jdl.android.test.R;
import jdl.android.test.Welcome;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
public class Welcome extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Login.class);
		startActivity(intent);
		//this.finish();
      } 
    public void welcome_back(View v) {  
      	//Intent intent = new Intent();
		//intent.setClass(Welcome.this,Login.class);
		//startActivity(intent);
		this.finish();
      } 
    public void welcome_explain(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Explain.class);
		startActivity(intent);
      } 

}
