package railsRoadApplication.Util;

import static java.lang.System.exit;

public class RouteParser {
    char from, to;
    Long distance;

    RouteParser() {
    }

    public Long convertToLong(String input) {
        long ret = 0;
        for (int i = input.length() - 1, j = 0; i >= 0; i--, j++) {
            if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                System.out.print("Error Parsing Input");
                ;
                exit(1);
            }
            ret += Math.pow(10.0, j * 1.0) * (input.charAt(i) - '0');
        }
        return ret;
    }

    public char getFrom() {
        return from;
    }

    public char getTo() {
        return to;
    }

    public long getDistance() {
        return distance;
    }

    public RouteParser(String route) {
        if (route.charAt(0) < 'A' || route.charAt(0) > 'Z' || route.charAt(1) < 'A' || route.charAt(1) > 'Z') {
            System.out.println("Error Parsing Input");
            exit(1);
        }
        from = route.charAt(0);
        to = route.charAt(1);
        distance = convertToLong(route.substring(2));
    }
}
