-- Создание схемы
CREATE SCHEMA IF NOT EXISTS railway;
SET search_path TO railway;

-- Таблица городов/населенных пунктов
CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    region VARCHAR(255)
);
-- Таблица железнодорожных станций
CREATE TABLE stations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    city_id INTEGER REFERENCES cities(id),
    address TEXT
);

-- Таблица поездов
CREATE TABLE trains (
    id SERIAL PRIMARY KEY,
    number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(255),
    train_type VARCHAR(100) NOT NULL,
    total_seats INTEGER
);

-- Таблица маршрутов
CREATE TABLE routes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    departure_station_id INTEGER REFERENCES stations(id),
    arrival_station_id INTEGER REFERENCES stations(id),
    distance_km INTEGER,
    duration_minutes INTEGER
);

-- Таблица промежуточных станций маршрута
CREATE TABLE route_stations (
    id SERIAL PRIMARY KEY,
    route_id INTEGER REFERENCES routes(id) ON DELETE CASCADE,
    station_id INTEGER REFERENCES stations(id),
    station_order INTEGER NOT NULL,
    stop_duration_minutes INTEGER DEFAULT 5,
    UNIQUE(route_id, station_id)
);

-- Таблица рейсов
CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    train_id INTEGER REFERENCES trains(id),
    route_id INTEGER REFERENCES routes(id),
    departure_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_date DATE NOT NULL,
    arrival_time TIME NOT NULL,
    available_seats INTEGER,
    base_price DECIMAL(10, 2)
);

-- Таблица вагонов
CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    train_id INTEGER REFERENCES trains(id),
    car_type VARCHAR(100) NOT NULL,
    car_number INTEGER NOT NULL,
    total_seats INTEGER NOT NULL,
    UNIQUE(train_id, car_number)
);

-- Таблица мест в вагонах
CREATE TABLE seats (
    id SERIAL PRIMARY KEY,
    car_id INTEGER REFERENCES cars(id),
    seat_number INTEGER NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    UNIQUE(car_id, seat_number)
);

-- Таблица пассажиров
CREATE TABLE passengers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    document_number VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    birth_date DATE
);

-- Таблица бронирований/билетов
CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    trip_id INTEGER REFERENCES trips(id),
    passenger_id INTEGER REFERENCES passengers(id),
    seat_id INTEGER REFERENCES seats(id),
    booking_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10, 2) NOT NULL
);

-- Таблица расписания по станциям
CREATE TABLE trip_schedules (
    id SERIAL PRIMARY KEY,
    trip_id INTEGER REFERENCES trips(id),
    station_id INTEGER REFERENCES stations(id),
    arrival_time TIME,
    departure_time TIME,
    station_order INTEGER,
    UNIQUE(trip_id, station_id)
);

-- Индексы для оптимизации запросов
CREATE INDEX idx_trips_departure_date ON trips(departure_date);
CREATE INDEX idx_trips_route_id ON trips(route_id);
CREATE INDEX idx_trips_train_id ON trips(train_id);
CREATE INDEX idx_bookings_trip_id ON bookings(trip_id);
CREATE INDEX idx_bookings_passenger_id ON bookings(passenger_id);
CREATE INDEX idx_stations_code ON stations(code);
CREATE INDEX idx_trip_schedules_trip_id ON trip_schedules(trip_id);
CREATE INDEX idx_route_stations_order ON route_stations(route_id, station_order);