package client;

public class ClientProtocol {

    final private String welcome = "Countdown 0.9 - Welcome";
    final private String from = "Countdown from ";
    final private String number = "\\d+";
    final private String start = "Starting Countdown from ";
    final private String buy = "Countdown finished - Bye";

    public String getWelcome() {
        return welcome;
    }

    public String createFrom(int number){
        return from + number;
    }

    public String getNumber() {
        return number;
    }

    public String getStart() {
        return start;
    }

    public String getBuy() {
        return buy;
    }

    public int extractNumber(String from){
        return Integer.parseInt(from.substring(from.lastIndexOf((' ') + 1)));
    }

    public boolean isWelcomeMessage(String welcome){
        return welcome.contains("Welcome");
    }

    public boolean isByeMessage(String welcome){
        return welcome.contains("Bye");
    }

    public boolean isStartMessage(String from){
        return welcome.contains(start);
    }

}
