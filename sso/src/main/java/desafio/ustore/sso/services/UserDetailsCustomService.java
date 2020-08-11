package desafio.ustore.sso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import desafio.ustore.sso.models.AuthUserDetail;
import desafio.ustore.sso.repository.UserRepository;


@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return new User("foo", "foo", new ArrayList<>());
		
		Optional<desafio.ustore.sso.models.User> opUser = userRepository.findByUsername(username);
		if(opUser.isPresent()) {
			AuthUserDetail authUserDetail = new AuthUserDetail(opUser.get());
			return authUserDetail;		
		}
		
		return null;
	}

}
