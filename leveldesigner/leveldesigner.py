
import sys

import PyQt4

from PyQt4.QtGui import *
from PyQt4.QtCore import *

from ImageViewer import Ui_ImageViewer
from ObjectBrowser import Ui_ObjectBrowser

class Object():
    def __init__(self, x, y, id):
        self.langle = 0.234
        self.rangle = 0.234
        self.x = x
        self.y = y
        self.isThrowable = False
        self.neighbors = [1,2,3]
        self.id = id
        self.objects = ["hello", "bomb"]

class Model(QAbstractTableModel):
    TRACK_ID = 0
    TRACK_LANGLE = 1
    TRACK_RANGLE = 2
    TRACK_X = 3
    TRACK_Y = 4
    TRACK_IS_THROWABLE = 5

    def __init__(self, array, parent=None):
        super(Model, self).__init__(parent)
        self.array = array

    def rowCount(self, parent):
        return len(self.array)

    def columnCount(self, parent):
        return 6

    def data(self, index, role):
        if role == Qt.DisplayRole or role == Qt.EditRole:
            if index.column() == Model.TRACK_ID:
                return self.array[index.row()-1].id
            if index.column() == Model.TRACK_X:
                return self.array[index.row()-1].x
            if index.column() == Model.TRACK_Y:
                return self.array[index.row()-1].y
            if index.column() == Model.TRACK_LANGLE:
                return self.array[index.row()-1].langle
            if index.column() == Model.TRACK_RANGLE:
                return self.array[index.row()-1].rangle
            if index.column() == Model.TRACK_IS_THROWABLE:
                return self.array[index.row()-1].isThrowable
        return None

    def setData(self, index, value, role=Qt.EditRole):
        if role == Qt.EditRole:
            if index.column() == Model.TRACK_ID:
                self.array[index.row()-1].id = value
            if index.column() == Model.TRACK_X:
                self.array[index.row()-1].x = value
            if index.column() == Model.TRACK_Y:
                self.array[index.row()-1].y = value
            if index.column() == Model.TRACK_LANGLE:
                self.array[index.row()-1].langle = value
            if index.column() == Model.TRACK_RANGLE:
                self.array[index.row()-1].rangle = value
            if index.column() == Model.TRACK_IS_THROWABLE:
                self.array[index.row()-1].isThrowable = value
            self.dataChanged.emit(index, index)
            return True
        return False

    def flags(self, index):
        return Qt.ItemIsEnabled | Qt.ItemIsSelectable | Qt.ItemIsEditable

class ObjectsModel(QAbstractTableModel):
    def __init__(self, array, parent=None):
        super(ObjectsModel, self).__init__(parent)
        self.array = array
        self.index = -1

    def setIndex(self, index):
        self.index = index
        self.reset()

    def rowCount(self, parent):
        if self.index < 0:
            return 0
        return len(self.array[self.index].objects)

    def columnCount(self, parent):
        return 1

    def data(self, index, role):
        if role == Qt.DisplayRole or role == Qt.EditRole:
            return self.array[self.index].objects[index.row()]

    def setData(self, index, value, role=Qt.EditRole):
        if role == Qt.EditRole:
            self.array[self.index].objects[index.row()] = value
            self.dataChanged.emit(index, index)
            return True
        return False

    def flags(self, index):
        return Qt.ItemIsEnabled | Qt.ItemIsSelectable | Qt.ItemIsEditable 

class NeighborsModel(QAbstractTableModel):
    def __init__(self, array, parent=None):
        super(NeighborsModel, self).__init__(parent)
        self.array = array
        self.index = -1

    def setIndex(self, index):
        self.index = index
        self.reset()

    def rowCount(self, parent):
        if self.index < 0:
            return 0
        return len(self.array[self.index].neighbors)

    def columnCount(self, parent):
        return 1

    def data(self, index, role):
        if role == Qt.DisplayRole or role == Qt.EditRole:
            return self.array[self.index].neighbors[index.row()]

    def setData(self, index, value, role=Qt.EditRole):
        if role == Qt.EditRole:
            self.array[self.index].neighbors[index.row()] = value
            self.dataChanged.emit(index, index)
            return True
        return False

    def flags(self, index):
        return Qt.ItemIsEnabled | Qt.ItemIsSelectable | Qt.ItemIsEditable 

