import React, { useState, useEffect, useCallback } from 'react';
import { Plus, Edit, Trash2, Save, X, Train, Users, Calendar, MapPin, Home, Settings, Search } from 'lucide-react';

import axios from 'axios';

const BASE_URL = 'http://localhost:8081/api';

const api = {
  get: (endpoint) => axios.get(`${BASE_URL}${endpoint}`),
  post: (endpoint, data) => axios.post(`${BASE_URL}${endpoint}`, data),
  put: (endpoint, data) => axios.put(`${BASE_URL}${endpoint}`, data),
  delete: (endpoint) => axios.delete(`${BASE_URL}${endpoint}`)
};

// Main App Component
const App = () => {
  const [currentPage, setCurrentPage] = useState('home');
  const [currentResource, setCurrentResource] = useState(null);

  const navigate = (page, resource = null) => {
    setCurrentPage(page);
    setCurrentResource(resource);
  };

  return (
      <div className="min-h-screen bg-gray-50">
        <nav className="bg-blue-600 text-white p-4">
          <div className="container mx-auto flex justify-between items-center">
            <h1 className="text-2xl font-bold flex items-center gap-2">
              <Train className="h-6 w-6" />
              Railway System
            </h1>
            <div className="flex gap-4">
              <button
                  onClick={() => navigate('home')}
                  className={`hover:bg-blue-700 px-3 py-2 rounded flex items-center gap-2 ${currentPage === 'home' ? 'bg-blue-700' : ''}`}
              >
                <Home className="h-4 w-4" />
                Home
              </button>
              <button
                  onClick={() => navigate('admin')}
                  className={`hover:bg-blue-700 px-3 py-2 rounded flex items-center gap-2 ${currentPage === 'admin' ? 'bg-blue-700' : ''}`}
              >
                <Settings className="h-4 w-4" />
                Admin Panel
              </button>
              <button
                  onClick={() => navigate('user')}
                  className={`hover:bg-blue-700 px-3 py-2 rounded flex items-center gap-2 ${currentPage === 'user' ? 'bg-blue-700' : ''}`}
              >
                <Search className="h-4 w-4" />
                User Booking
              </button>
            </div>
          </div>
        </nav>

        <div className="container mx-auto">
          {currentPage === 'home' && <HomePage navigate={navigate} />}
          {currentPage === 'admin' && !currentResource && <AdminPanel navigate={navigate} />}
          {currentPage === 'admin' && currentResource && <AdminResourcePage resource={currentResource} navigate={navigate} />}
          {currentPage === 'user' && <UserBookingPage />}
        </div>
      </div>
  );
};

// Home Page
const HomePage = ({ navigate }) => {
  return (
      <div className="p-8 text-center">
        <h2 className="text-4xl font-bold mb-8">Welcome to Railway Booking System</h2>
        <div className="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
          <button
              onClick={() => navigate('admin')}
              className="bg-blue-600 text-white p-8 rounded-lg hover:bg-blue-700 transition-colors"
          >
            <Settings className="h-12 w-12 mx-auto mb-4" />
            <h3 className="text-2xl font-bold mb-4">Admin Panel</h3>
            <p>Manage bookings, trains, routes, and all system resources</p>
          </button>
          <button
              onClick={() => navigate('user')}
              className="bg-green-600 text-white p-8 rounded-lg hover:bg-green-700 transition-colors"
          >
            <Search className="h-12 w-12 mx-auto mb-4" />
            <h3 className="text-2xl font-bold mb-4">Book a Trip</h3>
            <p>Search and book available seats for your journey</p>
          </button>
        </div>
      </div>
  );
};

