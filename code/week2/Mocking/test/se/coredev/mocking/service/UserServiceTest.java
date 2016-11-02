package se.coredev.mocking.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.coredev.mocking.model.User;
import se.coredev.mocking.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private UserRepository userRepository; // = mock(UserRepository.class);

	@Mock
	private IdGenerator idGenerator; // = mock(IdGenerator.class);

	@InjectMocks
	private UserService service; // = new UserService(userRepository, idGenerator);

	@Test
	public void canGetUser() {

		String userId = "1001";
		String username = "master";
		String password = "secret";

		when(userRepository.getById(userId)).thenReturn(new User(userId, username, password));

		User user = service.getUser(userId);

		assertThat(user, is(notNullValue()));
		assertThat(userId, is(user.getId()));
		assertThat(username, is(user.getUsername()));
		assertThat(password, is(user.getPassword()));
	}

	@Test
	public void willThrowExceptionWhenUserCouldNotBeFound() {

		String nonExistingUserId = "1001";
		String exceptionMessage = "Could not find user with id:" + nonExistingUserId;

		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(exceptionMessage);

		when(userRepository.getById(nonExistingUserId)).thenThrow(new RuntimeException(exceptionMessage));

		service.getUser(nonExistingUserId);
	}

	@Test
	public void willNotAcceptTooShortPassword() {

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Password must be at least 6 charcters long");

		String tooShortPassword = "123";
		service.createUser("master", tooShortPassword);
	}

	@Test
	public void canCreateUser() {

		String userId = "1001";
		String username = "master";
		String password = "secret";

		when(idGenerator.generateId()).thenReturn(userId);

		User user = service.createUser(username, password);

		assertThat(user, is(notNullValue()));
		assertThat(user.getId(), is(userId));
		assertThat(user.getUsername(), is(username));
		assertThat(user.getPassword(), is(password));

		verify(userRepository).save(user);
	}

}
