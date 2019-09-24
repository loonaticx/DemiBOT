Hi! I'm Loonatic. I've been working on this HUGE project for several months now -- since December 2018. I've worked on it with collaboration from Disyer, or darktohka, and it's become one of the biggest projects I've made as a semi-novice programmer.

DemiBOT is currently in alpha, and still going through a complete work-in-progress phase. Although, I'm confident enough to say that DemiBOT is stable enough for me to display it around!

# So, what is DemiBOT exactly?

As what was mentioned in the title, it's a *open-source* Java Discord bot designed to give specified users the ability to remotely control the machine of where DemiBOT is being ran from.

Some features + commands include (but are definitely not limited to):

*Assuming we have a display render:*

* ;ss --> Screenshots the screen, and then uploads it to Discord.
* ;lc --> Left click.
* ;mm 0, 0 --> Moves the mouse \[x\] amount horizontally, and \[y\] amount vertically.
* ;t message --> Types "message" (like if it were simulating a keyboard) on DemiBOT's host machine.

*And if we don't:*

* ;cmd --> Remote command prompt/terminal commands.
* ;upload --> Uploads a file (less than 8MB) onto Discord.
* ;sysinfo --> Displays system information on the host machine.
* ;run --> Runs remote applications.

# GitHub Repository

DemiBOT is aimed to be completely open source for the public.

***It is still in alpha, so bugs and missing features are still being worked on!***

[https://github.com/toontownloony/DemiBOT/](https://github.com/toontownloony/DemiBOT/)

# Usages for DemiBOT

DemiBOT is great for ""psuedo-remote"" SSHing to computers when you aren't able to directly SSH to the host machine.  With ;cmd, you are able to run simple, lightweight commands (such as ;cmd sudo apt update && sudo apt upgrade) on the go.

DemiBOT can also be used for checking the status of a computer, checking what's being displayed, doing any minor graphical changes (such as opening chrome to check something...), and so on.

# Security

With great power, comes great responsibility. Currently, I've implemented multiple configurations (and checks) to ensure only the right people have access to control DemiBOT. In botconfig.json, you are able to config and decide which channels, servers, and users DemiBOT will listen to. I'm planning to add more security checks in the future, just to ensure that the platform isn't completely responsible for any... "accidents"...

# The Future of DemiBOT

As previously stated, I'm still a novice at programming and work on DemiBOT when school isn't kicking into my free time. I'm planning to soon ***document the heck out of my code***, implement more shortcut commands, fix a few bugs here and there, etc. Eventually, I'm aiming to get to a point where DemiBOT can become a framework to have a computer remote-controller for many chat platforms -- but that's a goal for the near future :).

# Conclusion

I don't know how many people actually read through these entire things, but if you did, I appreciate it a lot!!! I still have many features to implement and fix into DemiBOT, but it's been my coolest project I've worked on for months. I hope you found this concept interesting!
This document was extracted from a Reddit post I made a while ago.
