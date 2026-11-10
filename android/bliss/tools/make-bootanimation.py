#!/usr/bin/env python3
"""Generate an original GameXWIN bootanimation.zip (Android format)."""
from pathlib import Path
from zipfile import ZipFile, ZIP_STORED
from PIL import Image, ImageDraw, ImageFont
import math
out=Path(__file__).resolve().parents[1]/"assets"/"bootanimation.zip"
out.parent.mkdir(parents=True,exist_ok=True)
w,h=1280,720
font_path="/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"
font=ImageFont.truetype(font_path,70)
small=ImageFont.truetype(font_path,28)
frames=[]
for i in range(30):
    im=Image.new("RGB",(w,h),(9,13,31))
    d=ImageDraw.Draw(im)
    x=w//2;y=h//2-50
    for radius in range(210,75,-14):
        t=(i/30)*2*math.pi
        c=(int(30+40*(1+math.sin(t+radius/55))),35,int(110+45*(1+math.cos(t+radius/65))))
        d.ellipse((x-radius,y-radius,x+radius,y+radius),outline=c,width=2)
    d.rounded_rectangle((x-82,y-82,x+82,y+82),radius=38,fill=(55,40,120),outline=(145,115,255),width=4)
    d.text((x,y),"GX",font=font,anchor="mm",fill="white")
    d.text((x,h-130),"GameXWIN OS",font=font,anchor="mm",fill=(235,232,255))
    d.text((x,h-78),"AETHER EDITION",font=small,anchor="mm",fill=(156,155,220))
    frames.append(im)
import io
with ZipFile(out,"w",compression=ZIP_STORED) as z:
    z.writestr("desc.txt","1280 720 24\np 0 0 part0\n")
    for i,im in enumerate(frames):
        b=io.BytesIO();im.save(b,format="PNG")
        z.writestr(f"part0/{i:05d}.png",b.getvalue())
print(out)
