public class SubscriptionService {
    public void subscribeUser(User user) {
        user.setSubscribed(true);
        // Further subscription logic
    }

    public void unsubscribeUser(User user) {
        user.setSubscribed(false);
        // Further unsubscription logic
    }
}
