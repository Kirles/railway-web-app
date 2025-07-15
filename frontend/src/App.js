import React, { useState, useEffect, useCallback } from 'react';
import { Plus, Edit, Trash2, Save, X, Train, Calendar, MapPin, Home, Settings, Users, Search, ChevronLeft } from 'lucide-react';

const BASE_URL = 'http://localhost:8081/api';

const api = {
  get: (endpoint) => fetch(`${BASE_URL}${endpoint}`).then(res => res.json()).then(data => ({ data })),
  post: (endpoint, data) => fetch(`${BASE_URL}${endpoint}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  }).then(res => res.json()).then(data => ({ data })),
  put: (endpoint, data) => fetch(`${BASE_URL}${endpoint}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  }).then(res => res.json()).then(data => ({ data })),
  delete: (endpoint) => fetch(`${BASE_URL}${endpoint}`, {
    method: 'DELETE'
  }).then(res => {
    if (!res.ok) {
      throw new Error(`Failed to delete: ${res.status} ${res.statusText}`);
    }
    return { data: {} };
  })
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
      <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
        {/* Navigation */}
        <nav className="bg-white shadow-lg border-b border-slate-200">
          <div className="container mx-auto px-6 py-4">
            <div className="flex justify-between items-center">
              <div className="flex items-center space-x-4">
                <div className="flex items-center space-x-3">
                  <div className="p-2 bg-blue-600 rounded-xl">
                    <Train className="h-6 w-6 text-white" />
                  </div>
                  <h1 className="text-2xl font-bold text-slate-800">Railway Admin System</h1>
                </div>
              </div>
              <div className="flex items-center space-x-2">
                <button
                    onClick={() => navigate('home')}
                    className={`flex items-center space-x-2 px-4 py-2 rounded-lg transition-all duration-200 ${
                        currentPage === 'home'
                            ? 'bg-blue-100 text-blue-700 shadow-sm'
                            : 'text-slate-600 hover:bg-slate-100'
                    }`}
                >
                  <Home className="h-4 w-4" />
                  <span className="font-medium">Home</span>
                </button>
              </div>
            </div>
          </div>
        </nav>

        {/* Main Content */}
        <main className="container mx-auto px-6 py-8">
          {currentPage === 'home' && <HomePage navigate={navigate} />}
          {currentPage === 'admin' && !currentResource && <AdminPanel navigate={navigate} />}
          {currentPage === 'admin' && currentResource && <AdminResourcePage resource={currentResource} navigate={navigate} />}
        </main>
      </div>
  );
};

// Home Page
const HomePage = ({ navigate }) => {
  return (
      <div className="text-center py-16">
        <div className="max-w-2xl mx-auto">
          <div className="mb-8">
            <div className="inline-flex items-center justify-center w-20 h-20 bg-blue-100 rounded-full mb-6">
              <Train className="h-10 w-10 text-blue-600" />
            </div>
            <h2 className="text-4xl font-bold text-slate-800 mb-4">Welcome to Railway Admin System</h2>
            <p className="text-xl text-slate-600">Manage all railway system resources efficiently and effectively</p>
          </div>

          <div className="bg-white rounded-2xl shadow-lg p-8 border border-slate-200">
            <h3 className="text-2xl font-semibold text-slate-800 mb-4">Get Started</h3>
            <p className="text-slate-600 mb-6">Access the comprehensive admin panel to manage bookings, trains, routes, passengers, and more.</p>

            <button
                onClick={() => navigate('admin')}
                className="inline-flex items-center space-x-3 bg-blue-600 text-white px-8 py-4 rounded-xl hover:bg-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
            >
              <Settings className="h-6 w-6" />
              <span className="text-lg font-medium">Open Admin Panel</span>
            </button>
          </div>
        </div>
      </div>
  );
};

