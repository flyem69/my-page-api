package my.page.api.authentication.dao;

import my.page.api.authentication.models.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Nullable User findByEmail(@NotNull String email);

    @Nullable User findByName(@NotNull String name);

    @NotNull List<User> findByEmailOrName(@NotNull String email, @NotNull String name);
}
