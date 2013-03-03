
import sys
import math
import PyQt4

from PyQt4.QtGui import *
from PyQt4.QtCore import *

from ImageViewer import Ui_ImageViewer
from ObjectBrowser import Ui_ObjectBrowser
from WayPointEditor import Ui_WayPointEditor

from lxml import etree

class WayPoint():
    def __init__(self, x, y, id):
        self.langle = 0.234
        self.rangle = 0.234
        self.x = x
        self.y = y
        self.isThrowable = False
        self.neighbors = []
        self.id = id
        self.objects = []

    def serializeToXml(self):
        root = etree.Element("waypoint")
        etree.SubElement(root, "langle").text = str(self.langle)
        etree.SubElement(root, "rangle").text = str(self.rangle)
        etree.SubElement(root, "x").text = str(self.x)
        etree.SubElement(root, "y").text = str(self.y)
        etree.SubElement(root, "isThrowable").text = str(self.isThrowable)
        etree.SubElement(root, "id").text = str(self.id)
        
        list = etree.Element("list")
        list.attrib['type']='neighbor'
        for id in self.neighbors:
            etree.SubElement(list, "id").text = str(id)
        root.append(list)

        list = etree.Element("list")
        list.attrib['type']='object'
        for object in self.objects:
            etree.SubElement(list, "object").text = str(object)
        root.append(list)

        return root

class ImageViewer(QWidget, Ui_ImageViewer):
    def __init__(self, parent=None):
        super(ImageViewer, self).__init__(parent)
        self.setupUi(self)
        self.setUpdatesEnabled(True)
        self.init()
        self.data = []
        self.currentObject = None
        self.area = 4
        self.onResetButton()

    def init(self):
        self.zoomingSize = QSize(100,100)
        self.image = QPixmap("./background-full-new.png")
        self.debugLabel.setText("Width: " + str(self.image.width()) + " Height: " + str(self.image.height()))
        self.ratio = self.image.size()
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))
        self.setConnections()

    def setData(self, data):
        self.data = data

    def paintEvent(self, qpaintevent):
        painter = QPainter(self.imageView.pixmap())
        painter.setBrush(QBrush(Qt.red))
        for object in self.data:
            x,y = self.localPos(object.x, object.y)
            painter.drawRect(x-self.area, y-self.area, 2*self.area, 2*self.area)

        painter.setPen(Qt.green)
        for object1 in self.data:
            x1, y1 = self.localPos(object1.x, object1.y)
            neighbors = object1.neighbors
            for neighbor in neighbors:
                for object2 in self.data:
                    if neighbor == object2.id:
                        x2, y2 = self.localPos(object2.x, object2.y)
                        painter.drawLine(x1,y1,x2,y2)


    def setConnections(self):
        self.connect(self.plusButton, SIGNAL("clicked()"), self.onPlusButton)
        self.connect(self.minusButton, SIGNAL("clicked()"), self.onMinusButton)
        self.connect(self.resetButton, SIGNAL("clicked()"), self.onResetButton)
        self.connect(self.saveButton, SIGNAL("clicked()"), self.onSaveButton)

    def onSaveButton(self):
        root = etree.Element("level")
        root.attrib['name']='level1'

        for wayPoint in self.data:
            root.append(wayPoint.serializeToXml())

        handle = etree.tostring(root, pretty_print=True, encoding='utf-8', xml_declaration=True)
        applic = open("./level.xml", "w")
        applic.writelines(handle)
        applic.close()

    def onPlusButton(self):
        self.ratio+=self.zoomingSize
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))
        self.update()

    def onMinusButton(self):
        temp = self.ratio-self.zoomingSize
        if temp.width() > 0 and temp.height() > 0:
            self.ratio-=self.zoomingSize
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))
        self.update()

    def onResetButton(self):
        self.imageView.setPixmap(self.image.scaled(self.size(), Qt.KeepAspectRatio, Qt.FastTransformation))
        self.ratio = self.imageView.pixmap().size()
        self.update()

    def mouseReleaseEvent(self, event):
        super(ImageViewer, self).mouseReleaseEvent(event)
        if event.button() == Qt.LeftButton:
            self.currentObject = None
        if event.button() == Qt.RightButton:
            if self.currentObject != None:
                for object in self.data:
                    if object.id == self.currentObject.id:
                        continue
                    localX, localY = self.getMousePos(event)
                    x, y = self.localPos(object.x, object.y)
                    if x-self.area <= localX <= x+self.area and y-self.area <= localY <= y+self.area:
                        if self.currentObject.id not in object.neighbors:
                            object.neighbors.append(self.currentObject.id)
                            self.currentObject.neighbors.append(object.id)
                        else:
                            object.neighbors.remove(self.currentObject.id)
                            self.currentObject.neighbors.remove(object.id)
                        self.onResetButton()
                        break
                self.currentObject=None

    def mouseMoveEvent(self, event):
        super(ImageViewer, self).mouseMoveEvent(event)
        if event.buttons() == Qt.LeftButton and self.currentObject!= None:
            localX, localY = self.getMousePos(event)
            x, y = self.realPos(localX, localY)
            self.currentObject.x = x
            self.currentObject.y = y
            self.onResetButton()

    def mousePressEvent(self, event):
        super(ImageViewer, self).mousePressEvent(event)
        if (self.imageView.pixmap().rect().contains(self.imageView.mapFromGlobal(event.globalPos()))):
            localX, localY = self.getMousePos(event)
            realX, realY = self.realPos(localX, localY)
            self.posLabel.setText("X: " + str(realX) + " Y: " + str(realY))
            if event.buttons() == Qt.LeftButton:
                for object in self.data:
                    x, y = self.localPos(object.x, object.y)
                    if x-self.area <= localX <= x+self.area and y-self.area <= localY <= y+self.area:
                        self.currentObject = object
                        break
            if event.buttons() == Qt.RightButton:
                for object in self.data:
                    x, y = self.localPos(object.x, object.y)
                    if x-self.area <= localX <= x+self.area and y-self.area <= localY <= y+self.area:
                        self.currentObject = object
                        break

    def mouseDoubleClickEvent(self, event):
        super(ImageViewer, self).mouseDoubleClickEvent(event)
        localX, localY = self.getMousePos(event)
        realX, realY = self.realPos(localX, localY)
        if event.buttons() == Qt.LeftButton:
            id = 0
            found = False
            while not found:
                found = True
                for wayPoint in self.data:
                    if wayPoint.id == id:
                        found = False
                        id += 1    
                        break
            self.data.append(WayPoint(realX, realY, id))
        if event.buttons() == Qt.RightButton:
            for wayPoint in self.data:
                x, y = self.localPos(wayPoint.x, wayPoint.y)
                if x-self.area <= localX <= x+self.area and y-self.area <= localY <= y+self.area:
                    dialog = WayPointEditor(wayPoint, self)
                    ret = dialog.exec_()
                    if QDialog.Rejected == ret:
                        pass
                    elif QDialog.Accepted == ret:
                        pass
                    else:
                        for wp in self.data:
                            if wayPoint.id in wp.neighbors:
                                wp.neighbors.remove(wayPoint.id)
                        self.data.remove(wayPoint)
                    break
        self.onResetButton()
                
    def getMousePos(self, event):
        pos = self.imageView.mapFromGlobal(event.globalPos())
        return pos.x(), pos.y()

    def realPos(self, x, y):
        return (x*self.image.width())/self.imageView.pixmap().width(), \
            (y*self.image.height())/self.imageView.pixmap().height()

    def localPos(self, x, y):
        return (x*self.imageView.pixmap().width())/self.image.width(), \
            (y*self.imageView.pixmap().height())/self.image.height()

