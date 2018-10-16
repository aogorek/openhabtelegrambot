# openhabtelegrambot

This is an application to control OpenHab instance with Telegram BOT.
More details, instruction of use, and a demo on my blog:

https://regulargeek.blogspot.com/2018/06/control-openhab-using-telegram-messenger.html

# Modifications in this fork
 - changed message formatting for better readability
 - improved a bit upon security
 - the bot can now be limited to only talk to defined chat IDs
 - the bot can now be limited to control only defined openHAB items (regular expressions possible; check regex101.com)
 
## application.properties

Here are some examples on how to use the new attributes __allowedChatIDs__ and __allowedItems__. Please remember you HAVE to define them for __openhabtelegrambot__ to run.

```
// allow everyone to control/use the bot
allowedChatIDs=
allowedChatIDs=*

// allow only a single chat instance to control/use the bot
allowedChatIDs=1234

// allow several chat instances to control/use the bot
allowedChatIDs=1234,5678

// allow all items to be controlled through the bot
allowedItems=
allowedItems=*

// allow only a single item to be controlled through the bot
allowedItems=switchLightLivingRoom

// allow multiple items to be controlled through the bot
allowedItems=switchLightLivingRoom,switchLightBedRoom

// allow multiple items to be controlled through the bot (with regular expressions)
allowedItems=switchLight.*
allowedItems=switchLight.*,switchPowerOutlet.*
```