// Admin Panel
const AdminPanel = ({ navigate }) => {
  const resources = [
    { name: 'bookings', label: 'Bookings', icon: Calendar },
    { name: 'cars', label: 'Cars', icon: Train },
    { name: 'cities', label: 'Cities', icon: MapPin },
    { name: 'passengers', label: 'Passengers', icon: Users },
    { name: 'routes', label: 'Routes', icon: MapPin },
    { name: 'routeStations', label: 'Route Stations', icon: MapPin },
    { name: 'seats', label: 'Seats', icon: Train },
    { name: 'stations', label: 'Stations', icon: MapPin },
    { name: 'trains', label: 'Trains', icon: Train },
    { name: 'trips', label: 'Trips', icon: Calendar },
    { name: 'tripSchedules', label: 'Trip Schedules', icon: Calendar },
  ];

  return (
      <div className="p-8">
        <h2 className="text-3xl font-bold mb-8">Admin Panel</h2>
        <div className="grid md:grid-cols-3 lg:grid-cols-4 gap-6">
          {resources.map((resource) => {
            const IconComponent = resource.icon;
            return (
                <button
                    key={resource.name}
                    onClick={() => navigate('admin', resource.name)}
                    className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow border text-left"
                >
                  <IconComponent className="h-8 w-8 text-blue-600 mb-4" />
                  <h3 className="text-xl font-semibold">{resource.label}</h3>
                  <p className="text-gray-600 mt-2">Manage {resource.label.toLowerCase()}</p>
                </button>
            );
          })}
        </div>
      </div>
  );
};

