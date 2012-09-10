#! /usr/bin/env python

#HOWTO: import class
from org.columba.core.gui.action import AbstractColumbaAction
 
#HOWTO: importing a full package
from org.columba.core.scripting import extensions
 
from javax.swing import JOptionPane
 
#HOWTO: subclass an action
class MyAction(AbstractColumbaAction):
  def __init__(self):
   AbstractColumbaAction.__init__(self,None,"Python HelloWorld")
 
  def actionPerformed(self,event):
   JOptionPane.showMessageDialog(None,"Hello World!")
 
# first of all, set the metadata. This is useful and is displayed in the Macros dialog
scriptObj.setMetadata("Jython Example","Celso Pinto","This is a Jython example script. Doesn't do anything")
 
#now, let's add our action into columba's Utilities menu
menu = extensions.ExtensionPointManager.getInstance().getExtensionPoint(extensions.MenuExtensionPoint.EXTENSION_POINT_ID)
menu.addAction(MyAction(),"menu_utilities","bottom")