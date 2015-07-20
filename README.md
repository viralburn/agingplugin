#Aging Plugin

This is a plugin for the Atlassian Confluence WIKI. http://www.oblivion.ch/aging-plugin/

Allows pages to be marked as outdated if not edited within a configured period of time. This is not under active development as this was created for a specific use case, but may be useful to others. 

#Usage

Global Configurations

The global configurations can be found under : Confluence Admin -> Aging Plugin Configuration 
- Period Type : Set the period type to use to check for outdated pages
- Period Amount : Set the number days/months/years as elapsed time since the last modification
- Ignore Label : Set a label to be used on pages that should not be marked (eg. unchanging documentation)


Space Configurations

The Space configurations can be found under : Space Admin -> Addons -> Aging Plugin Configuration. 
These changes are specific to the space. 
- Use default : If set, will ignore any settings on this page and will use the global configuration
- Period Type : Set the period type to use to check for outdated pages
- Period Amount : Set the number days/months/years as elapsed time since the last modification


#SDK Commands

Here are the SDK commands you'll use immediately:

* atlas-run   -- installs this plugin into the product and starts it on localhost
* atlas-debug -- same as atlas-run, but allows a debugger to attach at port 5005
* atlas-cli   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running product instance
* atlas-help  -- prints description for all commands in the SDK

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
