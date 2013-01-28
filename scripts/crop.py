import sys
import Image

img = Image.open("./goblinWalksL.png")

width = img.size[0] / 12
height = img.size[1]

print width, height

x = 0
y = 0

for i in range(12):
	box = (x, y, width+x, height+y)
	new = img.crop(box)
	new.save(str(11-i)+".png", "PNG")
	x += width
