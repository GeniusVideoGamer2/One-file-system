package tv.plus.pro.home;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;
public class AikoActivity extends Activity {
 private Avatar avatar;
 @Override public void onCreate(Bundle b){super.onCreate(b);LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setBackgroundColor(0xff10112b);
 TextView title=new TextView(this);title.setText("✦ AIKO  •  TV+ Pro Anime Maskotu");title.setTextColor(-1);title.setTextSize(24);title.setPadding(20,16,12,12);root.addView(title);
 avatar=new Avatar();root.addView(avatar,new LinearLayout.LayoutParams(-1,0,1));
 TextView info=new TextView(this);info.setText("Merhaba! Ben Aiko. Kumandayla beni seçebilir, oyun açabilir veya paylaşım yapabilirsin.");info.setTextColor(0xffe2d5ff);info.setTextSize(17);info.setPadding(20,10,20,10);root.addView(info);
 LinearLayout buttons=new LinearLayout(this);buttons.setOrientation(0);
 add(buttons,"🎮 Türk City",()->startActivity(new Intent(this,tv.plus.pro.game.TurkCityActivity.class)));
 add(buttons,"⇄ Dosya Paylaş",()->{Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("*/*");i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,93);});
 add(buttons,"✦ Ambient TV",()->startActivity(new Intent(this,AmbientActivity.class)));
 root.addView(buttons);setContentView(root);}
 void add(LinearLayout parent,String label,Runnable r){Button b=new Button(this);b.setText(label);b.setAllCaps(false);b.setOnClickListener(v->r.run());parent.addView(b,new LinearLayout.LayoutParams(0,-2,1));}
 @Override protected void onActivityResult(int request,int result,Intent data){super.onActivityResult(request,result,data);if(request==93&&result==RESULT_OK&&data!=null&&data.getData()!=null){android.net.Uri uri=data.getData();Intent send=new Intent(Intent.ACTION_SEND);String type=getContentResolver().getType(uri);send.setType(type==null?"application/octet-stream":type);send.putExtra(Intent.EXTRA_STREAM,uri);send.setClipData(android.content.ClipData.newUri(getContentResolver(),"TV+ Dosya",uri));send.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);startActivity(Intent.createChooser(send,"Quick Share / Dosya Paylaş"));}}
 class Avatar extends View {Paint p=new Paint(3);long start=System.currentTimeMillis();Avatar(){super(AikoActivity.this);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
 void oval(Canvas c,int color,float l,float t,float r,float b){p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawOval(l,t,r,b,p);}
 @Override protected void onDraw(Canvas c){super.onDraw(c);c.save();float scale=Math.min(getWidth()/480f,getHeight()/480f);c.translate((getWidth()-480*scale)/2,(getHeight()-480*scale)/2);c.scale(scale,scale);
 float t=(System.currentTimeMillis()-start)/1000f;float bob=(float)Math.sin(t*2)*5;
 p.setShader(new LinearGradient(0,0,480,480,0xff21165a,0xff0c1838,Shader.TileMode.CLAMP));c.drawRoundRect(30,12,450,468,38,38,p);p.setShader(null);
 c.save();c.translate(0,bob);
 oval(c,0xff5033aa,125,120,355,396);oval(c,0xff9a72f4,145,100,335,370);
 oval(c,0xff25294f,136,313,344,460);oval(c,0xffb8a2ff,196,327,284,412);
 oval(c,0xffffd7c7,162,135,320,334);
 oval(c,0xff6841bd,148,116,332,198);oval(c,0xff9d6aff,152,103,320,164);
 oval(c,0xff252047,142,181,174,269);oval(c,0xff252047,308,181,340,269);
 oval(c,0xffe6dfff,146,197,175,260);oval(c,0xffe6dfff,306,197,335,260);
 oval(c,0xff22224b,185,231,220,261);oval(c,0xff22224b,262,231,297,261);
 oval(c,0xff9b60e8,195,235,212,257);oval(c,0xff9b60e8,271,235,288,257);
 oval(c,0xffef99ad,178,277,209,292);oval(c,0xffef99ad,274,277,305,292);
 p.setColor(0xff9e496e);p.setStrokeWidth(3);p.setStyle(Paint.Style.STROKE);c.drawArc(232,277,251,292,5,170,false,p);p.setStyle(Paint.Style.FILL);
 c.restore();postInvalidateDelayed(40);}
 }
}