// 代码生成时间: 2025-10-12 00:00:35
@Singleton
public class FirewallRuleService {

    private final FirewallRuleRepository firewallRuleRepository;

    // Constructor injection for the repository
    public FirewallRuleService(FirewallRuleRepository firewallRuleRepository) {
        this.firewallRuleRepository = firewallRuleRepository;
    }

    /**
     * Retrieves all firewall rules.
     * @return a list of firewall rules.
     */
    public List<FirewallRule> listAllRules() {
        return firewallRuleRepository.findAll();
    }

    /**
     * Saves a new firewall rule.
     * @param rule the firewall rule to save.
     * @return the saved firewall rule.
     */
    public FirewallRule saveRule(FirewallRule rule) {
        return firewallRuleRepository.save(rule);
    }

    /**
     * Deletes a firewall rule by its ID.
     * @param id the ID of the rule to delete.
     * @throws FirewallRuleNotFoundException if the rule is not found.
     */
    public void deleteRule(Long id) {
        FirewallRule rule = firewallRuleRepository.findById(id).orElseThrow(
            () -> new FirewallRuleNotFoundException("Firewall Rule not found with ID: " + id)
        );
        firewallRuleRepository.delete(rule);
    }

    /**
     * Fires an event to notify other components about a change in firewall rules.
     * @param rule the firewall rule that was changed.
     */
    public void fireEvent(FirewallRule rule) {
        // Event publishing logic goes here
        // For example: eventPublisher.publish(rule);
    }
}

/**
 * Repository interface for firewall rules.
 */
public interface FirewallRuleRepository extends JpaRepository<FirewallRule, Long> {

    // Custom repository methods can be added here
}

/**
 * Entity representing a firewall rule.
 */
@Entity
@Table(name = "firewall_rules")
public class FirewallRule {

    @Id
    private Long id;

    private String name;
    private String ipRange;
    private boolean enabled;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

/**
 * Custom exception for firewall rule not found.
 */
public class FirewallRuleNotFoundException extends RuntimeException {

    public FirewallRuleNotFoundException(String message) {
        super(message);
    }
}
