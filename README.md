# Haven

Haven is a super simple plugin that allows you to deny most hostile mob spawn in an area defined by: Haven/NoSpawn/src/main/resources/repellers.list
Format: x,y,z,radius

Add new entries to the list, reload the server plugins.

# Disclaimer!
This is a truly awful plugin that works for my own purposes. I am making this public in case someone else needs this functionality and ideally knows how to code and can change it to their liking. There is currently no easy way to add new repelled zones without manually modifying a file and reloading the server, and the list of denied mobs is hardcoded to the jar itself. It is possible that I will add this functionality as time goes on, but for now, you may have to add it yourself.

# TO DO:
Dynamically add new coordinates, commands.
Add "allowed" zones within denied zones, to allow smaller areas such as mob spawners within a larger denied zone.

Based on Mob Repellent 2.0, a discountinued 1.11 plugin that finally broke (for me) somewhere in 1.18:
https://www.spigotmc.org/resources/mob-repellent-2-0.7196/
