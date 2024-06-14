import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/generate-token")
    public String generateToken(Model model) {
        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Generate the token and store it in the database
        String token = tokenService.generateToken(username);
        model.addAttribute("token", token);

        return "enterToken";
    }

    @PostMapping("/validate-token")
    public String validateToken(@RequestParam String token, Model model) {
        if (tokenService.validateToken(token)) {
            // Set the security context as confirmed
            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/landing";
        } else {
            model.addAttribute("error", "Invalid token");
            return "enterToken";
        }
    }

    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }
}