class ImageViewer(QWidget, Ui_ImageViewer):
    def __init__(self, parent=None):
        super(ImageViewer, self).__init__(parent)
        self.setupUi(self)
        self.init()

    def init(self):
        self.zoomingSize = QSize(100,100)
        self.image = QPixmap("./background-full-new.png")
        self.debugLabel.setText("Width: " + str(self.image.width()) + " Height: " + str(self.image.height()))
        self.ratio = self.image.size()
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))
        self.setConnections()

    def setConnections(self):
        self.connect(self.plusButton, SIGNAL("clicked()"), self.onPlusBotton)
        self.connect(self.minusButton, SIGNAL("clicked()"), self.onMinusButton)
        self.connect(self.resetButton, SIGNAL("clicked()"), self.onResetButton)

    def onPlusBotton(self):
        self.ratio+=self.zoomingSize
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))

    def onMinusButton(self):
        self.ratio-=self.zoomingSize
        self.imageView.setPixmap(self.image.scaled(self.ratio, Qt.KeepAspectRatio, Qt.FastTransformation))

    def onResetButton(self):
        self.imageView.setPixmap(self.image.scaled(self.size(), Qt.KeepAspectRatio, Qt.FastTransformation))
        self.ratio = self.imageView.pixmap().size()

    def mousePressEvent(self, event):
        super(ImageViewer, self).mousePressEvent(event)
        if (self.imageView.pixmap().rect().contains(self.imageView.mapFromGlobal(event.globalPos()))):
            pos = self.imageView.mapFromGlobal(event.globalPos())
            localX = (pos.x()*self.image.width())/self.imageView.pixmap().width()
            localY = (pos.y()*self.image.height())/self.imageView.pixmap().height()
            self.posLabel.setText("X: " + str(localX) + " Y: " + str(localY))

class ObjectBrowser(QDockWidget, Ui_ObjectBrowser):
    def __init__(self, parent=None):
        super(ObjectBrowser, self).__init__(parent)
        self.setupUi(self)
        self.sellectedRow = None
        self.dataMapper = QDataWidgetMapper()
        self.setConnections()

    def setConnections(self):
        self.connect(self.listView, SIGNAL("clicked(QModelIndex)"), self.setSelection)

    def setSelection(self, current):
        self.dataMapper.setCurrentModelIndex(current)
        self.objectsModel.setIndex(current.row())
        self.neighborsModel.setIndex(current.row())

    def setModel(self, array):
        model = Model(array, self)
        self.objectsModel = ObjectsModel(array, self)
        self.neighborsModel = NeighborsModel(array, self)
        
        self.listView.setModel(model)
        self.objects.setModel(self.objectsModel)
        self.neighbors.setModel(self.neighborsModel)

        self.dataMapper.setModel(model)
        self.dataMapper.addMapping(self.xLine, Model.TRACK_X)
        self.dataMapper.addMapping(self.yLine, Model.TRACK_Y)
        self.dataMapper.addMapping(self.laLine, Model.TRACK_LANGLE)
        self.dataMapper.addMapping(self.raLine, Model.TRACK_RANGLE)
        self.dataMapper.addMapping(self.idLine, Model.TRACK_ID)
        self.dataMapper.addMapping(self.isThrowable, Model.TRACK_IS_THROWABLE)

class MainWindow(QMainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        w = ImageViewer(self)
        dock = ObjectBrowser(self)
        self.setCentralWidget(w)
        self.addDockWidget(Qt.RightDockWidgetArea, dock)

        array = []
        array.append(Object(1,2,3))
        array.append(Object(1,2,4))
        array.append(Object(1,2,5))
        array.append(Object(1,2,6))
        array.append(Object(1,2,7))
        array.append(Object(1,2,8))

        dock.setModel(array)

if __name__ == '__main__':
    app = QApplication(sys.argv)

    mainWindow = MainWindow()
    mainWindow.show()

    sys.exit(app.exec_())
