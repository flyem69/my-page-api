package my.page.api.streams.models;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class Streams {

    @Getter
    private final Set<String> sockets;

    public Streams() {
        sockets = Collections.synchronizedSet(new HashSet<>());
    }

    public boolean addSocket(String streamSocket) {
        return sockets.add(streamSocket);
    }

    public boolean deleteSocket(String streamSocket) {
        return sockets.remove(streamSocket);
    }
}
