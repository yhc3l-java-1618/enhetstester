package se.coredev.mocking.service;

import se.coredev.mocking.model.User;
import se.coredev.mocking.repository.UserRepository;

public final class UserService {

	private final UserRepository userRepository;
	private final IdGenerator idGenerator;

	public UserService(UserRepository userRepository, IdGenerator idGenerator) {
		this.userRepository = userRepository;
		this.idGenerator = idGenerator;
	}

	public User createUser(String username, String password) {

		if (password.length() < 6) { throw new IllegalArgumentException("Password must be at least 6 charcters long"); }

		User user = new User(idGenerator.generateId(), username, password);
		userRepository.save(user);
		return user;
	}

	public User getUser(String id) {
		return userRepository.getById(id);
	}

}
