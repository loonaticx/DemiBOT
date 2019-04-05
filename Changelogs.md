#DemiBOT Change Logs
**WARNING!!**
*DemiBOT is still undergoing alpha production. Some features may not be complete and could possibly be a bit buggy. If you'd like to contribute or send me a bug report, you can DM me on Discord @ loonatic#1337 . Please note that not everything is final, and could possibly change in the next update!*

##*Current Version:* 1.2.7 ALPHA
### 1.2.7 ALPHA Notes - 4/4/2019 
####"The Universe Expands"
* +Implemented ``Debug Mode`` (WIP) into the configuration. Check below for more information. **WARNING: UNFINISHED!** x
* +Fixed major bug regarding running the discord bot on a headless instance. Will force java.awt to set "headless" to "false" even if the computer is actually headless. If you're still having a problem running on a headless Linux device, I recommend running `export DISPLAY=:0` and then restarting the bot. More information coming soon.
* +Added ASCII header art. You can view the source in ``/resources/titleart`` and ``/resources/setupart``.
* +Implemented a "Debug" mode into the configuration file. Debug mode would only be used for diagnosing and investigating bugs. With Debug mode enabled, messages and other information will be outputted from the DemiBOT console window. If you're having issues starting up DemiBOT, make sure ``"DebugMode":`` exists in ``botconfig.json`` and is rather set to ``true`` or ``false``. (Don't worry, I'll eventually make a better fix for this later :p)
* +Implemented changelogs! Older changelogs coming soon :)
* -Removed ``/resources/asciiart``.

###1.2.6 ALPHA Notes - 3/23/2019
####"The Config Update v0.5"
* +Implemented more features into ``botconfig.json``!! ``botconfig`` is loaded with new customizable options, including:
    * ``ServerID`` (WIP) Server ID where the bot will listen into. **WARNING: UNFINISHED AND NOT WORKING!** x 
    * ``OwnerID`` The Bot Owner's ID. The bot by default will only listen to it's owner.
    * ``UserIDs`` (WIP) Users that the bot will listen to. **WARNING: UNFINISHED AND NOT WORKING!** x
* +The Discord bot will automatically make a new ``botconfig.json`` if it doesn't exist already or is null.
* +Currently working on a first time setup feature! TBA and WIP.     
* ~Tweaked ``CMDCommand.java`` for multi/cross-platform support.
