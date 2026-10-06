package com.pfc.thindesk.repository;

import com.pfc.thindesk.model.Usuario;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryUsuario extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    default Optional<Usuario> buscarPorEmail(String email) {
        return findByEmail(email);
    }

    default boolean verificarEmailExistente(String email) {
        return existsByEmail(email);
    }
}
