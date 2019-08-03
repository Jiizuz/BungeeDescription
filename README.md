# BungeeDescription

A simple BungeeCord Plugin to display a colorful description on Servers List

This Plugin handles the ProxyPingEvent provided by the BungeeCord API to change the default description on Pings, this plugins reads the resourse: 'descriptions.yml' and store the lines in form of List.

The stored descriptions are parsed to use in the Event and displays the colorful description.

Additionally the descriptions can be reloaded after a change in game by using the current command: '/bungeedescription' in the console or in game.

The available colors are parsed by using the prefix: '&' and can be checked here: https://minecraft.gamepedia.com/Formatting_codes

Can use: '%n' too for line separator. (The descriptions can have maxium two lines in Servers List)
