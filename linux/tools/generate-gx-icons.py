#!/usr/bin/env python3
"""Generate original GX SVG icons and desktop launchers, without external assets."""
from pathlib import Path
import html
root=Path(__file__).resolve().parents[1]
icons=root/"overlay/usr/share/icons/hicolor/scalable/apps"
icons.mkdir(parents=True,exist_ok=True)
apps={
"hub":("#8855ef","✦"),"files":("#4b9eff","▰"),"settings":("#8d8ba9","⚙"),
"store":("#8b55e8","◇"),"terminal":("#24283e",">_"),"notes":("#e4b75c","≡"),
"reminders":("#e67b73","✓"),"contacts":("#d795d9","♙"),"calendar":("#ed746f","11"),
"calculator":("#4b506f","+"),"browser":("#6e78ef","◎"),"photos":("#e68fb5","✿"),
"music":("#d65c9a","♫"),"weather":("#60a6db","☁"),"clock":("#4c537c","◷"),
"phone":("#41bf83","☎"),"messages":("#4d9fe6","●"),"maps":("#5cb4a1","⌖"),
"mail":("#5b85e8","✉"),"about":("#8b68d6","GX")}
for name,(color,glyph) in apps.items():
    svg=f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 128 128">
<defs><linearGradient id="g" x1="0" y1="0" x2="1" y2="1"><stop stop-color="#fff" stop-opacity=".38"/><stop offset=".48" stop-color="{color}"/><stop offset="1" stop-color="#221342"/></linearGradient></defs>
<rect x="9" y="9" width="110" height="110" rx="29" fill="#0b0920"/>
<rect x="12" y="10" width="104" height="103" rx="27" fill="url(#g)"/>
<path d="M29 27 Q64 9 99 27" fill="none" stroke="#fff" stroke-opacity=".43" stroke-width="4" stroke-linecap="round"/>
<text x="64" y="80" text-anchor="middle" font-family="DejaVu Sans,sans-serif" font-size="{32 if len(glyph)>1 else 56}" font-weight="bold" fill="white">{html.escape(glyph)}</text>
</svg>'''
    (icons/("gx-"+name+".svg")).write_text(svg)
    desktop=root/("overlay/usr/share/applications/gx-"+name+".desktop")
    if desktop.exists():
        txt=desktop.read_text()
        import re
        txt=re.sub(r"(?m)^Icon=.*$", "Icon=gx-"+name,txt)
        desktop.write_text(txt)
print("Generated",len(apps),"GX icons")