// Admin Panel
const AdminPanel = ({ navigate }) => {
  const resources = [
    { name: 'bookings', label: 'Bookings', icon: Calendar, color: 'bg-emerald-500', lightColor: 'bg-emerald-50' },
    { name: 'cars', label: 'Cars', icon: Train, color: 'bg-blue-500', lightColor: 'bg-blue-50' },
    { name: 'cities', label: 'Cities', icon: MapPin, color: 'bg-purple-500', lightColor: 'bg-purple-50' },
    { name: 'passengers', label: 'Passengers', icon: Users, color: 'bg-orange-500', lightColor: 'bg-orange-50' },
    { name: 'routes', label: 'Routes', icon: MapPin, color: 'bg-red-500', lightColor: 'bg-red-50' },
    { name: 'routeStations', label: 'Route Stations', icon: MapPin, color: 'bg-indigo-500', lightColor: 'bg-indigo-50' },
    { name: 'seats', label: 'Seats', icon: Train, color: 'bg-teal-500', lightColor: 'bg-teal-50' },
    { name: 'stations', label: 'Stations', icon: MapPin, color: 'bg-cyan-500', lightColor: 'bg-cyan-50' },
    { name: 'trains', label: 'Trains', icon: Train, color: 'bg-green-500', lightColor: 'bg-green-50' },
    { name: 'trips', label: 'Trips', icon: Calendar, color: 'bg-yellow-500', lightColor: 'bg-yellow-50' },
    { name: 'tripSchedules', label: 'Trip Schedules', icon: Calendar, color: 'bg-pink-500', lightColor: 'bg-pink-50' },
  ];

  return (
      <div className="py-8">
        <div className="text-center mb-12">
          <h2 className="text-4xl font-bold text-slate-800 mb-4">Admin Panel</h2>
          <p className="text-xl text-slate-600">Choose a resource to manage</p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {resources.map((resource) => {
            const IconComponent = resource.icon;
            return (
                <button
                    key={resource.name}
                    onClick={() => navigate('admin', resource.name)}
                    className="group bg-white p-6 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-slate-200 hover:border-slate-300 transform hover:-translate-y-1"
                >
                  <div className={`inline-flex items-center justify-center w-16 h-16 ${resource.lightColor} rounded-xl mb-4 group-hover:scale-110 transition-transform duration-300`}>
                    <IconComponent className={`h-8 w-8 ${resource.color.replace('bg-', 'text-')}`} />
                  </div>
                  <h3 className="text-xl font-semibold text-slate-800 mb-2">{resource.label}</h3>
                  <p className="text-sm text-slate-500">Manage {resource.label.toLowerCase()}</p>
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
  const [searchTerm, setSearchTerm] = useState('');

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
      bookings: ['trip', 'passenger', 'seat', 'bookingDate', 'price'],
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

  const filteredData = data.filter(item => {
    const searchFields = getFieldsForResource();
    return searchFields.some(field =>
        item[field]?.toString().toLowerCase().includes(searchTerm.toLowerCase())
    );
  });

  if (loading) {
    return (
        <div className="flex items-center justify-center py-16">
          <div className="flex items-center space-x-3">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span className="text-slate-600 font-medium">Loading...</span>
          </div>
        </div>
    );
  }

  if (error) {
    return (
        <div className="bg-red-50 border border-red-200 rounded-xl p-6 text-center">
          <div className="text-red-600 font-medium">Error: {error}</div>
        </div>
    );
  }

  return (
      <div className="py-8">
        {/* Header */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <div className="flex items-center space-x-4">
              <button
                  onClick={() => navigate('admin')}
                  className="flex items-center space-x-2 text-blue-600 hover:text-blue-800 font-medium transition-colors duration-200"
              >
                <ChevronLeft className="h-5 w-5" />
                <span>Back to Admin Panel</span>
              </button>
            </div>
            <button
                onClick={() => setShowForm(true)}
                className="flex items-center space-x-2 bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              <Plus className="h-5 w-5" />
              <span>Add New</span>
            </button>
          </div>

          <div className="flex items-center justify-between">
            <h2 className="text-3xl font-bold text-slate-800 capitalize">{resource.replace(/([A-Z])/g, ' $1').toLowerCase()}</h2>

            {/* Search */}
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-slate-400" />
              <input
                  type="text"
                  placeholder="Search..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 pr-4 py-2 border border-slate-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
          </div>
        </div>

        {/* Form */}
        {showForm && (
            <div className="mb-8">
              <GenericForm
                  fields={getFieldsForResource()}
                  initialData={{}}
                  onSubmit={handleCreate}
                  onCancel={() => setShowForm(false)}
                  title={`Add New ${resource.replace(/([A-Z])/g, ' $1').toLowerCase()}`}
              />
            </div>
        )}

        {/* Table */}
        <div className="bg-white rounded-2xl shadow-lg overflow-hidden border border-slate-200">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-slate-200">
              <thead className="bg-slate-50">
              <tr>
                <th className="px-6 py-4 text-left text-xs font-semibold text-slate-600 uppercase tracking-wider border-r border-slate-200">
                  ID
                </th>
                {getFieldsForResource().map((field, index) => (
                    <th key={field} className={`px-6 py-4 text-left text-xs font-semibold text-slate-600 uppercase tracking-wider ${index < getFieldsForResource().length - 1 ? 'border-r border-slate-200' : ''}`}>
                      {field.replace(/([A-Z])/g, ' $1').toLowerCase().replace(/^./, str => str.toUpperCase())}
                    </th>
                ))}
                <th className="px-6 py-4 text-left text-xs font-semibold text-slate-600 uppercase tracking-wider border-l border-slate-200">
                  Actions
                </th>
              </tr>
              </thead>
              <tbody className="bg-white divide-y divide-slate-100">
              {filteredData.map((item, rowIndex) => (
                  <tr key={item.id} className={`hover:bg-slate-50 transition-colors duration-150 ${rowIndex % 2 === 0 ? 'bg-white' : 'bg-slate-25'}`}>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-slate-900 border-r border-slate-100">
                      {item.id}
                    </td>
                    {getFieldsForResource().map((field, colIndex) => (
                        <td key={field} className={`px-6 py-4 whitespace-nowrap text-sm text-slate-700 ${colIndex < getFieldsForResource().length - 1 ? 'border-r border-slate-100' : ''}`}>
                          {editingItem === item.id ? (
                              <input
                                  type="text"
                                  value={formData[field] || ''}
                                  onChange={(e) => setFormData({...formData, [field]: e.target.value})}
                                  className="w-full px-3 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                              />
                          ) : (
                              <span className="block">
                          {typeof item[field] === 'boolean' ? (
                              <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${item[field] ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                              {item[field] ? 'Yes' : 'No'}
                            </span>
                          ) : (
                              item[field]
                          )}
                        </span>
                          )}
                        </td>
                    ))}
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-slate-700 border-l border-slate-100">
                      {editingItem === item.id ? (
                          <div className="flex items-center space-x-2">
                            <button
                                onClick={() => handleUpdate(item.id, formData)}
                                className="p-2 text-green-600 hover:text-green-800 hover:bg-green-50 rounded-lg transition-colors duration-200"
                            >
                              <Save className="h-4 w-4" />
                            </button>
                            <button
                                onClick={() => {
                                  setEditingItem(null);
                                  setFormData({});
                                }}
                                className="p-2 text-slate-600 hover:text-slate-800 hover:bg-slate-50 rounded-lg transition-colors duration-200"
                            >
                              <X className="h-4 w-4" />
                            </button>
                          </div>
                      ) : (
                          <div className="flex items-center space-x-2">
                            <button
                                onClick={() => {
                                  setEditingItem(item.id);
                                  setFormData(item);
                                }}
                                className="p-2 text-blue-600 hover:text-blue-800 hover:bg-blue-50 rounded-lg transition-colors duration-200"
                            >
                              <Edit className="h-4 w-4" />
                            </button>
                            <button
                                onClick={() => handleDelete(item.id)}
                                className="p-2 text-red-600 hover:text-red-800 hover:bg-red-50 rounded-lg transition-colors duration-200"
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

          {filteredData.length === 0 && (
              <div className="text-center py-12">
                <div className="text-slate-500">No items found</div>
              </div>
          )}
        </div>
      </div>
  );
};

// Generic Form Component
const GenericForm = ({ fields, initialData, onSubmit, onCancel, title }) => {
  const [formData, setFormData] = useState(initialData);

  const handleSubmit = (e) => {
    if (e && e.preventDefault) {
      e.preventDefault();
    }
    onSubmit(formData);
  };

  const handleInputChange = (field, value) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  return (
      <div className="bg-white p-8 rounded-2xl shadow-lg border border-slate-200">
        <h3 className="text-2xl font-semibold text-slate-800 mb-6">{title}</h3>
        <div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {fields.map(field => (
                <div key={field} className="space-y-2">
                  <label className="block text-sm font-medium text-slate-700">
                    {field.replace(/([A-Z])/g, ' $1').toLowerCase().replace(/^./, str => str.toUpperCase())}
                  </label>
                  {field === 'isAvailable' ? (
                      <select
                          value={formData[field] || false}
                          onChange={(e) => handleInputChange(field, e.target.value === 'true')}
                          className="w-full px-4 py-3 border border-slate-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors duration-200"
                      >
                        <option value={true}>Yes</option>
                        <option value={false}>No</option>
                      </select>
                  ) : (
                      <input
                          type={field.includes('Id') || field.includes('Minutes') || field.includes('Km') || field.includes('Seats') ? 'number' : 'text'}
                          value={formData[field] || ''}
                          onChange={(e) => handleInputChange(field, e.target.value)}
                          className="w-full px-4 py-3 border border-slate-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors duration-200"
                          required
                      />
                  )}
                </div>
            ))}
          </div>

          <div className="flex items-center space-x-4 mt-8">
            <button
                onClick={handleSubmit}
                className="px-6 py-3 bg-blue-600 text-white rounded-xl hover:bg-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl font-medium"
            >
              Save
            </button>
            <button
                onClick={onCancel}
                className="px-6 py-3 bg-slate-500 text-white rounded-xl hover:bg-slate-600 transition-all duration-200 shadow-lg hover:shadow-xl font-medium"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
  );
};

export default App;