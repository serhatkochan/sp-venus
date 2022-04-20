package mahrek.spVenus.core.security.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mahrek.spVenus.core.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsManager implements UserDetails {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsManager(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public String getRole(){return user.getRole();}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UserDetailsManager user = (UserDetailsManager) o;
            return this.getUserId().equals(user.getUserId());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getUserId()});
    }
}
