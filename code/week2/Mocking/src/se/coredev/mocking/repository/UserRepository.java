package se.coredev.mocking.repository;

import se.coredev.mocking.model.User;

public interface UserRepository {

	User getById(String id);

	void save(User user);
}
