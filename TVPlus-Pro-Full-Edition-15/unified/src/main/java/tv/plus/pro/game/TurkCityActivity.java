package tv.plus.pro.game;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.*;
import android.view.*;
import android.speech.tts.TextToSpeech;
import java.util.*;
public class TurkCityActivity extends Activity {
 CityView city; TextToSpeech voice;
 @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().getDecorView().setSystemUiVisibility(5894|1024|512);voice=new TextToSpeech(this,status->{if(status==TextToSpeech.SUCCESS)voice.setLanguage(new Locale("tr","TR"));});city=new CityView(this);setContentView(city);}
 @Override public void onDestroy(){if(voice!=null){voice.stop();voice.shutdown();}super.onDestroy();}
 class Citizen {float x,y;int direction;String name;Citizen(float x,float y,String n){this.x=x;this.y=y;name=n;direction=(int)(Math.random()*4);}}
 class CityView extends View{
  Paint p=new Paint(3);Random random=new Random(19);ArrayList<Citizen> people=new ArrayList<>();float playerX=640,playerY=450;float dx=0,dy=0;long last=System.currentTimeMillis();String message="TÜRK CITY | Yön tuşları: hareket • OK: konuş";long messageUntil=0;int talkIndex=0;String[] lines={"Merhaba, hoş geldin!","Bugün hava çok güzel.","Şu köşedeki kafeye uğradın mı?","Mahallede yeni misin?","Görüşürüz, iyi oyunlar!"};
  CityView(Context c){super(c);setFocusable(true);requestFocus();for(int i=0;i<22;i++)people.add(new Citizen(90+random.nextInt(1100),120+random.nextInt(700),new String[]{"Ece","Mert","Deniz","Ayşe","Can","Zeynep"}[i%6]));}
  void color(int c){p.setColor(c);p.setStyle(Paint.Style.FILL);p.setStrokeWidth(1);}
  void rect(Canvas c,float x,float y,float w,float h,int col){color(col);c.drawRect(x,y,x+w,y+h,p);}
  void txt(Canvas c,String s,float x,float y,int size,int col){color(col);p.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));p.setTextSize(size);c.drawText(s,x,y,p);}
  @Override protected void onDraw(Canvas actual){super.onDraw(actual);float sx=getWidth()/1280f,sy=getHeight()/720f;Canvas c=actual;c.save();c.scale(sx,sy);rect(c,0,0,1280,720,0xff091126);rect(c,0,0,1280,72,0xff141b3b);
   for(int y=0;y<9;y++)for(int x=0;x<13;x++){int px=x*105-15,py=y*105-20;rect(c,px,py,94,94,(x+y)%2==0?0xff25364b:0xff293b51);rect(c,px+9,py+9,76,76,0xff374b61);rect(c,px+19,py+19,56,56,0xff52677a);}
   rect(c,0,310,1280,118,0xff30343f);rect(c,570,75,140,645,0xff30343f);
   for(int x=15;x<1280;x+=95)rect(c,x,366,45,5,0xffe6d39b);
   for(int y=90;y<720;y+=95)rect(c,635,y,5,45,0xffe6d39b);
   long now=System.currentTimeMillis();float dt=Math.min(.05f,(now-last)/1000f);last=now;
   playerX=Math.max(20,Math.min(1260,playerX+dx*240*dt));playerY=Math.max(90,Math.min(700,playerY+dy*240*dt));
   for(Citizen n:people){if(random.nextFloat()<.007)n.direction=random.nextInt(5);float speed=27*dt;n.x+=n.direction==1?speed:n.direction==2?-speed:0;n.y+=n.direction==3?speed:n.direction==4?-speed:0;n.x=Math.max(15,Math.min(1265,n.x));n.y=Math.max(85,Math.min(705,n.y));color(0x99000000);c.drawOval(n.x-10,n.y+9,n.x+10,n.y+16,p);color(0xff7d5cff);c.drawCircle(n.x,n.y,10,p);color(0xffffd0b0);c.drawCircle(n.x,n.y-12,7,p);}
   color(0x99000000);c.drawOval(playerX-16,playerY+10,playerX+16,playerY+20,p);color(0xff00d8ff);c.drawCircle(playerX,playerY,14,p);color(0xffffd4b0);c.drawCircle(playerX,playerY-18,9,p);
   txt(c,"TÜRK CITY  •  Yaşayan Şehir",25,47,27,0xfff0e9ff);txt(c,"NPC: "+people.size()+"   |   Mahalle 01",920,46,19,0xffbbadff);
   rect(c,12,637,1256,70,0xdd10142c);txt(c,message,30,678,21,0xffffffff);txt(c,"OK: NPC ile konuş   •   Geri: çık",835,614,17,0xffc6d3ff);
   c.restore();postInvalidateDelayed(33);
  }
  void talk(){Citizen nearest=null;float distance=Float.MAX_VALUE;for(Citizen n:people){float d=(n.x-playerX)*(n.x-playerX)+(n.y-playerY)*(n.y-playerY);if(d<distance){distance=d;nearest=n;}}if(nearest!=null&&distance<16000){message=nearest.name+": "+lines[talkIndex++%lines.length];if(voice!=null)voice.speak(lines[(talkIndex-1)%lines.length],TextToSpeech.QUEUE_FLUSH,null,"citizen");}else message="Konuşmak için bir karaktere yaklaş.";}
  @Override public boolean onKeyDown(int key,KeyEvent e){if(key==KeyEvent.KEYCODE_DPAD_LEFT)dx=-1;else if(key==KeyEvent.KEYCODE_DPAD_RIGHT)dx=1;else if(key==KeyEvent.KEYCODE_DPAD_UP)dy=-1;else if(key==KeyEvent.KEYCODE_DPAD_DOWN)dy=1;else if(key==KeyEvent.KEYCODE_DPAD_CENTER||key==KeyEvent.KEYCODE_ENTER){talk();return true;}else return super.onKeyDown(key,e);return true;}
  @Override public boolean onKeyUp(int key,KeyEvent e){if(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_DPAD_RIGHT)dx=0;if(key==KeyEvent.KEYCODE_DPAD_UP||key==KeyEvent.KEYCODE_DPAD_DOWN)dy=0;return super.onKeyUp(key,e);}
 }
}