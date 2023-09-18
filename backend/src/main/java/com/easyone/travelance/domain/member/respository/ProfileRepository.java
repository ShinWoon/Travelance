package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
