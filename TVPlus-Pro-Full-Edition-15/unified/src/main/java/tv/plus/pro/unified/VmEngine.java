package tv.plus.pro.unified;
import android.content.*;import android.net.Uri;import android.os.Build;import java.io.*;import java.util.*;
/** QEMU process bridge. Requires an ABI-matched, packaged libqemu-system-x86_64.so executable.
 * No root, physical USB passthrough or hardware boot is provided. */
public final class VmEngine {
 private Process process;
 public synchronized boolean running(){return process!=null&&process.isAlive();}
 public File binary(Context c){return new File(c.getApplicationInfo().nativeLibraryDir,"libqemu-system-x86_64.so");}
 public boolean available(Context c){File f=binary(c);return f.isFile()&&f.canExecute();}
 public File disk(Context c){return new File(c.getFilesDir(),"vm-boot.img");}
 public void importDisk(Context c,Uri uri)throws IOException{
  File dst=disk(c);File tmp=new File(c.getFilesDir(),"vm-boot.tmp");
  try(InputStream in=c.getContentResolver().openInputStream(uri);OutputStream out=new FileOutputStream(tmp)){if(in==null)throw new IOException("Dosya açılamadı");byte[] buf=new byte[65536];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);}
  if(dst.exists()&&!dst.delete())throw new IOException("Eski görüntü silinemedi");
  if(!tmp.renameTo(dst))throw new IOException("Disk görüntüsü taşınamadı");
 }
 public synchronized void start(Context c,int ram)throws IOException{
  if(running())throw new IOException("VM zaten çalışıyor");
  if(!available(c))throw new IOException("Bu cihaz için QEMU native motoru APK'da yok");
  if(!disk(c).isFile())throw new IOException("Önce disk görüntüsü içe aktar");
  String file=disk(c).getAbsolutePath().toLowerCase(Locale.ROOT);
  String format=file.endsWith(".qcow2")?"qcow2":"raw";
  // Imported disk uses a fixed filename; determine format by its header instead.
  try(RandomAccessFile raf=new RandomAccessFile(disk(c),"r")){if(raf.length()>=4&&raf.readInt()==0x514649fb)format="qcow2";}
  List<String> args=Arrays.asList(binary(c).getAbsolutePath(),"-machine","pc","-accel","tcg","-m",String.valueOf(ram),"-smp","1","-drive","file="+disk(c).getAbsolutePath()+",format="+format+",if=ide,readonly=on","-boot","c","-vnc","127.0.0.1:1","-monitor","none","-serial","none","-net","none");
  ProcessBuilder pb=new ProcessBuilder(args);pb.redirectErrorStream(true);pb.redirectOutput(new File(c.getFilesDir(),"vm-qemu.log"));process=pb.start();
 }
 public synchronized void stop(){if(process!=null){process.destroy();process=null;}}
}