// Generic Admin Resource Page
const AdminResourcePage = ({ resource, navigate }) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingItem, setEditingItem] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({});

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      const response = await api.get(`/${resource}`);
      setData(response.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [resource]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleCreate = async (item) => {
    try {
      await api.post(`/${resource}`, item);
      await fetchData();
      setShowForm(false);
      setFormData({});
    } catch (err) {
      setError(err.message);
    }
  };

  const handleUpdate = async (id, item) => {
    try {
      await api.put(`/${resource}/${id}`, item);
      await fetchData();
      setEditingItem(null);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      try {
        await api.delete(`/${resource}/${id}`);
        await fetchData();
      } catch (err) {
        setError(err.message);
      }
    }
  };

  const getFieldsForResource = () => {
    const fieldsMap = {
      bookings: ['trip', 'passenger', 'seat', 'bookingDate'],
      cars: ['train', 'carType', 'carNumber', 'totalSeats'],
      cities: ['name', 'region'],
      passengers: ['firstName', 'lastName', 'documentNumber', 'phone', 'birthDate'],
      routes: ['name', 'departureStation', 'arrivalStation', 'route-distanceKm', 'durationMinutes'],
      routeStations: ['route', 'station', 'stationOrder', 'stopDurationMinutes'],
      seats: ['car', 'seatNumber', 'isAvailable'],
      stations: ['name', 'code', 'city', 'address'],
      trains: ['number', 'name', 'trainType', 'totalSeats'],
      trips: ['train', 'route', 'departureDate', 'departureTime', 'arrivalDate', 'arrivalTime', 'availableSeats', 'basePrice'],
      tripSchedules: ['trip', 'station', 'arrivalTime', 'departureTime', 'stationOrder'],
    };
    return fieldsMap[resource] || ['name'];
  };

  if (loading) return <div className="p-8">Loading...</div>;
  if (error) return <div className="p-8 text-red-600">Error: {error}</div>;

  return (
      <div className="p-8">
        <div className="flex justify-between items-center mb-8">
          <div className="flex items-center gap-4">
            <button
                onClick={() => navigate('admin')}
                className="text-blue-600 hover:text-blue-800 flex items-center gap-2"
            >
              ← Back to Admin Panel
            </button>
            <h2 className="text-3xl font-bold capitalize">{resource.replace('-', ' ')}</h2>
          </div>
          <button
              onClick={() => setShowForm(true)}
              className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center gap-2"
          >
            <Plus className="h-4 w-4" />
            Add New
          </button>
        </div>

        {showForm && (
            <GenericForm
                fields={getFieldsForResource()}
                initialData={{}}
                onSubmit={handleCreate}
                onCancel={() => setShowForm(false)}
                title={`Add New ${resource.replace('-', ' ')}`}
            />
        )}

        <div className="bg-white rounded-lg shadow overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                ID
              </th>
              {getFieldsForResource().map(field => (
                  <th key={field} className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    {field.replace(/([A-Z])/g, ' $1').toLowerCase()}
                  </th>
              ))}
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Actions
              </th>
            </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
            {data.map((item) => (
                <tr key={item.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {item.id}
                  </td>
                  {getFieldsForResource().map(field => (
                      <td key={field} className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {editingItem === item.id ? (
                            <input
                                type="text"
                                value={formData[field] || ''}
                                onChange={(e) => setFormData({...formData, [field]: e.target.value})}
                                className="w-full px-2 py-1 border rounded"
                            />
                        ) : (
                            typeof item[field] === 'boolean' ? (item[field] ? 'Yes' : 'No') : item[field]
                        )}
                      </td>
                  ))}
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {editingItem === item.id ? (
                        <div className="flex gap-2">
                          <button
                              onClick={() => handleUpdate(item.id, formData)}
                              className="text-green-600 hover:text-green-800"
                          >
                            <Save className="h-4 w-4" />
                          </button>
                          <button
                              onClick={() => {
                                setEditingItem(null);
                                setFormData({});
                              }}
                              className="text-gray-600 hover:text-gray-800"
                          >
                            <X className="h-4 w-4" />
                          </button>
                        </div>
                    ) : (
                        <div className="flex gap-2">
                          <button
                              onClick={() => {
                                setEditingItem(item.id);
                                setFormData(item);
                              }}
                              className="text-blue-600 hover:text-blue-800"
                          >
                            <Edit className="h-4 w-4" />
                          </button>
                          <button
                              onClick={() => handleDelete(item.id)}
                              className="text-red-600 hover:text-red-800"
                          >
                            <Trash2 className="h-4 w-4" />
                          </button>
                        </div>
                    )}
                  </td>
                </tr>
            ))}
            </tbody>
          </table>
        </div>
      </div>
  );
};

// Generic Form Component
const GenericForm = ({ fields, initialData, onSubmit, onCancel, title }) => {
  const [formData, setFormData] = useState(initialData);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const handleInputChange = (field, value) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  return (
      <div className="bg-white p-6 rounded-lg shadow-md mb-6">
        <h3 className="text-xl font-semibold mb-4">{title}</h3>
        <div className="grid md:grid-cols-2 gap-4">
          {fields.map(field => (
              <div key={field}>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  {field.replace(/([A-Z])/g, ' $1').toLowerCase()}
                </label>
                {field === 'isAvailable' ? (
                    <select
                        value={formData[field] || false}
                        onChange={(e) => handleInputChange(field, e.target.value === 'true')}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                      <option value={true}>Yes</option>
                      <option value={false}>No</option>
                    </select>
                ) : (
                    <input
                        type={field.includes('Id') || field.includes('Minutes') || field.includes('Km') || field.includes('Seats') ? 'number' : 'text'}
                        value={formData[field] || ''}
                        onChange={(e) => handleInputChange(field, e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                )}
              </div>
          ))}
          <div className="md:col-span-2 flex gap-2">
            <button
                onClick={handleSubmit}
                className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
            >
              Save
            </button>
            <button
                onClick={onCancel}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg hover:bg-gray-600"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
  );
};

// User Booking Page
const UserBookingPage = () => {
  const [seats, setSeats] = useState([]);
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [showBookingForm, setShowBookingForm] = useState(false);
  const [bookingData, setBookingData] = useState({
    passengerName: '',
    email: '',
    phone: ''
  });

  useEffect(() => {
    fetchSeatsAndTrips();
  }, []);

  const fetchSeatsAndTrips = async () => {
    try {
      setLoading(true);
      const [seatsResponse, tripsResponse] = await Promise.all([
        api.get('/seats'),
        api.get('/trips')
      ]);
      setSeats(seatsResponse.data);
      setTrips(tripsResponse.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleBooking = async (e) => {
    e.preventDefault();
    try {
      const bookingPayload = {
        passengerName: bookingData.passengerName,
        seatId: selectedSeat.id,
        status: 'CONFIRMED',
        bookingDate: new Date().toISOString().split('T')[0]
      };

      await api.post('/bookings', bookingPayload);
      alert('Booking successful!');
      setShowBookingForm(false);
      setSelectedSeat(null);
      setBookingData({ passengerName: '', email: '', phone: '' });
      fetchSeatsAndTrips();
    } catch (err) {
      alert('Booking failed: ' + err.message);
    }
  };

  const handleInputChange = (field, value) => {
    setBookingData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  if (loading) return <div className="p-8">Loading...</div>;
  if (error) return <div className="p-8 text-red-600">Error: {error}</div>;

  return (
      <div className="p-8">
        <h2 className="text-3xl font-bold mb-8">Book Your Journey</h2>

        {/* Trips List */}
        <div className="mb-8">
          <h3 className="text-xl font-semibold mb-4">Available Trips</h3>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
            {trips.map(trip => (
                <div key={trip.id} className="bg-white p-4 rounded-lg shadow border">
                  <h4 className="font-semibold text-lg">{trip.route}</h4>
                  <p className="text-gray-600">{trip.departureStation} → {trip.arrivalStation}</p>
                  <p className="text-sm text-gray-500">
                    Distance: {trip.distanceKm} km | Duration: {Math.floor(trip.durationMinutes / 60)}h {trip.durationMinutes % 60}m
                  </p>
                </div>
            ))}
          </div>
        </div>

        {/* Available Seats */}
        <div className="mb-8">
          <h3 className="text-xl font-semibold mb-4">Available Seats</h3>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {seats.filter(seat => seat.isAvailable).map(seat => (
                <div key={seat.id} className="bg-white p-4 rounded-lg shadow border hover:shadow-md transition-shadow">
                  <div className="flex justify-between items-start mb-2">
                    <div>
                      <h4 className="font-semibold">Seat {seat.seatNumber}</h4>
                      <p className="text-sm text-gray-600">Type: {seat.seatType}</p>
                      <p className="text-sm text-gray-600">Car ID: {seat.car}</p>
                    </div>
                    <span className="bg-green-100 text-green-800 text-xs px-2 py-1 rounded">
                  Available
                </span>
                  </div>
                  <button
                      onClick={() => {
                        setSelectedSeat(seat);
                        setShowBookingForm(true);
                      }}
                      className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition-colors"
                  >
                    Book This Seat
                  </button>
                </div>
            ))}
          </div>
        </div>

        {/* Booking Form Modal */}
        {showBookingForm && selectedSeat && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
              <div className="bg-white rounded-lg p-6 max-w-md w-full">
                <h3 className="text-xl font-semibold mb-4">Book Seat {selectedSeat.seatNumber}</h3>
                <div>
                  <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      Passenger Name
                    </label>
                    <input
                        type="text"
                        value={bookingData.passengerName}
                        onChange={(e) => handleInputChange('passengerName', e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                  </div>
                  <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      Email
                    </label>
                    <input
                        type="email"
                        value={bookingData.email}
                        onChange={(e) => handleInputChange('email', e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                  </div>
                  <div className="mb-6">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                      Phone
                    </label>
                    <input
                        type="tel"
                        value={bookingData.phone}
                        onChange={(e) => handleInputChange('phone', e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                  </div>
                  <div className="flex gap-2">
                    <button
                        onClick={handleBooking}
                        className="flex-1 bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors"
                    >
                      Confirm Booking
                    </button>
                    <button
                        onClick={() => {
                          setShowBookingForm(false);
                          setSelectedSeat(null);
                        }}
                        className="flex-1 bg-gray-500 text-white py-2 px-4 rounded-lg hover:bg-gray-600 transition-colors"
                    >
                      Cancel
                    </button>
                  </div>
                </div>
              </div>
            </div>
        )}
      </div>
  );
};

export default App;