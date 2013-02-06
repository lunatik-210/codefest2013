
import Image

newimg = Image.new("RGBA", (990,995))

imgsize = (330, 199)

imgwidth = 3
imgheight = 5

imgcount = 0

for j in range(imgheight):
	for i in range(imgwidth):
		if imgcount == 15:
			break
		img = Image.open(str(imgcount)+".png")
		newimg.paste(img, (i*imgsize[0],j*imgsize[1]))
		imgcount += 1

newimg.save("out.png", "PNG")