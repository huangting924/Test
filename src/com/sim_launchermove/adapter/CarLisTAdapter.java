package com.sim_launchermove.adapter;

import java.util.List;

import com.sim_launchermove.R;
import com.sim_launchermove.model.CarModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
/***
 * 首界面小车列表
 * @author huangting
 *
 */
public class CarLisTAdapter extends BaseAdapter{
	private Context context;
	private List<CarModel> carList;
	public CarLisTAdapter(Context mcContext,List<CarModel> listDate){
		this.context = mcContext;
		this.carList = listDate;
	}
	
	@Override
	public int getCount() {
		return carList.size();
	}

	@Override
	public Object getItem(int position) {
		return carList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.launcher_item, null);
			viewHolder.carName = (TextView) convertView.findViewById(R.id.car_name);
			viewHolder.carState = (TextView) convertView.findViewById(R.id.car_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CarModel carModel = carList.get(position);
	    viewHolder.carName.setText(carModel.getCarName());
	    viewHolder.carState.setText(carModel.getCarState());
		return convertView;
	}
	
	class ViewHolder{
		private TextView carName;
		private TextView carState;
	}
}
