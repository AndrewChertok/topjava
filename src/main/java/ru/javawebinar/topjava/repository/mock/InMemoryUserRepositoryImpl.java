package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    @Override
    public User save(User user) {
        log.info("save {}", user);

        if(user.isNew()) user.setId(counter.incrementAndGet());
        return repository.put(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);

        return repository.remove(id) != null ;
    }


    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream().sorted((u1, u2)->{
            if(u1.equals(u2)) return u1.getRegistered().compareTo(u2.getRegistered());
            else return u1.getName().compareTo(u2.getName());
        }).collect(Collectors.toList());
    }


    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(user ->user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
}