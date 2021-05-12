package run_time;

import java.time.Duration;
import java.time.Instant;

/**
 * A simple helper class to time the running time of other methods
 *
 * Help from this website: https://www.javatpoint.com/java-get-current-date and
 * especially Anila Sebastian's answer here https://stackoverflow.com/q/4927856
 */
public class RunTimeTimer {

    private Instant start;
    private Instant stop;

    public void start() {
        start = Instant.now();
    }

    public void stop() {
        stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println("Run time: " + timeElapsed.toMillis() + "ms");
    }

}
