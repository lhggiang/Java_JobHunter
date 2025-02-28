package vn.hoanggiang.jobhunter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String jobName;
    private double minSalary;
    private double maxSalary;
    private String level;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
