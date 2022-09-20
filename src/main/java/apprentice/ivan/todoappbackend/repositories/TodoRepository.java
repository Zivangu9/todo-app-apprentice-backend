package apprentice.ivan.todoappbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;

@Repository
public interface TodoRepository extends KeyValueRepository<Todo, Integer> {
    public Page<Todo> findByDoneAndNameLikeAndPriority(Boolean done, String name, Priorities priority,
            Pageable pageable);

    public Page<Todo> findByDoneAndNameLike(Boolean done, String name, Pageable pageable);

    public Page<Todo> findByDoneAndPriority(Boolean done, Priorities priority, Pageable pageable);

    public Page<Todo> findByNameLikeAndPriority(String name, Priorities priority, Pageable pageable);

    public Page<Todo> findByDone(Boolean done, Pageable pageable);

    public Page<Todo> findByNameLike(String name, Pageable pageable);

    public Page<Todo> findByPriority(Priorities priority, Pageable pageable);
}
