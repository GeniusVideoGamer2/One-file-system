package tv.plus.pro.game;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.media.ToneGenerator;
import android.media.AudioManager;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Locale;
import java.util.Random;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
public class TurkCityActivity extends Activity {
 private Scene scene;private TextToSpeech speech;private TextView hud;private int talk=0;
 private ToneGenerator sound;
 private final String[] effects={"Prism Pulse","Aether Spark","Solar Flash","Obsidian Wave","Nebula Burst","Crystal Echo"};
 private static final String CASH="939.939.393.939.393 $";
 private final String[] dialogues={"Merhaba, mahallemize hoş geldin!","Bugün hava çok güzel.","Şu ilerideki markete uğradın mı?","Görüşürüz, iyi oyunlar!"};
 @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().getDecorView().setSystemUiVisibility(5894|1024|512);
 sound=new ToneGenerator(AudioManager.STREAM_MUSIC,55);speech=new TextToSpeech(this,s->{if(s==TextToSpeech.SUCCESS)speech.setLanguage(new Locale("tr","TR"));});
 FrameLayout root=new FrameLayout(this);scene=new Scene(this);root.addView(scene);
 hud=new TextView(this);hud.setText("TÜRK CITY 3B  •  "+CASH+"  •  WASD: sür/yürü  •  E: araca bin  •  C: kokpit  •  Menü: gece");hud.setTextSize(16);hud.setTextColor(Color.WHITE);hud.setBackgroundColor(0xaa10122d);hud.setPadding(18,12,18,12);
 FrameLayout.LayoutParams hp=new FrameLayout.LayoutParams(-1,-2,Gravity.TOP);root.addView(hud,hp);setContentView(root);scene.setFocusable(true);scene.requestFocus();}
 @Override public boolean onKeyDown(int key,KeyEvent e){if(key==KeyEvent.KEYCODE_SPACE||key==KeyEvent.KEYCODE_BUTTON_A){if(!scene.renderer.crouch&&scene.renderer.jumpHeight<=0.01f){scene.renderer.jumpVelocity=5.8f;hud.setText("Zıpladın! • Space: zıpla • Ctrl: eğil");}return true;}if(key==KeyEvent.KEYCODE_CTRL_LEFT||key==KeyEvent.KEYCODE_CTRL_RIGHT){scene.renderer.crouch=true;hud.setText("Eğildin • Ctrl bırak: ayağa kalk");return true;}if(key==KeyEvent.KEYCODE_K||key==KeyEvent.KEYCODE_BUTTON_B){scene.renderer.skin=(scene.renderer.skin+1)%4;hud.setText("Neon araç skini: "+new String[]{"Prism","Aether","Solar","Obsidian"}[scene.renderer.skin]);return true;}if(key==KeyEvent.KEYCODE_V){scene.renderer.carType=(scene.renderer.carType+1)%12;hud.setText("Araç: "+new String[]{"Süper Spor","Şehir Coupe","SUV","Taksi","Elektrikli GT","Hatchback","Pickup","Minibüs","Klasik Spor","Ralli","Limuzin","Elektrikli Mini"}[scene.renderer.carType]);return true;}if(key==KeyEvent.KEYCODE_1){scene.renderer.effect=(scene.renderer.effect+1)%effects.length;hud.setText("Efekt: "+effects[scene.renderer.effect]+" • F: görsel efekt");return true;}if(key==KeyEvent.KEYCODE_2){scene.renderer.effectSkin=(scene.renderer.effectSkin+1)%4;hud.setText("Efekt skini: "+new String[]{"Neon Mor","Buz Mavisi","Altın","Zümrüt"}[scene.renderer.effectSkin]);return true;}if(key==KeyEvent.KEYCODE_F){scene.renderer.pulseUntil=System.nanoTime()+450000000L;if(sound!=null)sound.startTone(ToneGenerator.TONE_PROP_BEEP2,130);hud.setText(effects[scene.renderer.effect]+" • Işık ve arcade ses efekti");return true;}if(key==KeyEvent.KEYCODE_E||key==KeyEvent.KEYCODE_BUTTON_X){scene.renderer.driving=!scene.renderer.driving;scene.renderer.move=0;hud.setText(scene.renderer.driving?"Süper otomobile bindin! WASD: sür • C: kokpit • E: in":"Araçtan indin • E: tekrar bin");return true;}if(key==KeyEvent.KEYCODE_C||key==KeyEvent.KEYCODE_BUTTON_Y){scene.renderer.cockpit=!scene.renderer.cockpit;hud.setText(scene.renderer.cockpit?"Kokpit kamerası açık":"Dış kamera açık");return true;}if(key==KeyEvent.KEYCODE_MENU||key==KeyEvent.KEYCODE_BUTTON_START){scene.renderer.night=!scene.renderer.night;hud.setText(scene.renderer.night?"Gece modu etkin • Sokak ışıkları açık":"Gündüz modu etkin • Şehir turuna devam!");return true;}if(key==KeyEvent.KEYCODE_DPAD_CENTER||key==KeyEvent.KEYCODE_ENTER){float nearest=Float.MAX_VALUE;for(int i=0;i<scene.renderer.npcs.length;i++){float[] n=scene.renderer.npcs[i];float d=(n[0]-scene.renderer.x)*(n[0]-scene.renderer.x)+(n[1]-scene.renderer.z)*(n[1]-scene.renderer.z);nearest=Math.min(nearest,d);}if(nearest<18){String line=dialogues[talk++%dialogues.length];hud.setText("NPC: "+line);if(speech!=null)speech.speak(line,TextToSpeech.QUEUE_FLUSH,null,"npc");}else hud.setText("Konuşmak için bir NPC'ye yaklaş.  •  Yön tuşlarıyla ilerle.");return true;}if(key==KeyEvent.KEYCODE_DPAD_UP||key==KeyEvent.KEYCODE_W)scene.renderer.move=1;else if(key==KeyEvent.KEYCODE_DPAD_DOWN||key==KeyEvent.KEYCODE_S)scene.renderer.move=-1;else if(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_A)scene.renderer.turn=-1;else if(key==KeyEvent.KEYCODE_DPAD_RIGHT||key==KeyEvent.KEYCODE_D)scene.renderer.turn=1;else return super.onKeyDown(key,e);return true;}
 @Override public boolean onKeyUp(int key,KeyEvent e){if(key==KeyEvent.KEYCODE_CTRL_LEFT||key==KeyEvent.KEYCODE_CTRL_RIGHT){scene.renderer.crouch=false;hud.setText("Ayağa kalktın • Space: zıpla • Ctrl: eğil");return true;}if(key==KeyEvent.KEYCODE_DPAD_UP||key==KeyEvent.KEYCODE_DPAD_DOWN||key==KeyEvent.KEYCODE_W||key==KeyEvent.KEYCODE_S)scene.renderer.move=0;if(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_DPAD_RIGHT||key==KeyEvent.KEYCODE_A||key==KeyEvent.KEYCODE_D)scene.renderer.turn=0;return true;}
 @Override protected void onPause(){super.onPause();if(scene!=null)scene.onPause();}
 @Override protected void onResume(){super.onResume();if(scene!=null)scene.onResume();}
 @Override protected void onDestroy(){if(speech!=null){speech.stop();speech.shutdown();}if(sound!=null)sound.release();super.onDestroy();}
 static class Scene extends GLSurfaceView {final World renderer;float lastMouseX;Scene(Context c){super(c);setEGLContextClientVersion(2);renderer=new World();setRenderer(renderer);setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);} @Override public boolean onGenericMotionEvent(MotionEvent e){if((e.getSource()&android.view.InputDevice.SOURCE_MOUSE)==android.view.InputDevice.SOURCE_MOUSE){if(e.getActionMasked()==MotionEvent.ACTION_HOVER_MOVE){float delta=e.getX()-lastMouseX;if(lastMouseX!=0&&Math.abs(delta)<160)renderer.angle+=delta*.002f;lastMouseX=e.getX();return true;}}return super.onGenericMotionEvent(e);}}
 static class World implements GLSurfaceView.Renderer {
 final float[] projection=new float[16],view=new float[16],vp=new float[16],model=new float[16],mvp=new float[16];final float[][] npcs=new float[18][2];float x=0,z=0,angle=0;volatile int move=0,turn=0;volatile boolean night=false;volatile boolean driving=false,cockpit=false,crouch=false;volatile int skin=0,carType=0,effect=0,effectSkin=0;volatile long pulseUntil=0;float speed=0;volatile float jumpHeight=0,jumpVelocity=0;int program,posLoc,mvpLoc,colorLoc;FloatBuffer cube;long last=System.nanoTime();Random rng=new Random(21);
 final float[] verts={-1,-1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1,1,1, 1,-1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,1,1,-1, -1,-1,-1,-1,-1,1,-1,1,1,-1,-1,-1,-1,1,1,-1,1,-1, 1,-1,1,1,-1,-1,1,1,-1,1,-1,1,1,1,-1,1,1,1, -1,1,1,1,1,1,1,1,-1,-1,1,1,1,1,-1,-1,1,-1, -1,-1,-1,1,-1,-1,1,-1,1,-1,-1,-1,1,-1,1,-1,-1,1};
 World(){for(int i=0;i<npcs.length;i++){npcs[i][0]=rng.nextInt(65)-32;npcs[i][1]=rng.nextInt(65)-32;}}
 int shader(int type,String code){int s=GLES20.glCreateShader(type);GLES20.glShaderSource(s,code);GLES20.glCompileShader(s);return s;}
 @Override public void onSurfaceCreated(GL10 gl,EGLConfig cfg){GLES20.glEnable(GLES20.GL_DEPTH_TEST);GLES20.glClearColor(.035f,.055f,.12f,1);
 int v=shader(GLES20.GL_VERTEX_SHADER,"uniform mat4 uMVP; attribute vec3 aPos; void main(){gl_Position=uMVP*vec4(aPos,1.0);}");
 int f=shader(GLES20.GL_FRAGMENT_SHADER,"precision mediump float; uniform vec4 uColor; void main(){gl_FragColor=uColor;}");
 program=GLES20.glCreateProgram();GLES20.glAttachShader(program,v);GLES20.glAttachShader(program,f);GLES20.glLinkProgram(program);
 posLoc=GLES20.glGetAttribLocation(program,"aPos");mvpLoc=GLES20.glGetUniformLocation(program,"uMVP");colorLoc=GLES20.glGetUniformLocation(program,"uColor");
 cube=ByteBuffer.allocateDirect(verts.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();cube.put(verts).position(0);}
 @Override public void onSurfaceChanged(GL10 gl,int w,int h){GLES20.glViewport(0,0,w,h);Matrix.perspectiveM(projection,0,65,(float)w/Math.max(1,h),.1f,200);}
 void block(float bx,float by,float bz,float sx,float sy,float sz,float r,float g,float b){Matrix.setIdentityM(model,0);Matrix.translateM(model,0,bx,by,bz);Matrix.scaleM(model,0,sx,sy,sz);Matrix.multiplyMM(mvp,0,vp,0,model,0);GLES20.glUniformMatrix4fv(mvpLoc,1,false,mvp,0);GLES20.glUniform4f(colorLoc,r,g,b,1);GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,36);}
 // Detailed low-poly car assembled from body, cabin, windows, wheels and lights.
 void car(float cx,float cz,float yaw,int type,int paint,long now){
 float cr=paint==2?.96f:paint==3?.09f:paint==1?.12f:.43f;
 float cg=paint==2?.54f:paint==3?.11f:paint==1?.72f:.10f;
 float cb=paint==2?.08f:paint==3?.18f:paint==1?.91f:.77f;
 float width=type==2||type==6||type==7?1.27f:type==11?.83f:1.05f;
 float length=type==7||type==10?2.65f:type==4?2.32f:type==11?1.42f:2.02f;
 // Body and roof are rotated in world coordinates via local x/z axes.
 carPart(cx,cz,yaw,0,.56f,0,width,.36f,length,cr,cg,cb);
 carPart(cx,cz,yaw,0,1.02f,-.12f,width*.82f,type==2||type==7?.49f:.34f,length*.48f,.10f,.16f,.27f);
 carPart(cx,cz,yaw,0,1.32f,-.12f,width*.73f,.07f,length*.38f,cr*.8f,cg*.8f,cb*.8f);
 for(int side=-1;side<=1;side+=2)for(int end=-1;end<=1;end+=2){
 carPart(cx,cz,yaw,side*(width+.10f),.31f,end*length*.68f,.19f,.29f,.34f,.025f,.029f,.042f);
 carPart(cx,cz,yaw,side*(width+.22f),.31f,end*length*.68f,.035f,.15f,.19f,.42f,.44f,.48f);
 }
 for(int side=-1;side<=1;side+=2){
 carPart(cx,cz,yaw,side*width*.73f,.68f,-length*.93f,.2f,.12f,.055f,1f,.91f,.66f);
 carPart(cx,cz,yaw,side*width*.73f,.68f,length*.93f,.2f,.12f,.055f,.95f,.12f,.19f);
 }
 carPart(cx,cz,yaw,0,.65f,-length*1.01f,width*.58f,.10f,.035f,.08f,.08f,.12f);
 if(type==0||type==8||type==9)carPart(cx,cz,yaw,0,1.08f,length*.91f,width*.9f,.06f,.17f,cr*.7f,cg*.7f,cb*.7f);
 }
 void carPart(float cx,float cz,float yaw,float lx,float y,float lz,float sx,float sy,float sz,float r,float g,float b){
 float cs=(float)Math.cos(yaw),sn=(float)Math.sin(yaw);
 float wx=cx+lx*cs+lz*sn,wz=cz-lx*sn+lz*cs;
 Matrix.setIdentityM(model,0);Matrix.translateM(model,0,wx,y,wz);Matrix.rotateM(model,0,(float)Math.toDegrees(yaw),0,1,0);Matrix.scaleM(model,0,sx,sy,sz);
 Matrix.multiplyMM(mvp,0,vp,0,model,0);GLES20.glUniformMatrix4fv(mvpLoc,1,false,mvp,0);GLES20.glUniform4f(colorLoc,r,g,b,1);GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,36);
 }
 @Override public void onDrawFrame(GL10 gl){long now=System.nanoTime();float dt=Math.min(.05f,(now-last)/1e9f);last=now;if(jumpHeight>0||jumpVelocity>0){jumpHeight+=jumpVelocity*dt;jumpVelocity-=14f*dt;if(jumpHeight<0){jumpHeight=0;jumpVelocity=0;}}angle+=turn*dt*(driving?1.1f:1.6f);float target=move*(driving?17f:6f);speed+=(target-speed)*Math.min(1,dt*(driving?1.4f:8f));x+=Math.sin(angle)*speed*dt;z-=Math.cos(angle)*speed*dt;x=Math.max(-43,Math.min(43,x));z=Math.max(-43,Math.min(43,z));
 GLES20.glClearColor(night?.012f:.33f,night?.019f:.55f,night?.055f:.78f,1);GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);GLES20.glUseProgram(program);cube.position(0);GLES20.glVertexAttribPointer(posLoc,3,GLES20.GL_FLOAT,false,0,cube);GLES20.glEnableVertexAttribArray(posLoc);
 float behind=driving&&!cockpit?6.5f:0;float eye=driving?(cockpit?1.45f:3.6f):(crouch?1.2f:2.3f)+jumpHeight;Matrix.setLookAtM(view,0,x-(float)Math.sin(angle)*behind,eye,z+(float)Math.cos(angle)*behind,x+(float)Math.sin(angle)*10,driving?1.3f:1.8f,z-(float)Math.cos(angle)*10,0,1,0);Matrix.multiplyMM(vp,0,projection,0,view,0);
 block(0,-.25f,0,100,.2f,100,.18f,.29f,.24f);
 // Asphalt, pavements, curbs and pedestrian crossings.
 block(0,-.025f,0,7,.03f,100,.10f,.13f,.19f);
 block(0,-.025f,0,100,.03f,7,.10f,.13f,.19f);
 for(int side=-1;side<=1;side+=2){
 block(side*5.1f,.02f,0,1.65f,.055f,100,.42f,.43f,.49f);
 block(0,.02f,side*5.1f,100,.055f,1.65f,.42f,.43f,.49f);
 block(side*4.08f,.09f,0,.12f,.09f,100,.83f,.79f,.64f);
 block(0,.09f,side*4.08f,100,.09f,.12f,.83f,.79f,.64f);
 }
 for(int i=-3;i<=3;i++){block(i*.76f,.016f,-9f,.25f,.015f,.64f,.93f,.91f,.81f);block(-9f,.016f,i*.76f,.64f,.015f,.25f,.93f,.91f,.81f);}
 for(int k=-40;k<=40;k+=8){block(.02f,.005f,k, .13f,.01f,1.5f,.94f,.81f,.48f);block(k,.005f,.02f,1.5f,.01f,.13f,.94f,.81f,.48f);}
 for(int i=-4;i<=4;i++)for(int j=-4;j<=4;j++){if(i==0||j==0)continue;float h=3+Math.abs((i*17+j*11)%7);float px=i*10,pz=j*10;float facade=.23f+((i+5)%3)*.06f;
 block(px,h/2,pz,3.6f,h/2,3.6f,facade,.30f,.45f);
 block(px,h+.18f,pz,3.8f,.18f,3.8f,.34f,.26f,.48f);
 block(px,.42f,pz-3.65f,.62f,.42f,.08f,.10f,.12f,.18f);
 for(int floor=1;floor<h;floor+=2){
 block(px,floor,pz+3.65f,2.5f,.07f,.04f,.15f,.17f,.25f);
 for(int win=-2;win<=2;win+=2){
 float light=night?.9f:.40f;
 block(px+win,floor+.34f,pz+3.65f,.58f,.49f,.045f,light,light*.86f,.57f);
 block(px+win,floor+.34f,pz-3.65f,.58f,.49f,.045f,light,light*.86f,.57f);
 block(px+3.65f,floor+.34f,pz+win,.045f,.49f,.58f,light,light*.86f,.57f);
 }
 }}
 // Traffic follows two roads; headlights are visible at night.
 float seconds=now/1e9f;
 for(int i=0;i<7;i++){float along=((seconds*(i%2==0?3.2f:-2.6f)+i*17+140)%100)-50;float lane=i%2==0?1.9f:-1.9f;
 if(i%3==0){block(along,.65f,lane,1.55f,.45f,.85f,.15f,.43f,.85f);block(along,.97f,lane,.82f,.35f,.7f,.1f,.22f,.32f);}
 else{block(lane,.65f,along,.85f,.45f,1.55f,.78f,.15f,.23f);block(lane,.97f,along,.7f,.35f,.82f,.17f,.2f,.31f);}
 if(night){block(along,.65f,lane+.89f,.35f,.1f,.03f,1,.9f,.5f);block(lane+.89f,.65f,along,.03f,.1f,.35f,1,.9f,.5f);}
 }
 // Trees, lamp posts and illuminated windows.
 for(int i=-4;i<=4;i++){float a=i*10f+4.5f;
 block(a,1.6f,5.4f,.18f,1.6f,.18f,.28f,.29f,.36f);block(a,3.3f,5.4f,.65f,.12f,.28f,night?1f:.72f,night?.85f:.77f,.46f);
 block(5.4f,1.6f,a,.18f,1.6f,.18f,.28f,.29f,.36f);block(5.4f,3.3f,a,.28f,.12f,.65f,night?1f:.72f,night?.85f:.77f,.46f);
 block(a,1.1f,-6.5f,.25f,1.1f,.25f,.36f,.25f,.16f);block(a,2.55f,-6.5f,1.02f,.76f,1.02f,.07f,.30f,.16f);block(a-.45f,3.15f,-6.5f,.78f,.65f,.78f,.10f,.39f,.19f);block(a+.43f,3.04f,-6.5f,.75f,.70f,.75f,.06f,.36f,.17f);
 }
 // Cosmetic-only energy pulse: visual collectible effect, not a projectile or damage system.
 if(now<pulseUntil){float t=(pulseUntil-now)/450000000f;float rr=effectSkin==2?1f:effectSkin==3?.2f:.45f;float gg=effectSkin==3?1f:effectSkin==1?.8f:.25f;float bb=effectSkin==2?.2f:1f;float px=x+(float)Math.sin(angle)*3,pz=z-(float)Math.cos(angle)*3;block(px,1.2f,pz,.3f+t*.6f,.3f+t*.6f,.3f+t*.6f,rr,gg,bb);}
 // Player-owned purple supercar, visible in third-person driving view.
 if(driving&&!cockpit)car(x,z,angle,carType,skin,now);
 // Cockpit prototype: dashboard, windshield base, wheel and center console.
 if(driving&&cockpit){float frontX=x+(float)Math.sin(angle)*.65f,frontZ=z-(float)Math.cos(angle)*.65f;block(frontX,.9f,frontZ,1.12f,.19f,.32f,.12f,.1f,.21f);block(frontX,1.12f,frontZ,.27f,.08f,.18f,.32f,.15f,.65f);block(frontX-.38f,1.1f,frontZ,.23f,.23f,.08f,.045f,.05f,.08f);block(frontX+.38f,1.1f,frontZ,.23f,.23f,.08f,.045f,.05f,.08f);}
 for(int i=0;i<npcs.length;i++){float[] n=npcs[i];float t=now/1e9f+i*2f;
 float oldX=n[0],oldZ=n[1];n[0]+=(float)Math.sin(t)*dt*.4f;n[1]+=(float)Math.cos(t*.75f)*dt*.4f;
 float walking=(float)Math.sin(t*5f)*.18f;
 float coatR=.25f+(i%4)*.12f,coatG=.18f+(i%3)*.09f,coatB=.49f+(i%5)*.06f;
 block(n[0],1.12f,n[1],.27f,.45f,.20f,coatR,coatG,coatB);
 block(n[0],1.78f,n[1],.20f,.24f,.20f,.92f,.70f,.56f);
 block(n[0]-.38f,.98f+walking,n[1],.11f,.38f,.12f,coatR,coatG,coatB);
 block(n[0]+.38f,.98f-walking,n[1],.11f,.38f,.12f,coatR,coatG,coatB);
 block(n[0]-.15f,.40f-walking,n[1],.13f,.39f,.14f,.11f,.15f,.27f);
 block(n[0]+.15f,.40f+walking,n[1],.13f,.39f,.14f,.11f,.15f,.27f);
 }

 }
 }
}