class WayPointModel(QAbstractTableModel):
    def __init__(self, wayPoint, parent=None):
        super(WayPointModel, self).__init__(parent)
        self.wayPoint = wayPoint

    def appendRow(self):
        self.wayPoint.objects.append("empty")
        self.reset()

    def removeRow(self, index):
        self.wayPoint.objects.pop(index)
        self.reset()

    def rowCount(self, parent):
        return len(self.wayPoint.objects)

    def columnCount(self, parent):
        return 1

    def data(self, index, role):
        if role == Qt.DisplayRole or role == Qt.EditRole:
            return self.wayPoint.objects[index.row()]

    def setData(self, index, value, role=Qt.EditRole):
        if role == Qt.EditRole:
            self.wayPoint.objects[index.row()] = value
            self.dataChanged.emit(index, index)
            return True
        return False

    def flags(self, index):
        return Qt.ItemIsEnabled | Qt.ItemIsSelectable | Qt.ItemIsEditable 

class WayPointEditor(QDialog, Ui_WayPointEditor):
    DELETE = 2
    def __init__(self, wayPoint, parent=None):
        super(WayPointEditor, self).__init__(parent)
        self.setupUi(self)
        self.wayPoint = wayPoint
        self.currentIndex = None
        self.fillByData()
        self.setConnections()

    def fillByData(self):
        self.id.setText(self.id.text()+str(self.wayPoint.id))
        self.model = WayPointModel(self.wayPoint, self)
        self.objects.setModel(self.model)

    def setConnections(self):
        self.connect(self.okButton, SIGNAL("clicked()"), SLOT("accept()"))
        self.connect(self.deleteButton, SIGNAL("clicked()"), self.onDeleteButton)
        self.connect(self.addObjectButton, SIGNAL("clicked()"), self.onAddObjectButton)
        self.connect(self.deleteObjectButton, SIGNAL("clicked()"), self.onDeleteObjectButton)
        self.connect(self.objects, SIGNAL("clicked(QModelIndex)"), self.setSelection)

    def setSelection(self, index):
        self.currentIndex = index.row()

    def onAddObjectButton(self):
        self.model.appendRow()

    def onDeleteObjectButton(self):
        if self.currentIndex != None:
            self.model.removeRow(self.currentIndex)
        self.currentIndex = None

    def onDeleteButton(self):
        self.done(WayPointEditor.DELETE)

class MainWindow(QMainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        w = ImageViewer(self)

        array=[]
        w.setData(array)

        self.setCentralWidget(w)

if __name__ == '__main__':
    app = QApplication(sys.argv)

    mainWindow = MainWindow()
    mainWindow.show()

    sys.exit(app.exec_())
