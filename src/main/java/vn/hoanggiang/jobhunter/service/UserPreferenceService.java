package vn.hoanggiang.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.hoanggiang.jobhunter.domain.JobCreatedEvent;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.UserPreference;
import vn.hoanggiang.jobhunter.repository.UserPreferenceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;

    public List<User> findUsersInterestedInJob(JobCreatedEvent event) {
        List<UserPreference> preferences = userPreferenceRepository
                .findBySalaryBetweenAndJobNameLikeAndLevelAndLocationLike(
                        event.salary(),
                        event.name(),
                        event.level(),
                        event.location()
                );

        return preferences.stream()
                .map(UserPreference::getUser)
                .collect(Collectors.toList());
    }
}
