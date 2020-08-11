package desafio.ustore.sso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import desafio.ustore.sso.exception.BusinessException;
import desafio.ustore.sso.models.User;
import desafio.ustore.sso.repository.UserRepository;
import desafio.ustore.sso.validators.UserValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserValidator userValidator;

	public User create(User user) {
		userValidator.userFull(user);
		return userRepository.save(user);
	}

	public User edit(Integer id, User user) {
		Optional<User> userModified = userRepository.findById(id);
		
		if(userModified.isPresent()) {
			User userSave = userModified.get();
			BeanUtils.copyProperties(user, userSave);			
			userValidator.userFull(userSave);
			return userRepository.save(user);
		}
		
		throw new BusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário inexistente");
	}

	public Boolean delete(Integer id) {
		try {
			userRepository.deleteById(id);
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário inexistente");
		}

		return true;
	}

	public User load(Integer id) {
		return userRepository.getOne(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
}
