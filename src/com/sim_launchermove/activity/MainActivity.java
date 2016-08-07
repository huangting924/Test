package com.sim_launchermove.activity;

import java.util.ArrayList;
import java.util.List;

import com.sim_launchermove.R;
import com.sim_launchermove.adapter.CarLisTAdapter;
import com.sim_launchermove.model.CarModel;
import com.sim_launchermove.socket.SocketService;
import com.sim_launchermove.socket.SocketService.LoginDataCallBack;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements LoginDataCallBack{
	private ListView list_launcher;
	private List<CarModel> carList;
	private CarLisTAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
		startServiceMet();
	}

	private void startServiceMet() {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, SocketService.class);
		startService(intent);
	}

    /***
     * 初始化控件
     */
	private void initView() {
		list_launcher = (ListView) findViewById(R.id.list_launcher);
		list_launcher.setOnItemClickListener(lunOnItemLensterer);
		adapter = new CarLisTAdapter(this, setData());
	    list_launcher.setAdapter(adapter);
	}
	
	private List<CarModel> setData(){
		carList = new ArrayList<CarModel>();
		CarModel carModel1 = new CarModel("小车001", "正在运行");
		CarModel carModel2 = new CarModel("小车001", "正在运行");
		CarModel carModel3= new CarModel("小车001", "正在运行");
		CarModel carModel4 = new CarModel("小车001", "正在运行");
		CarModel carModel5 = new CarModel("小车001", "正在运行");
		CarModel carModel6 = new CarModel("小车001", "正在运行");
		CarModel carModel7 = new CarModel("小车001", "正在运行");
		CarModel carModel8 = new CarModel("小车001", "正在运行");
		CarModel carModel9 = new CarModel("小车001", "正在运行");
		CarModel carModel10 = new CarModel("小车001", "正在运行");
		CarModel carModel11 = new CarModel("小车001", "正在运行");
		
		carList.add(carModel1);
		carList.add(carModel2);
		carList.add(carModel3);
		carList.add(carModel4);
		carList.add(carModel5);
		carList.add(carModel6);
		carList.add(carModel7);
		carList.add(carModel8);
		carList.add(carModel9);
		carList.add(carModel10);
		carList.add(carModel11);
		return carList;
	}
	/***
	 * 小车列表点击监听
	 */
	private OnItemClickListener lunOnItemLensterer = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			CarModel carModel = carList.get(position);
			Intent intent = new Intent(MainActivity.this, CarItemDetailContorlActivity.class);
			intent.putExtra("carName", carModel.getCarName());
			startActivity(intent);
		}
	};
	@Override
	public void loginDataBack(List<CarModel> list) {
		
	}
    
}
