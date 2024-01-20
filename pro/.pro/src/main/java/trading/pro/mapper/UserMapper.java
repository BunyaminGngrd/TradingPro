package trading.pro.mapper;

import org.mapstruct.Mapper;
import trading.pro.dto.SignUpRequest;
import trading.pro.dto.User;
import trading.pro.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    User toDto(UserEntity userEntity);

    List<UserEntity> toUserEntityList(List<User> userList);

    List<User> toUserList(List<UserEntity> userEntityList);

    UserEntity fromSignUpRequest(SignUpRequest signUpRequest);
}
