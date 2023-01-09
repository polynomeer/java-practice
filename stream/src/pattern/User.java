package pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class User {
    private final int id;
    private final String name;
    private final String emailAddress;
    private final boolean isVerified;
    private final LocalDateTime createdAt;
    private final List<Integer> friendUserIds;

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.emailAddress = builder.emailAddress;
        this.isVerified = builder.isVerified;
        this.createdAt = builder.createdAt;
        this.friendUserIds = builder.friendUserIds;
    }

    public static Builder builder(int id, String name) {
        return new Builder(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public boolean isVerified() {
        return isVerified;
    }

    public List<Integer> getFriendUserIds() {
        return friendUserIds;
    }

    public static class Builder {
        public int id;
        public String name;
        public String emailAddress;
        public boolean isVerified;
        public LocalDateTime createdAt;
        public List<Integer> friendUserIds = new ArrayList<>();

        private Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder with(Consumer<Builder> consumer) {
            consumer.accept(this);
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", isVerified=" + isVerified +
                ", createdAt=" + createdAt +
                ", friendUserIds=" + friendUserIds +
                '}';
    }
}
