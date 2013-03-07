# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ImageViewer.ui'
#
# Created: Wed Mar  6 19:55:10 2013
#      by: PyQt4 UI code generator 4.7.2
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

class Ui_ImageViewer(object):
    def setupUi(self, ImageViewer):
        ImageViewer.setObjectName("ImageViewer")
        ImageViewer.resize(800, 600)
        ImageViewer.setMinimumSize(QtCore.QSize(800, 600))
        self.verticalLayout_2 = QtGui.QVBoxLayout(ImageViewer)
        self.verticalLayout_2.setObjectName("verticalLayout_2")
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.plusButton = QtGui.QPushButton(ImageViewer)
        self.plusButton.setObjectName("plusButton")
        self.horizontalLayout.addWidget(self.plusButton)
        self.minusButton = QtGui.QPushButton(ImageViewer)
        self.minusButton.setObjectName("minusButton")
        self.horizontalLayout.addWidget(self.minusButton)
        self.resetButton = QtGui.QPushButton(ImageViewer)
        self.resetButton.setObjectName("resetButton")
        self.horizontalLayout.addWidget(self.resetButton)
        self.saveXmlButton = QtGui.QPushButton(ImageViewer)
        self.saveXmlButton.setObjectName("saveXmlButton")
        self.horizontalLayout.addWidget(self.saveXmlButton)
        self.openXmlButton = QtGui.QPushButton(ImageViewer)
        self.openXmlButton.setObjectName("openXmlButton")
        self.horizontalLayout.addWidget(self.openXmlButton)
        self.openBackgroundButton = QtGui.QPushButton(ImageViewer)
        self.openBackgroundButton.setObjectName("openBackgroundButton")
        self.horizontalLayout.addWidget(self.openBackgroundButton)
        spacerItem = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem)
        self.posLabel = QtGui.QLabel(ImageViewer)
        self.posLabel.setObjectName("posLabel")
        self.horizontalLayout.addWidget(self.posLabel)
        self.debugLabel = QtGui.QLabel(ImageViewer)
        self.debugLabel.setText("")
        self.debugLabel.setObjectName("debugLabel")
        self.horizontalLayout.addWidget(self.debugLabel)
        self.verticalLayout_2.addLayout(self.horizontalLayout)
        self.scrollArea = QtGui.QScrollArea(ImageViewer)
        self.scrollArea.setHorizontalScrollBarPolicy(QtCore.Qt.ScrollBarAsNeeded)
        self.scrollArea.setWidgetResizable(True)
        self.scrollArea.setObjectName("scrollArea")
        self.scrollAreaWidgetContents = QtGui.QWidget(self.scrollArea)
        self.scrollAreaWidgetContents.setGeometry(QtCore.QRect(0, 0, 780, 544))
        self.scrollAreaWidgetContents.setObjectName("scrollAreaWidgetContents")
        self.verticalLayout = QtGui.QVBoxLayout(self.scrollAreaWidgetContents)
        self.verticalLayout.setObjectName("verticalLayout")
        spacerItem1 = QtGui.QSpacerItem(20, 40, QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Expanding)
        self.verticalLayout.addItem(spacerItem1)
        self.imageView = QtGui.QLabel(self.scrollAreaWidgetContents)
        self.imageView.setText("")
        self.imageView.setObjectName("imageView")
        self.verticalLayout.addWidget(self.imageView)
        spacerItem2 = QtGui.QSpacerItem(20, 40, QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Expanding)
        self.verticalLayout.addItem(spacerItem2)
        self.scrollArea.setWidget(self.scrollAreaWidgetContents)
        self.verticalLayout_2.addWidget(self.scrollArea)

        self.retranslateUi(ImageViewer)
        QtCore.QMetaObject.connectSlotsByName(ImageViewer)

    def retranslateUi(self, ImageViewer):
        ImageViewer.setWindowTitle(QtGui.QApplication.translate("ImageViewer", "ImageViewer", None, QtGui.QApplication.UnicodeUTF8))
        self.plusButton.setText(QtGui.QApplication.translate("ImageViewer", "+", None, QtGui.QApplication.UnicodeUTF8))
        self.minusButton.setText(QtGui.QApplication.translate("ImageViewer", "-", None, QtGui.QApplication.UnicodeUTF8))
        self.resetButton.setText(QtGui.QApplication.translate("ImageViewer", "Reset", None, QtGui.QApplication.UnicodeUTF8))
        self.saveXmlButton.setText(QtGui.QApplication.translate("ImageViewer", "Save to xml", None, QtGui.QApplication.UnicodeUTF8))
        self.openXmlButton.setText(QtGui.QApplication.translate("ImageViewer", "Open xml", None, QtGui.QApplication.UnicodeUTF8))
        self.openBackgroundButton.setText(QtGui.QApplication.translate("ImageViewer", "Open background", None, QtGui.QApplication.UnicodeUTF8))
        self.posLabel.setText(QtGui.QApplication.translate("ImageViewer", "X: 0 Y: 0", None, QtGui.QApplication.UnicodeUTF8))

