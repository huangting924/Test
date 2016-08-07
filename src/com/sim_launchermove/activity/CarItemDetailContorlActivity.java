package com.sim_launchermove.activity;

import java.util.ArrayList;
import java.util.List;

import com.sim_launchermove.R;
import com.sim_launchermove.adapter.ICarStateAdapter;
import com.sim_launchermove.model.CarStateModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/***
 * 控制小车移动界面
 * @author haugnting
 *
 */
public class CarItemDetailContorlActivity extends Activity implements OnClickListener{
	private ImageView iv_plays;
	private ImageView iv_left;
	private ImageView iv_right;
	private ImageView iv_up;
	private ImageView iv_down;
	private TextView car_title;
	private ListView car_exception;
	private boolean isPlay = true;
	private static final String STATR = "start";
	private static final String STOP = "stop";
	private static final String FORWARD = "forward";
	private static final String BACK = "back";
	private static final String LEFT = "left";
	private static final String RIGHT = "right";
	private ICarStateAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_detail);
		
		initView();
		initListener();
		List<CarStateModel> setStateData = setStateData();
		adapter = new ICarStateAdapter(this, setStateData);
		car_exception.setAdapter(adapter);
	}
	
	private void initView() {
		iv_plays = (ImageView) findViewById(R.id.iv_plays);
		iv_left = (ImageView) findViewById(R.id.iv_left);
		iv_right = (ImageView) findViewById(R.id.iv_right);
		iv_up = (ImageView) findViewById(R.id.iv_up);
		iv_down = (ImageView) findViewById(R.id.iv_down);
		car_title = (TextView) findViewById(R.id.car_title);
		car_exception = (ListView) findViewById(R.id.car_exception);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			String carName = bundle.getString("carName");
			Log.i("TAG", " carName==="+carName);
			car_title.setText(carName);
		}
	}
	

	private void initListener() {
		iv_plays.setOnClickListener(this);
		iv_left.setOnClickListener(this);
		iv_right.setOnClickListener(this);
		iv_down.setOnClickListener(this);
		iv_up.setOnClickListener(this);
		
	}
	
	private List<CarStateModel> setStateData(){
		List<CarStateModel> list = new ArrayList<CarStateModel>();
		CarStateModel model1 = new CarStateModel("异常");
		CarStateModel model2 = new CarStateModel("异常");
		CarStateModel model3 = new CarStateModel("异常");
		CarStateModel model4 = new CarStateModel("异常");
		CarStateModel model5 = new CarStateModel("异常");
		CarStateModel model6 = new CarStateModel("异常");
		CarStateModel model7 = new CarStateModel("异常");
		CarStateModel model8 = new CarStateModel("异常");
		CarStateModel model9 = new CarStateModel("异常");
		CarStateModel model10 = new CarStateModel("异常");
		
		list.add(model1);
		list.add(model2);
		list.add(model3);
		list.add(model4);
		list.add(model5);
		list.add(model6);
		list.add(model7);
		list.add(model8);
		list.add(model9);
		list.add(model10);
		return list;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_plays://开始，停止
			if(isPlay){
				sendControlOrder(STATR);
				iv_plays.setImageResource(R.drawable.stop);
				isPlay = false;
			}else {
				sendControlOrder(STOP);
				iv_plays.setImageResource(R.drawable.plays);
				isPlay = true;
			}
			break;
		case R.id.iv_left://左
			sendControlOrder(LEFT);
			break;
		case R.id.iv_right://右
			sendControlOrder(RIGHT);
			break;
		case R.id.iv_up://上
			sendControlOrder(FORWARD);
			break;
		case R.id.iv_down://下
			sendControlOrder(BACK);
			break;
		default:
			break;
		}
	}
	
	private void sendControlOrder(String order){
		Intent intent = new Intent("icar_control");
		intent.putExtra("contralOrder", order);
		sendBroadcast(intent);
	}
}
