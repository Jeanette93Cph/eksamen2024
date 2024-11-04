package dat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Guide
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private Integer yearsOfExperience;


    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Trip> trips = new ArrayList<>();


    public Guide(Integer id, String firstName, String lastName, String email, String phone, Integer yearsOfExperience)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }


    public Guide(String firstName, String lastName, String email, String phone, Integer yearsOfExperience)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }


    public Guide(GuideDTO guideDTO)
    {
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
    }


    public void addTrip(Trip trip)
    {
        if (trip != null) {
            this.trips.add(trip);
        }
    }


}
