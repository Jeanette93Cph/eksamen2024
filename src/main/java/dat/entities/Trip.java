package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Trip
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double latitude;
    private Double longitude;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;


    @ManyToOne
    @JoinColumn(name = "guide_id")
    @JsonBackReference
    private Guide guide;

    public Trip(Integer id, String name, LocalDateTime startTime, LocalDateTime endTime, Double latitude, Double longitude, Double price, Category category)
    {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
    }

    public Trip(String name, LocalDateTime startTime, LocalDateTime endTime, Double latitude, Double longitude, Double price, Category category)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
    }

    public Trip(String name, LocalDateTime startTime, LocalDateTime endTime, Double latitude, Double longitude, Double price, Category category, Guide guide)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public Trip(TripDTO tripDTO)
    {
        this.id = tripDTO.getId();
        this.name = tripDTO.getName();
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.latitude = tripDTO.getLatitude();
        this.longitude = tripDTO.getLongitude();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
    }




}
