Ext.ns('corux.webconfig');

corux.webconfig.ConfigPanel = Ext.extend(Ext.form.FormPanel, {
  okText : 'Ok',
  loadText : 'Load',
  connectingText : 'Connecting',
  statusText : 'Status',
  failedText : 'Save failed!',
  loadFailedText : 'Loading configuration failed!',
  waitMsgText : 'Sending data...',
  confirmTitle : 'Confirm save',
  confirmText : 'Do you really want to save? Saving an invalid configuration will break your repository.',

  initComponent : function() {
    var title;
    switch (this.item.type) {
    case 'git':
      title = '.git/config';
      break;
    case 'hg':
      title = '.hg/hgrc';
      break;
    case 'svn':
      title = 'conf/svnserve.conf';
      break;
    }
    var loadInitialized = false;
    var config = {
      title : title,
      id : 'webconfigForm',
      url : restUrl + 'plugins/webconfig.json',
      baseParams : {
        'repository-id' : this.item.id
      },
      layout : {
        type : 'vbox',
        align : 'stretch'
      },
      items : [ {
        xtype : 'textarea',
        name : 'content',
        hideLabel : true,
        flex : 1
      } ],
      buttons : [ {
        text : this.okText,
        formBind : true,
        scope : this,
        handler : this.saveHandler
      }, {
        text : this.loadText,
        formBind : false,
        scope : this,
        handler : this.loadHandler
      } ],
      show : function() {
        if (!loadInitialized) {
          loadInitialized = true;
          this.loadHandler();
        } else {
          this.removeClass('x-hide-display');
        }
      }
    };

    Ext.apply(this, Ext.apply(this.initialConfig, config));
    corux.webconfig.ConfigPanel.superclass.initComponent.apply(this, arguments);
  },

  loadHandler : function() {
    var self = this;
    this.getForm().load({
      method : 'GET',
      waitMsg : this.waitMsgText,
      failure : function() {
        if (debug) {
          console.debug(self.loadFailedText);
        }
        Ext.MessageBox.show({
          title : self.statusText,
          msg : self.loadFailedText,
          buttons : Ext.MessageBox.OK,
          icon : Ext.MessageBox.ERROR
        });
      }
    });
  },

  saveHandler : function() {
    var self = this;
    Ext.MessageBox.confirm(this.confirmTitle, this.confirmText, function(button) {
      if (button !== 'yes') {
        return;
      }
      self.getForm().submit({
        scope : self,
        method : 'POST',
        waitTitle : self.connectingText,
        waitMsg : self.waitMsgText,

        failure : function() {
          if (debug) {
            console.debug(self.failedText);
          }
          Ext.MessageBox.show({
            title : self.statusText,
            msg : self.failedText,
            buttons : Ext.MessageBox.OK,
            icon : Ext.MessageBox.ERROR
          });
        }
      });
    });
  }
});

Ext.reg('webConfigPanel', corux.webconfig.ConfigPanel);

Sonia.repository.openListeners.push(function(repository, panels) {
  if (admin) {
    switch (repository.type) {
    case 'git':
    case 'hg':
    case 'svn':
      panels.push({
        item : repository,
        xtype : 'webConfigPanel'
      });
      break;
    default:
      break;
    }
  }
});
