package io.mgk.threadpool.AsyncFutureStudy.repository;

import io.mgk.threadpool.AsyncFutureStudy.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDtlsRepository extends JpaRepository<UserDtls, Long> {
}
