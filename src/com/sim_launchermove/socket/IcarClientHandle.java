package com.sim_launchermove.socket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


import android.content.Context;
import android.util.Log;

public class IcarClientHandle extends IoHandlerAdapter {
	private CallBack call;
	private Context context;
	public IcarClientHandle(Context context) {
		this.context=context;
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		Log.i("TAG", "客户端连接成功===");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
		Log.i("TAG", "客户端关闭===");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
		Log.i("TAG", "客户端发生异常===");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		try {
			if(null!=call){
				call.Back(session, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		Log.i("TAG", "Begin to send Message========="+message);
		super.messageSent(session, message);
	}
	
	public interface CallBack
	{	
		void Back(IoSession session, Object message);
	}
	public void setCallBack(CallBack call){
		this.call=call;
	}

}
