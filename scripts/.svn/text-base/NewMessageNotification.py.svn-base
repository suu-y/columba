#
# This script declares an action that, upon message receival, 
# will show a message in the screen
# 
# This is an example of an event handler
# 
# WARNING: it can get pretty annoying so remember to test it with a single
# email and then remove the script :-)
#
# you have been warned!

from org.columba.mail.facade import IFolderFacade
from org.columba.mail.folder.event import IFolderListener
from org.columba.core.services import ServiceRegistry
from javax.swing import JOptionPane

class FolderListener(IFolderListener):
  def __init__(self):
    pass

  def messageAdded(self,event):
    JOptionPane.showMessageDialog(None,"Message Received!")

  def messageRemoved(self,event):
    pass
  def messageFlagChanged(self,event):
    pass
  def folderPropertyChanged(self,event):
    pass
  def folderAdded(self,event):
    pass
  def folderRemoved(self,event):
    pass


facade = ServiceRegistry.getInstance().getService(IFolderFacade)
inbox = facade.getLocalInboxFolder()
inbox.addFolderListener(FolderListener())

scriptObj.setMetadata("Signal handler ex.","Celso Pinto","When a message is received, a dialog pops up")
print "good"