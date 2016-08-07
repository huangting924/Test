package com.sim_launchermove.socket;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.sim_launchermove.model.CarModel;
import com.sim_launchermove.socket.IcarClientHandle.CallBack;
import com.sim_launchermove.util.PaseJsonUtil;
import com.sim_launchermove.util.ServerUtil;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class SocketService extends Service implements CallBack {
	/** ��ʼ���� */
	public static final int STATE_START_CONNECT = 990;
	/** ��ʼ����socket */
	public static final int STATE_CREATE_SOCKET_START = 1010;
	/** socket�����ɹ� */
	public static final int STATE_CREATE_SOCKET_SUCCESS = 1011;
	private BroadcastReceiver mBroadcastReceiver;

	private final String ADDRESS = "192.168.172.24";
	private final int PORT = 8899;
	private IcarClientHandle handel;
	private LoginDataCallBack callBack;
	private IoSession session;
	@SuppressLint("HandlerLeak")
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATE_START_CONNECT:// ��ʼ����
				SocketService.this.sendMessage(STATE_CREATE_SOCKET_START);
				break;
			case STATE_CREATE_SOCKET_START:// ��ʼ����socket
				RunnableExecutor.doAsync(createSocketRunnable);
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("TAG", " Service onCreate ");
		handel=new IcarClientHandle(getApplicationContext());
		handel.setCallBack(this);
		registerBoradcastReceiver();
		doStart();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void doStart() {
		sendMessage(STATE_START_CONNECT);
	}

	private Runnable createSocketRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				ObjectSerializationCodecFactory objectSerializationCodecFactory = new ObjectSerializationCodecFactory();
				objectSerializationCodecFactory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
				objectSerializationCodecFactory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
				// �ͻ��˵�ʵ��
				NioSocketConnector connector = new NioSocketConnector();
				// �������ӳ�ʱ
				connector.setConnectTimeoutMillis(30000);
				connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(objectSerializationCodecFactory));
//				connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
				connector.getFilterChain().addLast("logging", new LoggingFilter());
				connector.setHandler(handel);

				connector.getSessionConfig().setUseReadOperation(true);

				ConnectFuture cf = connector.connect(new InetSocketAddress(ADDRESS, PORT));
				cf.awaitUninterruptibly();
				IoSession session = cf.getSession();
				
				
				session.write("[U001,"+ServerUtil.getSerialNumber()+",AppHardWare]");
				
				session.getCloseFuture().awaitUninterruptibly();// �ȴ����ӶϿ�
				Log.d("TEST", "�ͻ��˶Ͽ�...");
				connector.dispose();

			} catch (Exception e) {// ����ʧ��
				Log.d("TEST", "�ͻ��������쳣...");
				e.printStackTrace();
			}

		}
	};

	protected final void sendMessage(int what) {
		mHandler.sendMessage(mHandler.obtainMessage(what));
	}

	protected final void sendMessage(int what, Object o) {
		mHandler.sendMessage(mHandler.obtainMessage(what, o));
	}

	@Override
	public void Back(IoSession session, Object message) {
		this.session = session;
		try {
			String data = message.toString();
			if(null == data || "".equals(data) || "null".equals(data)){
				return;
			}
			if(data.contains("S001")){//�����¼���ص���Ϣ
				List<CarModel> carListData = PaseJsonUtil.carListData(data);
				if(carListData.size() > 0){
					if(callBack != null){
						callBack.loginDataBack(carListData);//�ص�json���ݵ��׽���
					}
				}
			}else if(data.contains("S002")){//�������С��״̬����
				
			}else if(data.contains("S003")){//����С��״̬����
			
			}else if(data.equals("S004")){//�ͷſ���С������
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("icar_control");
		mBroadcastReceiver = new ICarContrPathBrodReceiver();
		// ע��㲥
		registerReceiver(mBroadcastReceiver, myIntentFilter);

	}

	public class ICarContrPathBrodReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("icar_control")) {
				String contralOrder = intent.getStringExtra("contralOrder");
				if(null != contralOrder && !"".equals(contralOrder)){
					Log.i("TAG", "contralOrder==="+contralOrder);
					Log.i("TAG", "contralOrder==="+"[U003,"+ServerUtil.getSerialNumber()+","+contralOrder+"]");
					session.write("[U003,"+ServerUtil.getSerialNumber()+","+contralOrder+"]");
				}
			}
		}

	}
	
	public interface LoginDataCallBack{
		void loginDataBack(List<CarModel> list);
	}

	public void setCallBack(LoginDataCallBack callBack) {
		this.callBack = callBack;
	}
}
