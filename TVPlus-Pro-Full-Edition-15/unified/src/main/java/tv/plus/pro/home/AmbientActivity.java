package tv.plus.pro.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.*;
import android.view.*;
import android.content.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/** Full-screen animated ambient display; no overlay or system-level screensaver privilege. */
public class AmbientActivity extends Activity {
 private AmbientView ambient;
 private final Handler handler=new Handler(Looper.getMainLooper());
 private final Runnable tick=new Runnable(){public void run(){if(ambient!=null){ambient.invalidate();handler.postDelayed(this,1000);}}};
 private android.content.SharedPreferences prefs;
 @Override public void onCreate(Bundle state){super.onCreate(state);prefs=getSharedPreferences("tvplus_ambient",0);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);getWindow().getDecorView().setSystemUiVisibility(5894|1024|512);ambient=new AmbientView(this);setContentView(ambient);ambient.setFocusable(true);ambient.requestFocus();}
 @Override protected void onResume(){super.onResume();handler.removeCallbacks(tick);handler.post(tick);}
 @Override protected void onPause(){handler.removeCallbacks(tick);super.onPause();}
 @Override protected void onDestroy(){handler.removeCallbacks(tick);getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);super.onDestroy();}
 @Override public boolean onKeyDown(int key,KeyEvent e){if(key==KeyEvent.KEYCODE_BACK)return super.onKeyDown(key,e);if(key==KeyEvent.KEYCODE_DPAD_CENTER||key==KeyEvent.KEYCODE_ENTER||key==KeyEvent.KEYCODE_MENU){showOptions();return true;}if(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_DPAD_RIGHT){int theme=prefs.getInt("theme",0);prefs.edit().putInt("theme",(theme+(key==KeyEvent.KEYCODE_DPAD_RIGHT?1:2))%3).apply();ambient.invalidate();return true;}return super.onKeyDown(key,e);}
 void showOptions(){String[] choices={"Tema: Neon Galaksi","Tema: Gece Gökyüzü","Tema: Minimal Siyah","Saniyeleri Göster / Gizle","Çıkış"};new AlertDialog.Builder(this).setTitle("Ambient TV • Ayarlar").setItems(choices,(d,which)->{if(which<3)prefs.edit().putInt("theme",which).apply();else if(which==3)prefs.edit().putBoolean("seconds",!prefs.getBoolean("seconds",false)).apply();else finish();ambient.invalidate();}).setNegativeButton("Kapat",null).show();}
 class AmbientView extends View {
  final Paint p=new Paint(3);final Random random=new Random(120);final float[] stars=new float[150];long started=android.os.SystemClock.elapsedRealtime();
  AmbientView(Context c){super(c);for(int i=0;i<stars.length;i++)stars[i]=random.nextFloat();}
  @Override public boolean onTouchEvent(android.view.MotionEvent e){if(e.getAction()==MotionEvent.ACTION_UP){showOptions();return true;}return true;}
  void centered(Canvas c,String value,float x,float y,float size,int color,boolean bold){p.reset();p.setAntiAlias(true);p.setColor(color);p.setTextSize(size);p.setTypeface(bold?Typeface.create("sans-serif-light",Typeface.NORMAL):Typeface.create("sans-serif",Typeface.NORMAL));p.setTextAlign(Paint.Align.CENTER);c.drawText(value,x,y,p);}
  @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight();if(w==0||h==0)return;int theme=prefs.getInt("theme",0);int[] colors=theme==0?new int[]{0xff05091d,0xff15124a,0xff07172e}:theme==1?new int[]{0xff030713,0xff101d38,0xff090b1a}:new int[]{0xff000000,0xff050505,0xff000000};p.setShader(new LinearGradient(0,0,w,h,colors,null,Shader.TileMode.CLAMP));c.drawRect(0,0,w,h,p);p.setShader(null);
   long t=android.os.SystemClock.elapsedRealtime()-started;
   if(theme!=2){
    for(int i=0;i<stars.length/3;i++){float x=stars[i*3]*w,y=stars[i*3+1]*h;float a=(float)(0.35+0.35*Math.sin(t/1700.0+i));p.setColor(Color.argb((int)(a*255),205,220,255));c.drawCircle(x,y,Math.max(0.7f,w/1400f)*(.5f+stars[i*3+2]),p);}
    if(theme==0){p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(Math.max(5,w*.012f));for(int j=0;j<3;j++){Path wave=new Path();float shift=(float)Math.sin(t/7000.0+j)*h*.06f;wave.moveTo(-w*.1f,h*(.75f+j*.06f)+shift);wave.cubicTo(w*.22f,h*.42f+shift,w*.6f,h*1.08f-shift,w*1.12f,h*.48f+shift);p.setColor(j==0?0x553f86ff:j==1?0x665b45ef:0x55bc4afa);c.drawPath(wave,p);}p.setStyle(Paint.Style.FILL);}
   }
   boolean seconds=prefs.getBoolean("seconds",false);String format=seconds?"HH:mm:ss":"HH:mm";String clock=new SimpleDateFormat(format,Locale.getDefault()).format(new Date());float scale=Math.min(w/1000f,h/600f);centered(c,clock,w*.5f,h*.48f,Math.max(42,112*scale),Color.WHITE,true);String date=new SimpleDateFormat("EEEE, d MMMM yyyy",Locale.getDefault()).format(new Date());centered(c,date,w*.5f,h*.59f,Math.max(15,27*scale),0xffc9cce8,false);centered(c,"TV+ Pro  •  Ambient TV",w*.5f,h*.87f,Math.max(12,19*scale),0xff9da5d8,false);centered(c,"OK / Menü: Ayarlar    •    Sağ / Sol: Tema    •    Geri: Çıkış",w*.5f,h*.94f,Math.max(10,15*scale),0xff8991b3,false);
  }
 }
}
