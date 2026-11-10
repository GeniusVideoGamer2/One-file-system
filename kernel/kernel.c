#include <stdint.h>
#include <stddef.h>
static volatile uint16_t *const vga=(uint16_t*)0xB8000;
static size_t cursor=0;
static void print(const char *s,uint8_t color){while(*s){if(*s=='\n'){cursor=(cursor/80+1)*80;}else if(cursor<80*25){vga[cursor++]=((uint16_t)color<<8)|(uint8_t)*s;}s++;}}
void kernel_main(void){for(size_t i=0;i<80*25;i++)vga[i]=0x0720;print("GameXWIN OS 1.0 | GameXKernel prototype\n",0x0B);print("Aether UI / GameXStore / Recovery: planned\n",0x07);print("Boot OK. No disk changes.\n",0x0A);for(;;)__asm__ volatile("hlt");}
