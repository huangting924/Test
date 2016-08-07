package com.sim_launchermove.adapter;

import java.util.List;

import com.sim_launchermove.R;
import com.sim_launchermove.model.CarStateModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ICarStateAdapter extends BaseAdapter {
	private Context context;
	private List<CarStateModel> carList;

	public ICarStateAdapter(Context mcContext, List<CarStateModel> listDate) {
		this.context = mcContext;
		this.carList = listDate;
	}

	@Override
	public int getCount() {
		return carList.size()>0?carList.size():0;
	}

	@Override
	public Object getItem(int position) {
		return carList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.state_item, null);
			viewHolder.stateName = (TextView) convertView.findViewById(R.id.state_name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CarStateModel stateModel = carList.get(position);
		viewHolder.stateName.setText(stateModel.getState());
		return convertView;
	}
	
	class ViewHolder{
		private TextView stateName;
	}

}
