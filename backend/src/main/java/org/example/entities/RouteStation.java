package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "route_stations", schema = "railway")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "station_order", nullable = false)
    private Integer stationOrder;

    @Column(name = "stop_duration_minutes")
    private Integer stopDurationMinutes;
}
