# IngotWarn
IngotWarn is a small warn-based plugin to help track what rules players have broken, what players have done, or any reason you desire. 
You can configure a max amount of warns and in the future, execute commands on a given amount of warns.

FEATURES:

- Warn a player
- Set a limit to the amount of warns a player can have
- Configure output messages with laguage.yml
- Check your own warns with a command
- Check others' warns with the command above and a separate permission
- Manage player's files within the game, no need to edit playerdata.yml

COMMANDS:

/warn (player) (reason) - Warn a designated player. Works for both online and Offline players
/checkwarns [player] - Check your warns. if a user is specified, it will check their warns (separate permission node for that!)
/adminwarn (command) - Admin management commands for IngotWarn
- clear (player) - Clear a given player's userfile
- delete (player) (index) - Delete a given warn for a player.
- edit (player) (index) (reason) - Edit a given warn for a player
- version - Check plugin version
- reload - Reload the plugin

PERMISSIONS:

- ingotwarn.warn - gives access to the /warn command
- ingotwarn.checkwarns - gives access to the /checkwarns command
- ingotwarn.checkwarns.others - gives access to checking other players within the /checkwarns command
- ingotwarn.adminwarn - gives access to the /adminwarn command

PLANNED FEATURES:

- Alert players that were warned offline when they rejoin
- Execute commands on a given warn amount
- Check if player changed their username and copy over their data if they have
