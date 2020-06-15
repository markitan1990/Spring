package boot.dto;

import lombok.Data;

import java.util.List;
@Data
public class ListUsersDto {
    private List<UserDto> userDtoList;
}
