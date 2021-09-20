package server;

public class ServerProtocol {

    final private String welcome = "Countdown 0.9 - Welcome";
    final private String from = "Countdown from ";
    final private String number = "\\d+";
    final private String start = "Starting Countdown from ";
    final private String buy = "Countdown finished - Bye";

    public String getWelcome() {
        return welcome;
    }

    public boolean isValidFrom(String from) {
        return from.matches(this.from + number);
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

    public int extractNumber(String from) {
        return Integer.parseInt(from.substring(from.lastIndexOf(' ') + 1));
    }
}
