package com.example.myzhuanpan;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private TextView mTvApple, mTvXiGua, mTvHuangGua, mTvLiZi, mTvChengZi,
			mTvLongYan, mTvShiZi, mTvJuZi, mTvTaoZi, mTvPiPa, mTvYouZi,
			mTvPuTao, mTvHuoLongGuo_1, mTvHuoLongGuo_2, mTvXiGua2, mTvXiGua3,
			mTvChengZi_2, mTvChengZi_3;

	private TextView mBtnApple, mBtnXiGua, mBtnHuangGua, mBtnLiZi, mBtnChengZi,
			mBtnLongYan;

	private TextView mBtnBegin, mBtnAdd, mBtnReduce;

	private TextView mTvKaiJiang, mTvToday;

	private TextView mTvNowPrint, mTvNowXiaZhu, mTvBeiShu, mTvDeFen;

	private TextView mBtnShangFen;

	private TextView mBtnDialogSure, mBtnDialogClean;

	private EditText mEdtDialogNum, mEdtDialogPsd;

	private ImageView mIvLogo;

	private String mStrNowPrint;

	private SharedPreferences sp;

	private MediaPlayer player;

	private MediaPlayer player2;

	private Dialog dialog;

	private int mFenShu = 0;
	private int num = 0;
	private int allPrint;

	private int musicRecuse[] = { R.raw.song2, R.raw.song, R.raw.song1,
			R.raw.song3, R.raw.song4 };

	private LinearLayout mLinear;

	private int isOnpause = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		mTvApple = $(R.id.tv_apple);
		mTvXiGua = $(R.id.tv_xigua);
		mTvHuangGua = $(R.id.tv_huanggua);
		mTvLiZi = $(R.id.tv_lizi);
		mTvChengZi = $(R.id.tv_chengzi);
		mTvLongYan = $(R.id.tv_longyan);
		mTvShiZi = $(R.id.tv_shizi);
		mTvJuZi = $(R.id.tv_juzi);
		mTvTaoZi = $(R.id.tv_TaoZi);
		mTvPiPa = $(R.id.tv_pipa);
		mTvYouZi = $(R.id.tv_youzi);
		mTvPuTao = $(R.id.tv_putao);
		mTvKaiJiang = $(R.id.tv_kaijiang);
		mTvToday = $(R.id.tv_today);
		mTvNowPrint = $(R.id.tv_now_print);
		mTvNowXiaZhu = $(R.id.tv_now_xiazhu);
		mTvBeiShu = $(R.id.tv_beishu);
		mTvDeFen = $(R.id.tv_defen);

		mLinear = $(R.id.linear);

		mTvHuoLongGuo_1 = $(R.id.tv_huolongguo1);
		mTvHuoLongGuo_2 = $(R.id.tv_huolongguo2);
		mTvXiGua2 = $(R.id.tv_xigua2);
		mTvXiGua3 = $(R.id.tv_xigua3);
		mTvChengZi_2 = $(R.id.tv_chengzi2);
		mTvChengZi_3 = $(R.id.tv_chengzi3);

		mBtnApple = $(R.id.btn_apple);
		mBtnXiGua = $(R.id.btn_xigua);
		mBtnHuangGua = $(R.id.btn_huanggua);
		mBtnLiZi = $(R.id.btn_lizi);
		mBtnChengZi = $(R.id.btn_chengzi);
		mBtnLongYan = $(R.id.btn_longyan);

		mBtnBegin = $(R.id.btn_begin);
		mBtnAdd = $(R.id.btn_add);
		mBtnReduce = $(R.id.btn_reduce);
		mBtnShangFen = $(R.id.btn_shangfen);

		mIvLogo = $(R.id.iv_logo);

		mBtnApple.setOnClickListener(this);
		mBtnXiGua.setOnClickListener(this);
		mBtnHuangGua.setOnClickListener(this);
		mBtnLiZi.setOnClickListener(this);
		mBtnChengZi.setOnClickListener(this);
		mBtnLongYan.setOnClickListener(this);

		mLinear.setOnClickListener(this);

		mBtnBegin.setOnClickListener(this);
		mBtnShangFen.setOnClickListener(this);

		mTvKaiJiang.setOnClickListener(this);
		mTvToday.setOnClickListener(this);
		mBtnAdd.setOnClickListener(this);
		mBtnReduce.setOnClickListener(this);

		sp = getSharedPreferences("MyJifen", MODE_PRIVATE);

		diaLogInit();

		allPrint = strToInt(mTvNowPrint.getText().toString());

		player = MediaPlayer.create(this, musicRecuse[getMusicId()]);

		player.start();

		player.setLooping(true);

		String a = sp.getString("allprint", "");

		try {
			if (!a.equals("")) {
				mTvNowPrint.setText(a + "");
			}
		} catch (Exception e) {

		}
	}

	/** 場宎趙dialog */
	private void diaLogInit() {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_shangfen);
		dialog.setTitle("奻煦");
		mBtnDialogSure = (TextView) dialog.findViewById(R.id.btn_dialog_sure);
		mBtnDialogClean = (TextView) dialog.findViewById(R.id.btn_dialog_clean);

		mEdtDialogNum = (EditText) dialog.findViewById(R.id.edt_dialog_num);
		mEdtDialogPsd = (EditText) dialog.findViewById(R.id.edt_dialog_psd);

		mBtnDialogSure.setOnClickListener(this);
		mBtnDialogClean.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {

		case R.id.btn_apple:

			mTvToday.setText(mTvApple.getText());
			mTvBeiShu.setText("x2");
			break;

		case R.id.btn_xigua:
			mTvToday.setText(mTvXiGua.getText());
			mTvBeiShu.setText("x3");
			break;

		case R.id.btn_huanggua:

			mTvToday.setText(mTvHuangGua.getText());
			mTvBeiShu.setText("x1");
			break;

		case R.id.btn_lizi:

			mTvToday.setText(mTvLiZi.getText());
			mTvBeiShu.setText("x1");
			break;

		case R.id.btn_chengzi:
			mTvToday.setText(mTvChengZi.getText());
			mTvBeiShu.setText("x1");
			break;

		case R.id.btn_longyan:
			mTvToday.setText(mTvLongYan.getText());
			mTvBeiShu.setText("x4");
			break;

		case R.id.btn_add:

			mStrNowPrint = mTvNowPrint.getText().toString();
			num = strToInt(mStrNowPrint);
			num--;

			mFenShu++;
			if (mFenShu >= 0 && mFenShu < allPrint) {
				mTvNowXiaZhu.setText(mFenShu + "");
				mTvNowPrint.setText((num) + "");
			}

			break;

		case R.id.btn_reduce:

			mStrNowPrint = mTvNowPrint.getText().toString();
			allPrint = strToInt(mStrNowPrint);
			num = strToInt(mStrNowPrint);
			num++;
			mFenShu--;
			if (mFenShu >= 0 && mFenShu < allPrint) {
				// 煦杅祥夔苤衾0ㄛ珩祥夔湮衾軞杅
				mTvNowXiaZhu.setText(mFenShu + "");
				mTvNowPrint.setText((num) + "");
			}

			break;

		case R.id.btn_begin:
			num = 0;
			mFenShu = 0;

			if (!mTvToday.getText().toString().equals("恁寁")
					&& !mTvNowXiaZhu.getText().toString().equals("0")) {
				mBtnBegin.setClickable(false);
				mTvDeFen.setText("0");
				player.pause();

				player2 = MediaPlayer.create(this, musicRecuse[getMusicId()]);
				player2.setLooping(true);

				new MyThread().start();

				player2.start();
			} else {
				Toast.makeText(this, "恁寁眈茼阨彆睿蛁杅", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.btn_shangfen:
			dialog.show();

			break;

		case R.id.btn_dialog_sure:
			// 隅偌聽ㄛ涴爵祥迡breakㄛ岆峈賸源晞壽敕dialog﹝
			String num = mEdtDialogNum.getText().toString();
			String psd = mEdtDialogPsd.getText().toString();
			if (numDengyuNum(num, psd)) {
				String strAllPrint = mTvNowPrint.getText().toString();
				int intNum = strToInt(num);
				int iniAllPrint = strToInt(strAllPrint);
				mTvNowPrint.setText((intNum + iniAllPrint) + "");
				mEdtDialogNum.setText("");
				mEdtDialogPsd.setText("");
			}
			dialog.dismiss();
			break;
		case R.id.btn_dialog_clean:
			
			String url=null;
			url="mqqwpa://im/chat?chat_type=wpa&uin=542886872&version=1";
							//uin蜊峈醴梓QQ瘍ㄐ
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

			dialog.dismiss();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		if (isOnpause == 1) {
			player.start();

		}
		super.onResume();
	}

	/** 蚚衾桄痐奻煦癹 */
	private boolean numDengyuNum(String num, String psd) {
		if (!num.equals("") && !psd.equals("")) {
			if (psd.equals("javaapk")) {
				return true;
			}
		}

		return false;

	}

	@SuppressWarnings("unchecked")
	public final <E extends View> E $(int id) {
		try {
			return (E) findViewById(id);
		} catch (ClassCastException ex) {

			throw ex;
		}
	}

	/** 場宎趙textview掖劓晇伎 */
	private void initTv() {
		mTvApple.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvXiGua.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvHuangGua.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvLiZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvChengZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvLongYan.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvShiZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvJuZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvTaoZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvPiPa.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvYouZi.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvPuTao.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvXiGua2.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvXiGua3.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvChengZi_2.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvChengZi_3.setBackgroundColor(getResources().getColor(R.color.tv_bg));
		mTvHuoLongGuo_1.setBackgroundColor(getResources().getColor(
				R.color.tv_bg));
		mTvHuoLongGuo_2.setBackgroundColor(getResources().getColor(
				R.color.tv_bg));
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			int maxQuanShu = getRanman();
			int zhongjiangNum = getRana();
			while (maxQuanShu >= 0) {
				Message message = new Message();
				message.what = 1;
				message.obj = maxQuanShu;
				if (maxQuanShu == zhongjiangNum) {
					maxQuanShu = 0;
				}
				SystemClock.sleep(50);
				handler.sendMessage(message);
				maxQuanShu--;
			}
			Message message = new Message();
			message.what = 2;
			message.obj = "笢蔣";
			handler.sendMessage(message);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				initTv();
				int logo = (Integer) msg.obj;
				int num = logo % 18;

				if (logo < 18) {
					num = logo;
				}
				switch (num) {

				case 17:
					mTvChengZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvChengZi.getText());

					mIvLogo.setImageResource(R.drawable.chengzi);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					break;
				case 16:
					mTvXiGua.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvXiGua.getText());

					mIvLogo.setImageResource(R.drawable.xigua);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear));
					break;
				case 15:
					mTvHuangGua.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvHuangGua.getText());

					mIvLogo.setImageResource(R.drawable.huanggua);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear2));
					break;
				case 14:
					mTvLiZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.lizi);

					mTvKaiJiang.setText(mTvLiZi.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_yellow));
					break;
				case 13:
					mTvLongYan.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.longyan);

					mTvKaiJiang.setText(mTvLongYan.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_set));
					break;
				case 12:
					mTvJuZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvJuZi.getText());

					mIvLogo.setImageResource(R.drawable.huanggua);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear2));

					break;

				case 11:
					mTvHuoLongGuo_1.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvHuoLongGuo_1.getText());

					mIvLogo.setImageResource(R.drawable.huolongguo);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_yellow));
					break;

				case 10:
					mTvXiGua2.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvXiGua2.getText());

					mIvLogo.setImageResource(R.drawable.xigua);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear2));
					break;

				case 9:
					mTvChengZi_3.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvChengZi_3.getText());

					mIvLogo.setImageResource(R.drawable.chengzi);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear));
					break;

				case 8:
					mTvPuTao.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.xigua);

					mTvKaiJiang.setText(mTvPuTao.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_linear));

					break;
				case 7:
					mTvYouZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvYouZi.getText());

					mIvLogo.setImageResource(R.drawable.lizi);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_yellow));
					break;

				case 6:
					mTvPiPa.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mTvKaiJiang.setText(mTvPiPa.getText());

					mIvLogo.setImageResource(R.drawable.apple);

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.linear));
					break;
				case 5:
					mTvTaoZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.chengzi);

					mTvKaiJiang.setText(mTvTaoZi.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_set));
					break;
				case 4:
					mTvXiGua3.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.xigua);
					mTvKaiJiang.setText(mTvXiGua3.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_linear));
					break;
				case 3:
					mTvChengZi_2.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.chengzi);
					mTvKaiJiang.setText(mTvChengZi_2.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_yellow));
					break;

				case 2:
					mTvHuoLongGuo_2.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.huolongguo);

					mTvKaiJiang.setText(mTvKaiJiang.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_set));
					break;

				case 1:
					mTvShiZi.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.longyan);

					mTvKaiJiang.setText(mTvShiZi.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_linear));

					break;
				case 0:
					mTvApple.setBackgroundColor(getResources().getColor(
							R.color.tv_set));

					mIvLogo.setImageResource(R.drawable.apple);

					mTvKaiJiang.setText(mTvApple.getText());

					mLinear.setBackgroundColor(getResources().getColor(
							R.color.tv_yellow));
					break;
				default:
					break;
				}
			} else if (msg.what == 2) {
				mBtnBegin.setClickable(true);

				mLinear.setBackgroundColor(getResources().getColor(
						R.color.linear3));

				if (player2.isPlaying()) {
					player = MediaPlayer.create(MainActivity.this,
							musicRecuse[getMusicId()]);
					player.start();
					player2.pause();
				}
				if (mTvKaiJiang.getText().toString()
						.equals(mTvToday.getText().toString())) {

					String strZhuShu = mTvNowXiaZhu.getText().toString();
					String strbeishu = mTvBeiShu.getText().toString();
					int ZhongJiangMoney = toInt(strbeishu)
							* strToInt(strZhuShu);

					mTvNowPrint.setText((strToInt(mTvNowPrint.getText()
							.toString()) + ZhongJiangMoney) + "");

					Toast.makeText(MainActivity.this,
							"鳩炰蠟ㄛ笢蔣賸ㄐㄐㄐ" + "僕" + ZhongJiangMoney + "啋", 0)
							.show();
					mTvDeFen.setText(ZhongJiangMoney + "");
					mTvToday.setText("恁寁");
					mTvNowXiaZhu.setText("0");
				} else {
					Toast.makeText(MainActivity.this, "竭疻熄ㄛ婬懂珨棒勘ㄐ", 0).show();
					mTvToday.setText("恁寁");
					mTvNowXiaZhu.setText("0");
				}

			}
		};
	};

	/** 莉汜珨跺呴儂杅ㄛ蚚懂樵隅蛌腔杅 */
	private int getRanman() {
		int max = 200;
		int min = 100;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;

	}

	/** 莉汜珨跺呴儂杅ㄛ蚚懂隅笢蔣瘍鎢 */
	private int getRana() {
		int max = 18;
		int min = 1;
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/** 莉汜珨跺呴儂杅ㄛ蚚懂隅貉 */
	private int getMusicId() {
		int max = 5;
		int min = 0;
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/** 趼睫揹曹峈int濬倰 */
	private int strToInt(String str) {

		return Integer.valueOf(str).intValue();

	}

	/** 菴媼跺趼睫揹 */
	private int toInt(String str) {

		return strToInt(str.substring(1));
	}

	@Override
	protected void onPause() {
		player.pause();
		isOnpause = 1;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		player.stop();
		if (player2 != null) {
			player2.stop();
		}

		super.onDestroy();
		Editor editor = sp.edit();
		editor.putString("allprint", mTvNowPrint.getText().toString() + "");
		editor.commit();
	}

}
