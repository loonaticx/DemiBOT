Todo:
hug erica when possible?

add checks for robot when computer is headless

Debug mode - some progress done

Demibot version --> yeah, need to change compile now

first time setup -

hold down left click

double left click

copy and paste clipboard

;k shortcuts (select all, cut all, paste, above)

;win command? like ;win run, windows hotkeys?

;ctrlaltdelete

restart bot, restart server, shutdown bot, shutdown server, 

can we make it where we can change the config from messaging the bot?

bugfix or q and a or something like faq

show screenshot grid


refresh / f5

file menu / alt+f

edit menu / alt e

view menu / alt + v

cut / ctrl + x

new window / ctrl + n

new folder / ctrl + shift + n

windows key / rwin

showdesktop / rwin + d

minimize all / rwin + m

task man / ctrl + shift + esc

chrome / start chrome

rename / f2 

open // enter / enter

switch next / alt+esc

switch prev / shift+alt+esc

run / rwin+r

search / ctrl+f

new tab / ctrl+t

close tab / ctrl + w

close // closewindow / alt+f4

select all / ctrl+a

copy / ctrl + c

paste / ctrl + v

undo / ctrl + z


webshot 

gitpush command

wget


expl? for like restarting explorer.exe
wget = get = download
downloads = list all files in /downloads/ directory
wipedowns = wipes all downloads in /downloads/ directory
rapid screnshot = rapidly sreenshots screen 
record gif
record mp4 (broken) 
config setting to change playing status type, or twitch redirect

put down newer commmands in ;help
config setting to listen to only specific roles and channels

Noted bugs:
demibot does not work with custom aliases on linux
roles broken rn


Completed:

xxx
5/4/19 --> Add thread pool - this means that DemiBot can run 8 commands at the same time, if they take a lot of time.
DemiBot now has a limit on how many concurrent commands a user can run: by default, only 1 concurrent command can be ran per user (this can be changed in config)
Fix command count HashMap with a ConcurrentHashMap to protect against race conditions
DemiBOT Has a 5 second timeout now to cmdcommand -- it will immediately stop the process after 5 seconds and let the user know, although this can be configured in the botconfig.json
Upload File command
4/12/19 --> custom prefix can be set in botsettings json
4/23/19 --> made sure ;cmd commands will only output when debug mode is on
option made to set custom channels where only demibot will listen in. if you have "any" in the config, it will listen to any channel.
4/24/19 --> Double left click command

4/10/19 --> When I make a change to config, it will crash if the config doesn't have a specific parameter. We need to make it so if it doesn't find that field, it'll make a new one instead. Don't get it mixed up with replacing the entire config with the default config,
Fix multiple user in config
multiple owners added
multiple servers added

4/9/19 --> migrated generated image save path and whatever to ./output, customized config again so you can change the output file paths to whatever you likem 
fixed debug permissions, 
change status of discord bot
put config for custom filepath name

4/6/19 --> CMD command works now on a completely headless server. Doesn't require java.awt to run now.


4/4/19 --> Finished major bug regarding running the discord bot on a headless instance. Will force java.awt to set "headless" to "false" even if the computer is actually headless.

Added a "Debug" mode in configuration. 

Added title art. 

Added changelogs!