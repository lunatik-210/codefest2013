import Image
import os

for file in os.listdir("."):
	if file != "flip.py" and file != "g":
	    img = Image.open(file)
	    img = img.transpose(Image.FLIP_LEFT_RIGHT)
	    img.save("./g/"+file, "PNG")
