package tv.plus.pro.gallery;
import android.app.*;import android.os.*;import android.content.*;import android.view.*;import android.widget.*;
public class MainActivity extends Activity{
LinearLayout root;void page(String name){LinearLayout base=new LinearLayout(this);base.setOrientation(1);base.setPadding(24,18,24,18);base.setBackgroundColor(0xff101025);TextView h=new TextView(this);h.setText("▶ TV+  "+name+"  15.0");h.setTextColor(-1);h.setTextSize(30);base.addView(h);ScrollView s=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(1);s.addView(root);base.addView(s);setContentView(base);}
void item(String name,Runnable click){TextView t=new TextView(this);t.setText(name);t.setTextSize(23);t.setTextColor(-1);t.setPadding(22,20,22,20);t.setFocusable(true);t.setBackgroundColor(0xff302450);t.setOnFocusChangeListener((v,f)->t.setBackgroundColor(f?0xff9857ff:0xff302450));t.setOnClickListener(v->click.run());root.addView(t);}
void launch(String pkg){Intent i=getPackageManager().getLaunchIntentForPackage(pkg);if(i!=null)startActivity(i);}
void system(String action){try{startActivity(new Intent(action));}catch(Exception e){startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));}}
@Override public void onCreate(Bundle b){super.onCreate(b);show();}
void show(){page("Galeri");item("Fotoğraf aç",()->pick("image/*",3));item("Video oynat",()->pick("video/*",4));}
void pick(String type,int req){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType(type);i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,req);}
@Override protected void onActivityResult(int req,int result,Intent data){super.onActivityResult(req,result,data);if(result!=RESULT_OK||data==null)return;android.net.Uri uri=data.getData();if(req==1){page("Klasör seçildi");item(uri.toString(),()->{});return;}Intent i=new Intent(Intent.ACTION_VIEW);i.setDataAndType(uri,req==3?"image/*":req==4?"video/*":getContentResolver().getType(uri));i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);try{startActivity(i);}catch(Exception e){new AlertDialog.Builder(this).setMessage("Görüntüleyici bulunamadı").setPositiveButton("Tamam",null).show();}}
}