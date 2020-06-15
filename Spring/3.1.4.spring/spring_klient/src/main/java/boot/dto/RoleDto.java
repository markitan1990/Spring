package boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements GrantedAuthority{
    private Long id;
    private String roleName;

    public RoleDto(String roleName){
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

}
