package lazarbosnjak.kucniBudzet.model.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.model.dto.UserDTO;
import lazarbosnjak.kucniBudzet.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDtoToUser implements Converter<UserDTO, User>{

	private final UserService userService;
	
	@Override
	public User convert(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
