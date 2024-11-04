package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Guide;
import dat.entities.Trip;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class GuideDTO
{
    @JsonProperty("id")
    private Integer id;

    @JsonIgnore
    private String firstName;

    @JsonIgnore
    private String lastName;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String phone;

    @JsonIgnore
    private Integer yearsOfExperience;

    @JsonProperty("totalPrice")
    private Double totalPrice;


    public GuideDTO(Integer id, String firstName, String lastName, String email, String phone, Integer yearsOfExperience)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }


    public GuideDTO(String firstName, String lastName, String email, String phone, Integer yearsOfExperience)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }


    public GuideDTO(Integer id, Double totalPrice)
    {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public GuideDTO(Guide guide)
    {
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
    }



}
