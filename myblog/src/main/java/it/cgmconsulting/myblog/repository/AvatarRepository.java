package it.cgmconsulting.myblog.repository;

import it.cgmconsulting.myblog.entity.AvatarId;
import it.cgmconsulting.myblog.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, AvatarId> {
}
