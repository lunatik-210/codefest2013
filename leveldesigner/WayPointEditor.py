# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'WayPointEditor.ui'
#
# Created: Fri Mar  8 18:01:43 2013
#      by: PyQt4 UI code generator 4.7.2
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

class Ui_WayPointEditor(object):
    def setupUi(self, WayPointEditor):
        WayPointEditor.setObjectName("WayPointEditor")
        WayPointEditor.resize(541, 496)
        self.verticalLayout_2 = QtGui.QVBoxLayout(WayPointEditor)
        self.verticalLayout_2.setObjectName("verticalLayout_2")
        self.id = QtGui.QLabel(WayPointEditor)
        self.id.setObjectName("id")
        self.verticalLayout_2.addWidget(self.id)
        self.verticalLayout = QtGui.QVBoxLayout()
        self.verticalLayout.setObjectName("verticalLayout")
        self.objects = QtGui.QListView(WayPointEditor)
        self.objects.setObjectName("objects")
        self.verticalLayout.addWidget(self.objects)
        self.horizontalLayout_2 = QtGui.QHBoxLayout()
        self.horizontalLayout_2.setObjectName("horizontalLayout_2")
        self.deleteObjectButton = QtGui.QPushButton(WayPointEditor)
        self.deleteObjectButton.setObjectName("deleteObjectButton")
        self.horizontalLayout_2.addWidget(self.deleteObjectButton)
        self.addObjectButton = QtGui.QPushButton(WayPointEditor)
        self.addObjectButton.setObjectName("addObjectButton")
        self.horizontalLayout_2.addWidget(self.addObjectButton)
        spacerItem = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_2.addItem(spacerItem)
        self.verticalLayout.addLayout(self.horizontalLayout_2)
        self.verticalLayout_2.addLayout(self.verticalLayout)
        self.groupBox = QtGui.QGroupBox(WayPointEditor)
        self.groupBox.setMinimumSize(QtCore.QSize(0, 0))
        self.groupBox.setObjectName("groupBox")
        self.horizontalLayout_3 = QtGui.QHBoxLayout(self.groupBox)
        self.horizontalLayout_3.setObjectName("horizontalLayout_3")
        self.verticalLayout_4 = QtGui.QVBoxLayout()
        self.verticalLayout_4.setObjectName("verticalLayout_4")
        self.x = QtGui.QLabel(self.groupBox)
        self.x.setObjectName("x")
        self.verticalLayout_4.addWidget(self.x)
        self.y = QtGui.QLabel(self.groupBox)
        self.y.setObjectName("y")
        self.verticalLayout_4.addWidget(self.y)
        self.isThrowable = QtGui.QCheckBox(self.groupBox)
        self.isThrowable.setObjectName("isThrowable")
        self.verticalLayout_4.addWidget(self.isThrowable)
        self.horizontalLayout_3.addLayout(self.verticalLayout_4)
        self.verticalLayout_2.addWidget(self.groupBox)
        spacerItem1 = QtGui.QSpacerItem(20, 40, QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Expanding)
        self.verticalLayout_2.addItem(spacerItem1)
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName("horizontalLayout")
        spacerItem2 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem2)
        self.deleteButton = QtGui.QPushButton(WayPointEditor)
        self.deleteButton.setObjectName("deleteButton")
        self.horizontalLayout.addWidget(self.deleteButton)
        self.okButton = QtGui.QPushButton(WayPointEditor)
        self.okButton.setObjectName("okButton")
        self.horizontalLayout.addWidget(self.okButton)
        self.verticalLayout_2.addLayout(self.horizontalLayout)

        self.retranslateUi(WayPointEditor)
        QtCore.QMetaObject.connectSlotsByName(WayPointEditor)

    def retranslateUi(self, WayPointEditor):
        WayPointEditor.setWindowTitle(QtGui.QApplication.translate("WayPointEditor", "WayPointEditor", None, QtGui.QApplication.UnicodeUTF8))
        self.id.setText(QtGui.QApplication.translate("WayPointEditor", "Id:", None, QtGui.QApplication.UnicodeUTF8))
        self.deleteObjectButton.setText(QtGui.QApplication.translate("WayPointEditor", "Delete", None, QtGui.QApplication.UnicodeUTF8))
        self.addObjectButton.setText(QtGui.QApplication.translate("WayPointEditor", "Add", None, QtGui.QApplication.UnicodeUTF8))
        self.groupBox.setTitle(QtGui.QApplication.translate("WayPointEditor", "Properties", None, QtGui.QApplication.UnicodeUTF8))
        self.x.setText(QtGui.QApplication.translate("WayPointEditor", "X:", None, QtGui.QApplication.UnicodeUTF8))
        self.y.setText(QtGui.QApplication.translate("WayPointEditor", "Y:", None, QtGui.QApplication.UnicodeUTF8))
        self.isThrowable.setText(QtGui.QApplication.translate("WayPointEditor", "Is throwable", None, QtGui.QApplication.UnicodeUTF8))
        self.deleteButton.setText(QtGui.QApplication.translate("WayPointEditor", "Delete", None, QtGui.QApplication.UnicodeUTF8))
        self.okButton.setText(QtGui.QApplication.translate("WayPointEditor", "Ok", None, QtGui.QApplication.UnicodeUTF8))

