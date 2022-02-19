package my.page.api.streams.endpoints;

import my.page.api.streams.models.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/streams")
public class StreamsEndpoint {

    @Autowired
    Streams streams;

    @GetMapping
    public ResponseEntity<Set<String>> getStreams() {
        return ResponseEntity.ok(streams.getSockets());
    }

    @PostMapping("/{socket}")
    public ResponseEntity<String> addStream(@PathVariable String socket) {
        if (streams.addSocket(socket)) {
            return ResponseEntity.ok()
                                 .build();
        }
        return ResponseEntity.status(400)
                             .build();
    }

    @DeleteMapping("/{socket}")
    public ResponseEntity<String> deleteStream(@PathVariable String socket) {
        if (streams.deleteSocket(socket)) {
            return ResponseEntity.ok()
                                 .build();
        }
        return ResponseEntity.status(400)
                             .build();
    }
}
