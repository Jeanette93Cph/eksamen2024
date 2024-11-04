package dat.dtos;

import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class TripDTO
{
    private Integer id;

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double latitude;
    private Double longitude;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Guide guide;

    public TripDTO(Integer id, String name, LocalDateTime startTime, LocalDateTime endTime, Double latitude, Double longitude, Double price, Category category)
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


    public TripDTO(String name, LocalDateTime startTime, LocalDateTime endTime, Double latitude, Double longitude, Double price, Category category)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
    }

    public TripDTO(Trip trip)
    {
        this.id = trip.getId();
        this.name = trip.getName();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.latitude = trip.getLatitude();
        this.longitude = trip.getLongitude();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guide = trip.getGuide();
    }

}
