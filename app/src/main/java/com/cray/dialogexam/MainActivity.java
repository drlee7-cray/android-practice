package com.cray.dialogexam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	Button btn_dialog, btn_start, btn_stop;
	ImageView img_view;
	Integer SelectIndex = 0;
	Integer TmpIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_dialog = (Button) findViewById(R.id.btn_dialog);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_stop = (Button) findViewById(R.id.btn_stop);
		img_view = (ImageView) findViewById(R.id.img_view);

		btn_dialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder ad =
					new AlertDialog.Builder(MainActivity.this);
				ad.setIcon(R.mipmap.ic_launcher);
				ad.setTitle("음악을 선택하세요");
				// ad.setMessage("음악을 선택하세요."); <-- ad.setSingleChoiceItems 외 동시 사용 불가

				TmpIndex = SelectIndex;
				ad.setSingleChoiceItems(
					R.array.musiclist,
					SelectIndex,
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						TmpIndex = i;
					}
				});

				ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						SelectIndex = TmpIndex;
						btn_start.performClick();
					}
				});

				ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});

				ad.show();
			}
		});

		btn_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				// 선택한 목록을 음악리스트에서 받아옴
				String item = getResources()
					.getStringArray(R.array.musiclist)[SelectIndex];

				// 선택한 음악을 임시변수에 보관
				Integer MusicResource = 0;

				if(item.equals("angelofgodfromheaven.mp3"))
					MusicResource = R.raw.angelofgodfromheaven;
				else if(item.equals("awayinamangerchristmas.mp3"))
					MusicResource = R.raw.awayinamangerchristmas;
				else if(item.equals("happychristmas.mp3"))
					MusicResource = R.raw.happychristmas;

				// Music 서비스 호출 준비!
				Intent intent = new Intent(
					getApplicationContext(),
					MusicService.class);

				// 넘겨울 파라미터를 설정 : 음악은 이걸로 부탁~해요
				intent.putExtra("music", MusicResource.toString());

				// 서비스 시작!
				startService(intent);
			}
		});

		btn_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 서비스 종료
				stopService(new Intent(
						getApplicationContext(),
						MusicService.class
				));
			}
		});
	}
}