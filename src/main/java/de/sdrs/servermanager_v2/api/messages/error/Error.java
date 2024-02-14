package de.sdrs.servermanager_v2.api.messages.error;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface Error {
    public Error Command(CommandSender sender, String usage);
    public Error PlayerNotFound(CommandSender sender, String playerName);
    public Error PlayerNotFound(String playerName);
    public Error PlayerNotOnline(CommandSender sender, String playerName);
    public Error needToBePlayer(CommandSender sender);
    public Error Generic(String... msg);
    public Error TargetUnavailable(CommandSender sender);
    public Error TargetWorldUnavailable(CommandSender sender);
    public Error NotWarpOwner(Player player);
    public Error WarpExists(Player player);
}
