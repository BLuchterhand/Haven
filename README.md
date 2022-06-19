# Haven - A simple spawn-control plugin for Minecraft 1.18

Inspired by Mob Repellent 2.0, a discountinued 1.11 plugin that finally broke (for me) somewhere in 1.18:
https://www.spigotmc.org/resources/mob-repellent-2-0.7196/

Haven is a super simple plugin that allows you to deny most hostile mob spawn in an area. The following commands can be used:

/haven create x y z radius

/haven delete x y z radius

/haven list

As of v0.2.0, the following command can be used to create an "allowed" zone that permits spawning in an area that is covered in a spawn-denying haven:

/haven allow x y z radius

PERMISSIONS: Requires "Haven.basics"


# Disclaimer!
This is a super simple plugin that works primarily for my own limited purposes. It is likely inefficient, as it's my first real exposure to Minecraft plugin development and one of my first Java programs. This plugin currently DOES NOT allow you to modify the banned mobs list! This will be an upcoming feature! If you need to modify it for now, you'll have to do so (rather easily) in the code and build it yourself.

