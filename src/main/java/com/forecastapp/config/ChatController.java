@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ChatController {
    @PostMapping("/api/ai/chat")
    public ResponseEntity<?> handleChat(@RequestBody String message) {
        // Handle request
        return ResponseEntity.ok("Response");
    }
}